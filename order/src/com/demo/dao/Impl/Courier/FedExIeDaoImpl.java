
package com.demo.dao.Impl.Courier;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.Courier.FedExIeDao;
import com.demo.dao.Impl.BaseDaoImpl;
import com.demo.entity.Courier.DhlFq;
import com.demo.entity.Courier.DhlGbj;
import com.demo.entity.Courier.EmsZf;
import com.demo.entity.Courier.FedExIe;


@Repository
public class FedExIeDaoImpl extends BaseDaoImpl<FedExIe,Long> implements FedExIeDao
{

    public FedExIeDaoImpl()
    {
        super(FedExIe.class);
    }
	//用国家查询联邦
	 public List getFedExIe(String guojia){
		 return ht.find("from FedExIe where guojia=?",new Object[]{guojia});
	 }
}
