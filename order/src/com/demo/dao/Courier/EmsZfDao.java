package com.demo.dao.Courier;

import java.util.List;

import com.demo.dao.BaseDao;
import com.demo.entity.Courier.EmsZf;

public interface EmsZfDao  extends BaseDao<EmsZf,Long>{
	//���Ҳ�ѯȫ��
	public List getEmszf(String guojia);
}
