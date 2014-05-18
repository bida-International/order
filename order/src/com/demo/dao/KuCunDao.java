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
	//物品和useriid查询
	public List<KuCunTable> getGoodsUserId(String goods);
	//采购员查看全部库存订单
	public String getStockOrder(Long userid,String time,String time1,String bianma,String orderId);
	//管理员查看全部库存订单
	public String getStockOrderAll(String time,String time1);
	//库存订单总金额
	public List<KuCunTable> getTheTotalAmount(String time,String time1);
	//库存订单总金额
	public List<KuCunTable> getUserId();
	//图片名称和用户编码查询
	public List<KuCunTable> getThePictureName(String picturename,Long userid);
	//库存编号查询
	public List<KuCunTable> getKuCunId(Long kucunid);

}
