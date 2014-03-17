package com.demo.entity.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="cangkuyuan")
public class CangKuYuan implements Serializable
{
   private static final long serialVersionUID = 1L;
    private Long id;//±àºÅ
    private String name;//ÐÕÃû
    private Long userid;
    private Long quanxian;
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

    public Long getQuanxian()
    {
        return quanxian;
    }

    public void setQuanxian(Long quanxian)
    {
        this.quanxian = quanxian;
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