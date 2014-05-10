package com.demo.biz.dhgate;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.demo.entity.ZhangHao;
import com.demo.utils.ApplicationUtils;
import com.demo.utils.HttpClientUtils;

@Service
public class DhShippingApiBiz {

	@Resource
	private DhCommonApiBiz dhCommonApiBiz;
	
	/**
	 * 获取运费模板列表
	 * @param dhAccount
	 * @return
	 */
	public JSONObject getTemplates(ZhangHao dhAccount) {
		String apiUrl = (String) ApplicationUtils.get("dhgateApiUrl");
		Map<String, String> paramMap = new HashMap<String, String>();
		if (!dhCommonApiBiz.putSystemParamsToParamMap(paramMap, dhAccount,
				"dh.shipping.templates.get")) {
			return null;
		}

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
