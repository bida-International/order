package com.demo.dao.user;

import java.util.List;

import com.demo.dao.BaseDao;
import com.demo.entity.user.YeWu;
import com.demo.entity.user.YeWu1;

public interface YeWu1Dao extends BaseDao<YeWu1, Long>{
	YeWu1 getByUserId(Long userId);
	//查询全部业务
	public List<YeWu1> getYeWuAll();
}