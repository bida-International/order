package com.demo.dao;


import java.util.List;

import com.demo.entity.KuCunTable;

public interface KuCunDao  extends BaseDao<KuCunTable,Long>{
	//标记号查询全部
	public KuCunTable getKuCunAll(String biaoji,String orderId);
	//订单号查询全部
	public KuCunTable getOrderAll(String orderid);
	//用编号查询全部
	public KuCunTable getBiaoHao(String biaoji);
}
