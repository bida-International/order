package com.demo.dao.Impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.GuoJiaDao;
import com.demo.dao.Order_DetailDao;
import com.demo.entity.Courier.YunFeiTable;
import com.demo.entity.order.OrderTable;
import com.demo.entity.order.Order_Detail;
import com.demo.entity.user.CaiGou;

@Repository
public class Order_DetailDaoImpl  extends BaseDaoImpl<Order_Detail, Long> implements Order_DetailDao {

	public Order_DetailDaoImpl() {
		super(Order_Detail.class);
		// TODO Auto-generated constructor stub
	}
	//订单号查询全部
	public List<Order_Detail> getAllOrder(String order){
		return ht.find("from Order_Detail where orderId = '"+order+"'");
	}
	//查询全部类目
	public String getAllCategory(){
		String hql = "from Order_Detail c where c.cat_name not in(select b.cat_name from LeiMuTable a,Order_Detail b where a.leimu like '%'+b.cat_name+'%') and c.sfModify is null";
		return hql;
	}
	//用编号查询全部
	public List<Order_Detail> getAllSelId(Long id){
		return ht.find("from Order_Detail a where a.id = ? ",new Object[]{id});
	}
}
