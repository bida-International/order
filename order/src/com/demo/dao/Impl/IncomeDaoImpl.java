
package com.demo.dao.Impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.IncomeDao;
import com.demo.dao.LeiMuDao;
import com.demo.dao.SpendingDao;
import com.demo.entity.IncomeTable;
import com.demo.entity.LeiMuTable;
import com.demo.entity.SpendingTable;
import com.demo.entity.order.OrderTable;


@Repository
public class IncomeDaoImpl extends BaseDaoImpl<IncomeTable,Long>
    implements IncomeDao
{

    public IncomeDaoImpl()
    {
        super(IncomeTable.class);
    }
	public String getIncome(String time,String time1){
		String stu = null;
		if ((time != null && !"".equals(time)) && (time1 != null && !"".equals(time1))) {
			stu = "from IncomeTable where (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"')";
		}else{
			stu = "from IncomeTable";
		}
		return stu;
	}
	//查看全部支出
	public List<IncomeTable> getAllInCome(String time,String time1){
		List<IncomeTable> stu = null;
		if (time != null && !"".equals(time) && (time1 != null && !"".equals(time1))) {
			stu =ht.find("select round(sum(money),3) from IncomeTable where (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"')");
		}else{
			stu = ht.find("select round(sum(money),3) from IncomeTable");
		}
		return stu;
	}
	//查看全部支出
	public List<IncomeTable> getAllInComes(String time,String time1){
		List<IncomeTable> stu = null;
		if ((time != null && !"".equals(time)) && (time1 != null && !"".equals(time1))) {
			stu = ht.find("from IncomeTable where (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"')");
		}else{
			stu = ht.find("from IncomeTable");
		}
		return stu;
	}
}
