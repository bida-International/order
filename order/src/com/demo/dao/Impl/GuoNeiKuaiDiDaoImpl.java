/*jadclipse*/// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) radix(10) lradix(10) 
// Source File Name:   GuoNeiKuaiDiDaoImpl.java

package com.demo.dao.Impl;

import com.demo.bean.MyHibernateTemplate;
import com.demo.dao.GuoNeiKuaiDiDao;
import com.demo.entity.GuoNeiKuaiDi;
import com.demo.entity.order.OrderTable;

import java.util.List;

import org.springframework.stereotype.Repository;
@Repository
public class GuoNeiKuaiDiDaoImpl extends BaseDaoImpl<GuoNeiKuaiDi,Long> implements GuoNeiKuaiDiDao
{

    public GuoNeiKuaiDiDaoImpl()
    {
        super(GuoNeiKuaiDi.class);
    }

    public List<GuoNeiKuaiDi> getGuoNeiKuaiDi()
    {
        return ht.find("from GuoNeiKuaiDi");
    }
    //国内快递查询
    public List<GuoNeiKuaiDi> getName(String name){
    	return ht.find("from GuoNeiKuaiDi where name = '"+name+"'");
    }
}
