package com.demo.dao;

import java.util.List;

import com.demo.entity.GuoNeiKuaiDi;

public interface GuoNeiKuaiDiDao extends BaseDao<GuoNeiKuaiDi,Long>
{

    public  List<GuoNeiKuaiDi> getGuoNeiKuaiDi();
    //���ڿ�ݲ�ѯ
    public List<GuoNeiKuaiDi> getName(String name);
}

