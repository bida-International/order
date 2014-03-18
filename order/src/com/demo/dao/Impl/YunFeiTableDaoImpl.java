package com.demo.dao.Impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.Courier.YunFeiTableDao;
import com.demo.entity.Courier.YunFeiTable;
@Repository
public class YunFeiTableDaoImpl extends BaseDaoImpl<YunFeiTable, Long> implements YunFeiTableDao{
	public YunFeiTableDaoImpl() {
		super(YunFeiTable.class);
	}
	//���Ҷ�Ӧ�� ��ѯ
	public YunFeiTable getCorresponding(String Corresponding){
		return ht.findFirst("from YunFeiTable where Corresponding = ?", new Object[]{Corresponding});
	}
}
