package com.demo.dao.user;
import java.util.List;

import com.demo.dao.BaseDao;
import com.demo.entity.user.CaiGou;

public interface CaiGouDao extends BaseDao<CaiGou, Long>{
	CaiGou getByUserId(Long userId);
	//查询全部采购
	List getCaiGou();
	//查看全部采购
	public String getAllCaiGou();
	//采购编号查询全部
	public List<CaiGou> getSelId(Long id);
	//采购管理员编号查询全部
	
	public List<CaiGou> getAdCaiGou(Long aduserid);
	
}