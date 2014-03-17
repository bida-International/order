package com.demo.dao;

import java.util.List;

import com.demo.entity.LeiMuTable;
import com.demo.entity.order.OrderTable;
import com.demo.entity.user.GuKeTable;


public interface LeiMuDao extends BaseDao<LeiMuTable,Long>
{
	//查询全部类目
	public List<LeiMuTable> getAllLeiMu();
	//用编号查询全部
    public abstract List<LeiMuTable> getSelId(Long long1);
	//用采购编号查询类目
    public abstract LeiMuTable getLeiMuUser(Long long1);
	//用类目名称查询全部
    public abstract List<LeiMuTable> getLmName(String name);
    //用类目名称查询全部
    public abstract List<LeiMuTable> getAllName(String name);
    //根据cateId查询
    public LeiMuTable getByCateId(String cateId);
}