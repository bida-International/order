package com.demo.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import com.demo.dao.GuoNeiKuaiDiDao;
import com.demo.dao.OrderDao;
import com.demo.dao.OrderTableDao;
import com.demo.entity.GuoNeiKuaiDi;
import com.demo.entity.order.OrderTable;
import com.demo.page.PageBean;
import com.demo.page.PageBiz;
import com.opensymphony.xwork2.ActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
@Controller("puchasingAssistantAction")
@Scope("prototype")
public class PuchasingAssistantAction extends BaseAction implements ServletRequestAware
{
	private static final long serialVersionUID = 1L;
	@Resource
    private OrderDao orderDao;
	@Resource
    private PageBiz pageBiz;
	@Resource
    private OrderTableDao orderTableDao;
	public int pageNumber;
    public String orderId;
    public String guoneidanhao;
    public String wuping;
    private PageBean pageBean;
    @Resource
    private GuoNeiKuaiDiDao guoNeiKuaiDiDao;
    public File excelfile;
    private HttpServletRequest request;
	
  
    public PageBean getPageBean()
    {
        return pageBean;
    }

    public void setPageBean(PageBean pageBean)
    {
        this.pageBean = pageBean;
    }
    //采购助理单号为空
    public String getNumberisempty() 
    {
    	int pageSize = 10;
    	pageBean = pageBiz.selSingleNumberEmpty(pageSize, pageNumber, guoneidanhao, orderId, wuping);
        return "guoneiweikong";
    }
  //导入
	public String getImportOrders(){
		try {	
			if(excelfile == null || excelfile.length() <= 0){
				msg = "请选择正确的excel文件";
			}
			HSSFWorkbook book = new HSSFWorkbook(new FileInputStream(excelfile));
			HSSFSheet sheet = book.getSheetAt(0);
	        HSSFRow row = null;  
	        for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {  
	            row = sheet.getRow(i);  
	            if (row == null) {  
	                continue;  
	            }   
	                OrderTable u = new OrderTable();   
	                GuoNeiKuaiDi kk = new GuoNeiKuaiDi();
	                row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                	u.setOrderId(row.getCell(4).getStringCellValue());
                	if(row.getCell(5) != null){
		                row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
	                	u.setGuoneidanhao(row.getCell(5).getStringCellValue());
                	}
                	if (row.getCell(6) != null) {
                		row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
                      	kk.setName(row.getCell(6).getStringCellValue());
					}
	              
                	List<OrderTable> order = orderTableDao.getOrderNull(u.getOrderId());
                	if (order.size() != 0){
                		if ((u.getGuoneidanhao() != null && !"".equals(u.getGuoneidanhao())) && (kk.getName() != null && !"".equals(kk.getName()))) {
							List<GuoNeiKuaiDi> dd = guoNeiKuaiDiDao.getName(kk.getName());
							if (dd.size() != 0){
								order.get(0).setId(order.get(0).getId());
								order.get(0).setGuoneidanhao(u.getGuoneidanhao());
								order.get(0).setGuoneikuaidiId(dd.get(0).getId());
								orderTableDao.merge(order.get(0));
							}else{
								order.get(0).setId(order.get(0).getId());
								order.get(0).setGuoneidanhao(u.getGuoneidanhao());
								order.get(0).setGuoneikuaidiId(14l);
								orderTableDao.merge(order.get(0));
							}
						}	                
					}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} 
		return getNumberisempty();
	}
	  //仓库修改单号为空 
    public String upweikong()
    {
        try
        {
        	request.setCharacterEncoding("UTF-8");
            String[] ch = request.getParameter("bulletinId").split("-");
            String[] guoneiyunshu = request.getParameter("guoneiyunshu").split("-");
            String[] kuaidi = request.getParameter("userid").split("-");
            String[] str = new String[ch.length];
            for(int i = 0; i < ch.length; i++)
            {
                List<OrderTable> ls = orderDao.getSelId(Long.parseLong(ch[i].toString()));
                if(ls.size() != 0)
                {               	
                	 if(kuaidi.length != ch.length){      	
                    	str[i] = i +".快递未选择,操作失败";
                    }
                	 
                	 else if(ch.length != guoneiyunshu.length){
                		 str[i] = i +".国内运输单号有未填的操作失败";
                	 }
                	 else{
                    	ls.get(0).setId(Long.parseLong(ch[i]));
                        ls.get(0).setGuoneidanhao(new String(guoneiyunshu[i].getBytes("ISO8859-1"), "utf-8"));
	                    ls.get(0).setGuoneikuaidiId(Long.parseLong(kuaidi[i]));	               
	                    orderDao.merge(ls.get(0));
	                    str[i] = i + ".操作成功!";
                    }
                }
            }

            ActionContext.getContext().put("str", str);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return getNumberisempty();
    }
    //仓库待放区
    public String zhijiefangru()
    {
        try
        {
            String[] ch = request.getParameter("bulletinId").split("-");
            String[] str = new String[ch.length];
            for(int i = 0; i < ch.length; i++)
            {
                List<OrderTable> ls = orderDao.getSelId(Long.parseLong(ch[i]));
                if(ls.size() != 0)
                {
                    ls.get(0).setId(Long.parseLong(ch[i]));
                    ls.get(0).setDaifahuo(2l);
                    orderDao.merge(ls.get(0));
                    str[i] = i+".操作成功！";
                }
            }

            ActionContext.getContext().put("wenti", str);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return getNumberisempty();
    }
    //仓库员已经发货
    public String getDaiFaHuo()
    {    
    	int pageSize = 10;
    	pageBean = pageBiz.selSingleNumberNotEmpty(pageSize, pageNumber, guoneidanhao, orderId, wuping);
        return "daifahuo";
    }
    //仓库待放区
    public String seldaifangqu()
    {
        try
        {
            List<OrderTable> ls = orderTableDao.getChaXunDaiFaHuo1(guoneidanhao, orderId);
            for(int i = 0; i < ls.size(); i++)
            {
                ls.get(i).setId(ls.get(i).getId());
                ls.get(i).setDaifahuo(2l);
                msg = "操作成功 ";
                orderTableDao.merge(ls.get(i));
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return getDaiFaHuo();
    }
    //仓库员返回到待放区
    public String wanchengorder()
    {
        try
        {
            String[] ch = request.getParameter("bulletinId").split("-");
            String[] str = new String[ch.length];
            for(int i = 0; i < ch.length; i++)
            {
                List<OrderTable> ls = orderDao.getSelId(Long.parseLong(ch[i]));
                if(ls.size() != 0)
                {
                    ls.get(0).setId(Long.parseLong(ch[i]));
                    ls.get(0).setDaifahuo(2l);
                    ls.get(0).setDaochu(0l);
                    orderDao.merge(ls.get(0));
                    str[i] = i +".操作成功";
                }
            }
            ActionContext.getContext().put("stra", str);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return getDaiFaHuo();
    }
    public void setServletRequest(HttpServletRequest arg0)
    {
        request = arg0;
    }
}
