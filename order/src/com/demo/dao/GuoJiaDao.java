package com.demo.dao;

import java.util.List;

import com.demo.entity.Express.YunFeiTable;
import com.demo.entity.order.OrderTable;

public interface GuoJiaDao  extends BaseDao<YunFeiTable,Long>{
	public abstract String getGuoJia(Long quyu,String guojia);//查询全部国家运费
	public abstract List<YunFeiTable> getAllGuoJia(String id);//用国家查询
	public List<YunFeiTable> getAllGuoJia();//查看全部国家
    //查看运费表字母
    public List<YunFeiTable> getYunTable();
	public abstract YunFeiTable getGuoJiaAll(Long id);//编号查询国家
}
