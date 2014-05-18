package com.demo.dao;

import java.util.List;

import com.demo.entity.IncomeTable;
import com.demo.entity.LeiMuTable;
import com.demo.entity.SpendingTable;
import com.demo.entity.order.OrderTable;
import com.demo.entity.user.GuKeTable;


public interface IncomeDao extends BaseDao<IncomeTable,Long>
{
	//�鿴����
	public String getIncome(String time,String time1);

	//�鿴ȫ������
	public List<IncomeTable> getAllInCome(String time,String time1);
	//�鿴ȫ������
	public List<IncomeTable> getAllInComes(String time,String time1);
}