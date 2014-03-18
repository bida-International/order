
package com.demo.dao.Impl.Courier;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.Courier.DhlfqDao;
import com.demo.dao.Impl.BaseDaoImpl;
import com.demo.entity.Courier.DhlFq;


@Repository
public class DhlfqDaoImpl extends BaseDaoImpl<DhlFq,Long> implements DhlfqDao
{

    public DhlfqDaoImpl()
    {
        super(DhlFq.class);
    }
	//国家查询DHL分区
	public List getGjSel(String guojia){
		return ht.find("from DhlFq where guojia=?",new Object[]{guojia});
	}
}
