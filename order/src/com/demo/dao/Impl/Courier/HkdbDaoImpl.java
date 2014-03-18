
package com.demo.dao.Impl.Courier;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.Courier.HkdbDao;
import com.demo.dao.Impl.BaseDaoImpl;
import com.demo.entity.Courier.DhlFq;
import com.demo.entity.Courier.DhlGbj;
import com.demo.entity.Courier.EmsZf;
import com.demo.entity.Courier.FedExIe;
import com.demo.entity.Courier.FedExIp;
import com.demo.entity.Courier.Gjsl;
import com.demo.entity.Courier.Hkdb;


@Repository
public class HkdbDaoImpl extends BaseDaoImpl<Hkdb,Long> implements HkdbDao
{

    public HkdbDaoImpl()
    {
        super(Hkdb.class);
    }
	//国家查询资费
	public List<Hkdb> getHkdb(String guojia){
		return ht.find("from Hkdb where guojia=?",new Object[]{guojia});
	}
}
