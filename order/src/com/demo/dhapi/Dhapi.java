package com.demo.dhapi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import com.demo.utils.HttpClientUtils;

public class Dhapi {

	public static void main(String[] args) {

		HttpClient client = HttpClientUtils.getHttpClient();
		String csgServiceUrl = "http://api.dhgate.com/dop/router";
		PostMethod ps = new PostMethod(csgServiceUrl);
		String accessToken = "xeb0107hoKxEEIV4EkGpnIRK8ZROOzjXXA8beAz8";

		// msg_topic
		// NameValuePair[] param = getMsgTopicParam(accessToken);
		// msg_detail
		// NameValuePair[] param = getMsgDetailParam(accessToken);
		// user
		// NameValuePair[] param = getUserParam(accessToken);
		// orders
//		NameValuePair[] param = getOrdersParam(accessToken);
		// order
//		NameValuePair[] param = getOrderParam(accessToken);
		// order product
//		 NameValuePair[] param = getOrderProductParam(accessToken);
		// product
//		 NameValuePair[] param = getProductParam(accessToken);
		// product status
//		 NameValuePair[] param = getProductStatusParam(accessToken);
		// category
		 NameValuePair[] param =getCategoryParam(accessToken);
		// categorys
		// NameValuePair[] param =getCategorysParam(accessToken);
		// my products
		// NameValuePair[]param = getMyProductsParam(accessToken);

		ps.setRequestBody(param);

		try {
			System.out.println("请求开始时间:" + new Date());
			client.executeMethod(ps);
			String responseStr = ps.getResponseBodyAsString();
			System.out.println("响应消息: " + responseStr);
			System.out.println("响应时间:" + new Date());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ps.releaseConnection();
		}

	}

	public static NameValuePair[] getMsgTopicParam(String accessToken) {
		// 请求站内信topics
		NameValuePair[] param = {
				new NameValuePair("access_token", accessToken),
				new NameValuePair("method", "dh.messages.get"),
				new NameValuePair("timestamp", String.valueOf(new Date()
						.getTime())), new NameValuePair("v", "1.0"),
				new NameValuePair("appid", ""),
				new NameValuePair("beforeday", "5"),
				new NameValuePair("marked", ""),
				new NameValuePair("msgtype", "001,002"),
				new NameValuePair("orderField", ""),
				new NameValuePair("orderType", ""),
				new NameValuePair("pagenum", "1"),
				new NameValuePair("pagesize", "20"),
				new NameValuePair("recivereaded", ""),
				new NameValuePair("searchContent", ""),
				new NameValuePair("state", ""),
				new NameValuePair("systemuserid", "") };
		return param;
	}

	public NameValuePair[] getMsgDetailParam(String accessToken) {
		NameValuePair[] param = {
				new NameValuePair("access_token", accessToken),
				new NameValuePair("method", "dh.message.detail.get"),
				new NameValuePair("timestamp", String.valueOf(new Date()
						.getTime())), new NameValuePair("v", "1.0"),

				// new NameValuePair("appid", "NJkPGfFsLCchu4HEC7do"),
				new NameValuePair("messageTopicId", "244466401") };
		return param;
	}

	public NameValuePair[] getUserParam(String accessToken) {
		NameValuePair[] param = {
				new NameValuePair("access_token", accessToken),
				new NameValuePair("method", "dh.user.base.get"),
				new NameValuePair("timestamp", String.valueOf(new Date()
						.getTime())),
				new NameValuePair("v", "1.0"),
				new NameValuePair("systemuserid",
						"ff80808142fa73bf0143093544e150be") };
		return param;
	}

	public static NameValuePair[] getOrdersParam(String accessToken) {
		NameValuePair[] param = {
				new NameValuePair("access_token", accessToken),
				new NameValuePair("method", "dh.order.list.get"),
				new NameValuePair("timestamp", String.valueOf(new Date()
						.getTime())), new NameValuePair("v", "1.1"),
				new NameValuePair("enddate", "2014-03-20 11:25:00"),
				new NameValuePair("startdate", "2014-02-20 11:25:00"),
				new NameValuePair("pages", "1"),
				new NameValuePair("pagesize", "20"),
				new NameValuePair("statusList", "103001")};
		return param;
	}

	public static NameValuePair[] getOrderParam(String accessToken) {
		NameValuePair[] param = {
				new NameValuePair("access_token", accessToken),
				new NameValuePair("method", "dh.order.get"),
				new NameValuePair("timestamp", String.valueOf(new Date()
						.getTime())), new NameValuePair("v", "1.0"),
				new NameValuePair("rfxno", "1457546717") };
		return param;
	}

	public static NameValuePair[] getOrderProductParam(String accessToken) {
		NameValuePair[] param = {
				new NameValuePair("access_token", accessToken),
				new NameValuePair("method", "dh.order.product.get"),
				new NameValuePair("timestamp", String.valueOf(new Date()
						.getTime())), new NameValuePair("v", "1.0"),
				new NameValuePair("rfxid", "ff808081451c5572014523cd5c572b7e") };
		return param;
	}

	public static NameValuePair[] getProductParam(String accessToken) {
		NameValuePair[] param = {
				new NameValuePair("access_token", accessToken),
				new NameValuePair("method", "dh.product.get"),
				new NameValuePair("timestamp", String.valueOf(new Date()
						.getTime())), new NameValuePair("v", "1.0"),
				new NameValuePair("itemcode", "187984889") };
		return param;
	}
	
	public static NameValuePair[] getProductStatusParam(String accessToken) {
		NameValuePair[] param = {
				new NameValuePair("access_token", accessToken),
				new NameValuePair("method", "dh.product.state.get"),
				new NameValuePair("timestamp", String.valueOf(new Date()
						.getTime())), new NameValuePair("v", "1.0"),
				new NameValuePair("itemcode", "187985044") };
		return param;
	}

	public static NameValuePair[] getCategoryParam(String accessToken) {
		NameValuePair[] param = {
				new NameValuePair("access_token", accessToken),
				new NameValuePair("method", "dh.category.get"),
				new NameValuePair("timestamp", String.valueOf(new Date()
						.getTime())), new NameValuePair("v", "1.0"),
				new NameValuePair("catepubid", "000000") };
		return param;
	}

	public static NameValuePair[] getCategorysParam(String accessToken) {
		NameValuePair[] param = {
				new NameValuePair("access_token", accessToken),
				new NameValuePair("method", "dh.categorys.get"),
				new NameValuePair("timestamp", String.valueOf(new Date()
						.getTime())), new NameValuePair("v", "1.0"),
				new NameValuePair("parentid", "139") };
		return param;
	}

	public static NameValuePair[] getMyProductsParam(String accessToken) {
		NameValuePair[] param = {
				new NameValuePair("access_token", accessToken),
				new NameValuePair("method", "dh.products.get"),
				new NameValuePair("timestamp", String.valueOf(new Date()
						.getTime())),
				new NameValuePair("v", "1.0"),
				new NameValuePair("pages", "1"),
				new NameValuePair("pagesize", "5"),
				new NameValuePair(
						"productQueryVo",
						"{biztype:null,counterfeittypeids:[],expireDateEnd:null,expireDateStart:null,forceWithdrawalOnEnd:null,istates:[0],itemcodes:null,notGoldStall:null,notLocalReturn:null,operatedateEnd:null,operatedateStart:null,srhdateEnd:null,srhdateStart:null}") };
		return param;
	}
}
