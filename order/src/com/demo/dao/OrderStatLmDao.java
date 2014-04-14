package com.demo.dao;

import java.util.Date;
import java.util.List;

import com.demo.entity.order.OrderStatLm;

public interface OrderStatLmDao extends BaseDao<OrderStatLm, Long> {

	// ��ȡ��ѯʱ����ڵĶ�������
	public Integer getOrderAmount(Long leimuId, Date beginTime, Date endTime);
	// ��ȡ��ѯʱ����ڵĶ����ܽ��
	public Double getTotalMoney(Long leimuId, Date beginTime, Date endTime);
	// ��ȡ��ѯʱ����ڵľ��׶�������
	public Integer getJiufenAmount(Long leimuId, Date beginTime, Date endTime);
	
	// ��ȡ����ͳ�Ƶ�����
	public OrderStatLm findDayStat(Long leimuId, Date statDate);
	// ��ȡ����ͳ�Ƶ�����
	public OrderStatLm findWeekStat(Long leimuId, Integer year, Integer week);
	// ��ȡ����ͳ�Ƶ�����
	public OrderStatLm findMonthStat(Long leimuId, Integer year, Integer month);
	
	// ��ȡ����һ��ͳ������ͳ�Ƶ�����
	public Date findNewestDayStatDate();
	// ��ȡ����һ���������ϴ�ʱ��
	public Date findEarliestOrderDate();
	
	// ��ȡ���˺ŷ����ͳ������
	public List getGroupListByLeimu();
	// ��ȡ���˺ŷ����һ��ʱ����ͳ������
	public List getGroupListByLeimu(Integer beginMonth, Integer endMonth);
	
	// ��ȡͳ������
	public List<OrderStatLm> getStatList(Long leimuId, Integer statType, Date beginTime, Date endTime);
}
