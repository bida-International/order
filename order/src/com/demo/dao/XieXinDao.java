package com.demo.dao;

import java.util.List;

import com.demo.entity.XieXinTable;

public interface XieXinDao extends BaseDao<XieXinTable, Long>{
	//查看信
	public String getAllKanXin(Long userid);
	//查看未读信息
	public List<XieXinTable> getAllWeiDu(Long userid);
	//查看全部未读信息
	public String getAllXinXi(Long userid);
	//发件人查询全部
	public String getFjjl(String fjr);
}
