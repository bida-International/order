package com.demo.dao.user;

import java.util.List;

import com.demo.dao.BaseDao;
import com.demo.entity.user.PurchasingAssistant;

public interface PuchasingAssistantDao extends BaseDao<PurchasingAssistant, Long>{
	PurchasingAssistant getByUserId(Long userId);

}