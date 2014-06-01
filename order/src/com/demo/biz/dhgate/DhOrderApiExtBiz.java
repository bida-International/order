package com.demo.biz.dhgate;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.ZhangHaoDao;
import com.demo.entity.ZhangHao;
import com.demo.entity.order.OrderTable;
import com.demo.utils.ApplicationUtils;
import com.demo.utils.HttpClientUtils;

@Service
@Transactional
public class DhOrderApiExtBiz {

	@Resource
	private DhCommonApiBiz dhCommonApiBiz;
	@Resource
	private ZhangHaoDao zhangHaoDao;
	
	/**
	 * 订单请款
	 * @param order
	 * @return
	 */
	public JSONObject moneyGet(OrderTable order) {
		ZhangHao dhAccount = zhangHaoDao.get(order.getZhanghaoId());
		if (dhAccount == null) {
			return null;
		}
		return this.moneyGet(order, dhAccount);
	}
	
	/**
	 * 订单请款
	 * @param order
	 * @param dhAccount
	 * @return
	 */
	public JSONObject moneyGet(OrderTable order, ZhangHao dhAccount) {
		String apiUrl = (String) ApplicationUtils.get("dhgateApiUrl");
		Map<String, String> paramMap = new HashMap<String, String>();
		if (!dhCommonApiBiz.putSystemParamsToParamMap(paramMap, dhAccount,
				"dh.order.money.get")) {
			return null;
		}
		paramMap.put("Apply", "{\"rfxNo\" : '" + order.getOrderId() + "'}");
		
		JSONObject respJson = HttpClientUtils.doPost(apiUrl, paramMap);
		if (respJson != null) {
			if (respJson.containsKey("code")) {
				if (respJson.getString("code").equals("2") || 
						respJson.getString("code").equals("40")) {
					dhCommonApiBiz.clearAccessToken(dhAccount);
					return null;
				}
			}
			
			JSONObject statusObj = respJson.getJSONObject("status");
			if (Integer.parseInt(statusObj.getString("code")) == 0) {
				return respJson;
			}
		}
		return null;
	}
}
