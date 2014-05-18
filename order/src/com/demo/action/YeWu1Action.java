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
    //ҵ��鿴ȫ�����䶩��
    public String getFenPeiAll(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	try {
    		System.out.println("+ҵ��++"+us.getUserId());
    	  	int pageSize = 10;
        	pageBean = pageBiz.selAllFenPei(pageSize, pageNumber, us.getUserId(), orderId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
  
    	return "fenpei";
    }
    //ҵ�� ���䶩��
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
                    str[i] = i+".����ʧ��";
                else
                if("".equals(sel[i]) || sel[i] == null)
                    str[i] = i+".����ʧ��";
                else
                if(ls.size() != 0)
                {
                    ((OrderTable)ls.get(0)).setId(Long.parseLong(ch[i]));
                    ((OrderTable)ls.get(0)).setFenpei(1l);
                    ((OrderTable)ls.get(0)).setCaigouyuan(Long.parseLong(sel[i]));
                    ls.get(0).setGetordersId(1l);
                    orderDao.merge((OrderTable)ls.get(0));
                    str[i] = i+".����ɹ���";
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
    //�������ⶩ��
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
                    str[i] = "�����ɹ�";
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
  //�޸ķ��䱸ע
    public String updateRemark(){
   	 return "upadminremark";
    }
    public OrderTable getUpdateId()
    {
        OrderTable stu = (OrderTable)orderDao.get(ordertable.getId());
        return stu;
    }
    //�޸ı�ע
    public String upAdminRemark(){
		String remark = ordertable.getRemark();
	   	ordertable = orderDao.get(ordertable.getId());
	   	ordertable.setRemark(remark);
	   	orderDao.merge(ordertable);
	   	msg = "�޸ĳɹ�"; 
	   	return getFenPeiAll();
    }
    //�鿴���ⶩ��
    public String getWenTiOrder(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	int pageSize = 10;
    	pageBean = pageBiz.selWenTiOrder(pageSize, pageNumber, us.getUserId(), orderId,dhgatezhanghao);
    	return "wenti";
    }
    //�������ⶩ��
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
                    str[i] = i+"���˺�δѡ�����ʧ�ܣ�";
                }
                else
                if("".equals(sel[i]) || sel[i] == null)
                {
                    str[i] = i+"����ʧ��";
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
                    str[i] = i+".�����ɹ�";
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
    //���ⶩ���Ѿ���� 
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
              		   str[i]  = i+".�˶����Ѿ�����ɵĶ���";
              	   }else{
                     ls.get(0).setId(Long.parseLong(ch[i]));
                     ls.get(0).setDaifahuo(0l);
                     ls.get(0).setWancheng(1l);
                     ls.get(0).setFenpei(1l);
                     ls.get(0).setXiugai(1l);
                     ls.get(0).setWanchengtime(new java.sql.Date(System.currentTimeMillis()));
                     orderDao.merge(ls.get(0));
                     str[i] = i+".�����ɹ�";
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
    //�޸ı�ע
    public String upRemark(){
    	return "upremark";
    }
    //�޸ı�ע
    public String updateWenTiRemark(){
    	String remark = ordertable.getRemark();
	   	ordertable = orderDao.get(ordertable.getId());
	   	ordertable.setRemark(remark);
	   	orderDao.merge(ordertable);
	   	msg = "�޸ĳɹ�"; 
	   	return getWenTiOrder();
    }
    public void setServletRequest(HttpServletRequest arg0)
    {
        request = arg0;
    }
    //ҵ��鿴���ж�����
    public String getYeAllOrder(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	int pageSize = 10;
    	pageBean=pageBiz.selYeAllOrder(pageSize, pageNumber, orderId,us.getUserId());
    	return "getallorder";
    }
    //�޸Ķ���
    public String upOrder(){
    	return "updateorder";
    }
    //�޸Ķ���
    public String updateOrder() throws Exception{
    	try {
    		LoginInfo us = (LoginInfo)getFromSession("logininfo");
        	//�õ����̱���ͼƬ��·��
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
            //ѭ���ϴ����ļ�
            for(int i = 0 ; i < uploadFile.size() ; i ++){
            
            		String[] stu = new String[uploadFile.size()];
               	 	InputStream is = new FileInputStream(uploadFile.get(i)); 
                    //�õ�ͼƬ�����λ��(����root���õ�ͼƬ�����·����tomcat�µĸù�����)
                    File destFile = new File(root,this.getUploadFileFileName().get(i));
                    //��ͼƬд�뵽�������õ�·����
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
	   	msg = "�޸ĳɹ�"; 
	   	return getYeAllOrder();
    }
    //��עΪ��������
    public String upjinji(){
    	ordertable = orderDao.get(ordertable.getId());
    	ordertable.setJingji(1l);
    	orderDao.merge(ordertable);
    	msg = "�޸ĳɹ� ��";
    	return getYeAllOrder();
    }
    //�鿴���׶���
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
    //�鿴�Ѿ�������׶���
    public String getDisputesDealtWith(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	int pageSize = 10;
    	pageBean = pageBiz.getDisputesDealtWith(pageSize, pageNumber, orderId, us.getUserId(), time, time1);
    	return "getDisputesDealtWith";
    }
    //�鿴δ������׶���
    public String getDisputependingBefore(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	int pageSize = 10;
    	pageBean = pageBiz.getDisputependingBefore(pageSize, pageNumber, orderId, us.getUserId(), time, time1);
    	return "getDisputependingBefore";
    }
	//�鿴ȫ���˻�
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
    //δ��ɶ����޸�Ϊ�Ѿ���� 
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
                          str[i] = i+".�����ɹ�";  	
					}else{
						  str[i] = i+".�˶��������˻�����,����ʧ��";
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
  
    //���ظ��ֿ��˼��˵�
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
                     str[i] = i +".�����ɹ�";
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
    //�����˻�����
    public String getProcessingReturns(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	int pageSize = 10;
    	pageBean = pageBiz.selProcessingReturns(pageSize, pageNumber, danhao, us.getUserId());
    	return "tuihuo";
    }
    //ҵ������
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
                      str[i] = i + ".�����ɹ���";
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
    //ҵ��δ������
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
                      str[i] = i + ".�����ɹ���";
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
    //�޸ı�ע
    public String upremarks(){
    	return "upremarks";
    }
    //�޸ı�ע
    public String updateRemarks(){
    	String remark = ordertable.getRemark();
    	ordertable = orderTableDao.get(ordertable.getId());
    	ordertable.setRemark(remark);
    	orderTableDao.merge(ordertable);
    	return getProcessingReturns();
    }
    //ҵ��õ�����
    public String getOrders(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	int pageSize = 10;
    	pageBean = pageBiz.getOrders(pageSize, pageNumber, orderId, us.getUserId());
    	return "getorders";
    }
    //ҵ��õ��Ѿ�������
    public String getBusinessDisposed(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	int pageSize = 10;
    	pageBean = pageBiz.getBusinessDisposed(pageSize, pageNumber, orderId, us.getUserId());
    	return "getdisposed";
    }
	 //ҵ������
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
                      str[i] = i + ".�����ɹ���";
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
    //ҵ�񷵻�δ������
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
                      str[i] = i + ".�����ɹ���";
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