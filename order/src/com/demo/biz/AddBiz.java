package com.demo.biz;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.stereotype.Service;

import com.demo.action.BaseAction;
import com.demo.dao.OrderDao;
import com.demo.entity.order.OrderTable;
import com.demo.vo.LoginInfo;
import com.opensymphony.xwork2.ActionContext;
@Service
public class AddBiz extends BaseAction implements ServletRequestAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private OrderDao orderDao;
	private HttpServletRequest request;

	public void addOrder(String[] dingdan,String[] jine,String[] yunshu,String[] zhanghao,String[] beizhu){
		try {
			
			String[] str = new String[dingdan.length];
				for(int i = 0; i < dingdan.length; i++){
					List<OrderTable> ls = orderDao.getAllOrderId(dingdan[i]);
					if("".equals(dingdan[i])||dingdan[i]==null)
			    	{
			    	    str[i]=i+1+".订单未输入添加失败！";				    		
			    	}
					else if(ls.size() != 0)
					{
						str[i]=i+1+".订单号已经存在添加失败！";
					}
					else if("".equals(jine[i])||jine[i]==null)
			    	{
			    	    str[i]=i+1+".金额未输入添加失败！";				    		
			    	}else if("".equals(jine[i])||jine[i]==null)
			    	{
			    		
			    	}
					else if("0".equals(zhanghao[i]) || Integer.parseInt(zhanghao[i]) == 0){
						str[i]=i+1+".账号未选择添加失败！";
					}else{
						OrderTable order = new OrderTable();
						order.setTime(new java.sql.Date(System.currentTimeMillis()));
						order.setCaigouyuan(0l);
						order.setOrderId(dingdan[i]);
						order.setMoney(Double.parseDouble(jine[i]));
						order.setYunshu(yunshu[i]);
						order.setZhanghaoId(Long.parseLong(zhanghao[i]));
						order.setRemark(beizhu[i]);
						LoginInfo login=(LoginInfo)getFromSession("logininfo");
						order.setDengluId(login.getUserId());
						orderDao.merge(order);
						str[i]=i+1+".订单添加成功！";
					}
				}
				ActionContext.getContext().put("insert", str);	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		this.request = arg0;
	}
}
