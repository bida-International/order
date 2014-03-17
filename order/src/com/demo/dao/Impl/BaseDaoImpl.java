package com.demo.dao.Impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.demo.bean.MyHibernateDaoSupport;
import com.demo.bean.MyHibernateTemplate;
import com.demo.dao.BaseDao;

public abstract class BaseDaoImpl<T, K> implements BaseDao<T, K>{
	protected Log log = LogFactory.getLog(this.getClass());
	
	private Class<T> entityclass;
	public BaseDaoImpl(Class<T> entityClass) {
		this.entityclass = entityClass;
	}
	@Resource
	protected MyHibernateTemplate ht;
	@Resource
	protected MyHibernateDaoSupport mhd;
	/**
	 * 新加或修改保存
	 * @param t
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	public T merge(T t){
		return (T)ht.merge(t);
	}

	@SuppressWarnings("unchecked")
	public void delete(T t)
	{
		ht.delete(t);
	}
	@SuppressWarnings("unchecked")
	public T get(K id){
		return (T)ht.get(entityclass, (Serializable)id);
	}
}
