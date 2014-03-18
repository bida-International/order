package com.demo.dao.Courier;

import java.util.List;

import com.demo.dao.BaseDao;
import com.demo.entity.Courier.FedExIeGbj;

public interface FedExIeGbjDao  extends BaseDao<FedExIeGbj,Long>{
	//区域和重量查询全部
	public List getFedExIeGbj(Double zl,String qy);
}
