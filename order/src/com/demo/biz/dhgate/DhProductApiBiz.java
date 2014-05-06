package com.demo.biz.dhgate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.demo.entity.ZhangHao;
import com.demo.utils.ApplicationUtils;
import com.demo.utils.HttpClientUtils;
import com.demo.vo.dhgate.Product;

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
	
	/**
	 * ��ȡ��Ʒ���б�
	 * @param dhAccount
	 * @return
	 */
	public JSONObject getGroups(ZhangHao dhAccount) {
		String apiUrl = (String) ApplicationUtils.get("dhgateApiUrl");
		Map<String, String> paramMap = new HashMap<String, String>();
		if (!dhCommonApiBiz.putSystemParamsToParamMap(paramMap, dhAccount,
				"dh.product.groups.get")) {
			return null;
		}
		paramMap.put("pageNo", "1");
		paramMap.put("pageSize", "20");
		
		String queryString = "";
		Iterator<String> it = paramMap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			if (!queryString.equals("")) {
				queryString += "&";
			}
			queryString += key  + "=" + paramMap.get(key);
		}
		
		String url = apiUrl + "?" + queryString;
		JSONObject respJson = HttpClientUtils.doGet(url);
		//JSONObject respJson = HttpClientUtils.doPost(apiUrl, paramMap);
		if (respJson != null) {
			if (!respJson.containsKey("status")) {
				return null;
			}
			
			JSONObject statusObj = respJson.getJSONObject("status");
			if (statusObj.getString("message").equalsIgnoreCase("OK")) {
				return respJson;
			}
		}
		return null;
	}
	
	/**
	 * ������Ʒ
	 * @param product
	 * @param dhAccount
	 * @return ����null��ʾ����ʧ��
	 */
	public JSONObject add(Product product, ZhangHao dhAccount) {
		String apiUrl = (String) ApplicationUtils.get("dhgateApiUrl");
		Map<String, String> paramMap = new HashMap<String, String>();
		if (!dhCommonApiBiz.putSystemParamsToParamMap(paramMap, dhAccount,
				"dh.product.add", "1.1")) {
			return null;
		}
		paramMap.put("product", JSONObject.fromObject(product).toString());
		JSONObject respJson = HttpClientUtils.doPost(apiUrl, paramMap);
		if (respJson != null) {
			System.out.println(respJson.toString());
			if (respJson.containsKey("code")) {
				if (respJson.getString("code").equals("2") || 
						respJson.getString("code").equals("40")) {
					dhCommonApiBiz.clearAccessToken(dhAccount);
					return null;
				}
			}
			
			JSONObject statusObj = respJson.getJSONObject("status");
			if (statusObj.getString("message").equalsIgnoreCase("OK") &&
					Integer.parseInt(statusObj.getString("code")) == 0) {
				return respJson;
			}
		}
		return null;
	}
}
