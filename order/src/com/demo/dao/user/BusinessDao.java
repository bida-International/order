package com.demo.dao.user;

import com.demo.dao.BaseDao;
import com.demo.entity.user.BusinessTable;
import com.demo.entity.user.CaiGouAdmin;

public interface BusinessDao extends BaseDao<BusinessTable, Long>{
	BusinessTable getByUserId(Long userId);
	//��ѯ�����
	public String getlargeorder(String orderId,String time,String time1);
}