
package com.demo.dao.Impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.user.ClipArtDao;
import com.demo.entity.user.ClipArt;
import com.demo.entity.user.YeWu1;


@Repository
public class ClipArtDaoImpl extends BaseDaoImpl<ClipArt,Long> implements ClipArtDao
{
    public ClipArtDaoImpl()
    {
        super(ClipArt.class);
    }
	public ClipArt getByUserId(Long userId) {
		return ht.findFirst("from ClipArt a where a.userid = ?", new Object[]{userId});
	}
	//查询全部美工
	public List<ClipArt> getAssignedAccount(){
		return ht.find("from ClipArt");
	}
}
