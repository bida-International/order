package com.demo.dao.Impl;


import com.demo.bean.MyHibernateTemplate;
import com.demo.dao.GuoNeiWangZhanDao;
import com.demo.entity.GuoNeiWangZhan;
import java.util.List;

import org.springframework.stereotype.Repository;

// Referenced classes of package com.demo.dao:
//            BaseDaoImpl, GuoNeiWangZhanDao
@Repository
public class GuoNeiWanZhanDaoImpl extends BaseDaoImpl<GuoNeiWangZhan,Long> implements GuoNeiWangZhanDao
{

    public GuoNeiWanZhanDaoImpl()
    {
        super(GuoNeiWangZhan.class);
    }

    public List<GuoNeiWangZhan> getGouNeiWangZhan()
    {
        return ht.find("from GuoNeiWangZhan");
    }
}

