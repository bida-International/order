package com.demo.dao.Impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.demo.dao.PageDao;
import com.demo.entity.order.OrderTable;
@Repository
public class PageDaoImpl extends BaseDaoImpl implements PageDao {

	public PageDaoImpl() {
		super(Object.class);
		// TODO Auto-generated constructor stub
	}
	public List selForPage(final String hql, final int countstart, final int length) {
		// TODO Auto-generated method stub
				 
		  List list=ht.executeFind(new HibernateCallback(){
					public Object doInHibernate(Session s)
					throws HibernateException, SQLException {			
					return s.createQuery(hql).setFirstResult(countstart).setMaxResults(length).list();  
				}
		  });
		return list;
	}
	public int getAllCount(String hql) {
		// TODO Auto-generated method stub
		int i=ht.find(hql).size();
		 return i;
	}
}
