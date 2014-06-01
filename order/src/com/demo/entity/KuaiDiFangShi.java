/*jadclipse*/// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) radix(10) lradix(10) 
// Source File Name:   KuaiDiFangShi.java

package com.demo.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="kuaidifangshi")
public class KuaiDiFangShi implements Serializable
{
	private static final long serialVersionUID = 1L;
    private Long id;//编号
    private String name;//姓名
    private String trackNum; // 17Track对应的编码
    
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

	public String getTrackNum() {
		return trackNum;
	}

	public void setTrackNum(String trackNum) {
		this.trackNum = trackNum;
	}

   
}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\Users\xielin\Desktop\classes xml\classes.jar
	Total time: 56 ms
	Jad reported messages/errors:
The class file version is 50.0 (only 45.3, 46.0 and 47.0 are supported)
	Exit status: 0
	Caught exceptions:
*/