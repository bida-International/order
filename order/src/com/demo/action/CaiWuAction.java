package com.demo.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.record.PageBreakRecord.Break;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.demo.dao.GuoJiaDao;
import com.demo.dao.KuCunDao;
import com.demo.dao.KuaiDiFangShiDao;
import com.demo.dao.OrderDao;
import com.demo.dao.OrderTableDao;
import com.demo.dao.YuCunDao;
import com.demo.dao.Courier.GjslDao;
import com.demo.dao.Courier.HkdbDao;
import com.demo.dao.Courier.HydbDao;
import com.demo.dao.Courier.SALdbDao;
import com.demo.dao.Courier.YunFeiTableDao;
import com.demo.dao.Courier.YunFeieDao;
import com.demo.entity.KuCunTable;
import com.demo.entity.KuaiDiFangShi;
import com.demo.entity.YunCun;
import com.demo.entity.Courier.Gjsl;
import com.demo.entity.Courier.Hkdb;
import com.demo.entity.Courier.Hydb;
import com.demo.entity.Courier.SALdb;
import com.demo.entity.Courier.YunFeiTable;
import com.demo.entity.Courier.YunFeiTableE;
import com.demo.entity.order.OrderTable;
import com.demo.list.PageModel;
import com.demo.page.PageBean;
import com.demo.page.PageBiz;
import com.demo.vo.LoginInfo;
import com.opensymphony.xwork2.ActionContext;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

@Controller("caiWuAction")
@Scope("prototype")
public class CaiWuAction extends BaseAction implements ServletRequestAware
{
    private static final long serialVersionUID = 1L;
    @Resource
	private PageBiz pageBiz;
    @Resource
    private OrderDao orderDao;
	@Resource
    private OrderTableDao orderTableDao;
	@Resource
    private GuoJiaDao guoJiaDao;
	@Resource
	private YuCunDao yuCunDao;
    @Resource
    private YunFeiTableDao yunFeiTableDao;
	@Resource
	private YunFeieDao yunFeieDao;
	@Resource
	private KuaiDiFangShiDao kuaiDiFangShiDao;
	@Resource
	private GjslDao gjslDao;
	@Resource
	private SALdbDao sALdbDao;
	@Resource
	private HydbDao hydbDao;
	@Resource
	private KuCunDao kucundao;
	@Resource
	private HkdbDao hkdbDao;
    private PageBean pageBean;
    public int pageNumber;
    public String danhao;
    public String time;
    public File excelfile;
    public String time1;
    public String orderId;
    public String gongyunshang;
    public Long selzhanghao;
    public List<OrderTable> orders;
    public String usertype;
    public String guoneidanhao;
    public int pageindex;
    public String msg;
    private OrderTable ordertable;
    public Long caigouyuan;
    public String chuli;
    private HttpServletRequest request;
    public File getExcelfile() {
		return excelfile;
	}
	public void setExcelfile(File excelfile) {
		this.excelfile = excelfile;
	}
	public OrderTable getOrdertable() {
		return ordertable;
	}
	public void setOrdertable(OrderTable ordertable) {
		this.ordertable = ordertable;
	}
	public PageBean getPageBean() {
		return pageBean;
	}
	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

    //财务返回导出运费页面
    public String getdaochukehu(){
    	return "importkehu";
    }
    //财务导出客户的运费
    public InputStream getExcelFile()
    {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("sheet1");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        HSSFCellStyle cellstyle = workbook.createCellStyle();
        sheet.setColumnWidth(0, 2000);
        sheet.setColumnWidth(1, 2500);
        sheet.setColumnWidth(2, 2500);
        sheet.setColumnWidth(3, 3900);
        sheet.setColumnWidth(4, 2000);
     
        try
        {
        	String id = request.getParameter("guke");
        	String time = request.getParameter("time");
        	String time1 = request.getParameter("time1");
            orders = orderDao.getDaoChuKeHuYunFei(Long.parseLong(id),time,time1);
           
            for(int i = 0; i < orders.size(); i++)
            {
                row = sheet.createRow(i);
                row.setHeight((short)1000);
                cteateCell(workbook, row, 0, orders.get(i).getName());
                if(orders.get(i).getZhongliang() == null){
                	cteateCell(workbook, row, 1, "");
                }else{
                	cteateCell(workbook, row, 1, orders.get(i).getZhongliang().toString());	
                }
                if(orders.get(i).getGuojiaId() == null){
                	cteateCell(workbook, row, 2, "");	
                }else{
                	cteateCell(workbook, row, 2, getAllYunFei(orders.get(i).getGuojiaId().toString()));
                }
                cteateCell(workbook, row, 3, orders.get(i).getDanhao());
                if(orders.get(i).getYunfei()==null){
                	cteateCell(workbook, row, 4, "");
                }else{
                	Double  yunfei = orders.get(i).getYunfei();
                	
                	DecimalFormat df = new DecimalFormat("0.000");
        			String num = df.format(yunfei);
                	cteateCell(workbook, row, 4,num);
                }
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try
        {
            workbook.write(baos);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        byte ba[] = baos.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        try
        {
            for(int i = 0; i < orders.size(); i++)
            {
                orders.get(i).setId(orders.get(i).getId());
                orders.get(i).setYunfeidaochu(1l);
                orderTableDao.merge(orders.get(i));
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return bais;
    }
    public String execute() throws Exception
    {
        return super.execute();
    }

    private void cteateCell(HSSFWorkbook wb, HSSFRow row, int col, String val)
    {
    	  //创建一个celll单元格  
        HSSFCell cell=row.createCell(col);  
        cell.setCellValue(val);  
        //创建样式  
        HSSFCellStyle cellstyle=wb.createCellStyle();    
        cellstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直方向居中对齐  
       // cellstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);//带边框  
        cellstyle.setWrapText(true);//设置自动换行  
        cell.setCellStyle(cellstyle);//给单元格设置样式
    }
    //财务修改客户入账情况 
    public String getRuZhang(){
    	int pageSize = 10;
    	pageBean = pageBiz.selRuZhang(pageSize, pageNumber, orderId, time, time1);
    	return "getruzhang";
    }
    //财务修改入账
    public String upruzhang(){
    	 try
         {
             String[] ch = request.getParameter("bulletinId").split("-");
             String[] sel = request.getParameter("seluserid").split("-");
             String[] str = new String[ch.length];
             for(int i = 0; i < ch.length; i++)
             {
            	
                 List<OrderTable> ls = orderDao.getSelId(Long.parseLong(ch[i]));
                 if(ls.size() != 0)
                 {
                     ls.get(0).setId(Long.parseLong(ch[i]));
                     ls.get(0).setRuzhang(Long.parseLong(sel[i]));
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
    	return getRuZhang();
    }
 

    //财务导出公司运费
    public String getDaoChuYunFei(){
    	return "daochuyunfei";
    }
    
    
    //财务导出公司运费
    public String daochuyunfei(){
    	String time = request.getParameter("time");
    	String time1 = request.getParameter("time1");
    	orders = orderTableDao.getDaoChuYunFei(time,time1);
    	return "exportKeHuOrder";
    	
    }
    //财务查看待入单
    public String getDaiRuDan()
    {
    	int pageSize = 10;
    	pageBean = pageBiz.selDaiRudan(pageSize, pageNumber, guoneidanhao, orderId);
        return "dairudan";
    }
    //用编号查询全部
    public OrderTable getUpdateIds()
    {
        OrderTable stu = null;
        try
        {
            stu = orderDao.get(ordertable.getId());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return stu;
    }
    //财务修改待入单
    public String daifangquwanchengorder()
    {
        try
        {
            String[] ch = request.getParameter("bulletinId").split("-");
            String[] str = new String[ch.length];
            for(int i = 0; i < ch.length; i++)
            {
                List<OrderTable> ls = orderDao.getSelId(Long.parseLong(ch[i]));
                List<KuCunTable> kc = kucundao.getKuCunId(ls.get(0).getKucunid());
                if (kc.size() != 0) {
					kc.get(0).setId(kc.get(0).getId());
					kc.get(0).setNum((kc.get(0).getNum())-(ls.get(0).getNum()));
					kc.get(0).setZynum((kc.get(0).getZynum())-(ls.get(0).getNum()));
					kc.get(0).setTotalprice((kc.get(0).getTotalprice())-(kc.get(0).getUnitprice()*ls.get(0).getNum()));
					kucundao.merge(kc.get(0));
				}
                if(ls.size() != 0)
                {
                    ls.get(0).setId(Long.parseLong(ch[i]));
                    ls.get(0).setDaifahuo(0l);
                    ls.get(0).setSumaitong(0l);
                    ls.get(0).setWancheng(1l);
                    ls.get(0).setDaochu(0l);
                    ls.get(0).setGetordersId(0l);
                    ls.get(0).setWanchengtime(new java.sql.Date(System.currentTimeMillis()));
                    orderDao.merge(ls.get(0));
                    str[i] = i+".操作成功";
                }
            }
            ActionContext.getContext().put("strss", str);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return getDaiRuDan();
    }
    //财务返回代发货
    public String fanhuidaifahuo()
    {
        String[] id = request.getParameter("bulletinId").split("-");
        for(int i = 0; i < id.length; i++)
        {
            List<OrderTable> ls = orderDao.getSelId(Long.parseLong(id[i]));
            if(ls.size() != 0)
            {
                ls.get(0).setId(Long.parseLong(id[i]));
                ls.get(0).setDaifahuo(1l);
                ls.get(0).setDaochu(0l);
                orderDao.merge(ls.get(0));
            }
        }

        msg = "操作成功";
        return getDaiRuDan();
    }
    //返回给仓库待待放区
    public String cangkudaifangqu(){
    	String[] id = request.getParameter("bulletinId").split("-");
        for(int i = 0; i < id.length; i++)
        {
            List<OrderTable> ls = orderDao.getSelId(Long.parseLong(id[i]));
            if(ls.size() != 0)
            {
                ls.get(0).setId(Long.parseLong(id[i]));
                ls.get(0).setDaochu(3l);
                
                orderDao.merge(ls.get(0));
            }
        }
        msg = "操作成功";
    	return getDaiRuDan();
    }
    //业务返回给顾客已经发货
    public String gukeyifahuo(){
    	String[] id = request.getParameter("bulletinId").split("-");
        for(int i = 0; i < id.length; i++)
        {
            List<OrderTable> ls = orderDao.getSelId(Long.parseLong(id[i]));
            if(ls.size() != 0)
            {
                ls.get(0).setId(Long.parseLong(id[i]));
                ls.get(0).setWancheng(1l);
                ls.get(0).setWanchengtime(new java.sql.Date(System.currentTimeMillis()));
                orderDao.merge(ls.get(0));
            }
        }

        msg = "操作成功";
    	return getDaiRuDan();
    }
    //财务返回修改待入单
    public String updairudan(){	
    	return "updairudan";
    }
    //财务返回修改待入单
    public String czupdatedairudan(){	
    	return "czupdairudan";
    }
    //超重
    public String czupdatedanhao(){
    	
    	return "updatedanhao";
    }
    
    //财务修改待入单
    public String updatedairudan() throws Exception{
    	String typeuser = request.getParameter("usertype");
    	String[] orderId = request.getParameterValues("orderIds");
    	String[] danhao = request.getParameterValues("danhao");
    	String[] guojia = request.getParameterValues("guojiaId");
    	String[] zhongliang = request.getParameterValues("zhongliang");
    	String[] type = request.getParameterValues("type");
    	String[] yunfei = request.getParameterValues("yunfei");
    	String dengluId = request.getParameter("dengluId");
    	 String[] str = new String[danhao.length];
    	try {
    		for(int i = 0; i < danhao.length; i++) {
    		
    		if(Integer.parseInt(typeuser) == 1){	
    	    	ordertable = orderDao.get(ordertable.getId());
    	    	ordertable.setDanhao(danhao[i]);
    	    	ordertable.setZhongliang(Double.parseDouble(zhongliang[i]));
    	    	ordertable.setYunfei(Double.parseDouble(yunfei[i]));	
    	    	orderDao.merge(ordertable);
    	    	msg = "修改成功、是否继续 ";
    			return "updairudan";
    		}
			List<OrderTable> dan = orderDao.getChaKanOrder(orderId[i]);
		
			List<YunFeiTable> ss = null;
			
			if(Integer.parseInt(type[i]) != 2){
			
				ss = guoJiaDao.getAllGuoJia(guojia[i].trim());
				if(ss.size()==0){
				str[i] =i + ".未找到此国家 ";
    			ActionContext.getContext().put("insert", str);
        		return "updairudan";
				}
			}
    		List<OrderTable> hh = orderDao.getAllDanHao(orderId[i],danhao[i]);
    		List<YunCun> yy = yuCunDao.getUserLastNum(Long.parseLong(dengluId));
    		List<YunFeiTableE> tt = null;
    		if(Integer.parseInt(type[i]) == 2){
    			tt = yunFeieDao.getGuoJia(guojia[i]);
    			if(tt.size()==0){
    			str[i] =i + ".E邮宝未找到此国家 ";
    			ActionContext.getContext().put("insert", str);
        		return "updairudan";
    			}
    		}
    		if(danhao.length != orderId.length){
    		
    			str[i] =i + ".单号未填写 ";
    			ActionContext.getContext().put("insert", str);
        		return "updairudan";
    		}
    		if(Double.parseDouble(zhongliang[i]) > 2 && Integer.parseInt(type[i]) == 0){
    			str[i] =i + ".小包重量不能大于2";
    			ActionContext.getContext().put("insert", str);
    			return "updairudan";
    		}
    		if(zhongliang[i] == null || "".equals(zhongliang[i])){
    			str[i] =i + ".重量未填写";
    			ActionContext.getContext().put("insert", str);
    			return "updairudan";
    		}   
    	
    		else if(Integer.parseInt(type[i]) == 0 && dan.size() != 0 && (dan.get(0).getDanhao() != null && !"".equals(dan.get(0).getDanhao()))){
    			ordertable = orderDao.get(ordertable.getId());
    			OrderTable ds = new OrderTable();
    			Long s=System.currentTimeMillis();
    			ds.setOrderId(s.toString());
    			
    			ds.setKuaidifangshiId(ordertable.getKuaidifangshiId());
    			ds.setSjc(1l);
    			orderTableDao.merge(ds);
    			Double yun = (ss.get(0).getMoney()*Double.parseDouble(zhongliang[i])*0.85+8);
        		
        		String gg="";
        		if(ordertable.getDggjdh()==null){
        			gg = "";
        		}else{
        			gg=ordertable.getDggjdh();
        		}
        		String k = "单号"+ds.getOrderId()+","+gg+"";
        		ordertable.setDggjdh(k);
        		Double aaa = null;
        		if(dan.get(0).getZhongliang() == null){
        			aaa = 0d;
        		}else{
        			aaa = dan.get(0).getZhongliang();
        		}
        		
        		ordertable.setYunfei(yun+dan.get(0).getYunfei());
        		if(yy.size() !=0){
        			Double u = yy.get(0).getMoney();
            		Double aa = u - ordertable.getYunfei();
        			YunCun bb = new YunCun();
        			bb.setMoney(u-Double.parseDouble(yunfei[i]));
        			bb.setSymoney(Double.parseDouble(yunfei[i]));
        			bb.setGukeId(yy.get(0).getGukeId());
        			
        			 java.util.Date d = new java.util.Date();
    	             SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	             String ff = f.format(d);
        			bb.setSytime(ff);
        			if(aa<0){
        				msg ="余额不足、还差"+(-aa)+"、操作成功";
        			}else{
        			 msg = "操作成功";
        			}
                     yuCunDao.merge(bb);
        			
        		}else{
        			msg ="提醒顾客付运费、操作成功 ";
        		}
        		str[i] =i +".操作成功 ";
        		orderDao.merge(ordertable);
    		}
    		else if(Integer.parseInt(type[i]) == 0 && dan.size() != 0 && (dan.get(0).getDanhao() == null || "".equals(dan.get(0).getDanhao()))){
    	
        		Double yun = (ss.get(0).getMoney()*Double.parseDouble(zhongliang[i])*0.85+8);
        		ordertable = orderDao.get(ordertable.getId());
        	
        		ordertable.setDggjdh(ordertable.getOrderId());
        	
        		ordertable.setYunfei(yun);
        		
        		if(yy.size() !=0){
        			Double u = yy.get(0).getMoney();
            		Double aa = u - yun;
        			YunCun bb = new YunCun();
        			bb.setMoney(u-yun);
        			bb.setSymoney(yun);
        			bb.setGukeId(yy.get(0).getGukeId());
        			 java.util.Date d = new java.util.Date();
    	             SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	             String ff = f.format(d);
        			bb.setSytime(ff);
        			if(aa<0){
        				msg ="余额不足、还差"+(-aa)+"、操作成功";
        			}else{
        			 msg = "操作成功";
        			}
                     yuCunDao.merge(bb);
        		}else{
        			msg ="提醒顾客付运费、操作成功 ";
        		}
        		str[i] =i +".操作成功 ";
        		orderDao.merge(ordertable);
        	}
        	else if(Integer.parseInt(type[i]) == 1 && (dan.get(0).getDanhao() != null && !"".equals(dan.get(0).getDanhao()))){
        	
        		ordertable = orderDao.get(ordertable.getId());
        		OrderTable ds = new OrderTable();
    			Long s=System.currentTimeMillis();
    			ds.setOrderId(s.toString());
    		
    			ds.setKuaidifangshiId(ordertable.getKuaidifangshiId());
    			ds.setSjc(1l);
    			orderTableDao.merge(ds);
    	    	ordertable.setYunfei(Double.parseDouble(yunfei[i])+dan.get(0).getYunfei());
    	    	if(yy.size() !=0){
        			Double u = yy.get(0).getMoney();
            		Double aa = u - ordertable.getYunfei();
        			YunCun bb = new YunCun();
        			bb.setMoney(u-Double.parseDouble(yunfei[i]));
        			bb.setSymoney(Double.parseDouble(yunfei[i]));
        			bb.setGukeId(yy.get(0).getGukeId());
        			 java.util.Date d = new java.util.Date();
    	             SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	             String ff = f.format(d);
        			bb.setSytime(ff);
        			if(aa<0){
        				msg ="余额不足、还差"+(-aa)+"、操作成功";
        			}else{
        			 msg = "操作成功";
        			}
                     yuCunDao.merge(bb);
        			
        		}else{
        			msg ="提醒顾客付运费、操作成功 ";
        		}
    	    	String gg="";
        		if(ordertable.getDggjdh()==null){
        			gg = "";
        		}else{
        			gg=ordertable.getDggjdh();
        		}
        		String k = "单号"+ds.getOrderId()+","+gg+"";
        		ordertable.setDggjdh(k);
    	    	str[i] =i + ".操作成功 ";
    	    	orderDao.merge(ordertable);
        	}
        	else if(Integer.parseInt(type[i]) == 1 && (dan.get(0).getDanhao() == null || "".equals(dan.get(0).getDanhao()))){
     
    	    	ordertable = orderDao.get(ordertable.getId());
    	    	ordertable.setYunfei(Double.parseDouble(yunfei[i]));
    	    	ordertable.setDggjdh(ordertable.getOrderId());
    	    	if(yy.size() !=0){
        			Double u = yy.get(0).getMoney();
            		Double aa = u - ordertable.getYunfei();
        			YunCun bb = new YunCun();
        			bb.setMoney(u-Double.parseDouble(yunfei[i]));
        			bb.setSymoney(Double.parseDouble(yunfei[i]));
        			bb.setGukeId(yy.get(0).getGukeId());
        			 java.util.Date d = new java.util.Date();
    	             SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	             String ff = f.format(d);
        			bb.setSytime(ff);
        			if(aa<0){
        				msg ="余额不足、还差"+(-aa)+"、操作成功";
        			}else{
        			 msg = "操作成功";
        			}
                     yuCunDao.merge(bb);
        			
        		}else{
        			msg ="提醒顾客付运费、操作成功 ";
        		}
    	    	str[i] =i + ".操作成功 ";
    	    	orderDao.merge(ordertable);
        	}
        	else if(Integer.parseInt(type[i]) == 2 && ((dan.get(0).getDanhao() == null || "".equals(dan.get(0).getDanhao())))){
        	
        		Double yun = 0d;
    	    	ordertable = orderDao.get(ordertable.getId());
    	    	ordertable.setDggjdh(ordertable.getOrderId());
    	    	if("澳洲".equals(guojia[i]) && 0<Double.parseDouble(zhongliang[i])&& Double.parseDouble(zhongliang[i])<500){
    	    		 yun = 25d;	
    	    		
    	    		 ordertable.setYunfei(yun);
    	    	}
    	    	if("澳洲".equals(guojia[i]) && (500<Double.parseDouble(zhongliang[i])&& Double.parseDouble(zhongliang[i])<2000)){
    	    		 yun = 30d;
    	    	
    	    		 ordertable.setYunfei(yun);
    	    	}else if(!"澳洲".equals(guojia[i])){
    	    		 yun = (0.08*Double.parseDouble(zhongliang[i]))+7;
    	    		
    	    		 ordertable.setYunfei(yun);
    	    	}
        		if(yy.size() !=0){
        			Double u = yy.get(0).getMoney();
            		Double aa = u - yun;
        			YunCun bb = new YunCun();
        			bb.setMoney(u-yun);
        			bb.setSymoney(yun);
        			bb.setGukeId(yy.get(0).getGukeId());
        			
        			 java.util.Date d = new java.util.Date();
    	             SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	             String ff = f.format(d);
        			bb.setSytime(ff);
        			if(aa<0){
        				msg ="余额不足、还差"+(-aa)+"、操作成功";
        			}else{
        			 msg = "操作成功";
        			}
                     yuCunDao.merge(bb);
        			
        		}else{
        			msg ="提醒顾客付运费、操作成功 ";
        		}
        		
    	    	str[i] =i + ".操作成功 ";
    	    	orderDao.merge(ordertable);
        	}
        	else if(Integer.parseInt(type[i]) == 2 && ((dan.get(0).getDanhao() != null || !"".equals(dan.get(0).getDanhao())))){
        		Double yun = 0d;
    	    	ordertable = orderDao.get(ordertable.getId());
    	    	OrderTable ds = new OrderTable();
    			Long s=System.currentTimeMillis();
    			ds.setOrderId(s.toString());
    		
    			ds.setKuaidifangshiId(ordertable.getKuaidifangshiId());
    			ds.setSjc(1l);
    			orderTableDao.merge(ds);
    	    	if("澳洲".equals(guojia[i]) && 0<Double.parseDouble(zhongliang[i])&& Double.parseDouble(zhongliang[i])<500){
    	    	if(dan.get(0).getDanhao() != null || !"".equals(dan.get(0).getDanhao())){
    	    		
    	    			 yun = dan.get(0).getYunfei()+25d;
    	    		
    	    			 ordertable.setYunfei(yun);
    	    		}
    	    	}
    	    	if("澳洲".equals(guojia[i]) && (500<Double.parseDouble(zhongliang[i])&& Double.parseDouble(zhongliang[i])<2000)){
    	    		if(dan.get(0).getDanhao() != null || !"".equals(dan.get(0).getDanhao())){
       	    		 yun = dan.get(0).getYunfei()+30d;
       	    		
       	    		 ordertable.setYunfei(yun);
       	    		}	
    	    	}else if(!"澳洲".equals(guojia[i])){
    	    		if(dan.get(0).getDanhao() != null || !"".equals(dan.get(0).getDanhao())){
    	    			yun =dan.get(0).getYunfei()+((0.08*Double.parseDouble(zhongliang[i])))+7;
    	    		
    	    			 ordertable.setYunfei(yun);
    	    		}
    	    	}
        		if(yy.size() !=0){
        			
        			Double u = yy.get(0).getMoney();
            		Double aa = u - yun;
        			YunCun bb = new YunCun();
        			bb.setMoney(u-yun);
        			bb.setSymoney(yun);
        			bb.setGukeId(yy.get(0).getGukeId());
        			
        			 java.util.Date d = new java.util.Date();
    	             SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	             String ff = f.format(d);
        			bb.setSytime(ff);
        			if(aa<0){
        				msg ="余额不足、还差"+(-aa)+"、操作成功";
        			}else{
        			 msg = "操作成功";
        			}
                     yuCunDao.merge(bb);
        			
        		}else{
        			msg ="提醒顾客付运费、操作成功 ";
        		}
    	    	str[i] =i + ".操作成功 ";
    	    	String gg="";
        		if(ordertable.getDggjdh()==null){
        			gg = "";
        		}else{
        			gg=ordertable.getDggjdh();
        		}
        		String k = "单号"+ds.getOrderId()+","+gg+"";
        		ordertable.setDggjdh(k);
    	    	orderDao.merge(ordertable);
        	}
    		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		ActionContext.getContext().put("insert", str);
    	return getDaiRuDan();
    }
    //财务修改运输单号 
    public String updanhao(){
    	return "updanhao";
    }
  //财务修改运输单号
    public String updatedanhao(){
    	String danhao = ordertable.getDanhao();
    	ordertable = orderDao.get(ordertable.getId());
    	ordertable.setDanhao(danhao);
    	orderDao.merge(ordertable);
    	msg = "修改成功 ";
    	return getDaiRuDan();
    }


    //财务得到修改订单
    public String getDeDaoOrderAll()
    {
        int pageSize = 10;
        pageBean = pageBiz.selYeWuDeDaoOrder(pageSize, pageNumber, orderId, gongyunshang, selzhanghao, danhao,time,time1);
        return "yewuallorder";
    }
    //财务修改订单
    public String getorderIdAll()
    {
        return "updateorders";
    }
    //业务修改订单
    public String updatedingdanAll()
    {
    
         String order = ordertable.getOrderId();
         String yunshu = ordertable.getYunshu();
         Double yunfei = ordertable.getYunfei();
         String guojia = ordertable.getGuojia();
         String remark = ordertable.getRemark();
         Double huilv = ordertable.getHuilv();
         Double tuikuan = ordertable.getTuikuan();
         Long shangwang = ordertable.getShangwang();
         Long qianshou = ordertable.getQianshou();
         Long ruzhang = ordertable.getRuzhang();
         String danhao = ordertable.getDanhao();
         Long jiufen = ordertable.getJiufen();
         Double huokuan = ordertable.getHuokuan();
         Double jine = ordertable.getMoney();
         Long tuihuo = ordertable.getTuihuo();
         String bianma = ordertable.getBianma(); 
         String gongyunshang = ordertable.getGongyunshang();
         Double zl = ordertable.getZhongliang();
         String time = ordertable.getJiufentime();
         ordertable = (OrderTable)orderDao.get(ordertable.getId());
         // 更新单号填写时间
         if (danhao == null || danhao.equals("")) {
        	 ordertable.setDanhaoFillTime(null);
         } else if (ordertable.getDanhao() == null || ordertable.getDanhao().equals("")) {
        	 ordertable.setDanhaoFillTime(new Date().getTime());
         }
         ordertable.setOrderId(order);
         ordertable.setYunshu(yunshu);
         ordertable.setYunfei(yunfei);
         ordertable.setGuojia(guojia);
         ordertable.setRemark(remark);
         ordertable.setHuilv(huilv);
         ordertable.setTuikuan(tuikuan);
         ordertable.setShangwang(shangwang);
         ordertable.setQianshou(qianshou);
         ordertable.setRuzhang(ruzhang);
         ordertable.setDanhao(danhao);
         ordertable.setJiufen(jiufen);
         ordertable.setHuokuan(huokuan);
         ordertable.setTuihuo(tuihuo);
         ordertable.setMoney(jine);
         ordertable.setBianma(bianma);
         ordertable.setZhongliang(zl);
         ordertable.setGongyunshang(gongyunshang);
         if(jiufen ==1 && (time == null || "".equals(time))){
        	 Date d = new Date();
             SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
             String ff = f.format(d);
             ordertable.setJiufentime(ff);
         }else if(jiufen ==1 && (time != null && !"".equals(time))){
        	    ordertable.setJiufentime(time);
         }
     
         ordertable.setXiugai(1l);
         orderDao.merge(ordertable);
         msg = "修改成功";
        return getDeDaoOrderAll();
    }
    //查看全部要付款订单
    public String getPayMent(){
    	int pageSize = 10;
    	pageBean = pageBiz.selgetPayment(pageSize, pageNumber);
    	return "getPayMent";
    }
    public String updedaos()
    {
     
        String ch[] = request.getParameter("bulletinId").split("-");
        String str[] = new String[ch.length];
        for(int i = 0; i < ch.length; i++)
        {
            List<OrderTable> ls = orderDao.getSelId(Long.valueOf(Long.parseLong(ch[i])));
            if(ls.size() != 0)
              
                    ls.get(0).setId(Long.parseLong(ch[i]));
               
                    ls.get(0).setDaifahuo(1l);
                    ls.get(0).setWancheng(0l);
                    ls.get(0).setFenpei(1l);
                    orderDao.merge(ls.get(0));
                    str[i] = i +".操作成功！";
        }

        ActionContext.getContext().put("str", str);
        return getPayMent();
    }
    //将完成订单返回给代发 
    public String fhdaifahuo()
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
                    ls.get(0).setFenpei(1l);
                    ls.get(0).setWancheng(0l);
                    ls.get(0).setDaifahuo(0l);
                    ls.get(0).setDaochu(0l);
                    ls.get(0).setSumaitong(0l);
                    ls.get(0).setGetordersId(1l);
                    orderDao.merge(ls.get(0));
                    str[i] = i + ".操作成功！";
                } 
            }

            ActionContext.getContext().put("wenti", str);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return getPayMent();
    }
    //代发订单
    public String daifa(){
    	try
        {
            String[] ch = request.getParameter("bulletinId").split("-");
            String[] str = new String[ch.length];
            for(int i = 0; i < ch.length; i++)
            {
                List<OrderTable> ls = orderDao.getSelId(Long.parseLong(ch[i]));
                if(ls.size() != 0)
                {
                	System.out.println("+代发订单+");
                    ls.get(0).setId(Long.parseLong(ch[i]));
                    ls.get(0).setDaifahuo(4l);
                    orderDao.merge(ls.get(0));
                    str[i] = i + ".操作成功！";
                } 
            }

            ActionContext.getContext().put("wenti", str);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    	return getPayMent();
    }
    //导入国际单号
	public String ImportOrders(){
		try {
			
			if(excelfile == null || excelfile.length() <= 0){
				msg = "请选择正确的excel文件";
			}
			HSSFWorkbook book = new HSSFWorkbook(new FileInputStream(excelfile));
			HSSFSheet sheet = book.getSheetAt(0);
	        HSSFRow row = null;  
	        HSSFCell cell = null;  
	        int total = 0;
	      
	        for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {  
	            row = sheet.getRow(i);  
	            if (row == null) {  
	                continue;  
	            }   
	                OrderTable u = new OrderTable();        
	                YunFeiTable y = new YunFeiTable();
	                KuaiDiFangShi kk = new KuaiDiFangShi();
                	
                 	row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                 	u.setDanhao(row.getCell(2).getStringCellValue());
                	row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                 	kk.setName(row.getCell(3).getStringCellValue());
                 	row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
                 	u.setOrderId(row.getCell(6).getStringCellValue());
                 	if(row.getCell(7) != null && !"".equals(row.getCell(7))){
	                 	u.setZhongliang(Double.parseDouble(row.getCell(7).toString()));
                 	}                 	
                 	row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
                 	y.setGuojia(row.getCell(8).getStringCellValue());
                 	List<OrderTable> order = orderTableDao.getOrder(u.getOrderId());
                 	List<YunFeiTable> yun = guoJiaDao.getAllGuoJia(y.getGuojia());  
                 	List<KuaiDiFangShi> kuai = kuaiDiFangShiDao.getCourier(kk.getName());
                 	List<YunFeiTableE> yunfei = yunFeieDao.getGuoJia(y.getGuojia());
                 	
                 	if (kuai.size() != 0 && order.size() != 0 && (u.getDanhao() != null && !"".equals(u.getDanhao())) && (u.getZhongliang() != null && !"".equals(u.getZhongliang()))){
                 	//航空小包运费
						if(kuai.get(0).getId() == 1l){
							if(yun.size() != 0){
								if((u.getDanhao()!=null && !"".equals(u.getDanhao())) && (u.getZhongliang() != null && !"".equals(u.getZhongliang())) && (u.getZhongliang() <=2 && u.getZhongliang()>0)){
									if (order.size() != 0) {			                 
					                	String ss =order.get(0).getDanhao();
					                	String dd =u.getDanhao();     
					                	
					                	if((ss!= null && !"".equals(ss))&&!ss.equals(dd)) {  
					                		Double yuns = (yun.get(0).getMoney()*u.getZhongliang()*0.85+8);
					                		order.get(0).setId(order.get(0).getId());	     	
						                	order.get(0).setDanhao(u.getDanhao()+","+order.get(0).getDanhao());
						                	if(row.getCell(7) != null && !"".equals(row.getCell(7)) ){
						                		order.get(0).setZhongliang(order.get(0).getZhongliang()+u.getZhongliang());
						                	}
						                	String num = "";
						                	DecimalFormat df = new DecimalFormat("0.000");
						        			num = df.format(yuns);
						                	order.get(0).setYunfei(Double.parseDouble(num)+order.get(0).getYunfei());
						                	orderTableDao.merge(order.get(0));
										}else{
											Double yuns = (yun.get(0).getMoney()*u.getZhongliang()*0.85+8);
											order.get(0).setId(order.get(0).getId());	     	
						                	order.get(0).setDanhao(u.getDanhao());
						                	if(row.getCell(7) != null && !"".equals(row.getCell(7))){
						                		order.get(0).setZhongliang(u.getZhongliang());
						                	}
						                	String num = "";
						                	DecimalFormat df = new DecimalFormat("0.000");
						        			num = df.format(yuns);
						                	order.get(0).setYunfei(Double.parseDouble(num));
						                	orderTableDao.merge(order.get(0));
										}	                
									}
								}
							}
						}
						//E邮宝运费
						if (kuai.get(0).getId()==4l){
							if (yunfei.size() != 0) {
								if (order.size() != 0) {			                 
				                	String ss =order.get(0).getDanhao();
				                	String dd =u.getDanhao();     
				                	String num = "";
				                	DecimalFormat df = new DecimalFormat("0.000");
				                
				                	if((ss != null && !"".equals(ss))&&!ss.equals(dd)){    
				                		Double yund = 0d;
				                		
				            	    	if("澳洲".equals(y.getGuojia()) && (0<u.getZhongliang() && u.getZhongliang()<=0.5)){
				            	    			
				            	    			 yund = order.get(0).getYunfei()+25d;
				            	    			 num = df.format(yund); 
				            	    			 order.get(0).setYunfei(Double.parseDouble(num));
				            	    	}
				            	    	if("澳洲".equals(y.getGuojia()) && (0.5<u.getZhongliang() && u.getZhongliang()<=2)){			            	    		
				               	    		 yund = order.get(0).getYunfei()+30d;
				               	    		 num = df.format(yund);
				               	    		 order.get(0).setYunfei(Double.parseDouble(num));
				               	    			
				            	    	}else if(!"澳洲".equals(y.getGuojia())){
				            	    		
				            	    			yund =order.get(0).getYunfei()+((0.08*u.getZhongliang()))*1000+7;		
				            	    			 num = df.format(yund);
				            	    			 order.get(0).setYunfei(Double.parseDouble(num));
				            	    	}
				                		order.get(0).setId(order.get(0).getId());	     	
					                	order.get(0).setDanhao(u.getDanhao()+","+order.get(0).getDanhao());
					                	
					                	if(row.getCell(7) != null && !"".equals(row.getCell(7))){
					                		order.get(0).setZhongliang(order.get(0).getZhongliang()+u.getZhongliang());
					                	}
					                	orderTableDao.merge(order.get(0));
									}else{
										Double yune = 0d;
										String nums = "";
					                	DecimalFormat dfs = new DecimalFormat("0.000");
						    	    	if("澳洲".equals(y.getGuojia()) && (0<u.getZhongliang() && u.getZhongliang()<=0.5)){
						    	    		 yune = 25d;	
						    	    		 nums = df.format(yune);
						    	    		 order.get(0).setYunfei(Double.parseDouble(nums));
						    	    	}
						    	    	if("澳洲".equals(y.getGuojia()) && (0.5<u.getZhongliang()&& u.getZhongliang()<=2)){
						    	    		 yune = 30d;
						    	    		 nums = df.format(yune);
						    	    		 order.get(0).setYunfei(Double.parseDouble(nums));
						    	    	}else if(!"澳洲".equals(y.getGuojia())){
						    	    		 yune = (0.08*u.getZhongliang())*1000+7;
						    	    		 nums = df.format(yune);
						    	    		 order.get(0).setYunfei(Double.parseDouble(nums));
						    	    	}
										order.get(0).setId(order.get(0).getId());	     	
					                	order.get(0).setDanhao(u.getDanhao());
					                	if(row.getCell(2) != null && !"".equals(row.getCell(2))){
					                		order.get(0).setZhongliang(u.getZhongliang());
					                	}
					                	orderTableDao.merge(order.get(0));
									}	                
								}
							}
						}
						//海运小包运费
					if (kuai.get(0).getId()==6l) {
						Double ye = 0d;
						if (u.getZhongliang() < 2) {
							Double countsize = u.getZhongliang();
		    	    		Double zhi=(Double) (countsize)*10;
		    	    		ye = (Double) Math.ceil(zhi);
		    	    		ye=(ye)/10;
						}
						List<Gjsl> ss = gjslDao.getZlYf(ye);
						if (ss.size() != 0) {
							String ssd =order.get(0).getDanhao();
		                	String dd =u.getDanhao();     
						  	if((ssd != null && !"".equals(ssd))&&!ss.equals(dd)){     	
		                		order.get(0).setId(order.get(0).getId());	     	
			                	order.get(0).setDanhao(u.getDanhao()+","+order.get(0).getDanhao());
			                	if(row.getCell(7) != null && !"".equals(row.getCell(7))){
			                		order.get(0).setZhongliang(order.get(0).getZhongliang()+u.getZhongliang());
			                	}
			                	order.get(0).setYunfei(order.get(0).getYunfei()+ss.get(0).getZifei()+8);
			                	orderTableDao.merge(order.get(0));
							}else{
								order.get(0).setId(order.get(0).getId());	     	
			                	order.get(0).setDanhao(u.getDanhao());
			                	if(row.getCell(7) != null && !"".equals(row.getCell(7))){
			                		order.get(0).setZhongliang(u.getZhongliang());
			                	}
			                	order.get(0).setYunfei(ss.get(0).getZifei()+8);
			                	orderTableDao.merge(order.get(0));
							}
						}
					}
					//海运大包运费 
					if (kuai.get(0).getId() == 7l) {
						List<Hydb> hy = hydbDao.getHydb(y.getGuojia());
						String ss =order.get(0).getDanhao();
	                	String dd =u.getDanhao(); 
	                	Double m = 0d;
						if (hy.size() != 0) {
							if (hy.get(0).getXz()!= null) {
							if (u.getZhongliang() <= hy.get(0).getXz()) {
								if(u.getZhongliang()>1d && (hy.get(0).getXz()>u.getZhongliang())){
				    	    		if(u.getZhongliang()>1){
				    	    			if(u.getZhongliang()<=2){
				        	    	    	Double countsize = u.getZhongliang()-1;
				            	    		Double zhi=(Double) (countsize/1);
				            	    		int ye = (int) Math.ceil(zhi);
				            	    			for (int j = 0; j < ye; j++) {	    				
				            	    				m=m+hy.get(0).getXuz();    	    				
				            					}
				            	    			m=m+hy.get(0).getMoney();
				        	    	    }else{	    	
				        	    		Double countsize = u.getZhongliang();
				        	    		Double zhi=(Double) (countsize);
				        	    		int ye = (int) Math.floor(zhi);
				        	    		if(countsize%1==0){
				        	    			ye=ye-1;
				        	    		}	
				        	    			for (int j = 0; j < ye; j++) { 				
				        	    				m=m+hy.get(0).getXuz();
				        					}
				        	    			m=m+hy.get(0).getMoney();
				        	    	    }
				        	        	}
				    	        	}else{
				    	        		m=hy.get(0).getMoney();
				    	        	}   
			                	if((ss != null && !"".equals(ss))&&!ss.equals(dd)){     	
			                		order.get(0).setId(order.get(0).getId());	     	
				                	order.get(0).setDanhao(u.getDanhao()+","+order.get(0).getDanhao());
				                	if(row.getCell(7) != null && !"".equals(row.getCell(7))){
				                		order.get(0).setZhongliang(order.get(0).getZhongliang()+u.getZhongliang());
				                	}
				                	order.get(0).setYunfei(order.get(0).getYunfei()+m+8);
				                	orderTableDao.merge(order.get(0));
								}else{
									order.get(0).setId(order.get(0).getId());	     	
				                	order.get(0).setDanhao(u.getDanhao());
				                	if(row.getCell(7) != null && !"".equals(row.getCell(7))){
				                		order.get(0).setZhongliang(u.getZhongliang());
				                	}
				                	order.get(0).setYunfei(m+8);
				                	orderTableDao.merge(order.get(0));
								}	
								}
							}else{
								if(u.getZhongliang()>1d && hy.get(0).getXuz() == null){
				    	    		if(u.getZhongliang()>1){
				    	    			if(u.getZhongliang()<=2){
				        	    	    	Double countsize = u.getZhongliang()-1;
				            	    		Double zhi=(Double) (countsize/1);
				            	    		int ye = (int) Math.ceil(zhi);
				            	    			for (int j = 0; j < ye; j++) {	    				
				            	    				m=m+hy.get(0).getXuz();    	    				
				            					}
				            	    			m=m+hy.get(0).getMoney();
				        	    	    }else{	    	
				        	    		Double countsize = u.getZhongliang();
				        	    		Double zhi=(Double) (countsize);
				        	    		int ye = (int) Math.floor(zhi);
				        	    		if(countsize%1==0){
				        	    			ye=ye-1;
				        	    		}	
				        	    			for (int j = 0; j < ye; j++) { 				
				        	    				m=m+hy.get(0).getXuz();
				        					}
				        	    			m=m+hy.get(0).getMoney();
				        	    	    }
				        	        	}
				    	        	}else{
				    	        		m=hy.get(0).getMoney();
				    	        	}   
			                	if((ss != null && !"".equals(ss))&&!ss.equals(dd)){     	
			                		order.get(0).setId(order.get(0).getId());	     	
				                	order.get(0).setDanhao(u.getDanhao()+","+order.get(0).getDanhao());
				                	if(row.getCell(7) != null && !"".equals(row.getCell(7))){
				                		order.get(0).setZhongliang(order.get(0).getZhongliang()+u.getZhongliang());
				                	}
				                	order.get(0).setYunfei(order.get(0).getYunfei()+m+8);
				                	orderTableDao.merge(order.get(0));
								}else{
									order.get(0).setId(order.get(0).getId());	     	
				                	order.get(0).setDanhao(u.getDanhao());
				                	if(row.getCell(7) != null && !"".equals(row.getCell(7))){
				                		order.get(0).setZhongliang(u.getZhongliang());
				                	}
				                	order.get(0).setYunfei(m+8);
				                	orderTableDao.merge(order.get(0));
								}
							}
						}
					}
					//航空大包运费 
					if (kuai.get(0).getId() == 11l) {
						List<Hkdb> hkdb = hkdbDao.getHkdb(y.getGuojia());
						String ss =order.get(0).getDanhao();
	                	String dd =u.getDanhao(); 
	                	Double m = 0d;
						if (hkdb.size() != 0) {
							if (hkdb.get(0).getXz()!= null) {
							if (u.getZhongliang() <= hkdb.get(0).getXz()) {
								if(u.getZhongliang()>1d && (hkdb.get(0).getXz()>u.getZhongliang())){
				    	    		if(u.getZhongliang()>1){
				    	    			if(u.getZhongliang()<=2){
				        	    	    	Double countsize = u.getZhongliang()-1;
				            	    		Double zhi=(Double) (countsize/1);
				            	    		int ye = (int) Math.ceil(zhi);
				            	    			for (int j = 0; j < ye; j++) {	    				
				            	    				m=m+hkdb.get(0).getXuz();    	    				
				            					}
				            	    			m=m+hkdb.get(0).getMoney();
				        	    	    }else{	    	
				        	    		Double countsize = u.getZhongliang();
				        	    		Double zhi=(Double) (countsize);
				        	    		int ye = (int) Math.floor(zhi);
				        	    		if(countsize%1==0){
				        	    			ye=ye-1;
				        	    		}	
				        	    			for (int j = 0; j < ye; j++) { 				
				        	    				m=m+hkdb.get(0).getXuz();
				        					}
				        	    			m=m+hkdb.get(0).getMoney();
				        	    	    }
				        	        	}
				    	        	}else{
				    	        		m=hkdb.get(0).getMoney();
				    	        	}   
			                	if((ss != null && !"".equals(ss))&&!ss.equals(dd)){     	
			                		order.get(0).setId(order.get(0).getId());	     	
				                	order.get(0).setDanhao(u.getDanhao()+","+order.get(0).getDanhao());
				                	if(row.getCell(7) != null && !"".equals(row.getCell(7))){
				                		order.get(0).setZhongliang(order.get(0).getZhongliang()+u.getZhongliang());
				                	}
				                	order.get(0).setYunfei(order.get(0).getYunfei()+m+8);
				                	orderTableDao.merge(order.get(0));
								}else{
									order.get(0).setId(order.get(0).getId());	     	
				                	order.get(0).setDanhao(u.getDanhao());
				                	if(row.getCell(7) != null && !"".equals(row.getCell(7))){
				                		order.get(0).setZhongliang(u.getZhongliang());
				                	}
				                	order.get(0).setYunfei(m+8);
				                	orderTableDao.merge(order.get(0));
								}	
								}
							}else{
								if(u.getZhongliang()>1d && hkdb.get(0).getXuz() == null){
				    	    		if(u.getZhongliang()>1){
				    	    			if(u.getZhongliang()<=2){
				        	    	    	Double countsize = u.getZhongliang()-1;
				            	    		Double zhi=(Double) (countsize/1);
				            	    		int ye = (int) Math.ceil(zhi);
				            	    			for (int j = 0; j < ye; j++) {	    				
				            	    				m=m+hkdb.get(0).getXuz();    	    				
				            					}
				            	    			m=m+hkdb.get(0).getMoney();
				        	    	    }else{	    	
				        	    		Double countsize = u.getZhongliang();
				        	    		Double zhi=(Double) (countsize);
				        	    		int ye = (int) Math.floor(zhi);
				        	    		if(countsize%1==0){
				        	    			ye=ye-1;
				        	    		}	
				        	    			for (int j = 0; j < ye; j++) { 				
				        	    				m=m+hkdb.get(0).getXuz();
				        					}
				        	    			m=m+hkdb.get(0).getMoney();
				        	    	    }
				        	        	}
				    	        	}else{
				    	        		m=hkdb.get(0).getMoney();
				    	        	}   
			                	if((ss != null && !"".equals(ss))&&!ss.equals(dd)){     	
			                		order.get(0).setId(order.get(0).getId());	     	
				                	order.get(0).setDanhao(u.getDanhao()+","+order.get(0).getDanhao());
				                	if(row.getCell(7) != null && !"".equals(row.getCell(7))){
				                		order.get(0).setZhongliang(order.get(0).getZhongliang()+u.getZhongliang());
				                	}
				                	order.get(0).setYunfei(order.get(0).getYunfei()+m+8);
				                	orderTableDao.merge(order.get(0));
								}else{
									order.get(0).setId(order.get(0).getId());	     	
				                	order.get(0).setDanhao(u.getDanhao());
				                	if(row.getCell(7) != null && !"".equals(row.getCell(7))){
				                		order.get(0).setZhongliang(u.getZhongliang());
				                	}
				                	order.get(0).setYunfei(m);
				                	orderTableDao.merge(order.get(0));
								}
							}
						}
					}
					//SAL大包运费
					if (kuai.get(0).getId() == 10l) {
						List<SALdb> SAL = sALdbDao.getSalDb(y.getGuojia());
						String ss =order.get(0).getDanhao();
	                	String dd =u.getDanhao(); 
	                	Double m = 0d;
						if (SAL.size() != 0) {
							if (SAL.get(0).getXz()!= null) {
							if (u.getZhongliang() <= SAL.get(0).getXz()) {
								if(u.getZhongliang()>1d && (SAL.get(0).getXz()>u.getZhongliang())){
				    	    		if(u.getZhongliang()>1){
				    	    			if(u.getZhongliang()<=2){
				        	    	    	Double countsize = u.getZhongliang()-1;
				            	    		Double zhi=(Double) (countsize/1);
				            	    		int ye = (int) Math.ceil(zhi);
				            	    			for (int j = 0; j < ye; j++) {	    				
				            	    				m=m+SAL.get(0).getXuz();    	    				
				            					}
				            	    			m=m+SAL.get(0).getMoney();
				        	    	    }else{	    	
				        	    		Double countsize = u.getZhongliang();
				        	    		Double zhi=(Double) (countsize);
				        	    		int ye = (int) Math.floor(zhi);
				        	    		if(countsize%1==0){
				        	    			ye=ye-1;
				        	    		}	
				        	    			for (int j = 0; j < ye; j++) { 				
				        	    				m=m+SAL.get(0).getXuz();
				        					}
				        	    			m=m+SAL.get(0).getMoney();
				        	    	    }
				        	        	}
				    	        	}else{
				    	        		m=SAL.get(0).getMoney();
				    	        	}   
			                	if((ss != null && !"".equals(ss))&&!ss.equals(dd)){     	
			                		order.get(0).setId(order.get(0).getId());	     	
				                	order.get(0).setDanhao(u.getDanhao()+","+order.get(0).getDanhao());
				                	if(row.getCell(7) != null && !"".equals(row.getCell(7))){
				                		order.get(0).setZhongliang(order.get(0).getZhongliang()+u.getZhongliang());
				                	}
				                	order.get(0).setYunfei(order.get(0).getYunfei()+m);
				                	orderTableDao.merge(order.get(0));
								}else{
									order.get(0).setId(order.get(0).getId());	     	
				                	order.get(0).setDanhao(u.getDanhao());
				                	if(row.getCell(7) != null && !"".equals(row.getCell(7))){
				                		order.get(0).setZhongliang(u.getZhongliang());
				                	}
				                	order.get(0).setYunfei(m+8);
				                	orderTableDao.merge(order.get(0));
								}	
								}
							}else{
								if(u.getZhongliang()>1d && SAL.get(0).getXuz() == null){
				    	    		if(u.getZhongliang()>1){
				    	    			if(u.getZhongliang()<=2){
				        	    	    	Double countsize = u.getZhongliang()-1;
				            	    		Double zhi=(Double) (countsize/1);
				            	    		int ye = (int) Math.ceil(zhi);
				            	    			for (int j = 0; j < ye; j++) {	    				
				            	    				m=m+SAL.get(0).getXuz();    	    				
				            					}
				            	    			m=m+SAL.get(0).getMoney();
				        	    	    }else{	    	
				        	    		Double countsize = u.getZhongliang();
				        	    		Double zhi=(Double) (countsize);
				        	    		int ye = (int) Math.floor(zhi);
				        	    		if(countsize%1==0){
				        	    			ye=ye-1;
				        	    		}	
				        	    			for (int j = 0; j < ye; j++) { 				
				        	    				m=m+SAL.get(0).getXuz();
				        					}
				        	    			m=m+SAL.get(0).getMoney();
				        	    	    }
				        	        	}
				    	        	}else{
				    	        		m=SAL.get(0).getMoney();
				    	        	}   
			                	if((ss != null && !"".equals(ss))&&!ss.equals(dd)){     	
			                		order.get(0).setId(order.get(0).getId());	     	
				                	order.get(0).setDanhao(u.getDanhao()+","+order.get(0).getDanhao());
				                	if(row.getCell(7) != null && !"".equals(row.getCell(7))){
				                		order.get(0).setZhongliang(order.get(0).getZhongliang()+u.getZhongliang());
				                	}
				                	order.get(0).setYunfei(order.get(0).getYunfei()+m+8);
				                	orderTableDao.merge(order.get(0));
								}else{
									order.get(0).setId(order.get(0).getId());	     	
				                	order.get(0).setDanhao(u.getDanhao());
				                	if(row.getCell(7) != null && !"".equals(row.getCell(7))){
				                		order.get(0).setZhongliang(u.getZhongliang());
				                	}
				                	order.get(0).setYunfei(m+8);
				                	orderTableDao.merge(order.get(0));
								}
							}
						}
					}
                 	if (kuai.get(0).getId() != 1l && kuai.get(0).getId() != 4l && kuai.get(0).getId() != 7l && kuai.get(0).getId() != 6l && kuai.get(0).getId() != 10l && kuai.get(0).getId() != 11l) {
	                 	if ((u.getOrderId()==null || "".equals(u.getOrderId())) || (u.getZhongliang() == null || "".equals(u.getZhongliang())) || (y.getGuojia() == null || "".equals(y.getGuojia())) || (u.getDanhao() == null || "".equals(u.getDanhao()))) {
							msg = "操作失败、模板里面所有字段都不能为空";
						}else{
	                 	if (order.size() != 0){			                 
		                	String ss =order.get(0).getDanhao();
		                	String dd =u.getDanhao();         
		                
		                	if((ss != null && !"".equals(ss))&&!ss.equals(dd)){     	
		                		order.get(0).setId(order.get(0).getId());	     	
			                	order.get(0).setDanhao(u.getDanhao()+","+order.get(0).getDanhao());
			                	if(row.getCell(7) != null && !"".equals(row.getCell(7))){
			                		order.get(0).setZhongliang(order.get(0).getZhongliang()+u.getZhongliang());
			                	}
			                	orderTableDao.merge(order.get(0));
							}else{
								order.get(0).setId(order.get(0).getId());	     	
			                	order.get(0).setDanhao(u.getDanhao());
			                	if(row.getCell(7) != null && !"".equals(row.getCell(7))){
			                		order.get(0).setZhongliang(u.getZhongliang());
			                	}
			                	orderTableDao.merge(order.get(0));
							}	                
						}
					}
		        } 
	        }
	      }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} 
		return getDaiRuDan();
	}
    //查询国家
    public YunFeiTable getCountry(){
    
    	YunFeiTable tt = null;
    	try {
    		 OrderTable stu = (OrderTable)orderDao.get(ordertable.getId());
    	  	 tt =  yunFeiTableDao.getCorresponding(stu.getCountry());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
  	  return tt;
    }
    //查看采购全部订单
    public String getFilter(){
	   	 int pageSize = 10;
	   	 pageBean = pageBiz.selFilter(pageSize, pageNumber, caigouyuan, orderId, time, time1);
	   	 return "getcaigous";
    }
    //测试
    public String ceshi(){
    	return "ceshi";
    }
    public void setServletRequest(HttpServletRequest arg0)
    {
        request = arg0;
    }


}