package com.demo.biz.aliexpress;

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
public class AliProductApiBiz {

	@Resource
	private AliCommonApiBiz aliCommonApiBiz;
	
	/**
	 * 取一个产品的详情
	 * @param productId
	 * @param aliAccount
	 * @return
	 */
	public JSONObject getProduct(String productId, ZhangHao aliAccount) {
		String apiUrl = ApplicationUtils.get("aliApiUrl") 
				+ "param2/1/aliexpress.open/api.findAeProductById/"
				+ aliAccount.getApp_key();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("productId", productId);
		if (!aliCommonApiBiz.putSystemParamsToParamMap(paramMap, aliAccount)) {
			return null;
		}
		
		JSONObject respJson = HttpClientUtils.doPost(apiUrl, paramMap);
		if (respJson != null && !respJson.containsKey("error_code")) {
			return respJson;
		}
		return null;
	}
}
