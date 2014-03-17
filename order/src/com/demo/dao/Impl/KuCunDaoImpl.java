package com.demo.dao.Impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.KuCunDao;
import com.demo.entity.KuCunTable;
@Repository
public class KuCunDaoImpl extends BaseDaoImpl<KuCunTable, Long> implements KuCunDao{
		public KuCunDaoImpl() {
			super(KuCunTable.class);
		}
		//标记号查询全部
		public KuCunTable getKuCunAll(String biaoji,String orderId){
			return ht.findFirst("from KuCunTable where biaoji = ? and orderId = ?",new Object[]{biaoji,orderId});
		}
		//订单号查询全部
		public KuCunTable getOrderAll(String orderid){
			return ht.findFirst("from KuCunTable where orderId= ?",new Object[]{orderid});
		}
		//用编号查询全部
		public KuCunTable getBiaoHao(String biaoji){
			return ht.findFirst("from KuCunTable where biaoji = ?",new Object[]{biaoji});
		}
	}

