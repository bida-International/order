package com.demo.dao.Courier;

import java.util.List;

import com.demo.dao.BaseDao;
import com.demo.entity.Courier.Gjsl;
import com.demo.entity.Courier.Hkdb;
import com.demo.entity.Courier.Kygbj;
import com.demo.entity.Courier.kyfq;

public interface KyfqGbjDao  extends BaseDao<Kygbj,Long>{
	//�����������ѯȫ��
	public List getKygbj(Long qy,Double zl);
}
