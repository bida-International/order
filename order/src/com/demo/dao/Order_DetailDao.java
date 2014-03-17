package com.demo.dao;

import java.util.List;

import com.demo.entity.order.Order_Detail;

public interface Order_DetailDao extends BaseDao<Order_Detail, Long>{
	//订单号查询全部
	public List<Order_Detail> getAllOrder(String order);
	//查询全部类目
	public String getAllCategory();
	//用编号查询全部
	public List<Order_Detail> getAllSelId(Long id);
}
