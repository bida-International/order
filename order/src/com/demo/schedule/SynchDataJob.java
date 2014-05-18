package com.demo.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;

import com.demo.biz.aliexpress.AliCommonApiBiz;
import com.demo.biz.aliexpress.AliOrderApiBiz;
import com.demo.biz.dhgate.DhCommonApiBiz;
import com.demo.biz.dhgate.DhMsgApiBiz;
import com.demo.biz.dhgate.DhOrderApiBiz;
import com.demo.dao.ZhangHaoDao;
import com.demo.entity.ZhangHao;

public class SynchDataJob implements InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		
	}

	@Resource
	private DhOrderApiBiz dhOrderApiBiz;
	@Resource
	private DhMsgApiBiz dhMsgApiBiz;
	@Resource
	private AliCommonApiBiz aliCommonApiBiz;
	@Resource
	private AliOrderApiBiz aliOrderApiBiz;
	@Resource
	private ZhangHaoDao zhangHaoDao;
	
	public void execute() {
		Integer curHour = Integer.parseInt(new SimpleDateFormat("HH").format(new Date()));
		
		System.out.println("----同步敦煌和速卖通数据----");
		try {
			// 每次同步5个账号的数据
			int topn = 5;
			List<ZhangHao> zhangHaos = zhangHaoDao.getTopnByOrderUpdateTime(topn);
			for (ZhangHao zhangHao : zhangHaos) {
				if (zhangHao.getAccount_type().equals(DhCommonApiBiz.ACCOUNT_TYPE)) { // 敦煌账号
					// 同步订单数据
					String result = dhOrderApiBiz.autoFetchOrders(zhangHao);
					System.out.println("账号" + zhangHao.getAccount() + "订单数据同步结果: " + result);
					// 同步站内信数据
					result = dhMsgApiBiz.autoFetchMsg(zhangHao);
					System.out.println("账号" + zhangHao.getAccount() + "站内信数据同步结果: " + result);
				} else if (zhangHao.getAccount_type().equals(AliCommonApiBiz.ACCOUNT_TYPE)) { // 速卖通账号
					// 同步订单数据
					String result = aliOrderApiBiz.autoFetchOrders(zhangHao);
					System.out.println("账号" + zhangHao.getAccount() + "订单数据同步结果: " + result);
				}
			}
			
			// 每天的6点和7点检查速卖通的refreshToken是否过期
			if (curHour == 6 || curHour == 7) {
				for (ZhangHao zhangHao : zhangHaos) {
					if (!zhangHao.getAccount_type().equals(AliCommonApiBiz.ACCOUNT_TYPE) ||
							zhangHao.getApp_key() == null ||
							zhangHao.getApp_key().equals("")) {
						continue;
					}
					
					aliCommonApiBiz.checkAndUpdateRefreshToken(zhangHao);
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
}
