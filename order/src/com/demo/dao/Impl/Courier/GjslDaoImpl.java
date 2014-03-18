
package com.demo.dao.Impl.Courier;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.Courier.GjslDao;
import com.demo.dao.Impl.BaseDaoImpl;
import com.demo.entity.Courier.DhlFq;
import com.demo.entity.Courier.DhlGbj;
import com.demo.entity.Courier.EmsZf;
import com.demo.entity.Courier.FedExIe;
import com.demo.entity.Courier.FedExIp;
import com.demo.entity.Courier.Gjsl;


@Repository
public class GjslDaoImpl extends BaseDaoImpl<Gjsl,Long> implements GjslDao
{

    public GjslDaoImpl()
    {
        super(Gjsl.class);
    }
	//重量查询运费
	public List<Gjsl> getZlYf(Double zl){
		return ht.find("from Gjsl where zhongliang=?", new Object[]{zl});
	}
}
