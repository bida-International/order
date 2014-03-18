package com.demo.dao.Courier;

import java.util.List;

import com.demo.dao.BaseDao;
import com.demo.entity.Courier.DhlFq;
import com.demo.entity.Courier.Dhlzk;
import com.demo.entity.Courier.Fedex;

public interface DhlZkDao  extends BaseDao<Dhlzk,Long>{
	//DHLZK²éÑ¯È«²¿
	public List<Dhlzk> getDhlzk();

}
