/*jadclipse*/// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) radix(10) lradix(10) 
// Source File Name:   KuaiDiFangShiDaoImpl.java

package com.demo.dao.Impl;

import com.demo.bean.MyHibernateTemplate;
import com.demo.dao.KuaiDiFangShiDao;
import com.demo.entity.KuaiDiFangShi;
import java.util.List;

import org.springframework.stereotype.Repository;

// Referenced classes of package com.demo.dao:
//            BaseDaoImpl, KuaiDiFangShiDao
@Repository
public class KuaiDiFangShiDaoImpl extends BaseDaoImpl<KuaiDiFangShi,Long>
    implements KuaiDiFangShiDao
{

    public KuaiDiFangShiDaoImpl()
    {
        super(KuaiDiFangShi.class);
    }

    public List<KuaiDiFangShi> getSelKuaiDiFangShi()
    {
        return ht.find("from KuaiDiFangShi");
    }
    //¿ìµÝ²éÑ¯
    public List<KuaiDiFangShi> getCourier(String name){
    	return ht.find("from KuaiDiFangShi where name = '"+name+"'");
    }
}
