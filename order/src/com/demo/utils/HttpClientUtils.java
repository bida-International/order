package com.demo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.LoggerFactory;

public class HttpClientUtils {
	
	public static Integer dhApiReqCount = 0; // 敦煌api请求数
	public static Integer aliApiReqCount = 0; // 速卖通api请求数
	public static String lastReqCountUpdateDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date()); // 请求数更新日期 yyyy-MM-dd
	
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
			LoggerFactory.getLogger(HttpClientUtils.class).error(e.toString(), e.fillInStackTrace());
		} finally {
			httpMethod.releaseConnection();
			updateApiReqCount(url); // 更新记数器
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
			LoggerFactory.getLogger(HttpClientUtils.class).error(e.toString(), e.fillInStackTrace());
		} finally {
			httpMethod.releaseConnection();
		}
		return returnJson;
	}
	
	/**
	 * 请求网页
	 * @param url
	 * @return
	 */
	public static String doGetHtml(String url) {
		HttpClient client = getHttpClient();
		GetMethod httpMethod = new GetMethod(url);
		String htmlStr = null;
		try {
			client.executeMethod(httpMethod);
			htmlStr = httpMethod.getResponseBodyAsString();
		} catch (Exception e) {
			LoggerFactory.getLogger(HttpClientUtils.class).error(e.toString(), e.fillInStackTrace());
		} finally {
			httpMethod.releaseConnection();
		}
		return htmlStr;
	}
	
	/**
	 * 更新请求数
	 * @param apiUrl
	 */
	private static void updateApiReqCount(String apiUrl) {
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		if (!lastReqCountUpdateDate.equals(date)) { // 新一天, 重置请求数
			dhApiReqCount = 0;
			aliApiReqCount = 0;
			lastReqCountUpdateDate = date;
		}
		
		if (apiUrl.indexOf((String) ApplicationUtils.get("dhgateApiUrl")) >= 0) {
			dhApiReqCount++;
		} else if (apiUrl.indexOf((String) ApplicationUtils.get("dhgateGetTokenUrl")) >= 0) {
			dhApiReqCount++;
		} else if (apiUrl.indexOf((String) ApplicationUtils.get("aliApiUrl")) >= 0) {
			aliApiReqCount++;
		}
	}
}
