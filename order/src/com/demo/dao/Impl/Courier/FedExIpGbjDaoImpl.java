
package com.demo.dao.Impl.Courier;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.Courier.FedExIpGbjDao;
import com.demo.dao.Impl.BaseDaoImpl;
import com.demo.entity.Courier.DhlFq;
import com.demo.entity.Courier.DhlGbj;
import com.demo.entity.Courier.EmsZf;
import com.demo.entity.Courier.FedExIe;
import com.demo.entity.Courier.FedExIeGbj;
import com.demo.entity.Courier.FedExIp;
import com.demo.entity.Courier.FedExIpgbj;


@Repository
public class FedExIpGbjDaoImpl extends BaseDaoImpl<FedExIpgbj,Long> implements FedExIpGbjDao
{

    public FedExIpGbjDaoImpl()
    {
        super(FedExIpgbj.class);
    }
	//区域和重量查询全部
	public List<FedExIpgbj> getFedExIpGbj(Double zl,String qy){
		return ht.find("from FedExIpgbj where zhongliang = ? and quyu=?",new Object[]{zl,qy});
	}
}
