
package com.demo.dao.Courier;

import java.util.List;

import com.demo.dao.BaseDao;
import com.demo.entity.Courier.YunFeiTable;

public interface YunFeiTableDao extends BaseDao<YunFeiTable,Long>
{
	//国家对应号 查询
	public YunFeiTable getCorresponding(String Corresponding);
}
