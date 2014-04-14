package com.demo.schedule;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;

import com.demo.biz.OrderStatLmBiz;
import com.demo.biz.OrderStatZhBiz;

/**
 * 执行统计定时任务
 *
 */
public class OrderStatJob implements InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
	}

	@Resource
	private OrderStatZhBiz orderStatZhBiz;
	@Resource
	private OrderStatLmBiz orderStatLmBiz;
	
	public void execute() {
		this.orderStatZhBiz.doStatistic(); // 执行账号订单数据统计
		this.orderStatLmBiz.doStatistic(); // 执行类目订单数据统计
	}
	
}
