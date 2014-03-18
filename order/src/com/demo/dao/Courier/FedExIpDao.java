package com.demo.dao.Courier;

import java.util.List;

import com.demo.dao.BaseDao;
import com.demo.entity.Courier.FedExIp;

public interface FedExIpDao  extends BaseDao<FedExIp,Long>{
	//用国家查询联邦
	 public List getFedExIp(String guojia);
}
