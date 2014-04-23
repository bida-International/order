package com.demo.biz.dhgate;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.demo.entity.ZhangHao;
import com.demo.utils.ApplicationUtils;
import com.demo.utils.HttpClientUtils;

@Service
public class DhProductApiBiz {

	@Resource
	private DhCommonApiBiz dhCommonApiBiz;
	
	/**
	 * ȡһ����Ʒ������
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
			if (respJson.containsKey("code")) {
				if (respJson.getString("code").equals("2") || 
						respJson.getString("code").equals("40")) {
					dhCommonApiBiz.clearAccessToken(dhAccount);
					return null;
				}
			}
			
			JSONObject statusObj = respJson.getJSONObject("status");
			if (statusObj.getString("message").equalsIgnoreCase("OK")) {
				return respJson;
			}
		}
		return null;
	}
	
	/**
	 * ��ȡ��Ʒ״̬
	 * @param itemCode
	 * @param dhAccount
	 * @return null-��ȡʧ��, 0-�¼�, 1-�ϼ�, 2-ɾ��
	 */
	public Integer getProductState(String itemCode, ZhangHao dhAccount) {
		String apiUrl = (String) ApplicationUtils.get("dhgateApiUrl");
		Map<String, String> paramMap = new HashMap<String, String>();
		if (!dhCommonApiBiz.putSystemParamsToParamMap(paramMap, dhAccount,
				"dh.product.state.get")) {
			return null;
		}
		paramMap.put("itemcode", itemCode);
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
				return respJson.getJSONObject("productState").getInt("istate");
			}
		}
		return null;
	}
	
	/**
	 * �����¼ܲ�Ʒ
	 * @param itemcodeList
	 * @param dhAccount
	 * @return
	 */
	public boolean downshelfProducts(String[] itemcodeList, ZhangHao dhAccount) {
		String apiUrl = (String) ApplicationUtils.get("dhgateApiUrl");
		Map<String, String> paramMap = new HashMap<String, String>();
		if (!dhCommonApiBiz.putSystemParamsToParamMap(paramMap, dhAccount,
				"dh.products.downshelf")) {
			return false;
		}
		paramMap.put("itemcodeList", JSONArray.fromObject(itemcodeList).toString());
		paramMap.put("withdrawaltype", "1");
		JSONObject respJson = HttpClientUtils.doPost(apiUrl, paramMap);
		if (respJson != null) {
			if (respJson.containsKey("code") && !respJson.getString("code").equals("0")) {
				return false;
			}
			
			JSONObject statusObj = respJson.getJSONObject("status");
			if (statusObj.getString("message").equalsIgnoreCase("OK")) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * �����ϼܲ�Ʒ
	 * @param itemcodeList
	 * @param dhAccount
	 * @return
	 */
	public boolean upshelfProducts(String[] itemcodeList, ZhangHao dhAccount) {
		String apiUrl = (String) ApplicationUtils.get("dhgateApiUrl");
		Map<String, String> paramMap = new HashMap<String, String>();
		if (!dhCommonApiBiz.putSystemParamsToParamMap(paramMap, dhAccount,
				"dh.products.upshelf")) {
			return false;
		}
		paramMap.put("itemcodeList", JSONArray.fromObject(itemcodeList).toString());
		JSONObject respJson = HttpClientUtils.doPost(apiUrl, paramMap);
		if (respJson != null) {
			if (respJson.containsKey("code") && !respJson.getString("code").equals("0")) {
				return false;
			}
			
			JSONObject statusObj = respJson.getJSONObject("status");
			if (statusObj.getString("message").equalsIgnoreCase("OK")) {
				return true;
			}
		}
		return false;
	}
}
