
package com.demo.dao.Impl.Courier;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.Courier.FedexDao;
import com.demo.dao.Impl.BaseDaoImpl;
import com.demo.entity.Courier.DhlFq;
import com.demo.entity.Courier.DhlGbj;
import com.demo.entity.Courier.Dhlzk;
import com.demo.entity.Courier.Fedex;


@Repository
public class FedexDaoImpl extends BaseDaoImpl<Fedex,Long> implements FedexDao
{

    public FedexDaoImpl()
    {
        super(Fedex.class);
    }
	//Fedex²éÑ¯È«²¿
	public List<Fedex> getFedex(){
		return ht.find("from Fedex");
	}
}
