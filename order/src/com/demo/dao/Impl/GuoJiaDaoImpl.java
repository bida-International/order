package com.demo.dao.Impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.GuoJiaDao;
import com.demo.entity.Express.YunFeiTable;
import com.demo.entity.order.OrderTable;
import com.demo.entity.user.CaiGou;

@Repository
public class GuoJiaDaoImpl  extends BaseDaoImpl<YunFeiTable, Long> implements GuoJiaDao {

	public GuoJiaDaoImpl() {
		super(YunFeiTable.class);
		// TODO Auto-generated constructor stub
	}
	//查询全部国家运费
	@Override
	public String getGuoJia(Long quyu, String guojia) {
		String stu = null;
        if((quyu == null || "".equals(quyu)) && !"".equals(guojia) && guojia != null)
            stu = "from YunFeiTable a where guojia='"+guojia+"' order by quyu desc";
        if(!"".equals(quyu) && quyu != null)
            stu = "from YunFeiTable a where quyu = "+quyu+" order by quyu desc";
        else
        if((quyu == null || "".equals(quyu)) && (guojia == null || "".equals(guojia)))
            stu = "from YunFeiTable order by quyu desc";
        return stu;
	}
	//用国家查询
	public List<YunFeiTable> getAllGuoJia(String id){
	
		return ht.find("from YunFeiTable where guojia like '%"+id+"%'");
	}
	//编号查询国家
	public YunFeiTable getGuoJiaAll(Long id){
		return ht.findFirst("from YunFeiTable where id = ?",new Object[]{id});
	}
	//查看全部国家
	public List<YunFeiTable> getAllGuoJia(){
		List<YunFeiTable> stu =ht.find("from YunFeiTable order by fenqu,id"); 
		return stu; 
	}
    //查看运费表字母
    public List<YunFeiTable> getYunTable(){
    	return ht.find("select distinct zimu from YunFeiTable");
    }
}
