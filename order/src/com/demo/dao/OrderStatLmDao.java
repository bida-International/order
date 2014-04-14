package com.demo.dao;

import java.util.Date;
import java.util.List;

import com.demo.entity.order.OrderStatLm;

public interface OrderStatLmDao extends BaseDao<OrderStatLm, Long> {

	// 获取查询时间段内的订单总数
	public Integer getOrderAmount(Long leimuId, Date beginTime, Date endTime);
	// 获取查询时间段内的订单总金额
	public Double getTotalMoney(Long leimuId, Date beginTime, Date endTime);
	// 获取查询时间段内的纠纷订单总数
	public Integer getJiufenAmount(Long leimuId, Date beginTime, Date endTime);
	
	// 获取按天统计的数据
	public OrderStatLm findDayStat(Long leimuId, Date statDate);
	// 获取按周统计的数据
	public OrderStatLm findWeekStat(Long leimuId, Integer year, Integer week);
	// 获取按月统计的数据
	public OrderStatLm findMonthStat(Long leimuId, Integer year, Integer month);
	
	// 获取最新一条统计数据统计的日期
	public Date findNewestDayStatDate();
	// 获取最早一条订单的上传时间
	public Date findEarliestOrderDate();
	
	// 获取按账号分组的统计数据
	public List getGroupListByLeimu();
	// 获取按账号分组的一段时间内统计数据
	public List getGroupListByLeimu(Integer beginMonth, Integer endMonth);
	
	// 获取统计数据
	public List<OrderStatLm> getStatList(Long leimuId, Integer statType, Date beginTime, Date endTime);
}
