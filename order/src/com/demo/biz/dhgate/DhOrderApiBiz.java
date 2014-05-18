package com.demo.biz.dhgate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class DhOrderApiBiz {
	
	@Resource
	private DhCommonApiBiz dhCommonApiBiz;
	@Resource
	private ZhangHaoDao zhangHaoDao;
	@Resource
	private OrderTableDao orderTableDao;
	@Resource
	private DhOrderBiz dhorderbiz;
	private final Integer pageSize = 20;
	private Integer updateCount = 0;
	
	/**
	 * 同步订单数据(用于自动同步)
	 * @return
	 */
	public String autoFetchOrders(ZhangHao dhAccount) {
		Date endDate = new Date();
		Date startDate = DateUtils.getAfterDaysDate(endDate, -120); // 取30天内
		
		String strStartDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
			.format(startDate);
		String strEndDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
			.format(endDate);
		String result = this.fetchOrders(dhAccount, strStartDate, strEndDate);
		
		if (!result.contains("错误")) {
			// 更新同步时间
			dhAccount.setOrder_update_time(endDate.getTime());
			zhangHaoDao.merge(dhAccount);
		}
		return result;
	}
	
	public String fetchOrders(ZhangHao dhAccount, String startDate, String endDate) {
		try {
			updateCount = 0;
			String result = this.fetchOrders(dhAccount, startDate, endDate, 1);

			if (result.equals("success")) {
				return "本次成功更新 " + updateCount + " 条订单数据";
			}
				return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "发生错误：" + e.getMessage();
		}
	}


	private String fetchOrders(ZhangHao dhAccount,  String startDate, 
			String endDate, Integer pageNum ) {
		try {
			String apiUrl = (String) ApplicationUtils.get("dhgateApiUrl");
			Map<String, String> paramMap = new HashMap<String, String>();
			if (!dhCommonApiBiz.putSystemParamsToParamMap(paramMap, dhAccount,
					"dh.order.list.get", "1.1")) {
				return "发生错误：" + DhCommonApiBiz.ERR_TOKEN;
			}
			paramMap.put("startdate", startDate);
			paramMap.put("enddate", endDate);
			paramMap.put("statusList", "103001,106002,105004,106001,102007,106003,111000,102111,102009,111000");//取订单
			paramMap.put("pagesize", pageSize.toString());
			paramMap.put("pages", pageNum.toString());
			
			JSONObject respJson = HttpClientUtils.doPost(apiUrl, paramMap);
			if (respJson != null) {
				if (respJson.containsKey("code")) {
					if (respJson.getString("code").equals("2") || 
							respJson.getString("code").equals("40")) {
						dhCommonApiBiz.clearAccessToken(dhAccount);
						return "发生错误：" + DhCommonApiBiz.ERR_TOKEN;
					} else if (!respJson.getString("code").equals("0")) {
						return "发生错误：" + respJson.getString("message");
					}
				}
				
				JSONObject statusObj = respJson.getJSONObject("status");
				if (Integer.parseInt(statusObj.getString("code")) == 0) {
					if (respJson.getString("rfxlist").equals("null") || 
							respJson.getString("rfxlist").equals("[]") ) {
						return "success";
					}
					
					JSONArray orderJsonArr = respJson.getJSONArray("rfxlist");
					for (int i = 0; i < orderJsonArr.size(); i++) {
						JSONObject orderJson = orderJsonArr.getJSONObject(i);
						String orderId = orderJson.getString("rfxno");
						String orderStatus = orderJson.getString("rfxstatusid");
						
						// 从db读取该订单数据
						List<OrderTable> orders = orderTableDao.getOrderAll(orderId);
						OrderTable order = null;
						if (orders != null && !orders.isEmpty()) {
							order = orders.get(0);
						}
						
						// 根据订单状态做相应的处理
						if (orderStatus.equals("103001")) {
							dhorderbiz.addOrder(dhAccount, orderJson, order);
						}
						else if (orderStatus.equals("106001")) {
							dhorderbiz.updateJiufenOrder(dhAccount, orderJson, order);
						} 
						else if (orderStatus.equals("102111")) {
							dhorderbiz.updateSuccess(dhAccount, orderJson, order);
						} 
						else if(orderStatus.equals("111000") || orderStatus.equals("111111") 
								|| orderStatus.equals("102009")){
							dhorderbiz.updateTuiKuan(dhAccount, orderJson, order);
						}
						else if (orderStatus.equals("105004")) {
							dhorderbiz.updateOrder(dhAccount, orderJson, order);
						} 
					}
					
					// 需要取下一页
					if (pageNum < respJson.getInt("sumPage")) {
						this.fetchOrders(dhAccount, startDate, endDate, pageNum + 1);
					}
					
					return "success";
				} else {
					return "发生错误：" + statusObj.getString("message");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "发生错误：" + e.getMessage();
		}
		
		return "发生错误：" + DhCommonApiBiz.CONN_ERR;
	}
}
