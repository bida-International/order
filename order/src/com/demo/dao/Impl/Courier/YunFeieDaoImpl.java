package com.demo.dao.Impl.Courier;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.Courier.YunFeieDao;
import com.demo.dao.Impl.BaseDaoImpl;
import com.demo.entity.Courier.YunFeiTableE;

@Repository
public class YunFeieDaoImpl  extends BaseDaoImpl<YunFeiTableE, Long> implements YunFeieDao {

	public YunFeieDaoImpl() {
		super(YunFeiTableE.class);
		// TODO Auto-generated constructor stub
	}

	//用国家查询
	public List<YunFeiTableE> getGuoJia(String guojia){
		return ht.find("from YunFeiTableE where guojia = ?",new Object[]{guojia});
	}
}
