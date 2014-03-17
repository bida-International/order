package com.demo.dao;

import java.util.List;

import com.demo.entity.DocumentTable;
import com.demo.entity.Express.YunFeiTable;
import com.demo.entity.order.OrderTable;

public interface DocumentDao  extends BaseDao<DocumentTable,Long>{
	//管理员得到未完成
	public String getUnfinish(String time,String time1);
	//编号查询全部
	public List<DocumentTable> getDocumentId(Long id);
	//审核文档
	public String getAudit(String time,String time1);
	//完成文档
	public String getCompleted(String time,String time1);
	//文档查询
	public List<DocumentTable> getDuplicateContent(String title);
}
