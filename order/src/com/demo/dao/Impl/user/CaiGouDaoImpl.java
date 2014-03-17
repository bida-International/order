package com.demo.dao.Impl.user;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.Impl.BaseDaoImpl;
import com.demo.dao.user.CaiGouDao;
import com.demo.entity.user.CaiGou;
import com.demo.entity.user.UserInfo;
import com.demo.entity.user.YeWu;

@Repository
public class CaiGouDaoImpl extends BaseDaoImpl<CaiGou, Long> implements CaiGouDao{
	public CaiGouDaoImpl() {
		super(CaiGou.class);
	}
	public CaiGou getByUserId(Long userId) {
		return ht.findFirst("from CaiGou a where a.userid = ?", new Object[]{userId});
	}
	//查询全部采购
	public List getCaiGou() {	
		List hql = ht.find("from CaiGou a");
		
		return hql;
	}
}
