package com.demo.dao.Courier;

import java.util.List;

import com.demo.dao.BaseDao;
import com.demo.entity.Courier.DhlGbj;

public interface DhlgbjDao  extends BaseDao<DhlGbj,Long>{
	//区域和重量查询DHL运费
	public List getZlQy(Double zl,Long qy);
}
