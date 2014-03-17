package com.demo.dao.user;
import java.util.List;

import com.demo.dao.BaseDao;
import com.demo.entity.user.CaiGou;

public interface CaiGouDao extends BaseDao<CaiGou, Long>{
	CaiGou getByUserId(Long userId);
	//查询全部采购
	List getCaiGou();
}