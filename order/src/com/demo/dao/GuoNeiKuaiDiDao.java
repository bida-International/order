package com.demo.dao;

import java.util.List;

import com.demo.entity.GuoNeiKuaiDi;

public interface GuoNeiKuaiDiDao extends BaseDao<GuoNeiKuaiDi,Long>
{

    public  List<GuoNeiKuaiDi> getGuoNeiKuaiDi();
    //国内快递查询
    public List<GuoNeiKuaiDi> getName(String name);
}

