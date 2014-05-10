package com.demo.dao.tools;

import com.demo.dao.BaseDao;
import com.demo.entity.tools.ProdPublishLog;

public interface ProdPublishLogDao extends BaseDao<ProdPublishLog, Long> {

	public ProdPublishLog getOneErrLog(Long afterTime);
}
