package com.demo.action;

import com.demo.dao.KuCunDao;
import com.demo.dao.OrderDao;
import com.demo.dao.OrderTableDao;
import com.demo.dao.ZhangHaoDao;
import com.demo.dao.Courier.YunFeiTableDao;
import com.demo.dao.user.CaiGouDao;
import com.demo.dao.user.UserDao;
import com.demo.entity.KuCunTable;
import com.demo.entity.ZhangHao;
import com.demo.entity.Courier.YunFeiTable;
import com.demo.entity.order.OrderTable;
import com.demo.entity.user.CaiGou;
import com.demo.entity.user.UserInfo;
import com.demo.list.PageData;
import com.demo.list.PageModel;
import com.demo.page.PageBean;
import com.demo.page.PageBiz;
import com.demo.vo.LoginInfo;
import com.opensymphony.xwork2.ActionContext;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
@Controller("caiGouAdminAction")
@Scope("prototype")
public class CaiGouAdminAction extends BaseAction implements ServletRequestAware
{
    private static final long serialVersionUID = 1L;
    @Resource
    private PageBiz pageBiz;
    @Resource
    private OrderDao orderDao;
    @Resource
    private UserDao userDao;
    @Resource
    private YunFeiTableDao yunFeiTableDao;
    @Resource
    private OrderTableDao orderTableDao;
    @Resource
    private KuCunDao kuCunDao;
    @Resource
    private ZhangHaoDao zhangHaoDao;
    @Resource
    private CaiGouDao caigoudao;
    public Long leimu;
    private OrderTable ordertable;
    public String zhanghaoIds;
    public String orderId;
    public String time;
    public String msg;
    public String wuping;
    public PageData pd;
    public int pageindex;
    public String wancheng;
    public String time1;
    public String caigoutime;
    public String caigoutime1;
    public int pageNumber;
    private PageBean pageBean;
    public List<OrderTable> orders;
    public String zhanghaoId;
    private int page;
    public String key;
    public String bianma;
    private Long cid;
    public String bianhao;
    private ZhangHao myzhangHao;
    public Long leimus;
    public Long category;
    public String gongyunshang;
    public String danhao;
    public String chuli;
    public Long caigouyuan;
    private HttpServletRequest request;
    
    public ZhangHao getMyzhangHao() {
		return myzhangHao;
	}

	public void setMyzhangHao(ZhangHao myzhangHao) {
		this.myzhangHao = myzhangHao;
	}

	public PageBean getPageBean()
    {
        return pageBean;
    }

    public void setPageBean(PageBean pageBean)
    {
        this.pageBean = pageBean;
    }

    public Long getCid()
    {
        return cid;
    }

    public void setCid(Long cid)
    {
        this.cid = cid;
    }

    public int getPage()
    {
        return page;
    }

    public void setPage(int page)
    {
        this.page = page;
    }

    public OrderTable getOrdertable()
    {
        return ordertable;
    }

    public void setOrdertable(OrderTable ordertable)
    {
        this.ordertable = ordertable;
    }

    public String getAdminDeDaoOrder()
    {
       int pageSize = 10;
       pageBean = pageBiz.selAdminDeDaoOrder(pageSize, pageNumber, orderId);
        return "cgadmindedaoorder";
    }


    public String updedaos()
    {
        LoginInfo us = (LoginInfo)getFromSession("logininfo");
        String ch[] = request.getParameter("bulletinId").split("-");
        String str[] = new String[ch.length];
        for(int i = 0; i < ch.length; i++)
        {
            List<OrderTable> ls = orderDao.getSelId(Long.valueOf(Long.parseLong(ch[i])));
            if(ls.size() != 0)
                if(((OrderTable)ls.get(0)).getWuping() == null || "".equals(((OrderTable)ls.get(0)).getWuping()) || ((OrderTable)ls.get(0)).getGongyunshang() == null || "".equals(((OrderTable)ls.get(0)).getGongyunshang()))
                {
                    str[i] = i + ".����"+ls.get(0).getOrderId()+"����Ϣû��д����";
                } else
                {
                    ls.get(0).setId(Long.parseLong(ch[i]));
                    ls.get(0).setCaigouyuan(us.getUserId());
                    ls.get(0).setDaifahuo(1l);
                    ls.get(0).setFenpei(1l);
                    ls.get(0).setGetordersId(0l);
                    orderDao.merge(ls.get(0));
                    str[i] = i +".�����ɹ���";
                }
        }
        ActionContext.getContext().put("str", str);
        return getCaiGouAdminDeDaoOrder();
    }

    public String upcgadminorder()
    {
        return "cgadminorder";
    }
    //�ɹ�����Ա�޸Ķ���
    public String cgadminorder() throws Exception
    {
    	try {
        	String orderId = ordertable.getOrderId();
            String gongyunshang = ordertable.getGongyunshang();
            String beizhu = ordertable.getRemark();
            Long cai = ordertable.getCaigouyuan();
            String danhao = ordertable.getDanhao();
            Double huokuan = ordertable.getHuokuan();
            String dizhi = ordertable.getGuowaidizhi();
            String wuping = ordertable.getWuping();
            Date shijian = ordertable.getCaigoutime();
            Long caigou = ordertable.getGuoneiwangzhanId();    
            String kucun = request.getParameter("kucun");
            Long kuaidi = ordertable.getKuaidifangshiId();
            String guojia = ordertable.getGuojia();
            String num = request.getParameter("num");
            String miaosu = request.getParameter("miaosu");
            ordertable = (OrderTable)orderDao.get(ordertable.getId());
            ordertable.setGongyunshang(gongyunshang);
            ordertable.setRemark(beizhu);
            ordertable.setCaigouyuan(cai);
            ordertable.setDanhao(danhao);
            ordertable.setGuowaidizhi(dizhi);
            ordertable.setCaigoutime(shijian);
            ordertable.setGuoneiwangzhanId(caigou); 
           	ordertable.setKuaidifangshiId(kuaidi);
            ordertable.setGuojia(guojia);
            ordertable.setXiugai(1l);
            Date d = new Date();
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String ff = f.format(d);
            if (Long.parseLong(kucun) == 1) {
            	 LoginInfo us = (LoginInfo)getFromSession("logininfo");
                 List<KuCunTable> kk = kuCunDao.getGoodsUserId(wuping);
                 if (kk.size() == 0) {
         			msg = "��涩������û���ҵ�("+wuping+")����Ʒ������ʧ��";
         			return "cgadminorder";
         		}else{
         			Long ku=0l;
         			Long nn = 0l;
         			if (kk.get(0).getNum() == null || kk.get(0).getNum() == 0l) {
    					nn = 0l;
    				}else{
    					nn = kk.get(0).getNum();
    				}
         			if (kk.get(0).getZynum()==null || kk.get(0).getZynum() == 0) {
    					ku = 0l;
    				}else{
    					ku=kk.get(0).getZynum();
    				}
         			if ((ku+(Long.parseLong(num)))>nn) {
    					msg = "ռ�ÿ����ڿ��������"+(((kk.get(0).getZynum())+(Long.parseLong(num)))-(kk.get(0).getNum()));
    					return "cgadminorder";
    				}
         			if (kk.get(0).getNum() < Long.parseLong(num)) {
         				kk.get(0).setId(kk.get(0).getId());
         				msg= "�����������"+((Long.parseLong(num))-(kk.get(0).getNum()))+"��������Ʒ��һ���޸���Ʒ����";
         			//	kk.get(0).setNum(0l);
         			//	kk.get(0).setOrderId(orderId);
         				kk.get(0).setSytime(f.parse(ff));
         				Long zy = 0l;
         				if (kk.get(0).getZynum() != null) {
    						zy = kk.get(0).getZynum() + kk.get(0).getZynum();
    						
    					}else{
    						zy = kk.get(0).getZynum();
    					}
         				kk.get(0).setZynum(zy);
         				kk.get(0).setZynum(kk.get(0).getNum());
         				ordertable.setHuokuan((kk.get(0).getUnitprice())*(kk.get(0).getNum()));
         				ordertable.setNum(kk.get(0).getNum());
         				kk.get(0).setThnum(kk.get(0).getNum());
         				kuCunDao.merge(kk.get(0));
         			}else{
         				Long zy = 0l;
     					
         				if (kk.get(0).getZynum() != null && kk.get(0).getZynum() != 0) {
    						zy = Long.parseLong(num) + kk.get(0).getZynum();
    						
    					}else{
    						zy = Long.parseLong(num);
    					}
         				kk.get(0).setId(kk.get(0).getId());
         			//	kk.get(0).setNum((kk.get(0).getNum()-(Long.parseLong(num))));
         			//	kk.get(0).setOrderId(orderId);
         				kk.get(0).setSytime(f.parse(ff));
         				kk.get(0).setZynum(zy);
         				ordertable.setNum(Long.parseLong(num));
         				ordertable.setHuokuan((kk.get(0).getUnitprice())*(Long.parseLong(num)));
         				kk.get(0).setThnum(Long.parseLong(num));
         				kuCunDao.merge(kk.get(0));
         			}
         			ordertable.setKucun(1l);
         			ordertable.setKucunid(kk.get(0).getId());
         			ordertable.setWuping(wuping+","+(Long.parseLong(num))+miaosu);
         		}
                 
    		}
            if (Long.parseLong(kucun) == 0) {
                ordertable.setHuokuan(huokuan);
                ordertable.setWuping(wuping);
    		}
            orderDao.merge(ordertable);
    		} catch (Exception e) {
    			// TODO: handle exception
    			e.printStackTrace();
    		}
    	
		return getCaiGouAdminDeDaoOrder();
       
    }

    public String fanhui()
    {
        ordertable = (OrderTable)orderDao.get(ordertable.getId());
        ordertable.setFenpei(0l);
        ordertable.setWancheng(0l);
        orderDao.merge(ordertable);
        msg = "�����ɹ�";
        return getWanChengOrder();
    }

    public String fanhuiorder()
    {
        ordertable = (OrderTable)orderDao.get(ordertable.getId());
        ordertable.setFenpei(0l);
        ordertable.setWancheng(0l);
        ordertable.setGetordersId(0l);
        orderDao.merge(ordertable);
        msg = "�����ɹ�";
        return getCaiGouAdminDeDaoOrder();
    }
    //�ѿ�涩��ȫ�����������
    public String daifangqu(){
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
                    ls.get(0).setGetordersId(2l);
                    ls.get(0).setFenpei(1l);
                    orderDao.merge(ls.get(0));
                    str[i] = i +".�����ɹ�";
                }
            }
            ActionContext.getContext().put("strsd", str);
           }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return getCaiGouAdminDeDaoOrder();
    }
    //�ɹ�����Ա�鿴�˻�
    public String getTuiHuo(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	int pageSize = 10;
    	pageBean = pageBiz.selReturnGoods(pageSize, pageNumber, us.getUserId(), orderId,danhao,caigouyuan);
    	return "tuihuo";
    }
    //�ɹ�����Ա���ص�������
    public String upsumaitong(){
    	 try
         {
             String ch[] = request.getParameter("bulletinId").split("-");
             for(int i = 0; i < ch.length; i++)
             {
                 List<OrderTable> ls = orderDao.getSelId(Long.valueOf(Long.parseLong(ch[i])));
                 if(ls.size() != 0)
                 {
                     ls.get(0).setId(Long.parseLong(ch[i]));
                     ls.get(0).setSumaitong(1l);
                     orderDao.merge(ls.get(0));
                 }
             }

             msg = "�����ɹ�";
         }
         catch(Exception e)
         {
             e.printStackTrace();
         }
         return getDaiFaHuo();
    }
    public String upcgadminwenchengorder()
    {
        return "upcgadminorder";
    }

    public OrderTable getCgUpOrderId()
    {
        OrderTable stu = (OrderTable)orderDao.get(ordertable.getId());
        return stu;
    }
   
    public String getcgadminorder()
    {
        return "cguporder";
    }
    //�ɹ�����Ա��ɶ���
    public String getWanChengOrder()
    {
    	try {
    	
    		 LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	        int pageSize = 10;
    	        pageBean = pageBiz.selCaiGouAdminWanChengOrder(pageSize, pageNumber, us.getUserId(), orderId, time, time1,caigoutime,caigoutime1,bianma,gongyunshang,wuping);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
       
        return "cguporder";
    }

    public String caigouadminuporder()
    {
        Date d = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        String ff = f.format(d);
        request.setAttribute("date", ff);
        return "cgadminorder";
    }

    public String cgupdateorder()
    {
        Long cai = ordertable.getCaigouyuan();
        String danhao = ordertable.getDanhao();
        Double huokuan = ordertable.getHuokuan();
        Double yunfei = ordertable.getYunfei();
        String gongyunshang = ordertable.getGongyunshang();
        ordertable = (OrderTable)orderDao.get(ordertable.getId());
        ordertable.setDanhao(danhao);
        ordertable.setYunfei(yunfei);
        ordertable.setHuokuan(huokuan);
        ordertable.setCaigouyuan(cai);
        ordertable.setGongyunshang(gongyunshang);
        ordertable.setXiugai(1l);
        orderDao.merge(ordertable);
        return getWanChengOrder();
    }

    public String addproduct()
    {
        return "addcporder";
    }

    public String addorder()
    {
        String dingdan[] = request.getParameterValues("dingdan");
        String jine[] = request.getParameterValues("jine");
        String yunshu[] = request.getParameterValues("yunshu");
        String zhanghao[] = request.getParameterValues("zhanghaoId");
        String beizhu[] = request.getParameterValues("remark");
        String str[] = new String[dingdan.length];
        for(int i = 0; i < dingdan.length; i++)
        {
            List<OrderTable> ls = orderDao.getAllOrderId(dingdan[i]);
            if("".equals(dingdan[i]) || dingdan[i] == null)
                str[i] = i+".��������Ϊ��";
            else
            if(ls.size() != 0)
                str[i] = i+".�����Ѿ�����";
            else
            if("".equals(jine[i]) || jine[i] == null)
                str[i] = i+"����Ϊ��";
            else
            if("0".equals(zhanghao[i]) || Integer.parseInt(zhanghao[i]) == 0)
            {
                str[i] = i+"�˺�Ϊѡ��";
            } else
            {
                OrderTable order = new OrderTable();
                order.setTime(new java.sql.Date(System.currentTimeMillis()));
                order.setCaigouyuan(0L);
                order.setOrderId(dingdan[i]);
                order.setMoney(Double.parseDouble(jine[i]));
                order.setYunshu(yunshu[i]);
                order.setZhanghaoId(Long.parseLong(zhanghao[i]));
                order.setRemark(beizhu[i]);
                LoginInfo login = (LoginInfo)getFromSession("logininfo");
                order.setDengluId(login.getUserId());
                orderDao.merge(order);
                str[i] = i+".�����ɹ�";
            }
        }

        ActionContext.getContext().put("insert", str);
        return "addcporder";
    }

    public String ceshi()
    {
        return getCaiGouAdminDeDaoOrder();
    }

    public String oldOrder()
    {
        LoginInfo us = (LoginInfo)getFromSession("logininfo");
        String username = request.getParameter("username");
        String pwd = request.getParameter("pwd");
        UserInfo user = userDao.getByUserNameOnPwd(username, pwd);
        try
        {
            if(user != null)
            {
                List<OrderTable> order = orderDao.getOldDaoRu(user.getId());
                for(int i = 0; i < order.size(); i++)
                {
                    order.get(i).setId(((OrderTable)order.get(i)).getId());
                    order.get(i).setCaigouyuan(us.getUserId());
                    orderDao.merge(order.get(i));
                    msg = "�����ɹ�";
                }

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return getWanChengOrder();
    }

    public String oldUnFinishedOrder()
    {
        LoginInfo us = (LoginInfo)getFromSession("logininfo");
        String username = request.getParameter("username");
        String pwd = request.getParameter("pwd");
        UserInfo user = userDao.getByUserNameOnPwd(username, pwd);
        System.out.println((new StringBuilder("user")).append(user).toString());
        try
        {
            if(user != null)
            {
                List<OrderTable> order = orderDao.getOldUnDaoRu(user.getId());
                for(int i = 0; i < order.size(); i++)
                {
                     order.get(i).setId(order.get(i).getId());
                    order.get(i).setCaigouyuan(us.getUserId());
                    orderDao.merge(order.get(i));
                    msg = "�����ɹ�";
                }

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return getCaiGouAdminDeDaoOrder();
    }
    //�ɹ�����Ա�õ�����
    public String getCaiGouAdminDeDaoOrder()
    {
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
 	   List<OrderTable> aa = orderTableDao.getXiuGaiOrder(us.getUserId());
 	   if(aa != null){
 		   for(int i = 0; i < aa.size(); i++)
             {
 			    aa.get(i).setId(aa.get(i).getId());
 			    aa.get(i).setJingji(1l);
                 orderTableDao.merge(aa.get(i));
             }
 	   }
 	   int pageSize = 10;
 	   pageBean = pageBiz.selDeDaoOrder(pageSize, pageNumber, us.getUserId(), orderId, time, time1, zhanghaoId,category);
        return "caigouadmindedaoorder";
    }
    //�ɹ�����Ա������
    public String getDaiFaHuo()
    {
        int pageSize = 10;
        LoginInfo us = (LoginInfo)getFromSession("logininfo");
        pageBean = pageBiz.selCaiGouAdminDaiFaHuo(pageSize, pageNumber, us.getUserId(), orderId, time, time1,caigoutime,caigoutime1,wuping,gongyunshang);
        return "caigouadmindaifahuo";
    }

    public String adminweiwancheng()
    {
        try
        {
            LoginInfo us = (LoginInfo)getFromSession("logininfo");
            orders = orderTableDao.getCaiGouAdminDeDaoOrder1(us.getUserId(), orderId, time);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return "exportOrder";
    }

    public String xuanzhe()
    {
        orders = new ArrayList();
        String id[] = request.getParameter("bulletinId").split("-");
        for(int i = 0; i < id.length; i++)
        {
            List<OrderTable> os = orderDao.getSelId(Long.parseLong(id[i]));
            orders.add(os.get(0));
        }

        return "exportOrder";
    }

    public String upadmindaifahuo()
    {
        return "cgadminupdaifahuo";
    }
    //�ɹ�����Ա�޸Ĵ�����
    public String cgadminupdaifahuo()
    {
        String gongyunshang = ordertable.getGongyunshang();
        String beizhu = ordertable.getRemark();
        Long cai = ordertable.getCaigouyuan();
        String danhao = ordertable.getDanhao();
        Double huokuan = ordertable.getHuokuan();
        String dizhi = ordertable.getGuowaidizhi();
        String wuping = ordertable.getWuping();
        Date shijian = ordertable.getCaigoutime();
        Long caigou = ordertable.getGuoneiwangzhanId();
        Long kuaidi = ordertable.getKuaidifangshiId();
        String guojia = ordertable.getGuojia();
        ordertable = (OrderTable)orderDao.get(ordertable.getId());
        
        ordertable.setGongyunshang(gongyunshang);
        ordertable.setRemark(beizhu);
        ordertable.setCaigouyuan(cai);
        ordertable.setDanhao(danhao);
        ordertable.setHuokuan(huokuan);
        ordertable.setGuowaidizhi(dizhi);
        ordertable.setGuojia(guojia);
        ordertable.setWuping(wuping);
        ordertable.setCaigoutime(shijian);
        ordertable.setGuoneiwangzhanId(caigou);
        ordertable.setKuaidifangshiId(kuaidi);
        ordertable.setXiugai(1l);
        orderDao.merge(ordertable);
        return getDaiFaHuo();
    }
    //�ɹ�����Ա��������������ͨ������Ա
    public String adminsumaitong()
    {
        try
        {
            String ch[] = request.getParameter("bulletinId").split("-");
            for(int i = 0; i < ch.length; i++)
            {
                List<OrderTable> ls = orderDao.getSelId(Long.valueOf(Long.parseLong(ch[i])));
                if(ls.size() != 0)
                {
                    ls.get(0).setId(Long.parseLong(ch[i]));
                    ls.get(0).setSumaitong(1l);
                    ls.get(0).setFenpei(1l);
                    ls.get(0).setGetordersId(0l);
                    orderDao.merge(ls.get(0));
                }
            }

            msg = "�����ɹ�";
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return getCaiGouAdminDeDaoOrder();
    }
    //����ҵ�����
    public String daifa()
    {
        try
        {
            String ch[] = request.getParameter("bulletinId").split("-");
            for(int i = 0; i < ch.length; i++)
            {
                List<OrderTable> ls = orderDao.getSelId(Long.parseLong(ch[i]));
                if(ls.size() != 0)
                {
                    ls.get(0).setId(Long.parseLong(ch[i]));
                    ls.get(0).setDenghuixin(1l);
                    ls.get(0).setFenpei(1l);
                    ls.get(0).setGetordersId(0l);
                    orderDao.merge(ls.get(0));
                }
            }

            msg = "�����ɹ�";
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return getCaiGouAdminDeDaoOrder();
    }
    //��ɶ�������
    public String fanhuidaifahuo()
    {
        try
        {
            String ch[] = request.getParameter("bulletinId").split("-");
            for(int i = 0; i < ch.length; i++)
            {
                List<OrderTable> ls = orderDao.getSelId(Long.valueOf(Long.parseLong(ch[i])));
                if(ls.size() != 0)
                {
                    ls.get(0).setId(Long.parseLong(ch[i]));
                    ls.get(0).setWancheng(0l);
                    ls.get(0).setDaifahuo(0l);
                    ls.get(0).setDaochu(0l);
                    ls.get(0).setGetordersId(1l);
                    orderDao.merge(ls.get(0));
                }
            }

            msg = "�����ɹ� ";
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return getWanChengOrder();
    }
    //�鿴���ⶩ��
    public String getWenTiOrder()
    {
        int pageSize = 10;
        pageBean = pageBiz.selAllWenTiOrder(pageSize, pageNumber, orderId,time,time1);
        return "wentiorder";
    }
    //�Ѿ���ɶ���
    public String mysuccessful(){
         String ch[] = request.getParameter("bulletinId").split("-");
         for(int i = 0; i < ch.length; i++)
         {
             List<OrderTable> ls = orderDao.getSelId(Long.valueOf(Long.parseLong(ch[i])));
             if(ls.size() != 0)
             {
                 ls.get(0).setId(Long.parseLong(ch[i]));
                 ls.get(0).setDaifahuo(0l);
                 ls.get(0).setWancheng(1l);
                 ls.get(0).setGetordersId(0l);
                 ls.get(0).setSumaitong(0l);  
                 ls.get(0).setWanchengtime(new java.sql.Date(System.currentTimeMillis()));
                 orderDao.merge(ls.get(0));
             }
         }
         msg = "�����ɹ� ";
    	return getWenTiOrder();
    }
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
                if(sel.length != ch.length)
                    str[i] = i+"���˺�δѡ�����ʧ�ܣ�";
                else
                if("".equals(sel[i]) || sel[i] == null)
                    str[i] = i+"����ʧ��";
                else
                if(ls.size() != 0)
                {
                    ls.get(0).setId(Long.parseLong(ch[i]));
                    ls.get(0).setFenpei(1l);
                    ls.get(0).setCaigouyuan(Long.parseLong(sel[i]));
                    ls.get(0).setDenghuixin(0l);
                    ls.get(0).setSumaitong(0l);
                    ls.get(0).setDaifahuo(0l);
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
    //�ɹ�����Ա�鿴����
    public String jiufen(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	int pageSize = 10;
    	pageBean = pageBiz.selDisputeOrder(pageSize, pageNumber, us.getUserId(), orderId, caigoutime, caigoutime1, leimus);
    	return "caigouadminjiufen";
    }
    //�ɹ�����Ա�鿴����С��0�Ķ���
    public String liRunAdmin(){
    
    	return "lirunadminzero";
    }
    //�鿴��涩��
    public String getKuCun(){
   	 LoginInfo us = (LoginInfo)getFromSession("logininfo");
   	 int pageSize = 10;
   	 pageBean = pageBiz.selKuCunOrder(pageSize, pageNumber,us.getUserId(), orderId);
   	 return "kucun";
    }
    //�ɹ�����Ա�޸Ŀ��
    public String upkucun(){
    	return "upnumber";
    }
    //�ɹ�����Ա�޸Ŀ�� 
    public String updatekucun(){
    	Long num = ordertable.getNum();
    	String remark = ordertable.getRemark();
    	ordertable = orderDao.get(ordertable.getId());
    	ordertable.setNum(num);
    	ordertable.setRemark(remark);
    	orderDao.merge(ordertable);
    	msg = "�޸ĳɹ� ";
    	return getKuCun();
    }
    //�޸��˺�
    public String updatezhanghao(){
    	return "upzhanghao";
    }
    //�޸��˺�
    public String myupzhanghao(){
    	 try
         {
    		 	Long zhanghao = ordertable.getZhanghaoId();
    	    	ordertable = orderDao.get(ordertable.getId());
    	    	ordertable.setZhanghaoId(zhanghao);
    	    	orderDao.merge(ordertable);
    	    	msg = "�޸ĳɹ� ";
         }
         catch(Exception e)
         {
             e.printStackTrace();
         }
    	return getDaiFaHuo();
    }
    //����ȫ�����׶���
    public String jforder(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	orders=orderTableDao.getJfOrder(us.getUserId());
    	if(orders.size()!=0){
    		  if(orders.size() != 0)
    	        {
	        	  for(int i = 0; i < orders.size(); i++)
	              {
	                  orders.get(i).setId(orders.get(i).getId());
	                  orders.get(i).setCgjf(1l);
	                  orderTableDao.merge(orders.get(i));
	              }
    	        }
    	}
    	return "cgadminexportOrder";
    }
    //�ɹ�����Ա�޸ĵ���״̬
    public String updateAllDaoChu()
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
                    if(ls.get(0).getDaochu() == 1){     	
                    	ls.get(0).setDaochu(0l);
                    }
                    else if(ls.get(0).getDaochu()==0){  	
                    	ls.get(0).setDaochu(1l);
                    }
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
        return "caigouadminjiufen";
    }
    //�����񸶿��
    public String getPayment(){
    
          String ch[] = request.getParameter("bulletinId").split("-");
          String str[] = new String[ch.length];
          for(int i = 0; i < ch.length; i++)
          {
              List<OrderTable> ls = orderDao.getSelId(Long.valueOf(Long.parseLong(ch[i])));
              if(ls.size() != 0){
                      ls.get(0).setId(Long.parseLong(ch[i]));
                      ls.get(0).setDaifahuo(3l);
                      ls.get(0).setFenpei(1l);
                      ls.get(0).setGetordersId(0l);
                      ls.get(0).setSingleAuditId(0l);
                      orderDao.merge(ls.get(0));
                      str[i] = i +".�����ɹ���";
              }
          }
          return getSingleAudit();
    }
    //�����񸶿��
    public String getPayments(){
    
          String ch[] = request.getParameter("bulletinId").split("-");
          String str[] = new String[ch.length];
          for(int i = 0; i < ch.length; i++)
          {
              List<OrderTable> ls = orderDao.getSelId(Long.valueOf(Long.parseLong(ch[i])));
              if(ls.size() != 0){
                      ls.get(0).setId(Long.parseLong(ch[i]));
                      ls.get(0).setDaifahuo(3l);
                      ls.get(0).setFenpei(1l);
                      ls.get(0).setGetordersId(0l);
                      ls.get(0).setSingleAuditId(0l);
                      orderDao.merge(ls.get(0));
                      str[i] = i +".�����ɹ���";
              }
          }
          return getCaiGouAdminDeDaoOrder();
    }
    //��ѯ����
    public YunFeiTable getCountry(){
    	YunFeiTable tt = null;
    	try {
    		 OrderTable stu = (OrderTable)orderDao.get(ordertable.getId());
    	  	 tt =  yunFeiTableDao.getCorresponding(stu.getCountry());
    	  	  System.out.println("++tt++"+tt.getGuojia());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
  	 
  	  return tt;
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
                	  if (ls.get(0).getTuihuo() == 1) {
                		  ls.get(0).setId(Long.parseLong(ch[i]));
                          ls.get(0).setDaifahuo(0l);
                          ls.get(0).setWancheng(1l);
                          ls.get(0).setGetordersId(0l);
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
          
   	 return getCaiGouAdminDeDaoOrder();
    }
    //����Ա�µ����
    public String getSingleAudit(){
    	try {	
    		LoginInfo us = (LoginInfo)getFromSession("logininfo");
	
	    	//List<CaiGou> cg = caigoudao.getAdCaiGou(us.getUserId());
	    	int pageSize = 10;
	    	pageBean = pageBiz.getSingleAudit(pageSize, pageNumber, orderId, us.getUserId());
	    	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	return "cgsingleaudit";
    }
    //�µ����
    public String singleaudit(){
    	String ch[] = request.getParameter("bulletinId").split("-");
        String str[] = new String[ch.length];
        for(int i = 0; i < ch.length; i++)
        {
            List<OrderTable> ls = orderDao.getSelId(Long.valueOf(Long.parseLong(ch[i])));
            if(ls.size() != 0){
                    ls.get(0).setId(Long.parseLong(ch[i]));
                    ls.get(0).setSingleAuditId(1l);
                    ls.get(0).setFenpei(1l);
                    ls.get(0).setGetordersId(0l);
                    orderDao.merge(ls.get(0));
                    str[i] = i +".�����ɹ���";
            }
        }
        ActionContext.getContext().put("strsd", str);
    	return getCaiGouAdminDeDaoOrder();
    }
    //����ɶ������ظ����� 
    public String fhdaifahuo()
    {
    	System.out.println("++++++");
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
                    ls.get(0).setFenpei(1l);
                    ls.get(0).setWancheng(0l);
                    ls.get(0).setDaifahuo(0l);
                    ls.get(0).setDaochu(0l);
                    ls.get(0).setSumaitong(0l);
                    ls.get(0).setGetordersId(1l);
                    System.out.println("++ordersid+++"+ls.get(0).getGetordersId());
                    ls.get(0).setSingleAuditId(0l);
                    orderDao.merge(ls.get(0));
                    str[i] = i + ".�����ɹ���";
                } 
            }

            ActionContext.getContext().put("wenti", str);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return getSingleAudit();
    }
    //�ɹ�����Ա�鿴δ��ɶ���
    public String getOutstandingOrders(){
    	LoginInfo us = (LoginInfo)getFromSession("logininfo");
    	int pageSize = 10;
    	pageBean = pageBiz.getOutstandingOrders(pageSize, page, us.getUserId(), orderId, caigouyuan);
    	return "caigouweiwancheng";
    }
    //����ͨ¼��
     public String getIntoaSingle(){
    	 LoginInfo us = (LoginInfo) getFromSession("logininfo");
    	 int pageSize = 10;
    	 pageBean = pageBiz.getIntoaSingle(pageSize, pageNumber, us.getUserId(), caigouyuan, orderId, time, time1);
    	 return "sumaitong";
     }
     //����ͨ�������ظ�ҵ��
     public String yewufanhui()
     {
         try
         {
             String ch[] = request.getParameter("bulletinId").split("-");
             for(int i = 0; i < ch.length; i++)
             {
                 List<OrderTable> ls = orderDao.getSelId(Long.valueOf(Long.parseLong(ch[i])));
                 if(ls.size() != 0)
                 {
                     ((OrderTable)ls.get(0)).setId(Long.valueOf(Long.parseLong(ch[i])));
                     ((OrderTable)ls.get(0)).setSumaitong(2l);
                     ls.get(0).setYunfei(0d);
                     orderDao.merge((OrderTable)ls.get(0));
                 }
             }

             msg = "�����ɹ�";
         }
         catch(Exception e)
         {
             e.printStackTrace();
         }
         return getIntoaSingle();
     }
    public void setServletRequest(HttpServletRequest arg0)
    {
        request = arg0;
    }
}
