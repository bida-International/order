
package com.demo.dao.Impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.LeiMuDao;
import com.demo.entity.LeiMuTable;
import com.demo.entity.order.OrderTable;


@Repository
public class LeiMuDaoImpl extends BaseDaoImpl<LeiMuTable,Long>
    implements LeiMuDao
{

    public LeiMuDaoImpl()
    {
        super(LeiMuTable.class);
    }
    //查询全部类目
    public List<LeiMuTable> getAllLeiMu(){
    	return ht.find("from LeiMuTable");
    }
  //用编号查询全部
    public  List<LeiMuTable> getSelId(Long id){
    	return ht.find("from LeiMuTable a where a.id = ?", new Object[] {
                id
            });
    }
  //用采购编号查询类目
    public LeiMuTable getLeiMuUser(Long userid){
    	return ht.findFirst("from LeiMuTable a where id = ?",new Object[]{userid});
    }
  //用类目名称查询全部
    public List<LeiMuTable> getLmName(String name){
    
    	return ht.find("from LeiMuTable where leimu like '%"+name+"%'");
    }
    //用类目名称查询全部
    public List<LeiMuTable> getAllName(String name){
    	return ht.find("from LeiMuTable where leimu like '%"+name+"%'");
    }
  //根据类目Id查询
	public LeiMuTable getByCateId(String cateId) {
		return ht.findFirst("from LeiMuTable where cateId = ?", new Object[]{cateId});
	}
}
