
package com.demo.dao.Impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.LeiMuDao;
import com.demo.dao.SpendingDao;
import com.demo.entity.LeiMuTable;
import com.demo.entity.SpendingTable;
import com.demo.entity.order.OrderTable;


@Repository
public class SpendingDaoImpl extends BaseDaoImpl<SpendingTable,Long>
    implements SpendingDao
{

    public SpendingDaoImpl()
    {
        super(SpendingTable.class);
    }
    //查看全部支出
	public String getAllSpeng(String time,String time1){
		String stu = null;
		if((time != null && !"".equals(time)) && (time1 != null && !"".equals(time1))){
		
			stu = "from SpendingTable where (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"')";
		}else{
		
			stu = "from SpendingTable";
		}
		return stu;
	}
	//查看支出
	public List<SpendingTable> getAllSpending(String time,String time1){
		List<SpendingTable> stu = null;
		if (time != null && !"".equals(time) && (time1 != null && !"".equals(time1))) {
			stu =ht.find("select round(sum(money),3) from SpendingTable where (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"')");
		}else{
			stu = ht.find("select round(sum(money),3) from SpendingTable");
		}
		return stu;
	}
	//查看全部支出
	public List<SpendingTable> getAllSpendings(String time,String time1){
		List<SpendingTable>  stu = null;
		if((time != null && !"".equals(time)) && (time1 != null && !"".equals(time1))){
		
			stu = ht.find("from SpendingTable where (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"')");
		}else{
		
			stu = ht.find("from SpendingTable");
		}
		return stu;	
	}
}
