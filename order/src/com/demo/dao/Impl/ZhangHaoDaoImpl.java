package com.demo.dao.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.demo.dao.PageDao;
import com.demo.dao.ZhangHaoDao;
import com.demo.entity.ZhangHao;
import com.demo.entity.order.OrderTable;
import com.demo.page.PageBean;
@Repository
public class ZhangHaoDaoImpl extends BaseDaoImpl<ZhangHao, Long> implements ZhangHaoDao{

	@Resource
	private PageDao pageDao;
	public ZhangHaoDaoImpl() {
		super(ZhangHao.class);
		// TODO Auto-generated constructor stub
	}
	@Override
	public List<ZhangHao> getAllZhangHao() {
		// TODO Auto-generated method stub
		return ht.find("from ZhangHao");
	}
	@Override
	public ZhangHao getZhangHao(String zhanghao) {
		// TODO Auto-generated method stub
		return ht.findFirst("from ZhangHao a where a.name = ?", new Object[]{zhanghao});
	}
	@Override
	public ZhangHao getZhangHaoId(Long zhanghaoId) {
		return ht.findFirst("from ZhangHao a where a.id = ?", new Object[]{zhanghaoId});
	}
	//查看全部敦煌账号
	 public String getdhgate(String name)
    {
		 String stu = null;
        if(name != null && !"".equals(name))
            stu = "from ZhangHao a where a.name = '"+name+"'";
        else
            stu = "from ZhangHao";
        return stu;
    }
	 //账号查询全部
	public List<ZhangHao> getZhangHaoName(String zhanghaoname){
		return ht.find("from ZhangHao where name = ?",new Object[]{zhanghaoname});
	}
	   //账号编号查询全部
    public List<ZhangHao> getZhangHaoIdAll(Long id){
    	return ht.find("from ZhangHao a where a.id = ?", new Object[] {
                id
           });
    }
    //账号名称查询全部
    public ZhangHao findUniqueByAccount(String account){
    	return ht.findFirst("from ZhangHao where name = ?", new Object[]{account});
    }

    // 获取一个账号信息
	@Override
	public ZhangHao findUnique(String account, String accountType) {
		return ht.findFirst("from ZhangHao where account = ? and account_type = ?", 
				new Object[]{ account, accountType });
	}
	@SuppressWarnings("unchecked")
	public List<ZhangHao> getAll(String accountType, Long bdUserId) {
	
		String hql = "from ZhangHao where account_type = ? ";
		if (bdUserId != null) {
			hql += "and bd_user_id = ?";
			return ht.find(hql, new Object[]{ accountType, bdUserId });
		} else {
			return ht.find(hql, new Object[]{ accountType });
		}
	}
	/**
	 * 分页查询
	 * @return
	 */
	public PageBean getAllByPage(int pageSize, int page, String accountType) {
		PageBean pageBean = new PageBean(pageDao);
        String queryPart = "";
        if (accountType != null) {
        	queryPart += "account_type = '" + accountType + "' ";
        }
        if (!queryPart.equals("")) {
        	queryPart = "where " + queryPart;
        }
        String hql = "from ZhangHao " + queryPart;
        return pageBean.getFenYe(hql, pageSize, page);
    }
}
