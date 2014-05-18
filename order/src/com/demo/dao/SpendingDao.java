package com.demo.dao;

import java.util.List;

import com.demo.entity.LeiMuTable;
import com.demo.entity.SpendingTable;
import com.demo.entity.order.OrderTable;
import com.demo.entity.user.GuKeTable;


public interface SpendingDao extends BaseDao<SpendingTable,Long>
{
	//�鿴ȫ��֧��
	public String getAllSpeng(String time,String time1);

	//�鿴ȫ��֧��
	public List<SpendingTable> getAllSpending(String time,String time1);
	//�鿴ȫ��֧��
	public List<SpendingTable> getAllSpendings(String time,String time1);
}