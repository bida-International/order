package com.demo.dao.Courier;

import java.util.List;

import com.demo.dao.BaseDao;
import com.demo.entity.Courier.Gjsl;

public interface GjslDao  extends BaseDao<Gjsl,Long>{
	//重量查询运费
	public List<Gjsl> getZlYf(Double zl);
}
