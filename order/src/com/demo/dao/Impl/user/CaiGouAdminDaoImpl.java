package com.demo.dao.Impl.user;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.Impl.BaseDaoImpl;
import com.demo.dao.user.CaiGouAdminDao;
import com.demo.entity.user.CaiGouAdmin;
@Repository
public class CaiGouAdminDaoImpl extends BaseDaoImpl<CaiGouAdmin, Long> implements CaiGouAdminDao{
		public CaiGouAdminDaoImpl() {
			super(CaiGouAdmin.class);
		}
		public CaiGouAdmin getByUserId(Long userId) {
			return ht.findFirst("from CaiGouAdmin a where a.userid = ?", new Object[]{userId});
		}
		public List<CaiGouAdmin> getCaiGouAdmin(Long caigouadmin, String username) {
			// TODO Auto-generated method stub
			return null;
		}
		//查看全部管理员
		public List getCaiGouAdminAll(){
			
			return ht.find("from CaiGouAdmin");
		}

	}

