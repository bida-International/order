package com.demo.utils;

import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
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
	 * ∑¢ÀÕpost«Î«Û
	 * @param requestXmlStr
	 * @return
	 */
	public static JSONObject doPost(String postUrl, Map<String, String> paramMap) {
		HttpClient client = getHttpClient();
		PostMethod ps = new PostMethod(postUrl);
		NameValuePair[] param = new NameValuePair[paramMap.size()];
		int i = 0;
		Iterator<String> it = paramMap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			param[i] = new NameValuePair(key, paramMap.get(key));
			i++;
		}
		ps.setRequestBody(param);
		JSONObject returnJson = null;
		try {
			client.executeMethod(ps);
			String responseStr = ps.getResponseBodyAsString();
			returnJson = JSONObject.fromObject(responseStr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ps.releaseConnection();
		}
		return returnJson;
	}
}
