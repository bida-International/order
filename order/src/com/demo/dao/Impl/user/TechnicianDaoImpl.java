package com.demo.dao.Impl.user;

import com.demo.bean.MyHibernateTemplate;
import com.demo.dao.Impl.BaseDaoImpl;
import com.demo.dao.user.TechnicianDao;

import com.demo.entity.user.ClipArt;
import com.demo.entity.user.Technician;


import org.springframework.stereotype.Repository;

@Repository
public class TechnicianDaoImpl extends BaseDaoImpl<Technician,Long>
    implements TechnicianDao
{
    public TechnicianDaoImpl()
    {
        super(Technician.class);
    }  
	public Technician getByUserId(Long userId) {
		return ht.findFirst("from Technician a where a.userid = ?", new Object[]{userId});
	}
}
