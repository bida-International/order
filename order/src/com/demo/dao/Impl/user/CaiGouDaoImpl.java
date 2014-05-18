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
	//查看全部采购
	public String getAllCaiGou(){
		String hql = "from CaiGou ";
		return hql;
	}
	//采购编号查询全部
	public List<CaiGou> getSelId(Long id){
		   return ht.find("from CaiGou a where a.id = ?", new Object[] {
		            id
		        });
	}
	//采购管理员编号查询全部
	public List<CaiGou> getAdCaiGou(Long aduserid){
		return ht.find("from CaiGou where cgadminId="+aduserid+"");
	}
}
