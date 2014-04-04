package com.demo.biz;

import java.text.DecimalFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.biz.aliexpress.AliCommonApiBiz;
import com.demo.dao.OrderStatZhDao;
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

	@Resource
	private OrderStatZhDao orderStatZhDao;
	
	private Double totalMoney;
	private Integer orderAmount;
	private Integer jiufenAmount;
	private Double jiufenRate;
	
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
		OrderStatZh monthStat = orderStatZhDao.findMonthStat(zhangHao.getId(), year, month);
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
