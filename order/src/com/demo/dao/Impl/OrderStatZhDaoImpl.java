package com.demo.dao.Impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.OrderStatZhDao;
import com.demo.entity.order.OrderStatZh;

/**
 * 账号订单统计数据操作
 *
 */
@Repository
public class OrderStatZhDaoImpl extends BaseDaoImpl<OrderStatZh, Long> implements
		OrderStatZhDao {

	public OrderStatZhDaoImpl() {
		super(OrderStatZh.class);
	}
	
	public Integer getOrderAmount(Long zhanghaoId, Date beginTime, Date endTime) {
		Long orderAmount = (Long) ht.findFirst("select count(*) from OrderTable where zhanghaoId = ? and time >= ? and time <= ?",
				new Object[] {zhanghaoId, beginTime, endTime});
		if (orderAmount == null) {
			return null;
		}
		return orderAmount.intValue();
	}

	public Double getTotalMoney(Long zhanghaoId, Date beginTime, Date endTime) {
		Double totalMoney = (Double) ht.findFirst("select sum(money) from OrderTable where zhanghaoId = ? and time >= ? and time <= ?",
				new Object[] {zhanghaoId, beginTime, endTime});
		if (totalMoney == null) {
			return 0d;
		}
		return totalMoney;
	}

	public Integer getJiufenAmount(Long zhanghaoId, Date beginTime, Date endTime) {
		Long jiufenAmount = (Long) ht.findFirst("select count(*) from OrderTable where zhanghaoId = ? and jiufen = 1 and time >= ? and time <= ?",
				new Object[] {zhanghaoId, beginTime, endTime});
		if (jiufenAmount == null) {
			return null;
		}
		return jiufenAmount.intValue();
	}

	public OrderStatZh findDayStat(Long zhanghaoId, Date statDate) {
		return ht.findFirst("from OrderStatZh where zhanghaoId = ? and statType = 1 and statDate = ?", 
				new Object[] {zhanghaoId, statDate});
	}

	public OrderStatZh findWeekStat(Long zhanghaoId, Integer year, Integer week) {
		return ht.findFirst("from OrderStatZh where zhanghaoId = ? and statType = 2 and statYear = ? and statWeek = ?", 
				new Object[] {zhanghaoId, year, week});
	}

	public OrderStatZh findMonthStat(Long zhanghaoId, Integer year, Integer month) {
		return ht.findFirst("from OrderStatZh where zhanghaoId = ? and statType = 3 and statYear = ? and statMonth = ?", 
				new Object[] {zhanghaoId, year, month});
	}
	
	public Date findNewestDayStatDate() {
		return ht.findFirst("select max(statDate) from OrderStatZh where statType = 1 and statDate is not null", null);
	}
	
	public Date findEarliestOrderDate() {
		return ht.findFirst("select min(time) from OrderTable where time is not null", null);
	}

	@Override
	public List getGroupListByZhangHao() {
		String hql = "select sum(orderAmount), sum(totalMoney), sum(jiufenAmount), zhanghaoId, zhanghaoAccount from OrderStatZh"
				+ " where statType = 3 group by zhanghaoId, zhanghaoAccount";
		return ht.find(hql);
	}

	@Override
	public List getGroupListByZhangHao(Integer beginMonth, Integer endMonth) {
		String hql = "select sum(orderAmount), sum(totalMoney), sum(jiufenAmount), zhanghaoId, zhanghaoAccount from OrderStatZh"
				+ " where statType = 3" 
				+ " and statMonth >= " + beginMonth
				+ " and statMonth <= " + endMonth
				+ " group by zhanghaoId, zhanghaoAccount";
		return ht.find(hql);
	}
}
