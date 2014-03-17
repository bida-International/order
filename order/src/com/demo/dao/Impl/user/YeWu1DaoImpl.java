package com.demo.dao.Impl.user;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.Impl.BaseDaoImpl;
import com.demo.dao.user.YeWu1Dao;
import com.demo.entity.user.YeWu;
import com.demo.entity.user.YeWu1;
@Repository
public class YeWu1DaoImpl extends BaseDaoImpl<YeWu1, Long> implements YeWu1Dao{
	public YeWu1DaoImpl() {
		super(YeWu1.class);
	}
	public YeWu1 getByUserId(Long userId) {
		return ht.findFirst("from YeWu1 a where a.userid = ?", new Object[]{userId});
	}
	//查询全部业务
	public List<YeWu1> getYeWuAll(){
		return ht.find("from YeWu1");
	}
}
