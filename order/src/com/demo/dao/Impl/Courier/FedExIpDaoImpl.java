
package com.demo.dao.Impl.Courier;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.Courier.FedExIpDao;
import com.demo.dao.Impl.BaseDaoImpl;
import com.demo.entity.Courier.DhlFq;
import com.demo.entity.Courier.DhlGbj;
import com.demo.entity.Courier.EmsZf;
import com.demo.entity.Courier.FedExIe;
import com.demo.entity.Courier.FedExIeGbj;
import com.demo.entity.Courier.FedExIp;
import com.demo.entity.Courier.FedExIpgbj;


@Repository
public class FedExIpDaoImpl extends BaseDaoImpl<FedExIp,Long> implements FedExIpDao
{

    public FedExIpDaoImpl()
    {
        super(FedExIp.class);
    }
	//国家查询全部
    public List getFedExIp(String guojia){
		 return ht.find("from FedExIp where guojia=?",new Object[]{guojia});
	}
}
