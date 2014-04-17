package com.demo.dao.Impl.user;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.Impl.BaseDaoImpl;
import com.demo.dao.user.PuchasingAssistantDao;
import com.demo.dao.user.YeWuDao;
import com.demo.entity.user.PurchasingAssistant;
import com.demo.entity.user.YeWu;
@Repository
public class PuchasingAssistantDaoImpl extends BaseDaoImpl<PurchasingAssistant, Long> implements PuchasingAssistantDao{
	public PuchasingAssistantDaoImpl() {
		super(PurchasingAssistant.class);
	}
	public PurchasingAssistant getByUserId(Long userId){
		return ht.findFirst("from PurchasingAssistant a where a.userid = ?", new Object[]{userId});
	}
}
