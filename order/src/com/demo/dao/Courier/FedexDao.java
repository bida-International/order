package com.demo.dao.Courier;

import java.util.List;

import com.demo.dao.BaseDao;
import com.demo.entity.Courier.DhlFq;
import com.demo.entity.Courier.Dhlzk;
import com.demo.entity.Courier.Fedex;

public interface FedexDao  extends BaseDao<Fedex,Long>{
	//Fedex²éÑ¯È«²¿
	public List<Fedex> getFedex();
}
