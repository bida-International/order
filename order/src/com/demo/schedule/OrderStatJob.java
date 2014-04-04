package com.demo.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;

import com.demo.biz.OrderStatZhBiz;
import com.demo.dao.OrderStatZhDao;
import com.demo.dao.ZhangHaoDao;
import com.demo.entity.ZhangHao;
import com.demo.utils.DateUtils;

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
	private OrderStatZhDao orderStatZhDao;
	@Resource
	private ZhangHaoDao zhangHaoDao;
	
	public void execute() {
		Date curDate = new Date();

		/**
		 * ͳ����ʼ����
		 * ��ʼ�Ӷ�����������һ���������ϴ����ڿ�ʼ,
		 * ֮���ͳ�Ʊ������һ������ͳ�Ƶ����ڿ�ʼ
		 */
		Date statDate = null;
		statDate = orderStatZhDao.findNewestDayStatDate();
		if (statDate == null) {
			statDate = orderStatZhDao.findEarliestOrderDate();
		}
		
		Date endDate = curDate;
		
		System.out.println("----ִ�ж�������ͳ��----");
		try {
			List<ZhangHao> zhangHaos = zhangHaoDao.getAllZhangHao();
			while (DateUtils.getIntervalDays(statDate, endDate) >= 0
					&& statDate.getTime() <= endDate.getTime()) {
				System.out.println("����ͳ��" + new SimpleDateFormat("yyyy-MM-dd").format(statDate)
						+ "�Ķ�������...");
				for (ZhangHao zhangHao : zhangHaos) {
					orderStatZhBiz.updateStatData(statDate, zhangHao);
				}
				statDate = DateUtils.getAfterDaysDate(statDate, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
