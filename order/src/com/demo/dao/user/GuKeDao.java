package com.demo.dao.user;

import java.util.List;

import com.demo.dao.BaseDao;
import com.demo.entity.user.GuKeTable;


public interface GuKeDao extends BaseDao<GuKeTable,Long>
{

    public abstract GuKeTable getByUserId(Long long1);
    //查询全部顾客
    public abstract List<GuKeTable> getGuKeAll();
  //编号查询全部顾客 
    public abstract GuKeTable getSelAllGuKe(Long userid);
    //姓名查看全部
    public abstract String getGuKeName(String name);
}