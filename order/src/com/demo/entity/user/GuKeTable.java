/*jadclipse*/// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) radix(10) lradix(10) 
// Source File Name:   GuKeTable.java

package com.demo.entity.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="guke")
public class GuKeTable implements Serializable
{
	private static final long serialVersionUID = 1L;
    private Long id;//编号
    private String name;//顾客姓名
    private Long userid;
    private Long quanxian;
    private String gukeQQ;//顾客QQ
    private String phone;//手机号
  
	public String getGukeQQ() {
		return gukeQQ;
	}

	public void setGukeQQ(String gukeQQ) {
		this.gukeQQ = gukeQQ;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getQuanxian()
    {
        return quanxian;
    }

    public void setQuanxian(Long quanxian)
    {
        this.quanxian = quanxian;
    }
    @Id
    @GeneratedValue
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Long getUserid()
    {
        return userid;
    }

    public void setUserid(Long userid)
    {
        this.userid = userid;
    }

   
}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\Users\xielin\Desktop\classes xml\classes.jar
	Total time: 58 ms
	Jad reported messages/errors:
The class file version is 50.0 (only 45.3, 46.0 and 47.0 are supported)
	Exit status: 0
	Caught exceptions:
*/