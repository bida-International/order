package com.demo.dao.Courier;

import java.util.List;

import com.demo.dao.BaseDao;
import com.demo.entity.Courier.EmsZf;

public interface EmsZfDao  extends BaseDao<EmsZf,Long>{
	//国家查询全部
	public List getEmszf(String guojia);
}
