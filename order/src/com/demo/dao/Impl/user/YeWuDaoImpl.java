package com.demo.dao.Impl.user;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.Impl.BaseDaoImpl;
import com.demo.dao.user.YeWuDao;
import com.demo.entity.user.YeWu;
@Repository
public class YeWuDaoImpl extends BaseDaoImpl<YeWu, Long> implements YeWuDao{
	public YeWuDaoImpl() {
		super(YeWu.class);
	}
	public YeWu getByUserId(Long userId) {
		return ht.findFirst("from YeWu a where a.userid = ?", new Object[]{userId});
	}
	//查询全部业务
	public List<YeWu> getYeWuAll(){
		return ht.find("from YeWu");
	}
}
