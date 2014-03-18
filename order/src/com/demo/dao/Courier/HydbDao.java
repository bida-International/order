package com.demo.dao.Courier;

import java.util.List;

import com.demo.dao.BaseDao;
import com.demo.entity.Courier.Hkdb;
import com.demo.entity.Courier.Hydb;

public interface HydbDao  extends BaseDao<Hydb,Long>{
	//国家查询资费
	public List<Hydb> getHydb(String guojia);
}
