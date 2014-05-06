package com.demo.biz.dhgate;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.entity.ZhangHao;
import com.demo.utils.ApiCacheUtils;
import com.demo.utils.ApplicationUtils;
import com.demo.utils.HttpClientUtils;

@Service
@Transactional
public class DhCategoryApiBiz {

	@Resource
	private DhCommonApiBiz dhCommonApiBiz;

	/**
	 * 获取一个类目信息
	 * @param categoryId
	 * @param dhAccount
	 * @return
	 */
	public JSONObject getCategory(String categoryId, ZhangHao dhAccount) {
		final String CACHE_KEY = "dh.category.get_" + categoryId;
		Object obj = ApiCacheUtils.getInstance().getCache(CACHE_KEY);
		if (obj != null) {
			return JSONObject.fromObject(obj);
		}
		
		String apiUrl = (String) ApplicationUtils.get("dhgateApiUrl");
		Map<String, String> paramMap = new HashMap<String, String>();
		if (!dhCommonApiBiz.putSystemParamsToParamMap(paramMap, dhAccount,
				"dh.category.get")) {
			return null;
		}
		paramMap.put("catepubid", categoryId);

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
			if (statusObj.getString("message").equalsIgnoreCase("OK")) {
				ApiCacheUtils.getInstance().addCache(CACHE_KEY, respJson.toString());
				return respJson;
			}
		}
		return null;
	}
	
	/**
	 * 获取一个子类目列表
	 * @param parentId
	 * @param dhAccount
	 * @return
	 */
	public JSONObject getCategorys(String parentId, ZhangHao dhAccount) {
		final String CACHE_KEY = "dh.categorys.get_" + parentId;
		Object obj = ApiCacheUtils.getInstance().getCache(CACHE_KEY);
		if (obj != null) {
			return JSONObject.fromObject(obj);
		}
		
		String apiUrl = (String) ApplicationUtils.get("dhgateApiUrl");
		Map<String, String> paramMap = new HashMap<String, String>();
		if (!dhCommonApiBiz.putSystemParamsToParamMap(paramMap, dhAccount,
				"dh.categorys.get")) {
			return null;
		}
		paramMap.put("parentid", parentId);

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
			if (statusObj.getString("message").equalsIgnoreCase("OK")) {
				ApiCacheUtils.getInstance().addCache(CACHE_KEY, respJson.toString());
				return respJson;
			}
		}
		return null;
	}
	
	
}
