
package com.demo.dao.Impl.Courier;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.Courier.KyfqGbjDao;
import com.demo.dao.Impl.BaseDaoImpl;
import com.demo.entity.Courier.DhlFq;
import com.demo.entity.Courier.Kygbj;
import com.demo.entity.Courier.kyfq;


@Repository
public class KyGbjDaoImpl extends BaseDaoImpl<Kygbj,Long> implements KyfqGbjDao
{

    public KyGbjDaoImpl()
    {
        super(Kygbj.class);
    }
	//区域和重量查询全部
	public List getKygbj(Long qy,Double zl){
		return ht.find("from Kygbj where zu=? and zl=?",new Object[]{qy,zl});
	}
}
