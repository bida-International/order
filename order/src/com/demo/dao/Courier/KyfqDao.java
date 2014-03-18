package com.demo.dao.Courier;

import java.util.List;

import com.demo.dao.BaseDao;
import com.demo.entity.Courier.Gjsl;
import com.demo.entity.Courier.Hkdb;
import com.demo.entity.Courier.kyfq;

public interface KyfqDao  extends BaseDao<kyfq,Long>{
	//¹ú¼Ò²éÑ¯
	public List getkyfq(String guojia);
}
