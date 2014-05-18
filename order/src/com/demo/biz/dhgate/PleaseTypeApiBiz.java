package com.demo.biz.dhgate;

import java.applet.Applet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.OrderTableDao;
import com.demo.dao.ZhangHaoDao;
import com.demo.entity.ZhangHao;
import com.demo.entity.order.OrderTable;
import com.demo.utils.ApplicationUtils;
import com.demo.utils.DateUtils;
import com.demo.utils.HttpClientUtils;

@Service
@Transactional
public class PleaseTypeApiBiz {
	
	@Resource
	private DhCommonApiBiz dhCommonApiBiz;
	@Resource
	private OrderTableDao orderTableDao;
	private final Integer pageSize = 20;
	private Integer updateCount = 0;
	/**
	 * 同步订单数据(用于自动同步)
	 * @return
	 */
	public String autoPleaseTypeOrders(ZhangHao dhAccount) {
		String result = this.fetchOrders(dhAccount);
		return result;
	}	
	private String fetchOrders(ZhangHao dhAccount) {
		try {
			List<OrderTable> dd = orderTableDao.getDatas();//查询订单号
			String apiUrl = (String) ApplicationUtils.get("dhgateApiUrl");
			Map<String, String> paramMap = new HashMap<String, String>();
			if (!dhCommonApiBiz.putSystemParamsToParamMap(paramMap, dhAccount,
					"dh.order.money.get")) {
				return null;
			}
			String applyJson = null;
			if (dd.size() != 0) {
					 applyJson = "{\"rfxNo\" : '"+dd.get(0).getOrderId()+"'}";
					 paramMap.put("Apply", applyJson);
			}
			JSONObject respJson = HttpClientUtils.doPost(apiUrl, paramMap);
			if (respJson != null) {
				//updateOrder(dhAccount, respJson);
				if (dd.size() != 0) {
					orderTableDao.updateReceiving(dd.get(0).getId());
				}
				return "success";
				} else {
					return "发生错误：" ;
				}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "发生错误：" + DhCommonApiBiz.CONN_ERR;
	}
}
