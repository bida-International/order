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
public class AliCategoryApiBiz {
	@Resource
	private AliCommonApiBiz aliCommonApiBiz;
	
	/**
	 * 取一个产品的详情
	 * @param productId
	 * @param aliAccount
	 * @return
	 */
	public JSONObject getCategorys(ZhangHao aliAccount, String cateId) {
		String apiUrl = ApplicationUtils.get("aliApiUrl") 
				+ "param2/1/aliexpress.open/api.getChildrenPostCategoryById/"
				+ aliAccount.getApp_key();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("cateId", cateId);
		if (!aliCommonApiBiz.putSystemParamsToParamMap(paramMap, aliAccount)) {
			return null;
		}
		
		JSONObject respJson = HttpClientUtils.doPost(apiUrl, paramMap);
		if (respJson != null) {
			System.out.println(respJson.toString());
			return respJson;
		}
		return null;
	}
}
