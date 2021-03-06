package com.demo.biz;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.biz.aliexpress.AliCommonApiBiz;
import com.demo.dao.OrderStatZhDao;
import com.demo.dao.ZhangHaoDao;
import com.demo.entity.ZhangHao;
import com.demo.entity.order.OrderStatZh;
import com.demo.utils.DateUtils;

/**
 * 账号订单统计数据服务
 *
 */
@Service
@Transactional
public class OrderStatZhBiz {

	private static int statActionflag = 0; // 1.表示正在执行统计任务
	
	@Resource
	private OrderStatZhDao orderStatZhDao;
	@Resource
	private ZhangHaoDao zhangHaoDao;
	
	private Double totalMoney;
	private Integer orderAmount;
	private Integer jiufenAmount;
	private Double jiufenRate;
	
	/**
	 * 更新所有账号统计数据
	 */
	public void doStatistic() {
		if (statActionflag == 1) {
			return;
		}
		
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
		
		System.out.println("----执行账号订单数据统计----");
		statActionflag = 1;
		try {
			List<ZhangHao> zhangHaos = zhangHaoDao.getAllZhangHao();
			while (DateUtils.getIntervalDays(statDate, endDate) >= 0
					&& statDate.getTime() <= endDate.getTime()) {
				System.out.println("正在统计" + new SimpleDateFormat("yyyy-MM-dd").format(statDate)
						+ "的账号订单数据...");
				for (ZhangHao zhangHao : zhangHaos) {
					this.updateStatData(statDate, zhangHao);
				}
				statDate = DateUtils.getAfterDaysDate(statDate, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			statActionflag = 0;
		}
	}
	
	/**
	 * 更新一个账号某一天(相关日周月)的订单统计数据
	 * @param statDate
	 * @param zhanghaoId
	 * @param accountType
	 */
	public void updateStatData(Date statDate, ZhangHao zhangHao) {
		this.statByDay(statDate, zhangHao);
		this.statByWeek(statDate, zhangHao);
		this.statByMonth(statDate, zhangHao);
	}
	
	/**
	 * 统计一个账号一天的订单数据
	 * @param statDate
	 * @param zhanghaoId
	 * @param accountType
	 */
	private void statByDay(Date statDate, ZhangHao zhangHao) {
		Date statBeginTime = DateUtils.getDayStart(statDate);
		Date statEndTime = DateUtils.getDayEnd(statDate);
		
		// 统计数据
		this.initStatParams();
		this.setStatResult(zhangHao, statBeginTime, statEndTime);
		
		// 保存更新
		OrderStatZh dayStat = orderStatZhDao.findDayStat(zhangHao.getId(), statBeginTime);
		if (dayStat == null) {
			dayStat = new OrderStatZh();
			dayStat.setStatType(1);
			dayStat.setZhanghaoId(zhangHao.getId());
			dayStat.setZhanghaoAccount(zhangHao.getAccount());
			dayStat.setStatYear(DateUtils.getYear(statDate));
			dayStat.setStatDate(statBeginTime);
			dayStat.setStatBeginDate(statBeginTime);
			dayStat.setStatEndDate(statEndTime);
		}
		dayStat.setTotalMoney(totalMoney);
		dayStat.setOrderAmount(orderAmount);
		dayStat.setJiufenAmount(jiufenAmount);
		dayStat.setJiufenRate(jiufenRate);
		orderStatZhDao.merge(dayStat);
	}
	
	/**
	 * 统计一个账号一周的订单数据
	 * @param statDate
	 * @param zhanghaoId
	 * @param accountType
	 */
	private void statByWeek(Date statDate, ZhangHao zhangHao) {
		Date statBeginTime = DateUtils.getDayStart(DateUtils.getFirstDayOfWeek(statDate));
		Date statEndTime = DateUtils.getDayEnd(DateUtils.getLastDayOfWeek(statDate));
		
		// 统计数据
		this.initStatParams();
		this.setStatResult(zhangHao, statBeginTime, statEndTime);
		
		// 更新数据
		Integer year = DateUtils.getYear(statDate);
		Integer week = DateUtils.getWeek(statDate);
		OrderStatZh weekStat = orderStatZhDao.findWeekStat(zhangHao.getId(), year, week);
		if (weekStat == null) {
			weekStat = new OrderStatZh();
			weekStat.setStatType(2);
			weekStat.setZhanghaoId(zhangHao.getId());
			weekStat.setZhanghaoAccount(zhangHao.getAccount());
			weekStat.setStatYear(year);
			weekStat.setStatWeek(week);
			weekStat.setStatBeginDate(statBeginTime);
			weekStat.setStatEndDate(statEndTime);
		}
		weekStat.setTotalMoney(totalMoney);
		weekStat.setOrderAmount(orderAmount);
		weekStat.setJiufenAmount(jiufenAmount);
		weekStat.setJiufenRate(jiufenRate);
		orderStatZhDao.merge(weekStat);
	}
	
	/**
	 * 统计一个账号一月的订单数据
	 * @param statDate
	 * @param zhanghaoId
	 * @param accountType
	 */
	private void statByMonth(Date statDate, ZhangHao zhangHao) {
		Date statBeginTime = DateUtils.getDayStart(DateUtils.getFirstDayOfMonth(statDate));
		Date statEndTime = DateUtils.getDayEnd(DateUtils.getLastDayOfMonth(statDate));

		// 统计数据
		this.initStatParams();
		this.setStatResult(zhangHao, statBeginTime, statEndTime);
		
		// 更新数据
		Integer year = DateUtils.getYear(statDate);
		Integer month = DateUtils.getMonth(statDate);
		Integer statMonth = null;
		if (month < 10) {
			statMonth = Integer.parseInt(year.toString() + "0" + month.toString());
		} else {
			statMonth = Integer.parseInt(year.toString() + month.toString());
		}
		OrderStatZh monthStat = orderStatZhDao.findMonthStat(zhangHao.getId(), year, statMonth);
		if (monthStat == null) {
			monthStat = new OrderStatZh();
			monthStat.setStatType(3);
			monthStat.setZhanghaoId(zhangHao.getId());
			monthStat.setZhanghaoAccount(zhangHao.getAccount());
			monthStat.setStatYear(year);
			monthStat.setStatMonth(statMonth);
			monthStat.setStatBeginDate(statBeginTime);
			monthStat.setStatEndDate(statEndTime);
		}
		monthStat.setTotalMoney(totalMoney);
		monthStat.setOrderAmount(orderAmount);
		monthStat.setJiufenAmount(jiufenAmount);
		monthStat.setJiufenRate(jiufenRate);
		orderStatZhDao.merge(monthStat);
	}
	
	private void initStatParams() {
		totalMoney = 0d;
		orderAmount = 0;
		jiufenAmount = 0;
		jiufenRate = 0d;
	}
	
	/**
	 * 查询统计结果并设置
	 * @param zhanghaoId
	 * @param accountType
	 * @param statBeginTime
	 * @param statEndTime
	 * @param totalMoney
	 * @param orderAmount
	 * @param jiufenAmount
	 * @param jiufenRate
	 */
	private void setStatResult(ZhangHao zhangHao, Date statBeginTime, 
			Date statEndTime) {
		orderAmount = orderStatZhDao.getOrderAmount(zhangHao.getId(), statBeginTime, statEndTime);
		if (orderAmount == 0) {
			return;
		}
		
		totalMoney = orderStatZhDao.getTotalMoney(zhangHao.getId(), statBeginTime, statEndTime);
		if (zhangHao.getAccount_type().equals(AliCommonApiBiz.ACCOUNT_TYPE)) {
			totalMoney = Double.parseDouble(new DecimalFormat("#0.000").format(totalMoney));
		} else {
			totalMoney = Double.parseDouble(new DecimalFormat("#0.00").format(totalMoney));
		}
		jiufenAmount = orderStatZhDao.getJiufenAmount(zhangHao.getId(), statBeginTime, statEndTime);
		jiufenRate = Double.parseDouble(jiufenAmount.toString()) /
				Double.parseDouble(orderAmount.toString()) * 100;
		jiufenRate = Double.parseDouble(new DecimalFormat("#0.00").format(jiufenRate));
	}
}
