package com.demo.dao.Courier;

import java.util.List;

import com.demo.dao.BaseDao;
import com.demo.entity.Courier.FedExIp;

public interface FedExIpDao  extends BaseDao<FedExIp,Long>{
	//�ù��Ҳ�ѯ����
	 public List getFedExIp(String guojia);
}
