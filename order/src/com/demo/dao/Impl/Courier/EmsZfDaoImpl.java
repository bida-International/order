
package com.demo.dao.Impl.Courier;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.Courier.EmsZfDao;
import com.demo.dao.Impl.BaseDaoImpl;
import com.demo.entity.Courier.DhlFq;
import com.demo.entity.Courier.DhlGbj;
import com.demo.entity.Courier.EmsZf;


@Repository
public class EmsZfDaoImpl extends BaseDaoImpl<EmsZf,Long> implements EmsZfDao
{

    public EmsZfDaoImpl()
    {
        super(EmsZf.class);
    }
	//国家查询全部
	public List getEmszf(String guojia){
		return ht.find("from EmsZf where guojia = ?",new Object[]{guojia});
	}
}
