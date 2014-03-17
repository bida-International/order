package com.demo.dao;

import java.util.List;

import com.demo.entity.GuoNeiWangZhan;

public interface GuoNeiWangZhanDao  extends BaseDao<GuoNeiWangZhan,Long>
{

    public abstract List<GuoNeiWangZhan> getGouNeiWangZhan();
    
}
