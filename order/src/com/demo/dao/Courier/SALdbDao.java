package com.demo.dao.Courier;

import java.util.List;

import com.demo.dao.BaseDao;
import com.demo.entity.Courier.Gjsl;
import com.demo.entity.Courier.Hkdb;
import com.demo.entity.Courier.Kygbj;
import com.demo.entity.Courier.SALdb;
import com.demo.entity.Courier.kyfq;

public interface SALdbDao  extends BaseDao<SALdb,Long>{
	//¹ú¼Ò²éÑ¯
	public List<SALdb> getSalDb(String guojia);
}
