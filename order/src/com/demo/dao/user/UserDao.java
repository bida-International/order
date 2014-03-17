/*jadclipse*/// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) radix(10) lradix(10) 
// Source File Name:   UserDao.java

package com.demo.dao.user;

import com.demo.dao.BaseDao;
import com.demo.entity.*;
import com.demo.entity.user.CaiGou;
import com.demo.entity.user.CaiGouAdmin;
import com.demo.entity.user.UserInfo;
import com.demo.entity.user.YeWu;

import java.util.List;

// Referenced classes of package com.demo.dao:
//            BaseDao

public interface UserDao extends BaseDao<UserInfo ,Long>
{

    public abstract UserInfo getByAccount(String s);

    public abstract UserInfo getByUserNameOnPwd(String s, String s1);

    public abstract UserInfo getUserId(Long long1);

    public abstract UserInfo getPwd(String s);
    //管理员查看全部员工账号
    public abstract String getzhanghao(String s);

    public abstract CaiGou getcaigou(Long long1);

    public abstract CaiGouAdmin getcaigouadmin(Long long1);

    public abstract YeWu getyewu(Long long1);

    public abstract void getUser(Long long1);
    //查看全部账号
    public abstract List<UserInfo> getAllUser();
    //查询全部员工
    public abstract List<UserInfo> getYuanGong();
    //查询管理员
    public abstract List<UserInfo> getAdmin();
    //查看未读信
    public List<UserInfo> getWeiDuXin(Long userid);
}