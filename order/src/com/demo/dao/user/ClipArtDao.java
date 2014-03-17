package com.demo.dao.user;
import java.util.List;

import com.demo.dao.BaseDao;
import com.demo.entity.user.CaiGou;
import com.demo.entity.user.CaiWuTable;
import com.demo.entity.user.ClipArt;
import com.demo.entity.user.YeWu1;

public interface ClipArtDao extends BaseDao<ClipArt, Long>{
	ClipArt getByUserId(Long userId);
	//分配账号给美工
	public List<ClipArt> getAssignedAccount();
}