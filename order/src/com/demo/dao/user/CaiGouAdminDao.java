	package com.demo.dao.user;

	import java.util.List;
	import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.BaseDao;
import com.demo.entity.user.CaiGou;
import com.demo.entity.user.CaiGouAdmin;

	public interface CaiGouAdminDao extends BaseDao<CaiGouAdmin, Long>{
		CaiGouAdmin getByUserId(Long userId);
		List<CaiGouAdmin> getCaiGouAdmin(Long caigouadmin,String username);
		List getCaiGouAdminAll();//查看全部采购管理员
	}
