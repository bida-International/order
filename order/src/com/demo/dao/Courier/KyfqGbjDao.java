package com.demo.dao.Courier;

import java.util.List;

import com.demo.dao.BaseDao;
import com.demo.entity.Courier.Gjsl;
import com.demo.entity.Courier.Hkdb;
import com.demo.entity.Courier.Kygbj;
import com.demo.entity.Courier.kyfq;

public interface KyfqGbjDao  extends BaseDao<Kygbj,Long>{
	//区域和重量查询全部
	public List getKygbj(Long qy,Double zl);
}
