package com.demo.dao;

import java.util.List;

import com.demo.entity.LeiMuTable;
import com.demo.entity.SpendingTable;
import com.demo.entity.order.OrderTable;
import com.demo.entity.user.GuKeTable;


public interface SpendingDao extends BaseDao<SpendingTable,Long>
{
	//查看全部支出
	public String getAllSpeng(String time,String time1);

	//查看全部支出
	public List<SpendingTable> getAllSpending(String time,String time1);
	//查看全部支出
	public List<SpendingTable> getAllSpendings(String time,String time1);
}