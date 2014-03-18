package com.demo.dao.Courier;

import java.util.List;

import com.demo.dao.BaseDao;
import com.demo.entity.Courier.FedExIe;

public interface FedExIeDao  extends BaseDao<FedExIe,Long>{
	//用国家查询联邦
	 public List getFedExIe(String guojia);
}
