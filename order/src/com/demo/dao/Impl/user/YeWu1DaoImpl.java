package com.demo.dao.Impl.user;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.demo.dao.Impl.BaseDaoImpl;
import com.demo.dao.user.YeWu1Dao;
import com.demo.entity.user.YeWu;
import com.demo.entity.user.YeWu1;
@Repository
public class YeWu1DaoImpl extends BaseDaoImpl<YeWu1, Long> implements YeWu1Dao{
	public YeWu1DaoImpl() {
		super(YeWu1.class);
	}
	public YeWu1 getByUserId(Long userId) {
		return ht.findFirst("from YeWu1 a where a.userid = ?", new Object[]{userId});
	}
	//查询全部业务
	public List<YeWu1> getYeWuAll(){
		return ht.find("from YeWu1");
	}
	//查询最久的那条时间
	@SuppressWarnings("unchecked")
	public List<YeWu1> getTimes(){
		final String hql = "from YeWu1 order by disputeTime";
		return ht.executeFind(new HibernateCallback(){
				public Object doInHibernate(Session s)
				throws HibernateException, SQLException {			
				return s.createQuery(hql).setFirstResult(0).setMaxResults(1).list();  
			}
		});
	}
	  //修改订单状态
    public void updateDisputeStatus(String time,Long userid){
    	ht.bulkUpdate("update YeWu1 set disputeTime='"+time+"' where userid='"+userid+"'");
    }
	//查询站内信最久的那条时间
	@SuppressWarnings("unchecked")
	public List<YeWu1> getMsgTimes(){
		final String hql = "from YeWu1 order by letterTime";
		return ht.executeFind(new HibernateCallback(){
				public Object doInHibernate(Session s)
				throws HibernateException, SQLException {			
				return s.createQuery(hql).setFirstResult(0).setMaxResults(1).list();  
			}
		});
	}
	  //修改订单状态
    public void updateMsgStatus(String time,Long userid){
    	ht.bulkUpdate("update YeWu1 set letterTime='"+time+"' where userid='"+userid+"'");
    }
    //删除账号
    public void deleteMsgStatus(Long userid){
    	ht.bulkUpdate("delete YeWu1 where userid='"+userid+"'");
    }
}
