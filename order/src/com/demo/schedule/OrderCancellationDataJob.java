package com.demo.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;

import com.demo.biz.aliexpress.AliCommonApiBiz;
import com.demo.biz.aliexpress.AliOrderApiBiz;
import com.demo.biz.dhgate.DhApplicationApiOrderBiz;
import com.demo.biz.dhgate.DhCommonApiBiz;
import com.demo.biz.dhgate.DhComplaintsApiOrderBiz;
import com.demo.biz.dhgate.DhDisputeTheOrderBiz;
import com.demo.biz.dhgate.DhMsgApiBiz;
import com.demo.biz.dhgate.DhOrderApiBiz;
import com.demo.biz.dhgate.DhRefundApiOrderBiz;
import com.demo.biz.dhgate.DhSuccessfulApiOrderBiz;
import com.demo.biz.dhgate.OrderCancellationApiOrderBiz;
import com.demo.dao.ZhangHaoDao;
import com.demo.entity.ZhangHao;

public class OrderCancellationDataJob implements InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		
	}

	@Resource
	private DhDisputeTheOrderBiz dhDisputeTheOrderBiz;
	@Resource
	private AliCommonApiBiz aliCommonApiBiz;
	@Resource
	private ZhangHaoDao zhangHaoDao;
	@Resource
	private DhRefundApiOrderBiz dhRefundApiOrderBiz;
	@Resource
	private OrderCancellationApiOrderBiz ordercancellationapiorderBiz;
	@Resource
	private DhComplaintsApiOrderBiz dhComplaintsApiOrderBiz;
	@Resource
	private DhApplicationApiOrderBiz dhApplicationApiOrderBiz;
	@Resource
	private DhSuccessfulApiOrderBiz dhSuccessfulApiOrderBiz;
	public void execute() {
		Integer curHour = Integer.parseInt(new SimpleDateFormat("HH").format(new Date()));
//		Integer curMin = Integer.parseInt(new SimpleDateFormat("mm").format(new Date()));

		/**
		 * 自动同步时间段：07:00到19:00
		 */
//		boolean synchData = false;
//		if (curHour >= 7 && curHour < 19) {
//			synchData = true;
//		}
//		if (!synchData) {
//			return;
//		}
		
		System.out.println("----同步敦煌买家取消退款申请订单数据----");
		try {
			List<ZhangHao> zhangHaos = zhangHaoDao.getAllZhangHao();
			for (ZhangHao zhangHao : zhangHaos) {
				if (zhangHao.getAccount_type().equals(DhCommonApiBiz.ACCOUNT_TYPE)) { // 敦煌账号
					// 同步订单数据
					String result = ordercancellationapiorderBiz.autoFetchOrderCancellationOrders(zhangHao);
					System.out.println("账号" + zhangHao.getAccount() + "订单数据同步结果: " + result);
				} 
			}
			
			// 每天的7点检查速卖通的refreshToken是否过期
			if (curHour == 7) {
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
