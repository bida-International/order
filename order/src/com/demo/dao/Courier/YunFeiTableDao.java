
package com.demo.dao.Courier;

import java.util.List;

import com.demo.dao.BaseDao;
import com.demo.entity.Courier.YunFeiTable;

public interface YunFeiTableDao extends BaseDao<YunFeiTable,Long>
{
	//���Ҷ�Ӧ�� ��ѯ
	public YunFeiTable getCorresponding(String Corresponding);
}
