
package com.demo.dao.Impl.Courier;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.Courier.KyfqDao;
import com.demo.dao.Impl.BaseDaoImpl;
import com.demo.entity.Courier.DhlFq;
import com.demo.entity.Courier.kyfq;


@Repository
public class KyfqDaoImpl extends BaseDaoImpl<kyfq,Long> implements KyfqDao
{

    public KyfqDaoImpl()
    {
        super(kyfq.class);
    }
  //���Ҳ�ѯ
	public List getkyfq(String guojia){
		return ht.find("from kyfq where guojia =?",new Object[]{guojia});
	}
}
