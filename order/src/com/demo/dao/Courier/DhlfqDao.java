package com.demo.dao.Courier;

import java.util.List;

import com.demo.dao.BaseDao;
import com.demo.entity.Courier.DhlFq;

public interface DhlfqDao  extends BaseDao<DhlFq,Long>{
	//���Ҳ�ѯDHL����
	public List getGjSel(String guojia);
}
