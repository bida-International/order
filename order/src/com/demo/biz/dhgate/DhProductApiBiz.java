package com.demo.biz.dhgate;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.entity.ZhangHao;
import com.demo.utils.ApplicationUtils;
import com.demo.utils.HttpClientUtils;

@Service
@Transactional
public class DhProductApiBiz {

	@Resource
	private DhCommonApiBiz dhCommonApiBiz;
	
	/**
	 * 取一个产品的详情
	 * @param itemCode
	 * @param dhAccount
	 * @return
	 */
	public JSONObject getProduct(String itemCode, ZhangHao dhAccount) {
		String apiUrl = (String) ApplicationUtils.get("dhgateApiUrl");
		Map<String, String> paramMap = new HashMap<String, String>();
		if (!dhCommonApiBiz.putSystemParamsToParamMap(paramMap, dhAccount,
				"dh.product.get")) {
			return null;
		}
		paramMap.put("itemcode", itemCode);
		JSONObject respJson = HttpClientUtils.doPost(apiUrl, paramMap);
		if (respJson != null) {
			if (respJson.containsKey("code") && !respJson.getString("code").equals("0")) {
				return null;
			}
			
			JSONObject statusObj = respJson.getJSONObject("status");
			if (statusObj.getString("message").equalsIgnoreCase("OK")) {
				return respJson;
			}
		}
		return null;
	}
	
}
