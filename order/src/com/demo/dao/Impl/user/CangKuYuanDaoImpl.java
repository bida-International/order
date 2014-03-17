package com.demo.dao.Impl.user;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.Impl.BaseDaoImpl;
import com.demo.dao.user.CangKuYuanDao;
import com.demo.entity.user.CangKuYuan;

@Repository
public class CangKuYuanDaoImpl extends BaseDaoImpl<CangKuYuan, Long> implements CangKuYuanDao{
  public CangKuYuanDaoImpl()
    {
        super(CangKuYuan.class);
    }
    public CangKuYuan getByUserId(Long userId)
    {
        return ht.findFirst("from CangKuYuan a where a.userid = ?", new Object[] {
            userId
        });
    }
    //²éÑ¯È«²¿²Ö¿â
    public  List<CangKuYuan> getCangKuYuanAll(){
    	return ht.find("from CangKuYuan "); 
    }
}
