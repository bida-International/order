package com.demo.dao;

import java.util.List;

import com.demo.entity.YunCun;
import com.demo.entity.order.OrderTable;

public interface YuCunDao extends BaseDao<YunCun, Long>{
	//顾客编号查询
	public List<YunCun> getGuKeId(Long gukeid);
	//查询全部预存
	public String getYuCun(String time,String time1);
	//顾客编号查询最后一条数据
	public List<YunCun> getLastNum(Long gukeid);
	//多表查询 用客户userid查询最后一条数据
	public List<YunCun> getUserLastNum(Long gukeid);
	//查看运费详情
	public String getXqYuCun(Long userid);
}