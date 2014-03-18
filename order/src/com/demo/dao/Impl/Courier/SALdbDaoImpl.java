
package com.demo.dao.Impl.Courier;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.Courier.SALdbDao;
import com.demo.dao.Impl.BaseDaoImpl;
import com.demo.entity.Courier.DhlFq;
import com.demo.entity.Courier.DhlGbj;
import com.demo.entity.Courier.EmsZf;
import com.demo.entity.Courier.FedExIe;
import com.demo.entity.Courier.FedExIp;
import com.demo.entity.Courier.SALdb;


@Repository
public class SALdbDaoImpl extends BaseDaoImpl<SALdb,Long> implements SALdbDao
{

    public SALdbDaoImpl()
    {
        super(SALdb.class);
    }
  //¹ú¼Ò²éÑ¯
	public List<SALdb> getSalDb(String guojia){
		return ht.find("from SALdb where guojia=?",new Object[]{guojia});
	}
}
