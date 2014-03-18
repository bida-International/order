package com.demo.dao.Courier;

import java.util.List;

import com.demo.dao.BaseDao;
import com.demo.entity.Courier.YunFeiTable;
import com.demo.entity.Courier.YunFeiTableE;
import com.demo.entity.order.OrderTable;

public interface YunFeieDao  extends BaseDao<YunFeiTableE,Long>{
	public abstract List<YunFeiTableE> getGuoJia(String guojia);//查询全部国家运费

}
