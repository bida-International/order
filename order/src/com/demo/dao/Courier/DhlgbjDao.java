package com.demo.dao.Courier;

import java.util.List;

import com.demo.dao.BaseDao;
import com.demo.entity.Courier.DhlGbj;

public interface DhlgbjDao  extends BaseDao<DhlGbj,Long>{
	//�����������ѯDHL�˷�
	public List getZlQy(Double zl,Long qy);
}
