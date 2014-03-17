package com.demo.dao.Impl.user;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.Impl.BaseDaoImpl;
import com.demo.dao.user.CaiWuDao;
import com.demo.entity.user.CaiWuTable;

@Repository
public class CaiWuDaoImpl extends BaseDaoImpl<CaiWuTable, Long> implements CaiWuDao{
	public CaiWuDaoImpl() {
		super(CaiWuTable.class);
	}
	public CaiWuTable getByUserId(Long userId) {
		return ht.findFirst("from CaiWuTable a where a.userid = ?", new Object[]{userId});
	}
	//查询全部财务
	public List<CaiWuTable> getCaiWu() {	
		List<CaiWuTable> hql = ht.find("from CaiWuTable a");
		
		return hql;
	}
}
