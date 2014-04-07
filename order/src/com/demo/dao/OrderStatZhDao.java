package com.demo.dao;

import java.util.Date;
import java.util.List;

import com.demo.entity.order.OrderStatZh;

public interface OrderStatZhDao extends BaseDao<OrderStatZh, Long> {

	// ��ȡ��ѯʱ����ڵĶ�������
	public Integer getOrderAmount(Long zhanghaoId, Date beginTime, Date endTime);
	// ��ȡ��ѯʱ����ڵĶ����ܽ��
	public Double getTotalMoney(Long zhanghaoId, Date beginTime, Date endTime);
	// ��ȡ��ѯʱ����ڵľ��׶�������
	public Integer getJiufenAmount(Long zhanghaoId, Date beginTime, Date endTime);
	
	// ��ȡ����ͳ�Ƶ�����
	public OrderStatZh findDayStat(Long zhanghaoId, Date statDate);
	// ��ȡ����ͳ�Ƶ�����
	public OrderStatZh findWeekStat(Long zhanghaoId, Integer year, Integer week);
	// ��ȡ����ͳ�Ƶ�����
	public OrderStatZh findMonthStat(Long zhanghaoId, Integer year, Integer month);
	
	// ��ȡ����һ��ͳ������ͳ�Ƶ�����
	public Date findNewestDayStatDate();
	// ��ȡ����һ���������ϴ�ʱ��
	public Date findEarliestOrderDate();
	
	// ��ȡ���˺ŷ����ͳ������
	public List getGroupListByZhangHao();
	// ��ȡ���˺ŷ����һ��ʱ����ͳ������
	public List getGroupListByZhangHao(Integer beginMonth, Integer endMonth);
	
	// ��ȡͳ������
	public List<OrderStatZh> getStatList(Long zhanghaoId, Integer statType, Date beginTime, Date endTime);
}
