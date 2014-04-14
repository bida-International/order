package com.demo.schedule;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;

import com.demo.biz.OrderStatLmBiz;
import com.demo.biz.OrderStatZhBiz;

/**
 * ִ��ͳ�ƶ�ʱ����
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
		this.orderStatZhBiz.doStatistic(); // ִ���˺Ŷ�������ͳ��
		this.orderStatLmBiz.doStatistic(); // ִ����Ŀ��������ͳ��
	}
	
}
