package com.demo.dao.Courier;

import java.util.List;

import com.demo.dao.BaseDao;
import com.demo.entity.Courier.FedExIeGbj;
import com.demo.entity.Courier.FedExIpgbj;

public interface FedExIpGbjDao  extends BaseDao<FedExIpgbj,Long>{
	//�����������ѯȫ��
	public List<FedExIpgbj> getFedExIpGbj(Double zl,String qy);
}
