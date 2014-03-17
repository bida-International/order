//package com.demo.biz.dhgate;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.annotation.Resource;
//
//import net.sf.json.JSONObject;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.demo.entity.ZhangHao;
//import com.demo.utils.ApplicationUtils;
//import com.demo.utils.HttpClientUtils;
//
//@Service
//@Transactional
//public class DhCategoryApiBiz {
//
//	@Resource
//	private DhCommonApiBiz dhCommonApiBiz;
//
//	/**
//	 * 获取一个类目信息
//	 * @param categoryId
//	 * @param dhAccount
//	 * @return
//	 */
//	public JSONObject getCategory(String categoryId, ZhangHao dhAccount) {
//		String apiUrl = (String) ApplicationUtils.get("dhgateApiUrl");
//		Map<String, String> paramMap = new HashMap<String, String>();
//		if (!dhCommonApiBiz.putSystemParamsToParamMap(paramMap, dhAccount,
//				"dh.category.get")) {
//			return null;
//		}
//		paramMap.put("catepubid", categoryId);
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
//}
