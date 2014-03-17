package com.demo.dao;
import java.util.List;

import com.demo.entity.HuiLvTable;
import com.demo.entity.user.CaiGou;

public interface HuiLvDao extends BaseDao<HuiLvTable, Long>{
	//时间查询全部
	public abstract HuiLvTable getHuiLvTime(String time);
	//时间查询全部
	public abstract List<HuiLvTable>  getHuiTime(String time);
}