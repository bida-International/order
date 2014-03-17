
package com.demo.dao.Impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.DocumentDao;
import com.demo.entity.DocumentTable;


@Repository
public class DocumentDaoImpl extends BaseDaoImpl<DocumentTable,Long>
    implements DocumentDao
{

    public DocumentDaoImpl()
    {
        super(DocumentTable.class);
    }
	//得到未完成
	public String getUnfinish(String time,String time1){
		String stu = null;
		if((time != null && !"".equals(time)) && (time1 != null && !"".equals(time1))){
			stu = "from DocumentTable where GetDocuments=1 and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"')";
		}else{
			stu = "from DocumentTable where GetDocuments = 1";
		}
		return stu;
	}
	//编号查询全部
	public List<DocumentTable> getDocumentId(Long id){
		 return ht.find("from DocumentTable a where a.id = ?", new Object[]{id});
	}
	//审核文档
	public String getAudit(String time,String time1){
		String stu = null;
		if((time != null && !"".equals(time)) && (time1 != null && !"".equals(time1))){
			stu = "from DocumentTable where Audit=1 and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"')";		
		}
		else{
			stu = "from DocumentTable where Audit = 1";
		}
		return stu;
	}
	//完成文档
	public String getCompleted(String time,String time1){
		String stu = null;
		if((time != null && !"".equals(time)) && (time1 != null && !"".equals(time1))){
			stu = "from DocumentTable where Complete=1 and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"')";		
		}
		else{
			stu = "from DocumentTable where Complete = 1";
		}
		return stu;
	}
	//文档查询
	public List<DocumentTable> getDuplicateContent(String title){
		return ht.find("from DocumentTable where title = '"+title+"'");
	}
}
