
package com.demo.dao.Impl.Courier;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.Courier.DhlgbjDao;
import com.demo.dao.Impl.BaseDaoImpl;
import com.demo.entity.Courier.DhlFq;
import com.demo.entity.Courier.DhlGbj;


@Repository
public class DhlgbjDaoImpl extends BaseDaoImpl<DhlGbj,Long> implements DhlgbjDao
{

    public DhlgbjDaoImpl()
    {
        super(DhlGbj.class);
    }
	//�����������ѯDHL�˷�
	public List getZlQy(Double zl,Long qy){
		return ht.find("from DhlGbj where zhongliang=? and quyu=?",new Object[]{zl,qy});
	}
}
