package com.demo.biz.dhgate;

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

import com.demo.dao.OrderTableDao;
import com.demo.dao.ZhangHaoDao;
import com.demo.entity.ZhangHao;
import com.demo.entity.order.OrderTable;
import com.demo.utils.ApplicationUtils;
import com.demo.utils.DateUtils;
import com.demo.utils.HttpClientUtils;

@Service
@Transactional
public class DhComplaintsApiOrderBiz {
	
	@Resource
	private DhCommonApiBiz dhCommonApiBiz;
	@Resource
	private OrderTableDao orderTableDao;
	private final Integer pageSize = 20;
	private Integer updateCount = 0;
	/**
	 * ͬ����������(�����Զ�ͬ��)
	 * @return
	 */
	public String autoFetchComplaintsOrders(ZhangHao dhAccount) {
		Date endDate = new Date();
		Date startDate = DateUtils.getAfterDaysDate(endDate, -30); // ȡ30����
		
		String strStartDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
			.format(startDate);
		String strEndDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
			.format(endDate);
		String result = this.fetchOrders(dhAccount, strStartDate, strEndDate);
		return result;
	}
	public String fetchOrders(ZhangHao dhAccount, String startDate, String endDate) {
		try {
			updateCount = 0;
			String result = this.fetchOrders(dhAccount, startDate, endDate, 1);
			if (result.equals("success")) {
				return "���γɹ����� " + updateCount + " ����������";
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "��������δ֪�쳣";
		}
	}
	
	private String fetchOrders(ZhangHao dhAccount,  String startDate, 
			String endDate, Integer pageNum) {
		try {
			String apiUrl = (String) ApplicationUtils.get("dhgateApiUrl");
			Map<String, String> paramMap = new HashMap<String, String>();
			if (!dhCommonApiBiz.putSystemParamsToParamMap(paramMap, dhAccount,
					"dh.order.list.get", "1.1")) {
				return "��������" + DhCommonApiBiz.ERR_TOKEN;
			}
			paramMap.put("startdate", startDate);
			paramMap.put("enddate", endDate);
			paramMap.put("statusList", "106002");
			paramMap.put("pagesize", pageSize.toString());
			paramMap.put("pages", pageNum.toString());
			
			JSONObject respJson = HttpClientUtils.doPost(apiUrl, paramMap);
		
			if (respJson != null) {
				if (respJson.containsKey("code")) {
					if (respJson.getString("code").equals("2") || 
							respJson.getString("code").equals("40")) {
						dhCommonApiBiz.clearAccessToken(dhAccount);
						return "��������" + DhCommonApiBiz.ERR_TOKEN;
					} else if (!respJson.getString("code").equals("0")) {
						return "��������" + respJson.getString("message");
					}
				}
				
				JSONObject statusObj = respJson.getJSONObject("status");
				if (Integer.parseInt(statusObj.getString("code")) == 0) {
					if (respJson.getString("rfxlist").equals("null") || 
							respJson.getString("rfxlist").equals("[]") ) {
						return "success";
					}
					
					JSONArray orders = respJson.getJSONArray("rfxlist");
				
					for (int i = 0; i < orders.size(); i++) {
							this.updateOrder(dhAccount, orders.getJSONObject(i));
					}
					
					// ��Ҫȡ��һҳ
					if (pageNum < respJson.getInt("sumPage")) {
						this.fetchOrders(dhAccount, startDate, endDate, pageNum + 1);
					}
					
					return "success";
				} else {
					return "��������" + statusObj.getString("message");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	
		return "��������" + DhCommonApiBiz.CONN_ERR;
	}
	/**
	 * ���¶������ݵ��������ݿ�
	 * @param dhAccount
	 * @param json
	 * @return �Ƿ��и���: 0-û�и��£�����Ҫ����1-�и���
	 */
	private int updateOrder(ZhangHao dhAccount, JSONObject json) {
		try {
			String orderId = json.getString("rfxno");
			List<OrderTable> orders = orderTableDao.getOrderAll(orderId);
			Date d = new Date();
	        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String ff = f.format(d);
			JSONObject orderDetail = this.getOrder(orderId, dhAccount);
			JSONObject rfxDto =null;
			Double totalrefund = null;
			if (orderDetail.containsKey("rfxDto")) {
				rfxDto = orderDetail.getJSONObject("rfxDto");
			} else if (orderDetail.containsKey("rfxVo")) {
				rfxDto = orderDetail.getJSONObject("rfxVo");
			}
			if (rfxDto == null) {
				return 0;
			}
			totalrefund = rfxDto.getDouble("totalrefund"); // �ܹ����˿��
			if (orders != null && !orders.isEmpty()) { 
				if (orders.get(0).getHuokuan() == null || orders.get(0).getHuokuan().equals(0l)) {
					orderTableDao.updateApplication(orderId, ff,totalrefund);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 1;
	}
	/**
	 * ��ȡһ��������Ϣ
	 * @param rfxno ������
	 * @param dhAccount
	 * @return
	 */
	public JSONObject getOrder(String rfxno, ZhangHao dhAccount) {
		String apiUrl = (String) ApplicationUtils.get("dhgateApiUrl");
		Map<String, String> paramMap = new HashMap<String, String>();
		if (!dhCommonApiBiz.putSystemParamsToParamMap(paramMap, dhAccount,
				"dh.order.get")) {
			return null;
		}
		paramMap.put("rfxno", rfxno);

		JSONObject respJson = HttpClientUtils.doPost(apiUrl, paramMap);
		if (respJson != null) {
			if (respJson.containsKey("code") && !respJson.getString("code").equals("0")) {
				return null;
			}
			
			JSONObject statusObj = respJson.getJSONObject("status");
			if (Integer.parseInt(statusObj.getString("code")) == 0) {
				return respJson;
			}
		}
		return null;
	}
	
	private String getOrderSn() {
		return new SimpleDateFormat("yyyy-MM-dd-HHmmss-SSS").format(new Date())
				+ new Random().nextInt(1000);
	}
}
