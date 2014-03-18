
package com.demo.dao.Impl.Courier;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.Courier.FedExIeGbjDao;
import com.demo.dao.Impl.BaseDaoImpl;
import com.demo.entity.Courier.DhlFq;
import com.demo.entity.Courier.DhlGbj;
import com.demo.entity.Courier.EmsZf;
import com.demo.entity.Courier.FedExIe;
import com.demo.entity.Courier.FedExIeGbj;


@Repository
public class FedExIeGbjDaoImpl extends BaseDaoImpl<FedExIeGbj,Long> implements FedExIeGbjDao
{

    public FedExIeGbjDaoImpl()
    {
        super(FedExIeGbj.class);
    }
	//区域和重量查询全部
	public List getFedExIeGbj(Double zl,String qy){
		return ht.find("from FedExIeGbj where zhongliang = ? and quyu=?",new Object[]{zl,qy});
	}
}
