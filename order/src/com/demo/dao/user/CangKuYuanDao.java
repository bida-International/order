package com.demo.dao.user;

import java.util.List;

import com.demo.dao.BaseDao;
import com.demo.entity.user.CangKuYuan;

public interface CangKuYuanDao extends BaseDao<CangKuYuan,Long>
{
    public abstract CangKuYuan getByUserId(Long long1);
    //²éÑ¯È«²¿²Ö¿â
    public abstract List<CangKuYuan> getCangKuYuanAll();
}
