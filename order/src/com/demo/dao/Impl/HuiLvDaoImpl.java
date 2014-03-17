package com.demo.dao.Impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.HuiLvDao;
import com.demo.entity.HuiLvTable;

@Repository
public class HuiLvDaoImpl extends BaseDaoImpl<HuiLvTable, Long> implements HuiLvDao{
	public HuiLvDaoImpl() {
		super(HuiLvTable.class);
	}
	//用时间查询汇率
	public HuiLvTable getHuiLvTime(String time){
		return ht.findFirst("from HuiLvTable where time = ?",new Object[]{time});
	}
	//时间查询全部
	public List<HuiLvTable>  getHuiTime(String time){
		
		return ht.find( "from HuiLvTable where time='"+time+"'");
	}
}
