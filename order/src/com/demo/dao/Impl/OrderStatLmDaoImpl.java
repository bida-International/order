package com.demo.dao.Impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.OrderStatLmDao;
import com.demo.entity.order.OrderStatLm;

@Repository
public class OrderStatLmDaoImpl extends BaseDaoImpl<OrderStatLm, Long> implements OrderStatLmDao {

	public OrderStatLmDaoImpl() {
		super(OrderStatLm.class);
	}
	
	public Integer getOrderAmount(Long leimuId, Date beginTime, Date endTime) {
		Long orderAmount = (Long) ht.findFirst("select count(*) from OrderTable where leimuid = ? and time >= ? and time <= ?",
				new Object[] {leimuId, beginTime, endTime});
		if (orderAmount == null) {
			return null;
		}
		return orderAmount.intValue();
	}

	public Double getTotalMoney(Long leimuId, Date beginTime, Date endTime) {
		Double totalMoney = (Double) ht.findFirst("select sum(money) from OrderTable where leimuid = ? and time >= ? and time <= ?",
				new Object[] {leimuId, beginTime, endTime});
		if (totalMoney == null) {
			return 0d;
		}
		return totalMoney;
	}

	public Integer getJiufenAmount(Long leimuId, Date beginTime, Date endTime) {
		Long jiufenAmount = (Long) ht.findFirst("select count(*) from OrderTable where leimuid = ? and jiufen = 1 and time >= ? and time <= ?",
				new Object[] {leimuId, beginTime, endTime});
		if (jiufenAmount == null) {
			return null;
		}
		return jiufenAmount.intValue();
	}

	public OrderStatLm findDayStat(Long leimuId, Date statDate) {
		return ht.findFirst("from OrderStatLm where leimuId = ? and statType = 1 and statDate = ?", 
				new Object[] {leimuId, statDate});
	}

	public OrderStatLm findWeekStat(Long leimuId, Integer year, Integer week) {
		return ht.findFirst("from OrderStatLm where leimuId = ? and statType = 2 and statYear = ? and statWeek = ?", 
				new Object[] {leimuId, year, week});
	}

	public OrderStatLm findMonthStat(Long leimuId, Integer year, Integer month) {
		return ht.findFirst("from OrderStatLm where leimuId = ? and statType = 3 and statYear = ? and statMonth = ?", 
				new Object[] {leimuId, year, month});
	}
	
	public Date findNewestDayStatDate() {
		return ht.findFirst("select max(statDate) from OrderStatLm where statType = 1 and statDate is not null", null);
	}
	
	public Date findEarliestOrderDate() {
		return ht.findFirst("select min(time) from OrderTable where time is not null", null);
	}

	@Override
	public List getGroupListByLeimu() {
		String hql = "select sum(orderAmount), sum(totalMoney), sum(jiufenAmount), leimuId, leimuName from OrderStatLm"
				+ " where statType = 3 group by leimuId, leimuName";
		return ht.find(hql);
	}

	@Override
	public List getGroupListByLeimu(Integer beginMonth, Integer endMonth) {
		String hql = "select sum(orderAmount), sum(totalMoney), sum(jiufenAmount), leimuId, leimuName from OrderStatLm"
				+ " where statType = 3" 
				+ " and statMonth >= " + beginMonth
				+ " and statMonth <= " + endMonth
				+ " group by leimuId, leimuName";
		return ht.find(hql);
	}
	
	public List<OrderStatLm> getStatList(Long leimuId, Integer statType, Date beginTime, Date endTime) {
		String hql = "from OrderStatLm where leimuId = ?"
				+ " and statType = ? and statBeginDate >= ? and statEndDate <= ? order by id";
		return ht.find(hql, new Object[]{leimuId, statType, beginTime, endTime});
	}

}
