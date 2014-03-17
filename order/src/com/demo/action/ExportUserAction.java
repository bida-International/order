package com.demo.action;

import com.demo.dao.OrderDao;
import com.demo.dao.OrderTableDao;
import com.demo.entity.order.OrderTable;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
@Controller("exportUserAction")
@Scope("prototype")
public class ExportUserAction extends BaseAction
    implements ServletRequestAware
{
	@Resource
    private OrderDao orderDao;
	@Resource
    private OrderTableDao orderTableDao;
    private HttpServletRequest request;
    public List<OrderTable> orders;
    public String orderId;
    public String time;
    public String time1;
    public String execute()
        throws Exception
    {
        try
        {
            String shangwang = request.getParameter("shangwang");
            String qianshou = request.getParameter("qianshou");
            String ruzhang = request.getParameter("ruzhang");
            orders = orderDao.getSelTiaoJiao(shangwang, qianshou, ruzhang);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return "export";
    }

    public String exportOrder()
    {
        return "exp";
    }
    //导出运费为空或者为0
    public String daochu(){
    	orders = orderDao.getDaoChuYunFei();
    	return "daochu";
    }
    //导出运输单号为空
    public String danhao(){
    	orders = orderDao.getDanHaoYunShuNull();
    	return "danhao";
    }
    public void setServletRequest(HttpServletRequest arg0)
    {
        request = arg0;
    }


}
