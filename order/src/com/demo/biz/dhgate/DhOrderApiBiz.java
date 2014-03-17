//package com.demo.biz.dhgate;
//
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//
//import javax.annotation.Resource;
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.demo.dao.HuiLvDao;
//import com.demo.dao.LeiMuDao;
//import com.demo.dao.OrderTableDao;
//import com.demo.dao.ZhangHaoDao;
//import com.demo.dao.Express.YunFeiTableDao;
//import com.demo.entity.HuiLvTable;
//import com.demo.entity.LeiMuTable;
//import com.demo.entity.ZhangHao;
//import com.demo.entity.Express.YunFeiTable;
//import com.demo.entity.order.OrderTable;
//import com.demo.utils.ApplicationUtils;
//import com.demo.utils.DateUtils;
//import com.demo.utils.HttpClientUtils;
//
//@Service
//@Transactional
//public class DhOrderApiBiz {
//	
//	@Resource
//	private DhCommonApiBiz dhCommonApiBiz;
//	@Resource
//	private DhProductApiBiz dhProductApiBiz;
//	@Resource
//	private OrderTableDao orderTableDao;
//	@Resource
//	private ZhangHaoDao zhangHaoDao;
//	@Resource
//	private LeiMuDao leiMuDao;
//	@Resource
//	private YunFeiTableDao yunFeiTableDao;
//	@Resource
//	private HuiLvDao huiLvDao;
//	private final Integer pageSize = 20;
//	private Integer updateCount = 0;
//	
//	/**
//	 * ͬ����������(�����Զ�ͬ��)
//	 * @return
//	 */
//	public String autoFetchOrders(ZhangHao dhAccount) {
//		Date endDate = new Date();
//		Date startDate = null;
//		Long orderUpdateTime = dhAccount.getOrder_update_time();
//		if (orderUpdateTime == null) {
//			startDate = DateUtils.getAfterDaysDate(endDate, -5); // ��ʼ��5��ǰ�����ݿ�ʼȡ
//		} else {
//			startDate = new Date(orderUpdateTime);
//		}
//		
//		String strStartDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//			.format(startDate);
//		String strEndDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//			.format(endDate);
//		String result = this.fetchOrders(dhAccount, strStartDate, strEndDate);
//		if (!result.contains("����")) {
//			// ����ͬ��ʱ��
//			dhAccount.setOrder_update_time(endDate.getTime());
//			zhangHaoDao.merge(dhAccount);
//		}
//		return result;
//	}
//	
//	public String fetchOrders(ZhangHao dhAccount, String startDate, String endDate) {
//		try {
//			updateCount = 0;
//			String result = this.fetchOrders(dhAccount, startDate, endDate, 1);
//			if (result.equals("success")) {
//				return "���γɹ����� " + updateCount + " ����������";
//			}
//			return result;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "��������δ֪�쳣";
//		}
//	}
//	
//	private String fetchOrders(ZhangHao dhAccount,  String startDate, 
//			String endDate, Integer pageNum) {
//		String apiUrl = (String) ApplicationUtils.get("dhgateApiUrl");
//		Map<String, String> paramMap = new HashMap<String, String>();
//		if (!dhCommonApiBiz.putSystemParamsToParamMap(paramMap, dhAccount,
//				"dh.order.list.get", "1.1")) {
//			return "��������" + DhCommonApiBiz.ERR_TOKEN;
//		}
//		paramMap.put("startdate", startDate);
//		paramMap.put("enddate", endDate);
//		paramMap.put("statusList", "101006");
//		paramMap.put("pagesize", pageSize.toString());
//		paramMap.put("pages", pageNum.toString());
//		
//		JSONObject respJson = HttpClientUtils.doPost(apiUrl, paramMap);
//		if (respJson != null) {
//			if (respJson.containsKey("code")) {
//				if (respJson.getString("code").equals("2")) {
//					dhCommonApiBiz.clearAccessToken(dhAccount);
//					return "��������" + DhCommonApiBiz.ERR_TOKEN;
//				} else if (!respJson.getString("code").equals("0")) {
//					return "��������" + respJson.getString("message");
//				}
//			}
//			
//			JSONObject statusObj = respJson.getJSONObject("status");
//			if (statusObj.getString("message").equalsIgnoreCase("OK")) {
//				if (respJson.getString("rfxlist").equals("null") || 
//						respJson.getString("rfxlist").equals("[]") ) {
//					return "success";
//				}
//				
//				JSONArray orders = respJson.getJSONArray("rfxlist");
//				for (int i = 0; i < orders.size(); i++) {
//					updateCount += this.updateOrder(dhAccount, orders.getJSONObject(i));
//				}
//				
//				// ��ȡ����������pagesize���ʱ������ȡ��һҳ
//				if (orders.size() == this.pageSize) {
//					this.fetchOrders(dhAccount, startDate, endDate, pageNum + 1);
//				}
//				
//				return "success";
//			} else {
//				return "��������" + statusObj.getString("message");
//			}
//		}
//		return "��������" + DhCommonApiBiz.CONN_ERR;
//	}
//	
//	/**
//	 * ���¶������ݵ��������ݿ�
//	 * @param dhAccount
//	 * @param json
//	 * @return �Ƿ��и���: 0-û�и��£�����Ҫ����1-�и���
//	 */
//	private int updateOrder(ZhangHao dhAccount, JSONObject json) {
//		String orderId = json.getString("rfxno");
//		List<OrderTable> orders = orderTableDao.getOrder(orderId);
//		java.util.Date d = new java.util.Date();
//        SimpleDateFormat f = new SimpleDateFormat("yyyyMM");
//        String ff = f.format(d);
//    	List<HuiLvTable> hh = huiLvDao.getHuiTime(ff);
//		List<ZhangHao> zh = zhangHaoDao.getZhangHaoIdAll(dhAccount.getId());
//		if (orders != null && !orders.isEmpty()) { // �Ѵ���, �����ظ����
//			return 0;
//		}
//		
//		OrderTable order = new OrderTable();
//		order.setOrderId(orderId);
//		order.setOrdersn(this.getOrderSn());
//		order.setAddress1(json.getString("addressline1")); // ��ַ
//		// ȡ����
//		String country = json.getString("country");
//		YunFeiTable yunFei = yunFeiTableDao.getCorresponding(country);
//		if (yunFei != null) {
//			country = yunFei.getGuojia();
//		}
//		order.setCountry(country);
//		order.setCity(json.getString("city")); // ����
//		order.setProvince(json.getString("state")); // ��
//		order.setPostcode(json.getString("postalcode")); // �ʱ�
//		order.setTelephone(json.getString("tel")); // �绰
//		order.setReceiver(json.getString("firstname") + " " + json.getString("lastname")); // �ջ���
//		// ȡ�����ַ
//		String guowaidizhi = "Contact Name: " + order.getReceiver() + "\r\n"
//				+ "Address: " + order.getAddress1() + "\r\n"
//				+ "City: " + order.getCity() + "\r\n"
//				+ "State: " + order.getProvince() + "\r\n" 
//				+ "Country: " + order.getCountry() + "\r\n"
//				+ "Postal Code: " + order.getPostcode() + "\r\n"
//				+ "Phone Number: " + order.getTelephone();
//		order.setGuowaidizhi(guowaidizhi);
//		order.setShippingtype(json.getString("shippingtype")); // ���䷽ʽ
//		if (!json.getString("rfxremark").equals("null")) {
//			order.setRemark(json.getString("rfxremark")); // ��ע
//		}
//		// ȡ��������, ȡ���
//		JSONObject orderDetail = this.getOrder(orderId, dhAccount);
//		Double totalPriceOfProduct = orderDetail.getJSONObject("rfxDto").getDouble("totalpriceofproduct"); // ��Ʒ���
//		Double shipcost = orderDetail.getJSONObject("rfxDto").getDouble("shipcost"); // �˷�
//		Double commissionAmount = orderDetail.getJSONObject("rfxDto").getDouble("commissionAmount"); // Ӷ�� 
//		Double money = totalPriceOfProduct + shipcost - commissionAmount;  // ʵ�ս��
//		money = Double.parseDouble(new DecimalFormat("#.00").format(money));
//		order.setMoney(money);
//		// ȡ������Ʒ
//		JSONObject orderProduct = this.getOrderProduct(json.getString("rfxid"), dhAccount);
//		JSONArray products = orderProduct.getJSONArray("lstProductDTO");
//		String wuping = ""; // ��Ʒ
//		String bianma = ""; // ����
//		for (int i = 0; i < products.size(); i++) {
//			// ������Ʒ
//			if (!wuping.equals("")) {
//				wuping += "+";
//			}
//			wuping += products.getJSONObject(i).getString("productname")
//					+  "(����:" + products.getJSONObject(i).getString("quantity") + ")" ;
//			// ȡ����
//			if (!bianma.equals("")) {
//				bianma += ",";
//			}
//			bianma = products.getJSONObject(i).getString("itemcode");
//		}
//		if(zh.size()!=0){
//      	  order.setArtistsGetOrdersId(zh.get(0).getClipArtId());
//        }
//	    if(hh.size() != 0){
//        	for (int j = 0; j < hh.size(); j++) {
//				order.setHuilv(hh.get(0).getHuilv());
//			}
//        }
//		order.setFenpei(1l);
//        order.setQianshou(0l);
//        order.setShangwang(0l);
//        order.setRuzhang(0l);
//        order.setGetordersId(1l);
//        order.setArtistsGetOrders(1l);
//		// ȡ��Ŀ
//		LeiMuTable leiMu = this.getOrderLeiMu(orderProduct, dhAccount);
//		if (leiMu != null) {
//			order.setLeimuid(leiMu.getId());
//		//	order.setYunshu(leiMu.getLeimu());
//			order.setCaigouyuan(leiMu.getUserid());
//		}
//		order.setWuping(wuping);
//		order.setBianma(bianma);
//		order.setZhanghaoId(dhAccount.getId());
//		order.setTime(new Date());
//		orderTableDao.merge(order);
//		return 1;
//	}
//	
//	/**
//	 * ��ȡһ��������Ϣ
//	 * @param rfxno ������
//	 * @param dhAccount
//	 * @return
//	 */
//	public JSONObject getOrder(String rfxno, ZhangHao dhAccount) {
//		String apiUrl = (String) ApplicationUtils.get("dhgateApiUrl");
//		Map<String, String> paramMap = new HashMap<String, String>();
//		if (!dhCommonApiBiz.putSystemParamsToParamMap(paramMap, dhAccount,
//				"dh.order.get")) {
//			return null;
//		}
//		paramMap.put("rfxno", rfxno);
//
//		JSONObject respJson = HttpClientUtils.doPost(apiUrl, paramMap);
//		if (respJson != null) {
//			if (respJson.containsKey("code") && !respJson.getString("code").equals("0")) {
//				return null;
//			}
//			
//			JSONObject statusObj = respJson.getJSONObject("status");
//			if (statusObj.getString("message").equalsIgnoreCase("OK")) {
//				return respJson;
//			}
//		}
//		return null;
//	}
//	
//	/**
//	 * ��ȡ������Ŀ
//	 * @param orderProduct
//	 * @return
//	 */
//	public LeiMuTable getOrderLeiMu(JSONObject orderProduct, ZhangHao dhAccount) {
//		// ȡ��Ʒ����
//		String itemCode = orderProduct.getJSONArray("lstProductDTO").getJSONObject(0).getString("itemcode");
//		JSONObject product = dhProductApiBiz.getProduct(itemCode, dhAccount);
//		if (product == null) {
//			return null;
//		}
//		// ȡ��ƷcatePubId��Ӧ��userId��ִ�з���
//		String catePubId = product.getJSONObject("product").getString("catePubId");
//		String parentCatePubId = catePubId.substring(0, 3);
//		LeiMuTable leiMu = leiMuDao.getByCateId(parentCatePubId);
//		if (leiMu == null) {
//			return null;
//		}
//		return leiMu;
//	}
//	
//	/**
//	 * ��ȡ�����Ĳ�Ʒ��Ϣ
//	 * @param rfxid ��������id
//	 * @param dhAccount
//	 * @return
//	 */
//	public JSONObject getOrderProduct(String rfxid, ZhangHao dhAccount) {
//		String apiUrl = (String) ApplicationUtils.get("dhgateApiUrl");
//		Map<String, String> paramMap = new HashMap<String, String>();
//		if (!dhCommonApiBiz.putSystemParamsToParamMap(paramMap, dhAccount,
//				"dh.order.product.get")) {
//			return null;
//		}
//		paramMap.put("rfxid", rfxid);
//
//		JSONObject respJson = HttpClientUtils.doPost(apiUrl, paramMap);
//		if (respJson != null) {
//			if (respJson.containsKey("code") && !respJson.getString("code").equals("0")) {
//				return null;
//			}
//			
//			JSONObject statusObj = respJson.getJSONObject("status");
//			if (statusObj.getString("message").equalsIgnoreCase("OK")) {
//				return respJson;
//			}
//		}
//		return null;
//	}
//	
//	private String getOrderSn() {
//		return new SimpleDateFormat("yyyy-MM-dd-HHmmss-SSS").format(new Date())
//				+ new Random().nextInt(1000);
//	}
//}