/*jadclipse*/// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) radix(10) lradix(10) 
// Source File Name:   UserDao.java

package com.demo.dao.user;

import com.demo.dao.BaseDao;
import com.demo.entity.*;
import com.demo.entity.user.CaiGou;
import com.demo.entity.user.CaiGouAdmin;
import com.demo.entity.user.ClipArt;
import com.demo.entity.user.Technician;
import com.demo.entity.user.UserInfo;
import com.demo.entity.user.YeWu;

import java.util.List;

// Referenced classes of package com.demo.dao:
//            BaseDao

public interface TechnicianDao extends BaseDao<Technician ,Long>
{
	Technician getByUserId(Long userId);
}