package com.demo.utils;

import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

public class HttpClientUtils {
	
	private static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
	
	static {
		connectionManager.getParams().setConnectionTimeout(30000);
		connectionManager.getParams().setSoTimeout(30000);
		connectionManager.getParams().setDefaultMaxConnectionsPerHost(32);
		connectionManager.getParams().setMaxTotalConnections(128);
		connectionManager.getParams().setBooleanParameter("http.protocol.expect-continue", false);
	}
	
	public static HttpClient getHttpClient() {
		return new HttpClient(connectionManager);
	}
	
	/**
	 * 发送post请求
	 * @param url 请求地址
	 * @param paramMap 请求参数 
	 * @return
	 */
	public static JSONObject doPost(String url, Map<String, String> paramMap) {
		HttpClient client = getHttpClient();
		PostMethod httpMethod = new PostMethod(url);
		NameValuePair[] param = new NameValuePair[paramMap.size()];
		int i = 0;
		Iterator<String> it = paramMap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			param[i] = new NameValuePair(key, paramMap.get(key));
			i++;
		}
		httpMethod.setRequestBody(param);
		JSONObject returnJson = null;
		try {
			client.executeMethod(httpMethod);
			String responseStr = httpMethod.getResponseBodyAsString();
			returnJson = JSONObject.fromObject(responseStr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpMethod.releaseConnection();
		}
		return returnJson;
	}
	
	/**
	 * 发送get请求
	 * @param url 请求地址
	 * @return
	 */
	public static JSONObject doGet(String url) {
		HttpClient client = getHttpClient();
		GetMethod httpMethod = new GetMethod(url);
		JSONObject returnJson = null;
		try {
			client.executeMethod(httpMethod);
			String responseStr = httpMethod.getResponseBodyAsString();
			returnJson = JSONObject.fromObject(responseStr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpMethod.releaseConnection();
		}
		return returnJson;
	}
}
