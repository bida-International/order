package com.demo.dao.Impl.user;

import org.springframework.stereotype.Repository;

import com.demo.dao.Impl.BaseDaoImpl;
import com.demo.dao.user.BusinessDao;
import com.demo.entity.user.BusinessTable;
@Repository
public class BusinessDaoImpl extends BaseDaoImpl<BusinessTable, Long> implements BusinessDao{
	public BusinessDaoImpl() {
		super(BusinessTable.class);
	}
	public BusinessTable getByUserId(Long userId) {
		return ht.findFirst("from BusinessTable a where a.userid = ?", new Object[]{userId});
	}
	//查询大金额订单
	public String getlargeorder(String orderId,String time,String time1){
		String stu = null;
		if ((orderId != null && !"".equals(orderId)) && (time == null || "".equals(time)) && (time1 == null || "".equals(time1))) {
			stu = "from OrderTable where orderId='"+orderId+"' and money > 100";
		}
		else if ((orderId == null || "".equals(orderId)) && (time != null && !"".equals(time)) && (time1 != null && !"".equals(time1))) {
			stu = "from OrderTable where (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"') and money > 100";
		}
		else if((orderId == null || "".equals(orderId)) && (time == null || "".equals(time)) && (time1 == null || "".equals(time1))){
			stu = "from OrderTable where  money > 100";
		}
		return stu;
	}
}
