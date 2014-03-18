
package com.demo.dao.Impl.Courier;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.Courier.DhlZkDao;
import com.demo.dao.Impl.BaseDaoImpl;
import com.demo.entity.Courier.DhlFq;
import com.demo.entity.Courier.DhlGbj;
import com.demo.entity.Courier.Dhlzk;


@Repository
public class DhlzkDaoImpl extends BaseDaoImpl<Dhlzk,Long> implements DhlZkDao
{

    public DhlzkDaoImpl()
    {
        super(Dhlzk.class);
    }
	//DHLZK²éÑ¯È«²¿
	public List<Dhlzk> getDhlzk(){
		return ht.find("from Dhlzk");
	}
}
