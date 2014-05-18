package com.demo.action;

import com.demo.dao.HuiLvDao;
import com.demo.dao.KuCunDao;
import com.demo.dao.LeiMuDao;
import com.demo.dao.OrderDao;
import com.demo.dao.OrderTableDao;
import com.demo.dao.XieXinDao;
import com.demo.dao.user.UserDao;
import com.demo.entity.HuiLvTable;
import com.demo.entity.KuCunTable;
import com.demo.entity.LeiMuTable;
import com.demo.entity.XieXinTable;
import com.demo.entity.order.OrderTable;
import com.demo.entity.user.GuKeTable;
import com.demo.entity.user.UserInfo;
import com.demo.list.PageModel;
import com.demo.page.PageBean;
import com.demo.page.PageBiz;
import com.demo.vo.LoginInfo;
import com.opensymphony.xwork2.ActionContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("yewu1Action")
@Scope("prototype")
public class YeWu1Action extends BaseAction implements ServletRequestAware
{
    private static final long serialVersionUID = 1L;
    @Resource
    private OrderDao orderDao;
    @Resource
    private LeiMuDao leiMuDao;
    @Resource
    private OrderTableDao orderTableDao;
    @Resource
    private UserDao userDao;
    @Resource
    private HuiLvDao huiLvDao;
    @Resource
    private PageBiz pageBiz;
    @Resource
    private XieXinDao xieXinDao;
    @Resource
    private KuCunDao kucundao;
    public String usertype;
    private OrderTable ordertable;
    private XieXinTable xiexin;
    public String msg;
    public int pageindex;
    public String orderId;
    public String time;
    public String time1;
    public Long zhanghaoId;
    public Long caigouyuan;
    public int pageNumber;
    public Long chuli;
    public Long chulis;
    public String danhao;
    public Long dhgatezhanghao;
    private PageBean pageBean;
    private HttpServletRequest request;
    private List<File> uploadFile;
    private List<String> uploadFileFileName;
    private List<String> uploadFileContentType;
    public List<File> getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(List<File> uploadFile) {
		this.uploadFile = uploadFile;
	}

	public List<String> getUploadFileFileName() {
		return uploadFileFileName;
	}

	public void setUploadFileFileName(List<String> uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	public List<String> getUploadFileContentType() {
		return uploadFileContentType;
	}

	public void setUploadFileContentType(List<String> uploadFileContentType) {
		this.uploadFileContentType = uploadFileContentType;
	}

	public PageBean getPageBean()
    {
        return pageBean;
    }

    public void setPageBean(PageBean pageBean)
    {
        this.pageBean = pageBean;
    }

    public OrderTable getOrdertable()
    {
        return ordertable;
    }

    public void setOrdertable(OrderTable ordertable)
    {
        this.ordertable = ordertable;
    }
    //业务查看全部分配订单
    public String getFenPeiAll(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	try {
    		System.out.println("+业务++"+us.getUserId());
    	  	int pageSize = 10;
        	pageBean = pageBiz.selAllFenPei(pageSize, pageNumber, us.getUserId(), orderId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
  
    	return "fenpei";
    }
    //业务 分配订单
    public String caigou_fenpei()
    {
        try
        {
            String ch[] = request.getParameter("bulletinId").split("-");
            String sel[] = request.getParameter("seluserid").split("-");
            String str[] = new String[ch.length];
            for(int i = 0; i < ch.length; i++)
            {
                List<OrderTable> ls = orderDao.getSelId(Long.parseLong(ch[i]));
                if(sel.length != ch.length)
                    str[i] = i+".操作失败";
                else
                if("".equals(sel[i]) || sel[i] == null)
                    str[i] = i+".操作失败";
                else
                if(ls.size() != 0)
                {
                    ((OrderTable)ls.get(0)).setId(Long.parseLong(ch[i]));
                    ((OrderTable)ls.get(0)).setFenpei(1l);
                    ((OrderTable)ls.get(0)).setCaigouyuan(Long.parseLong(sel[i]));
                    ls.get(0).setGetordersId(1l);
                    orderDao.merge((OrderTable)ls.get(0));
                    str[i] = i+".分配成功！";
                }
            }

            ActionContext.getContext().put("strsd", str);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return getFenPeiAll();
    }
    //分配问题订单
    public String wentiorder()
    {
        try
        {
            String ch[] = request.getParameter("bulletinId").split("-");
            String str[] = new String[ch.length];
            for(int i = 0; i < ch.length; i++)
            {
                List<OrderTable> ls = orderDao.getSelId(Long.parseLong(ch[i]));
                if(ls.size() != 0)
                {
                    ((OrderTable)ls.get(0)).setId(Long.parseLong(ch[i]));
                    ((OrderTable)ls.get(0)).setFenpei(2l);
                    orderDao.merge((OrderTable)ls.get(0));
                    str[i] = "操作成功";
                }
            }

            ActionContext.getContext().put("wenti", str);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return getFenPeiAll();
    }
  //修改分配备注
    public String updateRemark(){
   	 return "upadminremark";
    }
    public OrderTable getUpdateId()
    {
        OrderTable stu = (OrderTable)orderDao.get(ordertable.getId());
        return stu;
    }
    //修改备注
    public String upAdminRemark(){
		String remark = ordertable.getRemark();
	   	ordertable = orderDao.get(ordertable.getId());
	   	ordertable.setRemark(remark);
	   	orderDao.merge(ordertable);
	   	msg = "修改成功"; 
	   	return getFenPeiAll();
    }
    //查看问题订单
    public String getWenTiOrder(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	int pageSize = 10;
    	pageBean = pageBiz.selWenTiOrder(pageSize, pageNumber, us.getUserId(), orderId,dhgatezhanghao);
    	return "wenti";
    }
    //分配问题订单
    public String upfenpei()
    {
        try
        {
            String ch[] = request.getParameter("bulletinId").split("-");
            String sel[] = request.getParameter("seluserid").split("-");
            String str[] = new String[ch.length];
            for(int i = 0; i < ch.length; i++)
            {
                List<OrderTable> ls = orderDao.getSelId(Long.parseLong(ch[i]));
                if(sel.length != ch.length){
                    str[i] = i+"有账号未选择操作失败！";
                }
                else
                if("".equals(sel[i]) || sel[i] == null)
                {
                    str[i] = i+"操作失败";
                }
                else
                if(ls.size() != 0)
                {
                    ls.get(0).setId(Long.parseLong(ch[i]));        
                    ls.get(0).setCaigouyuan(Long.parseLong(sel[i]));
                    ls.get(0).setDenghuixin(0l);
                    ls.get(0).setSumaitong(0l);
                    ls.get(0).setDaifahuo(0l);
                    ls.get(0).setDaochu(0l);
                    ls.get(0).setFawan(2l);
                    ls.get(0).setFenpei(1l);
                    ls.get(0).setGetordersId(1l);
                    ls.get(0).setXiugai(1l);
                    orderDao.merge(ls.get(0));
                    str[i] = i+".操作成功";
                }
            }
            ActionContext.getContext().put("str", str);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return getWenTiOrder();
    }
    //问题订单已经完成 
    public String upWtWcOrder(){
   	  try
         {
             String[] ch = request.getParameter("bulletinId").split("-");
             String[] str = new String[ch.length];
             for(int i = 0; i < ch.length; i++)
             {
                 List<OrderTable> ls = orderDao.getSelId(Long.parseLong(ch[i]));
                 if(ls.size() != 0)
                 {
              	   if(ls.get(0).getWancheng() == 1){
              		   str[i]  = i+".此订单已经是完成的订单";
              	   }else{
                     ls.get(0).setId(Long.parseLong(ch[i]));
                     ls.get(0).setDaifahuo(0l);
                     ls.get(0).setWancheng(1l);
                     ls.get(0).setFenpei(1l);
                     ls.get(0).setXiugai(1l);
                     ls.get(0).setWanchengtime(new java.sql.Date(System.currentTimeMillis()));
                     orderDao.merge(ls.get(0));
                     str[i] = i+".操作成功";
              	   }
                 }
             }
             ActionContext.getContext().put("str", str);
         }
         catch(Exception e)
         {
             e.printStackTrace();
         }
         
   	 return getWenTiOrder();
    }
    //修改备注
    public String upRemark(){
    	return "upremark";
    }
    //修改备注
    public String updateWenTiRemark(){
    	String remark = ordertable.getRemark();
	   	ordertable = orderDao.get(ordertable.getId());
	   	ordertable.setRemark(remark);
	   	orderDao.merge(ordertable);
	   	msg = "修改成功"; 
	   	return getWenTiOrder();
    }
    public void setServletRequest(HttpServletRequest arg0)
    {
        request = arg0;
    }
    //业务查看所有订单那
    public String getYeAllOrder(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	int pageSize = 10;
    	pageBean=pageBiz.selYeAllOrder(pageSize, pageNumber, orderId,us.getUserId());
    	return "getallorder";
    }
    //修改订单
    public String upOrder(){
    	return "updateorder";
    }
    //修改订单
    public String updateOrder() throws Exception{
    	try {
    		LoginInfo us = (LoginInfo)getFromSession("logininfo");
        	//得到工程保存图片的路径
    		String root = null;
    		if (uploadFile != null) {
    			root = ServletActionContext.getServletContext().getRealPath("WebRoot/"+"upload/");		
			}
        	String danhao = ordertable.getDanhao();
        	String remark = ordertable.getRemark();
    	   	ordertable = orderDao.get(ordertable.getId());
    	   	ordertable.setDanhao(danhao);
    	   	ordertable.setRemark(remark);
    	   
            java.util.Date ds = new java.util.Date();
            SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String ffs = fs.format(ds);
            ordertable.setScTime(fs.parse(ffs));
            ordertable.setYewuId(us.getUserId());
            File dirs = new File(root);//+(System.currentTimeMillis())   
            if(!dirs.exists()){  
                dirs.mkdirs();  
            }
            if(uploadFile == null){  
            	orderDao.merge(ordertable);
                return  getYeAllOrder();
            }else{
            //循环上传的文件
            for(int i = 0 ; i < uploadFile.size() ; i ++){
            
            		String[] stu = new String[uploadFile.size()];
               	 	InputStream is = new FileInputStream(uploadFile.get(i)); 
                    //得到图片保存的位置(根据root来得到图片保存的路径在tomcat下的该工程里)
                    File destFile = new File(root,this.getUploadFileFileName().get(i));
                    //把图片写入到上面设置的路径里
                    OutputStream os = new FileOutputStream(destFile);
                    byte[] buffer = new byte[1024];
                    int length  = 0;
                    while((length = is.read(buffer))>0){
                        os.write(buffer, 0, length);
                    }
                	ordertable.setUploadFile("upload"+"/"+this.getUploadFileFileName().get(i));
                   is.close();
                   os.close();

            	 ActionContext.getContext().put("insert", stu);
            }
            }
           
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	
		orderDao.merge(ordertable);
	   	msg = "修改成功"; 
	   	return getYeAllOrder();
    }
    //标注为紧急订单
    public String upjinji(){
    	ordertable = orderDao.get(ordertable.getId());
    	ordertable.setJingji(1l);
    	orderDao.merge(ordertable);
    	msg = "修改成功 ！";
    	return getYeAllOrder();
    }
    //查看纠纷订单
    public String getDispute(){
    	try {
    		LoginInfo us = (LoginInfo)getFromSession("logininfo");
        	int pageSize = 10;
        	pageBean = pageBiz.selDispute(pageSize, pageNumber, orderId, us.getUserId(),chuli,time,time1,zhanghaoId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	return "getDispute";
    }
    //查看已经处理纠纷订单
    public String getDisputesDealtWith(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	int pageSize = 10;
    	pageBean = pageBiz.getDisputesDealtWith(pageSize, pageNumber, orderId, us.getUserId(), time, time1);
    	return "getDisputesDealtWith";
    }
    //查看未处理纠纷订单
    public String getDisputependingBefore(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	int pageSize = 10;
    	pageBean = pageBiz.getDisputependingBefore(pageSize, pageNumber, orderId, us.getUserId(), time, time1);
    	return "getDisputependingBefore";
    }
	//查看全部退货
    public String getTuiHuoAll(){
    
    	try {
    		LoginInfo us = (LoginInfo)getFromSession("logininfo");
    		int pageSize = 10;
        	pageBean = pageBiz.selChaKanAll(pageSize, pageNumber, danhao,chulis,us.getUserId());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	
    	return "tuihuoAll";
    }
    //未完成订单修改为已经完成 
    public String upWcOrder(){
   	   try
          {
   		 
              String[] ch = request.getParameter("bulletinId").split("-");
              String[] str = new String[ch.length];
              for(int i = 0; i < ch.length; i++)
              {
                  List<OrderTable> ls = orderDao.getSelId(Long.parseLong(ch[i]));
                
                  if(ls.size() != 0)
                  {
                	  KuCunTable kk = new KuCunTable();
                      kk.setOrderId(ls.get(0).getOrderId());
                      kk.setTransportproviders(ls.get(0).getGongyunshang());
                      kk.setTotalprice(ls.get(0).getHuokuan());
                      kk.setUnitprice(ls.get(0).getHuokuan());
                      kk.setCoding(ls.get(0).getBianma());
                      kk.setTime(new java.sql.Date(System.currentTimeMillis()));
                      kucundao.merge(kk);
                	  if (ls.get(0).getTuihuo() == 1) {
                		  ls.get(0).setId(Long.parseLong(ch[i]));
                          ls.get(0).setDaifahuo(0l);
                          ls.get(0).setWancheng(1l);
                          ls.get(0).setGetordersId(0l);
                          ls.get(0).setChuli(1l);
                          ls.get(0).setSumaitong(0l);  
                          ls.get(0).setWanchengtime(new java.sql.Date(System.currentTimeMillis()));
                          orderDao.merge(ls.get(0));
                          str[i] = i+".操作成功";  	
					}else{
						  str[i] = i+".此订单不是退货订单,操作失败";
					}
                     
                  }
              }
              ActionContext.getContext().put("str", str);
          }
          catch(Exception e)
          {
              e.printStackTrace();
          }
          
   	 return getProcessingReturns();
    }
  
    //返回给仓库退件菜单
    public String redaifangqu(){
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
                     ls.get(0).setTuihuo(2l);
                     ls.get(0).setChuli(1l);
                     ls.get(0).setDaochu(0l);
                     //ls.get(0).setDanhao(null);
                     ls.get(0).setWancheng(0l);
                     orderDao.merge(ls.get(0));
                     str[i] = i +".操作成功";
                 }
             }
             ActionContext.getContext().put("strb", str);
         }
         catch(Exception e)
         {
             e.printStackTrace();
         }
         return getProcessingReturns();
    }
    //处理退货订单
    public String getProcessingReturns(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	int pageSize = 10;
    	pageBean = pageBiz.selProcessingReturns(pageSize, pageNumber, danhao, us.getUserId());
    	return "tuihuo";
    }
    //业务处理订单
    public String chuli(){
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
                      ls.get(0).setChuli(1l);  
                      orderDao.merge(ls.get(0));   
                      str[i] = i + ".操作成功！";
                  }
              }
              ActionContext.getContext().put("strsd", str);
          }
          catch(Exception e)
          {
              e.printStackTrace();
          }
     
    	return getDisputependingBefore();
    }
    //业务未处理订单
    public String weichuli(){
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
                      ls.get(0).setChuli(0l);  
                      orderDao.merge(ls.get(0));   
                      str[i] = i + ".操作成功！";
                  }
              }
              ActionContext.getContext().put("strsd", str);
          }
          catch(Exception e)
          {
              e.printStackTrace();
          }
     
    	return getDisputependingBefore();
    }
    //修改备注
    public String upremarks(){
    	return "upremarks";
    }
    //修改备注
    public String updateRemarks(){
    	String remark = ordertable.getRemark();
    	ordertable = orderTableDao.get(ordertable.getId());
    	ordertable.setRemark(remark);
    	orderTableDao.merge(ordertable);
    	return getProcessingReturns();
    }
    //业务得到订单
    public String getOrders(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	int pageSize = 10;
    	pageBean = pageBiz.getOrders(pageSize, pageNumber, orderId, us.getUserId());
    	return "getorders";
    }
    //业务得到已经处理订单
    public String getBusinessDisposed(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	int pageSize = 10;
    	pageBean = pageBiz.getBusinessDisposed(pageSize, pageNumber, orderId, us.getUserId());
    	return "getdisposed";
    }
	 //业务处理订单
    public String BusinessProcess(){
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
                      ls.get(0).setSfbusinessId(1l);
                      orderDao.merge(ls.get(0));   
                      str[i] = i + ".操作成功！";
                  }
              }
              ActionContext.getContext().put("strsd", str);
          }
          catch(Exception e)
          {
              e.printStackTrace();
          }
     
    	return getOrders();
    }
    //业务返回未处理订单
    public String untreated(){
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
                      ls.get(0).setSfbusinessId(0l);
                      orderDao.merge(ls.get(0));   
                      str[i] = i + ".操作成功！";
                  }
              }
              ActionContext.getContext().put("strsd", str);
          }
          catch(Exception e)
          {
              e.printStackTrace();
          }
     
    	return getBusinessDisposed();
    }
}