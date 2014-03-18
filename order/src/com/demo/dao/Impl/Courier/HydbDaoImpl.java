
package com.demo.dao.Impl.Courier;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.Courier.HydbDao;
import com.demo.dao.Impl.BaseDaoImpl;
import com.demo.entity.Courier.DhlFq;
import com.demo.entity.Courier.Hkdb;
import com.demo.entity.Courier.Hydb;


@Repository
public class HydbDaoImpl extends BaseDaoImpl<Hydb,Long> implements HydbDao
{

    public HydbDaoImpl()
    {
        super(Hydb.class);
    }
	//国家查询资费
	public List<Hydb> getHydb(String guojia){
		return ht.find("from Hydb where guojia=?",new Object[]{guojia});
	}
}
