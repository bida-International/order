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
	private OrderStatZhDao orderStatZhDao;
	@Resource
	private ZhangHaoDao zhangHaoDao;
	
	public void execute() {
		Date curDate = new Date();

		/**
		 * 统计起始日期
		 * 初始从订单表里最早一条订单的上传日期开始,
		 * 之后从统计表的最新一条数据统计的日期开始
		 */
		Date statDate = null;
		statDate = orderStatZhDao.findNewestDayStatDate();
		if (statDate == null) {
			statDate = orderStatZhDao.findEarliestOrderDate();
		}
		
		Date endDate = curDate;
		
		System.out.println("----执行订单数据统计----");
		try {
			List<ZhangHao> zhangHaos = zhangHaoDao.getAllZhangHao();
			while (DateUtils.getIntervalDays(statDate, endDate) >= 0
					&& statDate.getTime() <= endDate.getTime()) {
				System.out.println("正在统计" + new SimpleDateFormat("yyyy-MM-dd").format(statDate)
						+ "的订单数据...");
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
