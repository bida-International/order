package com.demo.dao.Courier;

import java.util.List;

import com.demo.dao.BaseDao;
import com.demo.entity.Courier.Gjsl;
import com.demo.entity.Courier.Hkdb;

public interface HkdbDao  extends BaseDao<Hkdb,Long>{
	//���Ҳ�ѯ�ʷ�
	public List<Hkdb> getHkdb(String guojia);
}
