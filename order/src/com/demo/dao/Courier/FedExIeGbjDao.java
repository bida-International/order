package com.demo.dao.Courier;

import java.util.List;

import com.demo.dao.BaseDao;
import com.demo.entity.Courier.FedExIeGbj;

public interface FedExIeGbjDao  extends BaseDao<FedExIeGbj,Long>{
	//�����������ѯȫ��
	public List getFedExIeGbj(Double zl,String qy);
}
