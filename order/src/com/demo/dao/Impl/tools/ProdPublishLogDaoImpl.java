package com.demo.dao.Impl.tools;

import org.springframework.stereotype.Repository;

import com.demo.dao.Impl.BaseDaoImpl;
import com.demo.dao.tools.ProdPublishLogDao;
import com.demo.entity.tools.ProdPublishLog;

@Repository
public class ProdPublishLogDaoImpl extends BaseDaoImpl<ProdPublishLog, Long> implements
		ProdPublishLogDao {

	public ProdPublishLogDaoImpl() {
		super(ProdPublishLog.class);
	}

	/**
	 * 查找afterTime后的第一条错误日志
	 */
	public ProdPublishLog getOneErrLog(Long afterTime) {
		String hql = "from ProdPublishLog where result = 0 and createTime > ? order by createTime asc";
		return ht.findFirst(hql, new Object[] { afterTime });
	}
}
