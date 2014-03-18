package com.demo.biz.aliexpress;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.LeiMuDao;
import com.demo.dao.OrderTableDao;
import com.demo.dao.ZhangHaoDao;
import com.demo.dao.Courier.YunFeiTableDao;
import com.demo.entity.LeiMuTable;
import com.demo.entity.ZhangHao;
import com.demo.entity.Courier.YunFeiTable;
import com.demo.entity.order.OrderTable;
import com.demo.utils.ApplicationUtils;
import com.demo.utils.DateUtils;
import com.demo.utils.HttpClientUtils;

@Service
@Transactional
public class AliOrderApiBiz {

	@Resource
	private AliCommonApiBiz aliCommonApiBiz;
	@Resource
	private ZhangHaoDao zhangHaoDao;
	@Resource
	private OrderTableDao orderTableDao;
	@Resource
	private LeiMuDao leiMuDao;
	@Resource
	private YunFeiTableDao yunFeiTableDao;
	
	private final Integer pageSize = 20;
	private Integer updateCount = 0;
	
	/**
	 * ͬ����������(�����Զ�ͬ��)
	 * @return
	 */
	public String autoFetchOrders(ZhangHao aliAccount) {
		Date endDate = new Date();
		Date startDate = null;
		Long orderUpdateTime = aliAccount.getOrder_update_time();
		if (orderUpdateTime == null) {
			startDate = DateUtils.getAfterDaysDate(endDate, -5); // ��ʼ��5��ǰ�����ݿ�ʼȡ
		} else {
			startDate = new Date(orderUpdateTime);
		}
		
		String result = this.fetchOrders(aliAccount, startDate, endDate);
		if (!result.contains("����")) {
			// ����ͬ��ʱ��
			aliAccount.setOrder_update_time(endDate.getTime());
			zhangHaoDao.merge(aliAccount);
		}
		return result;
	}
	
	public String fetchOrders(String account, Date startDate, Date endDate) {
		ZhangHao aliAccount = zhangHaoDao.findUnique(account, 
				AliCommonApiBiz.ACCOUNT_TYPE);
		return this.fetchOrders(aliAccount, startDate, endDate);
	}
	
	public String fetchOrders(ZhangHao aliAccount, Date startDate, Date endDate) {
		try {
			updateCount = 0;
			String strStartDate =  new SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
				.format(startDate);
			String strEndDate =  new SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
				.format(endDate);
			String result = this.fetchOrders(aliAccount, strStartDate, strEndDate, 1);
			if (result.equals("success")) {
				return "���γɹ����� " + updateCount + " ����������";
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "��������δ֪�쳣";
		}
	}
	
	/**
	 * ץȡһҳ��������
	 * @param aliAccount
	 * @param startDate
	 * @param endDate
	 * @param pageNum
	 * @return
	 */
	private String fetchOrders(ZhangHao aliAccount, String startDate, 
			String endDate, Integer pageNum) {
		String apiUrl = ApplicationUtils.get("aliApiUrl") 
				+ "param2/1/aliexpress.open/api.findOrderListQuery/"
				+ aliAccount.getApp_key();
		Map<String, String> paramMap = new HashMap<String, String>();
		if (!aliCommonApiBiz.putSystemParamsToParamMap(paramMap, aliAccount)) {
			return "��������" + AliCommonApiBiz.ERR_TOKEN;
		}
		paramMap.put("page", pageNum.toString());
		paramMap.put("pageSize", pageSize.toString());
		paramMap.put("createDateStart", startDate);
		paramMap.put("createDateEnd", endDate);
		paramMap.put("orderStatus", "WAIT_SELLER_SEND_GOODS");
		
		JSONObject respJson = HttpClientUtils.doPost(apiUrl, paramMap);
		if (respJson != null) {
			if (respJson.containsKey("error_code")) {
				if (respJson.getString("error_code").equals("401")) {
					aliCommonApiBiz.clearAccessToken(aliAccount);
					return "��������" + AliCommonApiBiz.ERR_TOKEN;
				} else {
					return "��������" + respJson.getString("error_message");
				}
			}
			
			if (!respJson.containsKey("orderList") || 
					respJson.getString("orderList").equals("null") || 
					respJson.getString("orderList").equals("[]") ) {
				return "success";
			}
			// �������
			JSONArray orders = respJson.getJSONArray("orderList");
			for (int i = 0; i < orders.size(); i++) {
				JSONObject json = orders.getJSONObject(i);
				updateCount += this.saveOrder(json, aliAccount);
			}
			
			// ��ȡ����������pagesize���ʱ������ȡ��һҳ
			if (orders.size() == this.pageSize) {
				this.fetchOrders(aliAccount, startDate, endDate,
						pageNum + 1);
			}
			return "success";
		}
		return "��������" + AliCommonApiBiz.CONN_ERR;
	}
	
	/**
	 * ����һ������
	 * @param json
	 * @param aliAccount
	 * @return
	 */
	private int saveOrder(JSONObject json, ZhangHao aliAccount) {
		String orderId = json.getString("orderId");
		List<OrderTable> orders = orderTableDao.getOrder(orderId);
		if (orders != null && !orders.isEmpty()) {
			return 0;
		}
		
		OrderTable order = new OrderTable();
		order.setOrderId(orderId);
		order.setOrdersn(this.getOrderSn());
		order.setZhanghaoId(aliAccount.getId());
		order.setTime(new Date());
		
		// ������
		Double money = new Double("0");
		if (json.containsKey("loanAmount")) {
			money = json.getJSONObject("loanAmount").getDouble("amount");
		} else if (json.containsKey("payAmount")) {
			money = json.getJSONObject("payAmount").getDouble("amount");
		}
		if (!money.equals(0)) {
			money = Double.parseDouble(new DecimalFormat("#.00").format(money * 0.95)); // ʵ�ս��
			order.setMoney(money);
		}
		
		String wuping = ""; // ��Ʒ
		String shippingType = ""; // ��������
		String bianma = ""; // ����
		JSONArray products = json.getJSONArray("productList");
		for (int i = 0; i < products.size(); i++) {
			// ������Ʒ
			if (!wuping.equals("")) {
				wuping += "+";
			}
			wuping += products.getJSONObject(i).getString("productName")
					+ "(����:" + products.getJSONObject(i).getString("productCount") + ")" ;
			// ������������
			String logisticsType = products.getJSONObject(i).getString("logisticsType");
			if (!shippingType.contains(logisticsType)) {
				if (!shippingType.equals("")) {
					shippingType += ",";
				}
				shippingType += logisticsType;
			}
			// �������
			if (!bianma.equals("")) {
				bianma += ",";
			}
			bianma = products.getJSONObject(i).getString("productId");
		}
		order.setWuping(wuping);
		order.setShippingtype(shippingType);
		order.setBianma(bianma);
		// ȡ��Ŀ
		Long leiMuId = null;
		List<LeiMuTable> leiMus = leiMuDao.getAllName(aliAccount.getAccount());
		if (leiMus != null && !leiMus.isEmpty()) {
			leiMuId = leiMus.get(0).getId();
		}
		order.setLeimuid(leiMuId);
		// ��������
		JSONObject orderDetailJson = this.getOrder(orderId, aliAccount);
		if (orderDetailJson != null) {
			JSONObject receiptAddress = orderDetailJson.getJSONObject("receiptAddress");
			order.setAddress1(receiptAddress.getString("detailAddress"));
			// ȡ����
			String country = receiptAddress.getString("country");
			YunFeiTable yunFei = yunFeiTableDao.getCorresponding(country);
			if (yunFei != null) {
				country = yunFei.getGuojia();
			}
			order.setCountry(country);
			order.setCity(receiptAddress.getString("city"));
			order.setProvince(receiptAddress.getString("province"));
			order.setPostcode(receiptAddress.getString("zip"));
			String tel = "";
			if (receiptAddress.containsKey("mobileNo") 
					&& !receiptAddress.getString("mobileNo").equals("")) {
				tel = receiptAddress.getString("mobileNo");
			} else {
				if (receiptAddress.containsKey("phoneCountry")) {
					tel += receiptAddress.getString("phoneCountry");
				}
				if (receiptAddress.containsKey("phoneArea")) {
					tel += receiptAddress.getString("phoneArea");
				}
				if (receiptAddress.containsKey("phoneNumber")) {
					tel += receiptAddress.getString("phoneNumber");
				}
			}
			order.setTelephone(tel);
			order.setReceiver(receiptAddress.getString("contactPerson"));
			// ȡ�����ַ
			String guowaidizhi = "Contact Name: " + order.getReceiver() + "\r\n"
					+ "Address: " + order.getAddress1() + "\r\n"
					+ "City: " + order.getCity() + "\r\n"
					+ "State: " + order.getProvince() + "\r\n" 
					+ "Country: " + order.getCountry() + "\r\n"
					+ "Postal Code: " + order.getPostcode() + "\r\n"
					+ "Phone Number: " + order.getTelephone();
			order.setGuowaidizhi(guowaidizhi);
		}
		orderTableDao.merge(order);
		return 1;
	}
	
	public JSONObject getOrder(String orderId, ZhangHao aliAccount) {
		String apiUrl = ApplicationUtils.get("aliApiUrl") 
				+ "param2/1/aliexpress.open/api.findOrderById/"
				+ aliAccount.getApp_key();
		Map<String, String> paramMap = new HashMap<String, String>();
		if (!aliCommonApiBiz.putSystemParamsToParamMap(paramMap, aliAccount)) {
			return null;
		}
		paramMap.put("orderId", orderId);
		JSONObject respJson = HttpClientUtils.doPost(apiUrl, paramMap);
		if (respJson != null) {
			if (respJson.containsKey("error_code")) {
				return null;
			}
			return respJson;
		}
		return null;
	}

	private String getOrderSn() {
		return new SimpleDateFormat("yyyy-MM-dd-HHmmss-SSS").format(new Date())
				+ new Random().nextInt(1000);
	}
}
