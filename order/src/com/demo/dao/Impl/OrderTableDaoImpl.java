package com.demo.dao.Impl;

import com.demo.bean.MyHibernateTemplate;
import com.demo.dao.OrderTableDao;
import com.demo.entity.Express.YunFeiTable;
import com.demo.entity.order.DhgateAccounts;
import com.demo.entity.order.OrderTable;
import com.demo.entity.order.Order_Detail;

import java.io.PrintStream;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class OrderTableDaoImpl extends BaseDaoImpl<OrderTable , Long> implements OrderTableDao
{

    public OrderTableDaoImpl()
    {
        super(OrderTable.class);
    }
    //采购查看利润小于0
    public List<OrderTable> getCaiGouLiRun(Long userid, String orderId, String time, String time1,String zhanghaoId)
    {
         List<OrderTable> stu = null;
         if((time != null && !"".equals(time)) && (time1 != null && !"".equals(time1)) && (orderId == null || "".equals(orderId)) && (zhanghaoId == null || "".equals(zhanghaoId)))
         {
        	
             stu = stu = ht.find("from OrderTable a where (a.denghuixin = 0 or a.denghuixin is null) and sjc is null and (a.sumaitong = 0 or a.sumaitong is null) and a.fenpei = 1 and wancheng = 1 and (daifahuo = 0 or daifahuo is null) and a.caigouyuan = "+userid+" and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"')");
         }
         if((!"".equals(orderId) && orderId != null) && (time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (zhanghaoId == null || "".equals(zhanghaoId))){
        	
        	 stu = ht.find("from OrderTable a where (a.denghuixin = 0 or denghuixin is null)  and sjc is null and (a.sumaitong = 0 or sumaitong is null) and a.fenpei = 1 and wancheng = 1   and caigouyuan = "+userid+" and orderId = '"+orderId+"'");
         }
         if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (zhanghaoId != null && !"".equals(zhanghaoId))){
        	
        	 stu = ht.find("from OrderTable a where (a.denghuixin = 0 or denghuixin is null) and sjc is null and (a.sumaitong = 0 or sumaitong is null) and a.fenpei = 1 and wancheng = 1 and (daifahuo = 0 or daifahuo is null) and caigouyuan = "+userid+" and a.zhanghaoId in(select b.id from ZhangHao b where b.id = "+zhanghaoId+")");
         }
         if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (zhanghaoId == null || "".equals(zhanghaoId))){
        	
        	 stu = ht.find("from OrderTable a where a.fenpei = 1  and sjc is null  and wancheng = 1 and caigouyuan = "+userid+"");
         }
         return stu;
         
    }
    //采购大于0小于30
    public List<OrderTable> getCaiGouLiRunInterval(Long userid, String orderId, String time, String time1)
    {
    	 List<OrderTable> stu = null;
        if((time != null || !"".equals(time)) && time1 != null && !"".equals(time1) && (orderId == null || "".equals(orderId)))
            stu = ht.find("from OrderTable where fenpei = 1 and sjc is null and wancheng = 1 and caigouyuan = "+userid+" and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"')");
        if(!"".equals(orderId) && orderId != null)
            stu = ht.find("from OrderTable where fenpei = 1 and sjc is null and wancheng = 1 and caigouyuan = "+userid+" and orderId = ?",new Object[]{orderId});
        else
        if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)))
            stu = ht.find("from OrderTable where fenpei = 1 and sjc is null and wancheng = 1 and caigouyuan = "+userid+"");
        return stu;
    }
    //采购管理员得到订单
    public String getCaiGouAdminDeDaoOrder(Long userid, String orderId, String time, String time1,String zhanghaoId)
    {
    
    	String stu = null;
         if((time != null || !"".equals(time)) && time1 != null && !"".equals(time1) && (orderId == null || "".equals(orderId)) && (zhanghaoId == null || "".equals(zhanghaoId)))
         {
             stu = "from OrderTable a where (a.denghuixin = 0 or denghuixin is null) and sjc is null and (a.sumaitong = 0 or sumaitong is null) and a.fenpei = 1 and (wancheng = 0 or wancheng is null) and (daifahuo = 0 or daifahuo is null) and a.caigouyuan = "+userid+" and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"') order by jingji desc,id desc";
         }
         if(!"".equals(orderId) && orderId != null){
             stu = "from OrderTable a where (a.denghuixin = 0 or denghuixin is null) and sjc is null and (a.sumaitong = 0 or sumaitong is null) and a.fenpei = 1 and (wancheng = 0 or wancheng is null) and (daifahuo = 0 or daifahuo is null) and caigouyuan = "+userid+" and orderId = '"+orderId+"' order by jingji desc,id desc";
         }
         if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (zhanghaoId != null && !"".equals(zhanghaoId))){
        	 stu = "from OrderTable a where (a.denghuixin = 0 or denghuixin is null) and sjc is null and (a.sumaitong = 0 or sumaitong is null) and a.fenpei = 1 and (wancheng = 0 or wancheng is null) and (daifahuo = 0 or daifahuo is null) and caigouyuan = "+userid+" and a.zhanghaoId in(select b.id from ZhangHao b where b.id = '"+zhanghaoId+"') order by jingji desc,id desc";
         }
         if((time != null && !"".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (zhanghaoId == null || "".equals(zhanghaoId)))
         {
        	
             stu = "from OrderTable a where (a.denghuixin = 0 or denghuixin is null) and sjc is null and (a.sumaitong = 0 or sumaitong is null) and a.fenpei = 1 and (wancheng = 0 or wancheng is null) and (daifahuo = 0 or daifahuo is null) and a.caigouyuan = "+userid+" and datediff(day,time,'"+time+"')=0 order by jingji desc,id desc";
         }
         else
         if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (zhanghaoId == null || "".equals(zhanghaoId)))
             stu = "from OrderTable a where (a.denghuixin = 0 or denghuixin is null) and sjc is null and (a.sumaitong = 0 or sumaitong is null) and a.fenpei = 1 and (wancheng = 0 or wancheng is null) and (daifahuo = 0 or daifahuo is null) and caigouyuan = "+userid+" order by jingji desc,id desc";
         return stu;
    }

    public List<OrderTable> getCaiGouAdminDeDaoOrder1(Long userid, String orderId, String time)
    {
    	List<OrderTable> stu = null;
        if((orderId == null || "".equals(orderId)) && !"".equals(time) && time != null)
            stu = ht.find((new StringBuilder("from OrderTable a where (a.denghuixin = 0 or denghuixin is null) and sjc is null and (a.sumaitong = 0 or sumaitong is null) and a.fenpei = 1 and (wancheng = 0 or wancheng is null) and (daifahuo = 0 or daifahuo is null) and a.caigouyuan = ")).append(userid).append(" and time = '").append(time).append("' order by zhanghaoId").toString());
        if(!"".equals(orderId) && orderId != null)
            stu = ht.find((new StringBuilder("from OrderTable a where (a.denghuixin = 0 or denghuixin is null) and sjc is null and (a.sumaitong = 0 or sumaitong is null) and a.fenpei = 1 and (wancheng = 0 or wancheng is null) and (daifahuo = 0 or daifahuo is null) and caigouyuan = ")).append(userid).append(" and orderId = '").append(orderId).append("' order by zhanghaoId").toString());
        else
        if((time == null || "".equals(time)) && (orderId == null || "".equals(orderId)))
            stu = ht.find((new StringBuilder("from OrderTable a where (a.denghuixin = 0 or denghuixin is null)  and sjc is null and (a.sumaitong = 0 or sumaitong is null) and a.fenpei = 1 and (wancheng = 0 or wancheng is null) and (daifahuo = 0 or daifahuo is null) and caigouyuan = ")).append(userid).append(" order by zhanghaoId").toString());
        return stu;
    }
    //采购查看待发货
    public String getDaiFaHuo(Long userid, String orderId, String time, String time1,String caigoutime,String caigoutime1,String wuping,String gongyunshang)
    {
        String stu = null;
        if((time != null || !"".equals(time)) && time1 != null && !"".equals(time1) && (orderId == null || "".equals(orderId)) && (caigoutime == null || "".equals(caigoutime)) && (caigoutime1 == null || "".equals(caigoutime1)) && (wuping == null || "".equals(wuping)) && (gongyunshang == null || "".equals(gongyunshang)))
            stu = "from OrderTable where  fenpei =1 and (wancheng = 0 or wancheng is null)  and caigouyuan = "+userid+" and id not in(select id from OrderTable a where getordersId = 1 and caigouyuan = "+userid+") and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"') order by zhanghaoId,time";
        if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (caigoutime != null && !"".equals(caigoutime)) && (caigoutime1 != null && !"".equals(caigoutime1)) && (wuping == null || "".equals(wuping)) && (gongyunshang == null || "".equals(gongyunshang))){
        	stu = "from OrderTable where  fenpei =1 and (wancheng = 0 or wancheng is null)  and caigouyuan = "+userid+" and id not in(select id from OrderTable a where getordersId = 1 and caigouyuan = "+userid+") and (convert(varchar(10),caigoutime,120) between '"+caigoutime+"'and '"+caigoutime1+"') order by zhanghaoId,time";
        }
        if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId != null && !"".equals(orderId))&&(caigoutime == null || "".equals(caigoutime)) && (caigoutime1 == null || "".equals(caigoutime1)) && (wuping == null || "".equals(wuping)) && (gongyunshang == null || "".equals(gongyunshang)))
            stu = "from OrderTable where  fenpei =1 and  orderId = '"+orderId+"'  and (wancheng = 0 or wancheng is null) and caigouyuan = "+userid+" and id not in(select id from OrderTable a where getordersId = 1 and caigouyuan = "+userid+") order by zhanghaoId,time";
        if((time != null && !"".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (caigoutime == null || "".equals(caigoutime)) && (caigoutime1 == null || "".equals(caigoutime1)) && (wuping == null || "".equals(wuping)) && (gongyunshang == null || "".equals(gongyunshang))){
        	stu = "from OrderTable where  fenpei =1 and (wancheng = 0 or wancheng is null)  and caigouyuan = "+userid+" and id not in(select id from OrderTable a where getordersId = 1 and caigouyuan = "+userid+") and datediff(day,time,'"+time+"')=0  order by zhanghaoId,time";
        }
        if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (caigoutime == null || "".equals(caigoutime)) && (caigoutime1 == null || "".equals(caigoutime1)) && (wuping != null && !"".equals(wuping)) && (gongyunshang == null || "".equals(gongyunshang))){
        	stu = "from OrderTable where  fenpei =1 and (wancheng = 0 or wancheng is null)  and caigouyuan = "+userid+" and id not in(select id from OrderTable a where getordersId = 1 and caigouyuan = "+userid+") and wuping like '%"+wuping+"%' order by zhanghaoId,time";
        }
        else
            if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId))&&(caigoutime == null || "".equals(caigoutime)) && (caigoutime1 == null || "".equals(caigoutime1)) && (wuping == null || "".equals(wuping)) && (gongyunshang != null && !"".equals(gongyunshang)))
                stu = "from OrderTable where gongyunshang like '%"+gongyunshang+"%' and fenpei =1 and (wancheng = 0 or wancheng is null) and caigouyuan = "+userid+" and id not in(select id from OrderTable a where getordersId = 1 and caigouyuan = "+userid+") order by zhanghaoId,time";
        else
        if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId))&&(caigoutime == null || "".equals(caigoutime)) && (caigoutime1 == null || "".equals(caigoutime1)) && (wuping == null || "".equals(wuping)) && (gongyunshang == null || "".equals(gongyunshang)))
            stu = "from OrderTable where fenpei =1 and (wancheng = 0 or wancheng is null) and caigouyuan = "+userid+" and id not in(select id from OrderTable a where getordersId = 1 and caigouyuan = "+userid+") order by zhanghaoId,time";
        return stu;
    }
    //单号不为空
    public List<OrderTable> getChaXunDaiFaHuo(String guoneidanhao, String orderId,String wuping)
    {
    	List<OrderTable> stu = null;
         if((guoneidanhao != null || !"".equals(guoneidanhao)) && (orderId == null || "".equals(orderId)) && ("".equals(wuping) || wuping == null))
             stu = ht.find("from OrderTable where (guoneidanhao is not null and guoneidanhao != '') and daifahuo = 1  and guoneidanhao like '%"+guoneidanhao+"%' order by fenpei,caigoutime");
         if(("".equals(wuping) || wuping == null) && (guoneidanhao == null || "".equals(guoneidanhao)) && (orderId != null && !"".equals(orderId)))
             stu = ht.find("from OrderTable where (guoneidanhao is not null and guoneidanhao != '') and daifahuo = 1  and orderId = '"+orderId+"' order by fenpei,caigoutime");
         if(("".equals(orderId) || orderId == null) && (guoneidanhao == null || "".equals(guoneidanhao)) && (wuping != null && !"".equals(wuping))){
         	stu = ht.find("from OrderTable where (guoneidanhao is not null and guoneidanhao != '') and daifahuo = 1  and wuping like '%"+wuping+"%' order by fenpei,caigoutime");
         }
         else if((guoneidanhao == null || "".equals(guoneidanhao)) && (orderId == null || "".equals(orderId)))
             stu = ht.find("from OrderTable where (guoneidanhao is not null and guoneidanhao != '') and daifahuo = 1 order by fenpei,caigoutime");
         return stu;
    }
    //仓库查看待放区
    public List<OrderTable> getDaiFangQu(String guoneidanhao, String orderId)
    {
    	List<OrderTable> stu = null;
        if((orderId == null || "".equals(orderId)) && !"".equals(guoneidanhao) && guoneidanhao != null){
            stu = ht.find("from OrderTable  where (daochu = 0 or daochu = 1 or daochu is null) and (wancheng = 0 or wancheng is null)  and sjc is null and daifahuo = 2 and guoneidanhao =? and (chaozhongqu is null or chaozhongqu = 0) order by guoneikuaidiId desc,guoneidanhao", new Object[] {
                guoneidanhao
            });
        }
        if(orderId != null && !"".equals(orderId)){
            stu = ht.find("from OrderTable  where (daochu = 0 or daochu = 1 or daochu is null) and (wancheng = 0 or wancheng is null) and sjc is null and daifahuo = 2  and orderId =? and (chaozhongqu is null or chaozhongqu = 0) order by guoneikuaidiId desc,guoneidanhao", new Object[] {
                orderId
            });
        }
        else
        if((guoneidanhao == null || "".equals(guoneidanhao)) && (orderId == null || "".equals(orderId))){
            stu = ht.find("from OrderTable  where (daochu = 0 or daochu = 1 or daochu is null) and (wancheng = 0 or wancheng is null) and sjc is null and daifahuo = 2 and (chaozhongqu is null or chaozhongqu = 0) order by guoneikuaidiId desc,guoneidanhao");
        }
        return stu;
    }

    public List<OrderTable> getTopTen()
    {
        return ht.find("select top 10 * from OrderTable a where a.daifahuo = 2  and sjc is null");
    }
    //仓库查看单号为空 
    public List<OrderTable> getWeiKong(String guoneidanhao, String orderId,String wuping)
    {
    	List<OrderTable> stu = null;
        if((guoneidanhao != null || !"".equals(guoneidanhao)) && (orderId == null || "".equals(orderId)) && ("".equals(wuping) || wuping == null))
            stu = ht.find("from OrderTable where fenpei = 1 and (guoneidanhao is null or guoneidanhao = '') and daifahuo = 1 and (wancheng = 0 or wancheng is null) and sjc is null and guoneidanhao like '%"+guoneidanhao+"%' order by caigoutime,gongyunshang");
        if(("".equals(wuping) || wuping == null) && (guoneidanhao == null || "".equals(guoneidanhao)) && (orderId != null && !"".equals(orderId)))
            stu =  ht.find("from OrderTable where fenpei = 1 and (guoneidanhao is null or guoneidanhao = '') and daifahuo = 1 and (wancheng = 0 or wancheng is null) and sjc is null and orderId = '"+orderId+"' order by caigoutime,gongyunshang");
        if(("".equals(orderId) || orderId == null) && (guoneidanhao == null || "".equals(guoneidanhao)) && (wuping != null && !"".equals(wuping))){
        	stu =  ht.find("from OrderTable where fenpei = 1 and (guoneidanhao is null or guoneidanhao = '') and daifahuo = 1 and (wancheng = 0 or wancheng is null) and sjc is null and wuping like '%"+wuping+"%' order by caigoutime,gongyunshang");
        }
        else if((guoneidanhao == null || "".equals(guoneidanhao)) && (orderId == null || "".equals(orderId)))
            stu =  ht.find("from OrderTable where fenpei = 1 and (guoneidanhao is null or guoneidanhao = '') and daifahuo = 1 and (wancheng = 0 or wancheng is null) and sjc is null order by caigoutime,gongyunshang");
        return stu;
    }

    public List<OrderTable> getGuoNeiDanHao(String guoneidanhao)
    {
        return ht.find("from OrderTable where guoneidanhao = ? and sjc is null", new Object[] {
            guoneidanhao
        });
    }
    //管理员查看速卖通订单
    public String getAdminSuMaiTong(String orderId, String time, String time1)
    {
    	String stu = null;
        if((time != null || !"".equals(time)) && time1 != null && !"".equals(time1) && (orderId == null || "".equals(orderId)))
            stu = "from OrderTable a where fenpei = 1  and sjc is null and (wancheng = 0 or wancheng is null) and sumaitong = 1 and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"') order by zhanghaoId";
        if(!"".equals(orderId) && orderId != null)
            stu = "from OrderTable a where fenpei = 1  and sjc is null and (wancheng = 0 or wancheng is null) and sumaitong = 1 and orderId = '"+orderId+"' order by zhanghaoId";
              
        else
        if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)))
            stu = "from OrderTable a where fenpei = 1 and sjc is null and (wancheng = 0 or wancheng is null) and sumaitong = 1 order by zhanghaoId";
        return stu;
    }
    //业务查看已经入单订单
    public String getYeWuOrder(String orderId, String time, String time1)
    {
        String stu = null;
        if((time != null || !"".equals(time)) && time1 != null && !"".equals(time1) && (orderId == null || "".equals(orderId))){
            stu = "from OrderTable a where (gongyunshang is not null and gongyunshang != '') and fenpei = 1  and (wancheng = 0 or wancheng is null) and sumaitong = 2 and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"') order by zhanghaoId";
        }
        if(!"".equals(orderId) && orderId != null){
            stu = "from OrderTable a where (gongyunshang is not null and gongyunshang != '') and fenpei = 1  and (wancheng = 0 or wancheng is null) and sumaitong = 2 and orderId = '"+orderId+"' order by zhanghaoId";
        }
        else if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId))){
            stu = "from OrderTable a where (gongyunshang is not null and gongyunshang != '') and fenpei = 1  and (wancheng = 0 or wancheng is null) and sumaitong = 2 order by zhanghaoId";
        }
        return stu;
    }
    //业务查看未入单订单
    public String getWeiRuOrder(String orderId, String time, String time1)
    {
        String stu = null;
        if((time != null || !"".equals(time)) && time1 != null && !"".equals(time1) && (orderId == null || "".equals(orderId)))
            stu = "from OrderTable a where  (gongyunshang is null or gongyunshang = '') and fenpei = 1   and (wancheng = 0 or wancheng is null) and sumaitong = 2 and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"') order by zhanghaoId";
        if(!"".equals(orderId) && orderId != null)
            stu = "from OrderTable a where  (gongyunshang is null or gongyunshang = '') and fenpei = 1  and (wancheng = 0 or wancheng is null) and sumaitong = 2 and orderId = '"+orderId+"' order by zhanghaoId";
        else
        if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)))
            stu = "from OrderTable a where  (gongyunshang is null or gongyunshang = '') and fenpei = 1   and (wancheng = 0 or wancheng is null) and sumaitong = 2 order by zhanghaoId";
        return stu;
    }
    //业务查看代发
    public String getChaKanDaiFa(String orderId, String time, String time1)
    {
        String stu = null;
        if((time != null || !"".equals(time)) && time1 != null && !"".equals(time1) && (orderId == null || "".equals(orderId)))
            stu = "from OrderTable a where  denghuixin = 1 and fenpei = 1 and (wancheng = 0 or wancheng is null) and sjc is null and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"') order by zhanghaoId";
        if(!"".equals(orderId) && orderId != null)
            stu = "from OrderTable a where  denghuixin = 1 and fenpei = 1 and (wancheng = 0 or wancheng is null) and sjc is null and orderId = '"+orderId+"' order by zhanghaoId";
        else
        if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)))
            stu = "from OrderTable a where denghuixin = 1 and fenpei = 1 and (wancheng = 0 or wancheng is null)  and sjc is null order by zhanghaoId";
        return stu;
    }

    public OrderTable getSelYunShu(String yunshudanhao)
    {
        return (OrderTable)ht.findAll("from OrderTable a where a.guoneidanhao = ?  and sjc is null", new Object[] {
            yunshudanhao
        });
    }
    //查询顾客待发货
    public String getDaiFaHuo(String orderId,Long userId)
    {
        String stu = null;
        if(orderId != null && !"".equals(orderId))
            stu = "from OrderTable  where (wancheng = 0 or wancheng is null) and fenpei = 3  and sjc is null  and (daochu = 0 or daochu is null) and dengluId = "+userId+" and orderId = '"+orderId+"' order by id desc";
        else
            stu = "from OrderTable where (wancheng = 0 or wancheng is null) and fenpei = 3  and sjc is null  and (daochu = 0 or daochu is null) and dengluId = "+userId+" order by id desc";
        return stu;
    }
    //顾客已经发货
    public String getYiJingFaHuo(String orderId,Long userId)
    {
        String stu = null;
        if(orderId != null && !"".equals(orderId)){
            stu = "from OrderTable where wancheng = 1  and sjc is null and orderId = '"+orderId+"'  and fenpei = 3 and dengluId = "+userId+" order by id desc";
        }
        else{
            stu = "from OrderTable where wancheng = 1  and sjc is null  and fenpei = 3 and dengluId = "+userId+" order by id desc";
        }
        return stu;
    }
    //业务得到顾客全部完成订单
    public String getWanChengOrder(String danhao){
    	 String stu = null;
    	 if(danhao != null && !"".equals(danhao)){
             stu = "from OrderTable where wancheng = 1 and danhao = '"+danhao+"' and (tuihuo = 0 or tuihuo is null) order by id desc";
    	 }else{
    		 stu = "from OrderTable where wancheng = 1  and (tuihuo = 0 or tuihuo is null) order by id desc";
    	 }

         return stu;
    }
    //管理员查看全部完成订单
    public List<OrderTable> getSelWanCheng(String orderId, String guoneidanhao)
    {
    	List<OrderTable> stu = null;
        if((orderId == null || "".equals(orderId)) && !"".equals(guoneidanhao) && guoneidanhao != null)
        	   stu = ht.find("from OrderTable where fenpei != 3  and sjc is null and wancheng = 1 and guoneidanhao = ?", new Object[] {
                       guoneidanhao
                   });
       if(!"".equals(orderId) && orderId != null)
           stu = ht.find("from OrderTable where fenpei != 3  and sjc is null and wancheng = 1 and orderId = ?", new Object[] {
               orderId
       });
        else
        if((guoneidanhao == null || "".equals(guoneidanhao)) && (orderId == null || "".equals(orderId)))
            stu = ht.find("from OrderTable where fenpei != 3  and sjc is null and wancheng = 1");
        return stu;
    }
    //仓库导出全部
    public List<OrderTable> getDaoChu(Long dc)
    {
    	List<OrderTable> stu = null;
    	if(dc==2){
    		stu =  ht.find("from OrderTable where (guoneidanhao is null or guoneidanhao = '') and daifahuo = 1 order by gongyunshang");
    	}
    	if(dc==1){
    		stu =  ht.find("from OrderTable where daifahuo = 2 and (wancheng = 0 or wancheng is null) and (daochu = 0 or daochu is null)  and sjc is null order by guoneikuaidiId,guoneidanhao,guowaidizhi desc");
    	}if(dc==0){
    		stu =  ht.find("from OrderTable where tuihuo = 2");
    	}
    	return stu;
    }

    public List<OrderTable> getChaXunDaiFaHuo1(String guoneidanhao, String orderId)
    {
    	List<OrderTable> stu = null;
        if((orderId == null || "".equals(orderId)) && !"".equals(guoneidanhao) && guoneidanhao != null)
            stu = ht.find("from OrderTable a where a.guoneidanhao is not null and a.guoneidanhao != '' and a.daifahuo = 1  and sjc is null  and a.guoneidanhao like '%"+guoneidanhao+"%' order by caigoutime");
        if(!"".equals(orderId) && orderId != null)
            stu = ht.find("from OrderTable where guoneidanhao is not null and guoneidanhao != '' and daifahuo = 1  and sjc is null and orderId = '"+orderId+"' order by caigoutime");
        else
        if((guoneidanhao == null || "".equals(guoneidanhao)) && (orderId == null || "".equals(orderId)))
            stu = ht.find("from OrderTable where guoneidanhao is not null and guoneidanhao != '' and daifahuo = 1  and sjc is null order by caigoutime");
        return stu;
    }
    public String getSelTwoTble(){
    	 
    	return "select a.guoneidanhao,b.name from OrderTable a,GuoNeiKuaiDi b where a.guoneikuaidiId = b.id and daifahuo = 2  and sjc is null  and (daochu = 0 or daochu is null)";
    }
    //业务查询业务待录单
    public List<OrderTable> getDaiRuDan(String guoneidanhao,String orderId){
    	List<OrderTable> stu = null;
        if((orderId == null || "".equals(orderId)) && !"".equals(guoneidanhao) && guoneidanhao != null)
            stu = ht.find("from OrderTable  where (wancheng = 0 or wancheng is null) and daifahuo = 2 and daochu = 2 and guoneidanhao ='"+guoneidanhao+"'  and sjc is null and (chaozhongqu is null or chaozhongqu = 0) order by fenpei,guoneikuaidiId desc");
               
        if(orderId != null && !"".equals(orderId))
            stu = ht.find("from OrderTable  where (wancheng = 0 or wancheng is null) and daifahuo = 2 and daochu = 2 and orderId ='"+orderId+"'  and sjc is null and (chaozhongqu is null or chaozhongqu = 0) order by fenpei,guoneikuaidiId desc");
               
        else
        if((guoneidanhao == null || "".equals(guoneidanhao)) && (orderId == null || "".equals(orderId)))
            stu = ht.find("from OrderTable  where (wancheng = 0 or wancheng is null) and daifahuo = 2 and daochu = 2 and (chaozhongqu is null or chaozhongqu = 0)  and sjc is null order by fenpei,guoneikuaidiId desc");
        return stu;
    }
    //采购查看纠纷详情
	@Override
	public List<OrderTable> getJiuFen(Long userId, String orderId, String time,	String time1,Long leimus) {
			List<OrderTable> stu = null;
	        if((time != null || !"".equals(time)) && time1 != null && !"".equals(time1) && (orderId == null || "".equals(orderId))&&(leimus==null || "".equals(leimus))){
	            stu = ht.find("from OrderTable where wancheng = 1 and jiufen = 1 and caigouyuan="+userId+"  and (convert(varchar(10),jiufentime,120) between '"+time+"'and '"+time1+"')");
	        }
	        if(!"".equals(orderId) && orderId != null){
	            stu = ht.find("from OrderTable where wancheng = 1 and jiufen = 1 and caigouyuan="+userId+"  and orderId = "+orderId+"");
	        }
	        else
		        if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId))&&(leimus != null && !"".equals(leimus))){
		        
		            stu = ht.find("from OrderTable where leimuid="+leimus+" and wancheng = 1 and jiufen = 1 and caigouyuan="+userId+"");
		        }
	        else
	        if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId))&&(leimus == null || "".equals(leimus))){
	            stu = ht.find("from OrderTable where  wancheng = 1 and jiufen = 1   and caigouyuan="+userId+"");
	        }
	        return stu;
	}
	//导出纠纷订单
    public List<OrderTable> getJfOrder(Long userId){
    	return ht.find("from OrderTable where cgjf is null and wancheng = 1 and jiufen = 1  and sjc is null  and caigouyuan="+userId+"");
    }
	  //查看编码是否一样
    public List<OrderTable> getBianMa(String bianma){    	
    	return ht.find("from OrderTable where fenpei = 1 and wancheng = 1   and sjc is null  and bianma = ?",new Object[]{bianma});
    }
    //查看纠纷总金额
    public List<OrderTable> getJiuFenMoney(Long userId,String orderId,String time,String time1){
    	List<OrderTable> stu = null;
        try
        {
            if((time != null || !"".equals(time)) && time1 != null && !"".equals(time1) && (orderId == null || "".equals(orderId)))
                stu = ht.find("select round(sum(money),3)  from OrderTable where caigouyuan = ?  and sjc is null and fenpei = 1 and wancheng = 1 and jiufen = 1 and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"')", new Object[] {
                		userId
                });
            if(!"".equals(orderId) && orderId != null)
                stu = ht.find("select round(sum(money),3) from OrderTable where caigouyuan = ?   and sjc is null and fenpei = 1 and wancheng = 1 and jiufen = 1 and orderId = ?", new Object[] {
                		userId, orderId
                });
            else
            if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)))
                stu = ht.find("select round(sum(money),3) from OrderTable where caigouyuan = ?  and sjc is null  and fenpei = 1 and wancheng = 1 and jiufen = 1", new Object[] {
                		userId
                });
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return stu;
    }
    //客户查看出货订单
    public String  getChuHuoOrder(Long denglu,String orderId,String guoneidanhao){
    	String stu = null;
    	 if((orderId == null || "".equals(orderId)) && !"".equals(guoneidanhao) && guoneidanhao != null){
    		 stu = "from OrderTable  where orderId = '"+guoneidanhao+"' and fenpei = 3  and sjc is null  and dengluId = "+denglu+" and (wancheng = 0 or wancheng is null) and daifahuo = 2 order by time desc";
    	 }
    	 if(!"".equals(orderId) && orderId != null){
    		 stu = "from OrderTable  where orderId = '"+orderId+"' and fenpei = 3  and sjc is null  and dengluId = "+denglu+" and (wancheng = 0 or wancheng is null) and daifahuo = 2  order by time desc";
    	 }
    	 else
    	 if((guoneidanhao == null || "".equals(guoneidanhao)) && (orderId == null || "".equals(orderId))){
    		 stu = "from OrderTable  where fenpei = 3  and sjc is null  and dengluId = "+denglu+" and (wancheng = 0 or wancheng is null) and daifahuo = 2 order by time desc";
    	 }
    	 return stu;
    }
  //管理员查询全部订单
    public List<OrderTable> getChaKanOrder(String orderId,String time,String time1,String dhgatezhanghao,String danhao,String sumaitong,String bianma,String leimu){
    	List<OrderTable> stu = null;
          try
          {
              if((time != null && !"".equals(time)) && (time1 != null && !"".equals(time1)) && (orderId == null || "".equals(orderId)) && ("".equals(dhgatezhanghao) || dhgatezhanghao == null)&&("".equals(danhao) || danhao == null)&&(sumaitong==null || "".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu == null || "".equals(leimu)))
              {
              
                  stu = ht.find("from OrderTable where (fenpei != 3 or fenpei is null)   and sjc is null  and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"') order by time desc");
              }
              if(("".equals(dhgatezhanghao) || dhgatezhanghao == null) && (time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId != null && !"".equals(orderId))&&("".equals(danhao) || danhao == null)&&(sumaitong==null || "".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu == null || "".equals(leimu))){
              	
                  stu = ht.find("from OrderTable where (fenpei != 3 or fenpei is null) and sjc is null  and orderId = '"+orderId+"' order by time desc");
              }
              if(!"".equals(dhgatezhanghao) && dhgatezhanghao != null && (time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId))&&("".equals(danhao) || danhao == null)&&(sumaitong==null || "".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu == null || "".equals(leimu))){
                  stu = ht.find("from OrderTable a where (fenpei != 3 or fenpei is null) and sjc is null  and a.zhanghaoId in(select  b.id from ZhangHao b where b.id = '"+dhgatezhanghao+"') order by time desc");
              }
              if((!"".equals(dhgatezhanghao) && dhgatezhanghao != null)&& (time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId))&&("".equals(danhao) || danhao == null)&&(sumaitong==null || "".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu != null && !"".equals(leimu))){
                  stu = ht.find("from OrderTable a where (fenpei != 3 or fenpei is null)  and sjc is null  and a.zhanghaoId = "+dhgatezhanghao+" and leimuid="+leimu+" order by time desc");
              }
              if(!"".equals(dhgatezhanghao) && dhgatezhanghao != null && time != null && !"".equals(time) && time1 != null && !"".equals(time1) && (orderId == null || "".equals(orderId))&&("".equals(danhao) || danhao == null)&&(sumaitong==null || "".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu == null || "".equals(leimu))){
                  stu = ht.find("from OrderTable a where (fenpei != 3 or fenpei is null)  and sjc is null  and a.zhanghaoId in(select b.id from ZhangHao b where b.id = '"+dhgatezhanghao+"') and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"') order by time desc");
              }
              if((danhao != null && !"".equals(danhao)) && (dhgatezhanghao == null || "".equals(dhgatezhanghao))&&(time == null || "".equals(time)) && (time1 == null || "".equals(time1))&&(sumaitong==null || "".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu == null || "".equals(leimu))){
              	
              	stu = ht.find("from OrderTable where (fenpei != 3 or fenpei is null)  and sjc is null  and danhao='"+danhao+"' order by time desc");
              }
              else if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao == null || "".equals(dhgatezhanghao))&&("".equals(danhao) || danhao == null)&&(sumaitong==null || "".equals(sumaitong)||"".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu == null || "".equals(leimu))){
                  stu = ht.find("from OrderTable where (fenpei != 3 or fenpei is null)  and sjc is null  order by time desc");
              }
              else if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao == null || "".equals(dhgatezhanghao))&&("".equals(danhao) || danhao == null)&&(sumaitong == null||"".equals(sumaitong))&&(!"".equals(bianma) && bianma != null)&&(leimu == null || "".equals(leimu))){
              	stu=ht.find("from OrderTable where (fenpei != 3 or fenpei is null)  and sjc is null  and bianma = '"+bianma+"' order by time desc");
              }
              else if((time != null && !"".equals(time))&&((time1==null) || "".equals(time1)) && (orderId == null || "".equals(orderId)) && ("".equals(dhgatezhanghao) || dhgatezhanghao == null)&&("".equals(danhao) || danhao == null)&&(sumaitong==null || "".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu == null || "".equals(leimu)))
              {
              	
                  stu = ht.find("from OrderTable where (fenpei != 3 or fenpei is null)  and sjc is null  and datediff(day,time,'"+time+"')=0 order by time desc");
              }
              else if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao == null || "".equals(dhgatezhanghao))&&("".equals(danhao) || danhao == null)&&(sumaitong == null||"".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu != null && !"".equals(leimu))){
              	
              	stu=ht.find("from OrderTable where (fenpei != 3 or fenpei is null) and ssjc is null  and leimuid="+leimu+" order by time desc");
              }
              else if((time != null && !"".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao != null && !"".equals(dhgatezhanghao))&&("".equals(danhao) || danhao == null)&&(sumaitong == null||"".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu == null || "".equals(leimu))){
              	
              	stu=ht.find("from OrderTable where (fenpei != 3 or fenpei is null) and sjc is null and datediff(day,time,'"+time+"')=0 and zhanghaoId = "+dhgatezhanghao+" order by time desc");
              }
              else if((time != null && !"".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao == null || "".equals(dhgatezhanghao))&&(!"".equals(danhao) && danhao != null)&&(sumaitong == null||"".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu == null || "".equals(leimu))){
              	
              	stu=ht.find("from OrderTable where (fenpei != 3 or fenpei is null)  and sjc is null  and datediff(day,time,'"+time+"')=0 and danhao = '"+danhao+"' order by time desc");
              }
              else if((time != null && !"".equals(time)) && (time1 != null && !"".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao == null || "".equals(dhgatezhanghao))&&(!"".equals(danhao) && danhao != null)&&(sumaitong == null||"".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu == null || "".equals(leimu))){
              	
              	stu=ht.find("from OrderTable where (fenpei != 3 or fenpei is null) and sjc is null  and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"') and danhao = '"+danhao+"' order by time desc");
              }
              else if((time != null && !"".equals(time)) && (time1 != null && !"".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao == null || "".equals(dhgatezhanghao))&&("".equals(danhao) || danhao == null)&&(sumaitong == null||"".equals(sumaitong))&&(!"".equals(bianma) && bianma != null)&&(leimu == null || "".equals(leimu))){
              	
              	stu=ht.find("from OrderTable where (fenpei != 3 or fenpei is null) and sjc is null  and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"') and bianma = '"+bianma+"' order by time desc");
              }
              else if((time != null && !"".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao == null || "".equals(dhgatezhanghao))&&("".equals(danhao) || danhao == null)&&(sumaitong == null||"".equals(sumaitong))&&(!"".equals(bianma) && bianma != null)&&(leimu == null || "".equals(leimu))){
              	
              	stu=ht.find("from OrderTable where (fenpei != 3 or fenpei is null) and sjc is null  and datediff(day,time,'"+time+"')=0 and bianma = '"+bianma+"' order by time desc");
              }
              else if((time != null && !"".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao == null || "".equals(dhgatezhanghao))&&("".equals(danhao) || danhao == null)&&(sumaitong == null||"".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu != null && !"".equals(leimu))){
              	
              	stu=ht.find("from OrderTable where (fenpei != 3 or fenpei is null) and sjc is null  and datediff(day,time,'"+time+"')=0 and leimuid = '"+leimu+"' order by time desc");
              }
              else if((time != null && !"".equals(time)) && (time1 != null && !"".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao == null || "".equals(dhgatezhanghao))&&("".equals(danhao) || danhao == null)&&(sumaitong == null||"".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu != null && !"".equals(leimu))){
              	
              	stu=ht.find("from OrderTable where (fenpei != 3 or fenpei is null) and sjc is null and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"') and leimuid = '"+leimu+"' order by time desc");
              }
              else if((time != null && !"".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao != null && !"".equals(dhgatezhanghao))&&(!"".equals(danhao) && danhao != null)&&(sumaitong == null||"".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu == null || "".equals(leimu))){
              	
              	stu=ht.find("from OrderTable where (fenpei != 3 or fenpei is null) and sjc is null and datediff(day,time,'"+time+"')=0 and zhanghaoId = '"+dhgatezhanghao+"' and danhao = '"+danhao+"' order by time desc");
              }
              else if((time != null && !"".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao != null && !"".equals(dhgatezhanghao))&&("".equals(danhao) || danhao == null)&&(sumaitong == null||"".equals(sumaitong))&&(!"".equals(bianma) && bianma != null)&&(leimu == null || "".equals(leimu))){
              	
              	stu=ht.find("from OrderTable where (fenpei != 3 or fenpei is null) and sjc is null and datediff(day,time,'"+time+"')=0 and zhanghaoId = '"+dhgatezhanghao+"' and bianma = '"+bianma+"' order by time desc");
              }
              else if((time != null && !"".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao != null && !"".equals(dhgatezhanghao))&&("".equals(danhao) || danhao == null)&&(sumaitong == null||"".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu != null && !"".equals(leimu))){
              	
              	stu=ht.find("from OrderTable where (fenpei != 3 or fenpei is null) and sjc is null and datediff(day,time,'"+time+"')=0 and zhanghaoId = '"+dhgatezhanghao+"' and leimuid = '"+leimu+"' order by time desc");
              }
              else if((time != null && !"".equals(time)) && (time1 != null && !"".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao != null && !"".equals(dhgatezhanghao))&&(!"".equals(danhao) && danhao != null)&&(sumaitong == null||"".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu == null || "".equals(leimu))){
              	
              	stu=ht.find("from OrderTable where (fenpei != 3 or fenpei is null) and sjc is null and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"') and zhanghaoId = '"+dhgatezhanghao+"' and danhao = '"+danhao+"' order by time desc");
              }
              else if((time != null && !"".equals(time)) && (time1 != null && !"".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao != null && !"".equals(dhgatezhanghao))&&("".equals(danhao) || danhao == null)&&(sumaitong == null||"".equals(sumaitong))&&(!"".equals(bianma) && bianma != null)&&(leimu == null || "".equals(leimu))){
              	
              	stu=ht.find("from OrderTable where (fenpei != 3 or fenpei is null) and sjc is null and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"') and zhanghaoId = '"+dhgatezhanghao+"' and bianma = '"+bianma+"' order by time desc");
              }
              else if((time != null && !"".equals(time)) && (time1 != null && !"".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao != null && !"".equals(dhgatezhanghao))&&("".equals(danhao) || danhao == null)&&(sumaitong == null||"".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu != null && !"".equals(leimu))){
              	
              	stu=ht.find("from OrderTable where (fenpei != 3 or fenpei is null)  and sjc is null and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"') and zhanghaoId = '"+dhgatezhanghao+"' and leimuid = '"+leimu+"' order by time desc");
              }
              else if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao == null || "".equals(dhgatezhanghao))&&(!"".equals(danhao) && danhao != null)&&(sumaitong == null || "".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu != null && !"".equals(leimu))){
              	
              	stu=ht.find("from OrderTable where (fenpei != 3 or fenpei is null) and sjc is null and danhao='"+danhao+"' and leimuid = '"+leimu+"' order by time desc");
              }
              else if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao == null || "".equals(dhgatezhanghao))&&("".equals(danhao) || danhao == null)&&(sumaitong == null || "".equals(sumaitong))&&(!"".equals(bianma) && bianma != null)&&(leimu != null && !"".equals(leimu))){
              	
              	stu=ht.find("from OrderTable where (fenpei != 3 or fenpei is null) and sjc is null and bianma='"+bianma+"' and leimuid = '"+leimu+"' order by time desc");
              }
              else if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao == null || "".equals(dhgatezhanghao))&&(!"".equals(danhao) && danhao != null)&&(sumaitong == null || "".equals(sumaitong))&&(!"".equals(bianma) && bianma != null)&&(leimu != null && !"".equals(leimu))){
              	
              	stu=ht.find("from OrderTable where (fenpei != 3 or fenpei is null) and sjc is null and danhao = '"+danhao+"' and bianma='"+bianma+"' and leimuid = '"+leimu+"' order by time desc");
              }
              else if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao != null && !"".equals(dhgatezhanghao))&&("".equals(danhao) || danhao == null)&&(sumaitong == null || "".equals(sumaitong))&&(!"".equals(bianma) && bianma != null)&&(leimu != null && !"".equals(leimu))){
              	
              	stu=ht.find("from OrderTable where (fenpei != 3 or fenpei is null) and sjc is null and zhanghaoId = '"+dhgatezhanghao+"' and bianma='"+bianma+"' and leimuid = '"+leimu+"' order by time desc");
              }        
              else if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao != null && !"".equals(dhgatezhanghao))&&(!"".equals(danhao) && danhao != null)&&(sumaitong == null || "".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu != null && !"".equals(leimu))){
              	
              	stu=ht.find("from OrderTable where (fenpei != 3 or fenpei is null) and sjc is null and zhanghaoId = '"+dhgatezhanghao+"' and danhao='"+danhao+"' and leimuid = '"+leimu+"' order by time desc");
              }
              
              else if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao == null || "".equals(dhgatezhanghao))&&("".equals(danhao) || danhao == null)&&(Long.parseLong(sumaitong) == 0)&&("".equals(bianma) || bianma == null)&&(leimu == null || "".equals(leimu))){
              	stu=ht.find("from OrderTable where (fenpei != 3 or fenpei is null) and sjc is null and sumaitong=0 order by time desc");
              }
              else if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao == null || "".equals(dhgatezhanghao))&&("".equals(danhao) || danhao == null)&&(Long.parseLong(sumaitong) == 1)&&("".equals(bianma) || bianma == null)&&(leimu == null || "".equals(leimu))){
              	stu=ht.find("from OrderTable where (fenpei != 3 or fenpei is null) and sjc is null and sumaitong=1 order by time desc");
              }
          }
          catch(Exception e)
          {
              e.printStackTrace();
          }
          return stu;
    }
    //管理员查看速卖通订单
    public String getSuMaiTong(String orderId,String time,String time1){
    	 String stu = null;
         if((time != null || !"".equals(time)) && time1 != null && !"".equals(time1) && (orderId == null || "".equals(orderId)))
             stu = "from OrderTable a where  fenpei = 1 and sjc is null and wancheng=1 and (guojia is not null and guojia != '') and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"') order by zhanghaoId";
         if(!"".equals(orderId) && orderId != null)
             stu = "from OrderTable a where  fenpei = 1 and sjc is null and wancheng = 1 and (guojia is not null and guojia != '')  and orderId = '"+orderId+"' order by zhanghaoId";
         else
         if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)))
             stu = "from OrderTable a where  fenpei = 1 and sjc is null and wancheng = 1 and (guojia is not null and guojia != '') order by zhanghaoId";
         return stu;
    }
    //紧急订单
    public List<OrderTable> getXiuGaiOrder(Long userid){
    	return ht.find("from OrderTable a where a.denghuixin = 0 and sjc is null and a.sumaitong = 0 and a.fenpei = 1 and wancheng = 0 and daifahuo = 0 and caigouyuan = "+userid+" and time = DATEADD(dd, DATEDIFF(dd,0,getdate()-2), 0)");
    }
    //业务查看要修改入账的订单
    public String getRuZhang(String orderId,String time,String time1){
    	 String stu = null;
         if((time != null || !"".equals(time)) && time1 != null && !"".equals(time1) && (orderId == null || "".equals(orderId)))
             stu = "from OrderTable where fenpei = 3 and (ruzhang = 0 or ruzhang is null) and sjc is null and wancheng = 1 and yunfeidaochu = 1 and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"')";
         if(!"".equals(orderId) && orderId != null)
             stu = "from OrderTable where fenpei = 3 and (ruzhang = 0 or ruzhang is null) and sjc is null and wancheng = 1 and yunfeidaochu = 1 and orderId = '"+orderId+"'";
         else
         if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)))
             stu = "from OrderTable where fenpei = 3 and (ruzhang = 0 or ruzhang is null)  and sjc is null and wancheng = 1 and yunfeidaochu = 1";
         return stu;
    }
    //完成时间导出
    public List<OrderTable> getDaoChuYunFei(String time,String time1){
    	List<OrderTable> stu = null;
    	if((time != null && !"".equals(time)) && (time1 == null || "".equals(time1))){
    		
    		stu = ht.find("from OrderTable where wanchengtime='"+time+"'  and wancheng = 1  and sjc is null and fenpei = 1");
    	}else if((time != null && !"".equals(time))&&(time1 != null && !"".equals(time1))){
    		
    		stu = ht.find("from OrderTable where (convert(varchar(10),wanchengtime,120) between '"+time+"'and '"+time1+"') and sjc is null and wancheng = 1 and  fenpei = 1");
    	}
    	return stu;
    	 
    }
    //顾客查看退货 
    public String getTuiHuoAll(Long userid,String orderId,String danhao,String chuli){
    	String stu = "";
    	 if((danhao != null && !"".equals(danhao)) && (orderId == null || "".equals(orderId)) && (chuli == null || "".equals(chuli))){
             stu = "from OrderTable where dengluId = "+userid+" and fenpei = 3 and  tuihuo = 1 and sjc is null and danhao='"+danhao+"'";
         }
    	 else if(orderId != null && !"".equals(orderId) && (chuli == null || "".equals(chuli)) && (danhao == null || "".equals(danhao))){
    		stu = "from OrderTable where fenpei = 3 and tuihuo = 1 and sjc is null and orderId = '"+orderId+"' and dengluId = "+userid+"";
    	}else if((orderId == null || "".equals(orderId)) && (chuli == null || "".equals(chuli)) && (danhao == null || "".equals(danhao))){
    		stu = "from OrderTable where fenpei = 3 and tuihuo = 1 and sjc is null and dengluId = "+userid+"";
    	}else if((orderId == null || "".equals(orderId)) && (Long.parseLong(chuli) == 0) && (danhao == null || "".equals(danhao))){
    		stu = "from OrderTable where (chuli = 0 or chuli is null) and fenpei = 3 and sjc is null and tuihuo = 1 and dengluId = "+userid+"";
    	}else if((orderId == null || "".equals(orderId)) && (Long.parseLong(chuli) == 1) && (danhao == null || "".equals(danhao))){
    		stu = "from OrderTable where chuli = 1 and fenpei = 3 and tuihuo = 1 and sjc is null and dengluId = "+userid+"";
    	}
    	return stu;
    }
    //用时间查询
    public List<OrderTable> getTime(String time){
    	return ht.find("from OrderTable where convert(varchar(10),time,120) like '%"+time+"%' and sjc is null");
    }
    //财务查看全部退货
    public String getTuiHuoAll(String danhao,Long chuli,Long userid){
    	String stu = "";
    	
    	if((danhao != null && !"".equals(danhao)) && (chuli == null || "".equals(chuli))){
    	
    		stu = "from OrderTable where tuihuo = 1 and sjc is null and danhao like '%"+danhao+"%'  and zhanghaoId in(select id from ZhangHao where yewuId = "+userid+")";
    	}
    	else if((danhao == null || "".equals(danhao)) && (chuli == null || "".equals(chuli))){
    	
    		stu = "from OrderTable where tuihuo = 1 and sjc is null  and zhanghaoId in(select id from ZhangHao where yewuId = "+userid+")";
    	}
    	else if((danhao == null || "".equals(danhao)) && (chuli == 0l)){
        	
    		stu = "from OrderTable where tuihuo = 1 and sjc is null and (chuli = 0 or chuli is null)  and zhanghaoId in(select id from ZhangHao where yewuId = "+userid+")";
    	}
    	else if((danhao == null || "".equals(danhao)) && (chuli == 1l)){
    	
    		stu = "from OrderTable where tuihuo = 1 and sjc is null and chuli = 1  and zhanghaoId in(select id from ZhangHao where yewuId = "+userid+")";
    	}
    	
    	return stu;
    } 
    //查看退货订单
    public String getProcessingReturns(String danhao,Long userid){
	String stu = "";
    	
    	if(danhao != null && !"".equals(danhao)){
    	
    		stu = "from OrderTable where tuihuo = 1 and (chuli = 0 or chuli is null) and sjc is null and danhao like '%"+danhao+"%'  and zhanghaoId in(select id from ZhangHao where yewuId = "+userid+")";
    	}
    	else if(danhao == null || "".equals(danhao)){
    	
    		stu = "from OrderTable where tuihuo = 1 and (chuli = 0 or chuli is null) and sjc is null  and zhanghaoId in(select id from ZhangHao where yewuId = "+userid+")";
    	}

    	return stu;
    }
    //查看业务返回订单
    public String getFanHuiOrder(String guoneidanhao,String orderId){
    	String stu = "";
    	if((orderId == null || "".equals(orderId)) && !"".equals(guoneidanhao) && guoneidanhao != null){
    		stu = "from OrderTable a where daochu = 3 and guoneidanhao like '%"+guoneidanhao+"%'";
    	}
    	if(!"".equals(orderId) && orderId != null){
    		 stu = "from OrderTable where daochu = 3  and orderId = '"+orderId+"'";
    	}
    	 else
    	 if((guoneidanhao == null || "".equals(guoneidanhao)) && (orderId == null || "".equals(orderId))){
    		 stu = "from OrderTable where daochu = 3";
    	 }
    	return stu;
    }

    //查询订单表中没有的采购
    public List<OrderTable> getCaiGouNull(){
    	List<OrderTable> stu = ht.find("from OrderTable where (caigouyuan != 0 and caigouyuan != 1) and sjc is null and caigouyuan not in (select id from UserInfo where (quanxian = 3 or quanxian = 4))");
    	return stu;
    }
    //查看库存订单
    public String getKuCunOrder(Long userid,String orderId){
    	String stu = null;
        
        if(!"".equals(orderId) && orderId != null)
            stu = "from KuCunTable a where userid = "+userid+" and orderId = '"+orderId+"'";
       
        else{
            stu = "from KuCunTable a where userid = "+userid+"";
        }
        return stu;
    }
    //时间查询全部
    public List<OrderTable> getHuiLv(String time,String time1){
    	return ht.find("from OrderTable where (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"') and sjc is null");
    }
    //管理员查看运费为空
    public List<OrderTable> getYunFeiAll(Long userid,String orderId,String time,String time1){
    	List<OrderTable> stu = null;
        try
        {
            if((time != null || !"".equals(time)) && time1 != null && !"".equals(time1) && (orderId == null || "".equals(orderId)))
                stu = ht.find("select count(*) from OrderTable where wancheng=1  and (guojia is null or guojia = '') and caigouyuan = ?  and  (fenpei != 3 or fenpei is null) and yunfei is null and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"') and sjc is null", new Object[] {
                    userid
                });
            if(!"".equals(orderId) && orderId != null)
                stu = ht.find("select count(*) from OrderTable where wancheng=1  and (guojia is null or guojia = '') and  caigouyuan = ? and   (fenpei != 3 or fenpei is null) and yunfei is null and orderId = ? and sjc is null", new Object[] {
                    userid, orderId
                });
            else
            if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)))
                stu = ht.find("select count(*) from OrderTable   where wancheng=1  and (guojia is null or guojia = '') and caigouyuan = ? and   (fenpei != 3 or fenpei is null) and yunfei is null and sjc is null", new Object[] {
                    userid
                });
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return stu;
    }
  //货款为空
    public List<OrderTable> getHuoKuanAll(Long userid,String orderId,String time,String time1){
    	List<OrderTable> stu = null;
        try
        {
            if((time != null || !"".equals(time)) && time1 != null && !"".equals(time1) && (orderId == null || "".equals(orderId)))
                stu = ht.find("select count(*) from OrderTable where wancheng=1  and sjc is null and (guojia is null or guojia = '') and  (fenpei != 3 or fenpei is null) and huokuan is null and caigouyuan = ? and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"')", new Object[] {
                    userid
                });
            if(!"".equals(orderId) && orderId != null)
                stu = ht.find("select count(*) from OrderTable where  wancheng=1  and sjc is null and (guojia is null or guojia = '') and  caigouyuan = ? and orderId = ? and (fenpei != 3 or fenpei is null) and huokuan is null", new Object[] {
                    userid, orderId
                });
            else
            if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)))
                stu = ht.find("select count(*) from OrderTable where  wancheng=1  and sjc is null and (guojia is null or guojia = '') and  caigouyuan = ? and (fenpei != 3 or fenpei is null) and huokuan is null", new Object[] {
                    userid
                });
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return stu;
    }
  //运费为空
    public String getYunFeiAllNum(Long userid,String orderId,String time,String time1){
    	String stu = null;
        try
        {
            if((time != null || !"".equals(time)) && time1 != null && !"".equals(time1) && (orderId == null || "".equals(orderId)))
                stu = "from OrderTable where wancheng=1  and (guojia is null or guojia = '') and sjc is null and  caigouyuan = "+userid+"  and  (fenpei != 3 or fenpei is null) and yunfei is null and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"')";
            if(!"".equals(orderId) && orderId != null)
                stu = "from OrderTable where wancheng=1  and (guojia is null or guojia = '') and sjc is null and  caigouyuan = "+userid+" and  (fenpei != 3 or fenpei is null) and yunfei is null and orderId = '"+orderId+"'";
            else
            if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)))
                stu = "from OrderTable where wancheng=1  and (guojia is null or guojia = '') and sjc is null and caigouyuan = "+userid+" and  (fenpei != 3 or fenpei is null) and yunfei is null";
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return stu;
    }
    //货款为空
    public String getHuoKuanAllNum(Long userid,String orderId,String time,String time1){
    	String stu = null;
        try
        {
            if((time != null || !"".equals(time)) && time1 != null && !"".equals(time1) && (orderId == null || "".equals(orderId)))
                stu = "from OrderTable where wancheng=1  and (guojia is null or guojia = '') and sjc is null and  (fenpei != 3 or fenpei is null) and huokuan is null and caigouyuan = "+userid+" and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"')";
            if(!"".equals(orderId) && orderId != null)
                stu = "from OrderTable where wancheng=1  and (guojia is null or guojia = '') and sjc is null and  caigouyuan = "+userid+" and orderId = '"+orderId+"' and (fenpei != 3 or fenpei is null) and huokuan is null";
            else
            if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)))
                stu = "from OrderTable where  wancheng=1  and (guojia is null or guojia = '') and sjc is null and  caigouyuan = "+userid+" and (fenpei != 3 or fenpei is null) and huokuan is null";
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return stu;
    }
  //采购查看退货
    public String getTuiHuo(Long userid,String orderId,String danhao,String chuli){
    	String stu = null;
        try
        {
        	
            if((danhao != null && !"".equals(danhao)) && (orderId == null || "".equals(orderId)) && (chuli == null || "".equals(chuli))){
                stu = "from OrderTable where caigouyuan = "+userid+" and (fenpei != 3 or fenpei is null) and sjc is null and  tuihuo = 1 and danhao='"+danhao+"' order by time desc";
            }
            else if(!"".equals(orderId) && orderId != null){
                stu = "from OrderTable where caigouyuan = "+userid+" and (fenpei != 3 or fenpei is null) and sjc is null and  tuihuo = 1 and orderId = '"+orderId+"' order by time desc";
            }
            else if((danhao == null || "".equals(danhao)) && (orderId == null || "".equals(orderId)) && (chuli == null || "".equals(chuli))){
                stu = "from OrderTable where caigouyuan = "+userid+" and (fenpei != 3 or fenpei is null) and sjc is null and tuihuo = 1 order by time desc";
            }
            else if(danhao == null || "".equals(danhao) && (orderId == null || "".equals(orderId)) && (Long.parseLong(chuli) == 0)){
            	stu = "from OrderTable where caigouyuan = "+userid+" and (fenpei != 3 or fenpei is null) and sjc is null and tuihuo = 1 and (chuli = 0 or chuli is null)";
            }
            else if(danhao == null || "".equals(danhao) && (orderId == null || "".equals(orderId)) && (Long.parseLong(chuli) == 1)){
            	stu = "from OrderTable where caigouyuan = "+userid+" and (fenpei != 3 or fenpei is null) and sjc is null and tuihuo = 1 and chuli = 1";;
            }
           
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return stu;
    }
    //管理员查看纠纷个数
    public String getJiuFenNum(Long userid,String orderId,String time,String time1){
    	String stu = null;
        try
        {
            if((time != null || !"".equals(time)) && time1 != null && !"".equals(time1) && (orderId == null || "".equals(orderId))){
                stu = "from OrderTable where wancheng=1 and fenpei=1 and jiufen=1 and sjc is null and wancheng=1 and caigouyuan = "+userid+" and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"')";
            }
            if(!"".equals(orderId) && orderId != null){
                stu = "from OrderTable where fenpei = 1 and wancheng = 1 and jiufen =1 and sjc is nulland caigouyuan = "+userid+" and orderId='"+orderId+"'";
            }
            else
            if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId))){
                stu = "from OrderTable where  fenpei = 1 and wancheng = 1 and jiufen =1 and sjc is null and caigouyuan = "+userid+"";
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return stu;
    }
    //登录编号查询全部
    public OrderTable getDenglu(Long userid){
    	return ht.findFirst("from OrderTable where dengluId = ? and sjc is null",new Object[]{userid});
    }
    //查询时间差
    public String getTime(String time,String caigoutime){
        String hql = "select DateDiff(day,'"+time+"','"+caigoutime+"')";
        return hql;
    }
    //查看全部分配订单
    public String getAllFenPei(Long userid,String orderId){
    	String hql = null;
    	if(orderId != null && !"".equals(orderId)){
    		 hql = "from OrderTable where (fenpei=0 or fenpei is null) and sjc is null and (wancheng=0 or wancheng is null) and zhanghaoId in(select id from ZhangHao where yewuId = "+userid+") and orderId = '"+orderId+"'";
    	}else{
    		 hql = "from OrderTable where  (fenpei=0 or fenpei is null) and sjc is null and (wancheng=0 or wancheng is null) and zhanghaoId in(select id from ZhangHao where yewuId = "+userid+")";
    	}
    	return hql;
    }
    //查看全部分配订单
    public String getWenTiOrder(Long userid,String orderId,Long dhgatezhanghao){
    	String hql = null;
    	if((dhgatezhanghao != null && !"".equals(dhgatezhanghao))&& (orderId == null || "".equals(orderId))){
    		 hql = "from OrderTable where fenpei=2  and (wancheng=0 or wancheng is null) and zhanghaoId in(select id from ZhangHao where yewuId = "+userid+") and zhanghaoId = "+dhgatezhanghao+"";
    	}
    	if(orderId != null && !"".equals(orderId)){
    		 hql = "from OrderTable where fenpei=2  and (wancheng=0 or wancheng is null) and zhanghaoId in(select id from ZhangHao where yewuId = "+userid+") and orderId = '"+orderId+"'";
    	}else if((dhgatezhanghao == null || "".equals(dhgatezhanghao)) && (orderId == null || "".equals(orderId))){
    		 hql = "from OrderTable where fenpei=2  and (wancheng=0 or wancheng is null) and zhanghaoId in(select id from ZhangHao where yewuId = "+userid+")";
    	}
    	return hql;
    }
  //查看全部订单
    public List<OrderTable> getAllOrder(String orderId, String time, String time1, String dhgatezhanghao,String danhao,String sumaitong,String bianma,String leimu){

    	List<OrderTable> stu = null;
        try
        {
            if((time != null && !"".equals(time)) && (time1 != null && !"".equals(time1)) && (orderId == null || "".equals(orderId)) && ("".equals(dhgatezhanghao) || dhgatezhanghao == null)&&("".equals(danhao) || danhao == null)&&(sumaitong==null || "".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu == null || "".equals(leimu)))
            {
            
                stu =ht.find("from OrderTable where caigoutime is not null and sjc is null and  (fenpei != 3 or fenpei is null) and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"') order by zhanghaoId desc");
            }
            if(("".equals(dhgatezhanghao) || dhgatezhanghao == null) && (time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId != null && !"".equals(orderId))&&("".equals(danhao) || danhao == null)&&(sumaitong==null || "".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu == null || "".equals(leimu))){
            	
                stu = ht.find("from OrderTable where caigoutime is not null and sjc is null and  (fenpei != 3 or fenpei is null) and orderId = '"+orderId+"' order by zhanghaoId desc");
            }
            if(!"".equals(dhgatezhanghao) && dhgatezhanghao != null && (time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId))&&("".equals(danhao) || danhao == null)&&(sumaitong==null || "".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu == null || "".equals(leimu))){
                stu = ht.find("from OrderTable a where caigoutime is not null and sjc is null and (fenpei != 3 or fenpei is null) and a.zhanghaoId in(select b.id from ZhangHao b where b.id = '"+dhgatezhanghao+"') order by zhanghaoId desc");
            }
            if(!"".equals(dhgatezhanghao) && dhgatezhanghao != null && time != null && !"".equals(time) && time1 != null && !"".equals(time1) && (orderId == null || "".equals(orderId))&&("".equals(danhao) || danhao == null)&&(sumaitong==null || "".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu == null || "".equals(leimu))){
                stu = ht.find("from OrderTable a where caigoutime is not null  and sjc is null and  (fenpei != 3 or fenpei is null) and a.zhanghaoId in(select b.id from ZhangHao b where b.id = '"+dhgatezhanghao+"') and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"') order by zhanghaoId desc");
            }
            if((danhao != null && !"".equals(danhao)) && (dhgatezhanghao == null || "".equals(dhgatezhanghao))&&(time == null || "".equals(time)) && (time1 == null || "".equals(time1))&&(sumaitong==null || "".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu == null || "".equals(leimu))){
            	
            	stu = ht.find("from OrderTable where caigoutime is not null and sjc is null and (fenpei != 3 or fenpei is null) and danhao='"+danhao+"' order by zhanghaoId desc");
            }
            if((!"".equals(dhgatezhanghao) && dhgatezhanghao != null)&& (time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId))&&("".equals(danhao) || danhao == null)&&(sumaitong==null || "".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu != null && !"".equals(leimu))){
                stu = ht.find("from OrderTable a where caigoutime is not null and sjc is null and (fenpei != 3 or fenpei is null) and a.zhanghaoId = "+dhgatezhanghao+" and leimuid="+leimu+" order by zhanghaoId desc");
            }
            if((!"".equals(dhgatezhanghao) && dhgatezhanghao != null)&& (time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId))&&("".equals(danhao) || danhao == null)&&(sumaitong==null || "".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu != null && !"".equals(leimu))){
                stu = ht.find("from OrderTable a where caigoutime is not null and sjc is null and (fenpei != 3 or fenpei is null) and a.zhanghaoId = "+dhgatezhanghao+" and leimuid="+leimu+" order by zhanghaoId desc");
            }
            else if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao == null || "".equals(dhgatezhanghao))&&("".equals(danhao) || danhao == null)&&(sumaitong==null || "".equals(sumaitong)||"".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu == null || "".equals(leimu))){
                stu = ht.find("from OrderTable where caigoutime is not null and sjc is null and (fenpei != 3 or fenpei is null) order by zhanghaoId desc");
            }
            else if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao == null || "".equals(dhgatezhanghao))&&("".equals(danhao) || danhao == null)&&(sumaitong == null||"".equals(sumaitong))&&(!"".equals(bianma) && bianma != null)&&(leimu == null || "".equals(leimu))){
            	stu=ht.find("from OrderTable where caigoutime is not null and sjc is null and (fenpei != 3 or fenpei is null) and bianma = '"+bianma+"' order by zhanghaoId desc");
            }
            else if((time != null && !"".equals(time))&&((time1==null) || "".equals(time1)) && (orderId == null || "".equals(orderId)) && ("".equals(dhgatezhanghao) || dhgatezhanghao == null)&&("".equals(danhao) || danhao == null)&&(sumaitong==null || "".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu == null || "".equals(leimu)))
            {
            	
                stu = ht.find("from OrderTable where caigoutime is not null and sjc is null and (fenpei != 3 or fenpei is null) and datediff(day,time,'"+time+"')=0 order by zhanghaoId desc");
            }
            else if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao == null || "".equals(dhgatezhanghao))&&("".equals(danhao) || danhao == null)&&(sumaitong == null||"".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu != null && !"".equals(leimu))){
            	
            	stu=ht.find("from OrderTable where caigoutime is not null  and sjc is null and (fenpei != 3 or fenpei is null) and leimuid="+leimu+" order by zhanghaoId desc");
            }
            else if((time != null && !"".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao != null && !"".equals(dhgatezhanghao))&&("".equals(danhao) || danhao == null)&&(sumaitong == null||"".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu == null || "".equals(leimu))){
            	
            	stu=ht.find("from OrderTable where  caigoutime is not null and sjc is null and (fenpei != 3 or fenpei is null) and datediff(day,time,'"+time+"')=0 and zhanghaoId = "+dhgatezhanghao+"");
            }
            else if((time != null && !"".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao == null || "".equals(dhgatezhanghao))&&(!"".equals(danhao) && danhao != null)&&(sumaitong == null||"".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu == null || "".equals(leimu))){
            	
            	stu=ht.find("from OrderTable where  caigoutime is not null  and sjc is null and (fenpei != 3 or fenpei is null) and datediff(day,time,'"+time+"')=0 and danhao = '"+danhao+"'");
            }
            else if((time != null && !"".equals(time)) && (time1 != null && !"".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao == null || "".equals(dhgatezhanghao))&&(!"".equals(danhao) && danhao != null)&&(sumaitong == null||"".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu == null || "".equals(leimu))){
            	
            	stu=ht.find("from OrderTable where caigoutime is not null and sjc is null and (fenpei != 3 or fenpei is null) and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"') and danhao = '"+danhao+"'");
            }
            else if((time != null && !"".equals(time)) && (time1 != null && !"".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao == null || "".equals(dhgatezhanghao))&&("".equals(danhao) || danhao == null)&&(sumaitong == null||"".equals(sumaitong))&&(!"".equals(bianma) && bianma != null)&&(leimu == null || "".equals(leimu))){
            	
            	stu=ht.find("from OrderTable where caigoutime is not null and sjc is null and (fenpei != 3 or fenpei is null) and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"') and bianma = '"+bianma+"'");
            }
            else if((time != null && !"".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao == null || "".equals(dhgatezhanghao))&&("".equals(danhao) || danhao == null)&&(sumaitong == null||"".equals(sumaitong))&&(!"".equals(bianma) && bianma != null)&&(leimu == null || "".equals(leimu))){
            	
            	stu=ht.find("from OrderTable where caigoutime is not null and sjc is null and (fenpei != 3 or fenpei is null) and datediff(day,time,'"+time+"')=0 and bianma = '"+bianma+"'");
            }
            else if((time != null && !"".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao == null || "".equals(dhgatezhanghao))&&("".equals(danhao) || danhao == null)&&(sumaitong == null||"".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu != null && !"".equals(leimu))){
            	
            	stu=ht.find("from OrderTable where caigoutime is not null and sjc is null and (fenpei != 3 or fenpei is null) and datediff(day,time,'"+time+"')=0 and leimuid = '"+leimu+"'");
            }
            else if((time != null && !"".equals(time)) && (time1 != null && !"".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao == null || "".equals(dhgatezhanghao))&&("".equals(danhao) || danhao == null)&&(sumaitong == null||"".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu != null && !"".equals(leimu))){
            	
            	stu=ht.find("from OrderTable where caigoutime is not null and sjc is null and (fenpei != 3 or fenpei is null) and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"') and leimuid = '"+leimu+"'");
            }
            else if((time != null && !"".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao != null && !"".equals(dhgatezhanghao))&&(!"".equals(danhao) && danhao != null)&&(sumaitong == null||"".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu == null || "".equals(leimu))){
            	
            	stu=ht.find("from OrderTable where caigoutime is not null and sjc is null and (fenpei != 3 or fenpei is null) and datediff(day,time,'"+time+"')=0 and zhanghaoId = '"+dhgatezhanghao+"' and danhao = '"+danhao+"'");
            }
            else if((time != null && !"".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao != null && !"".equals(dhgatezhanghao))&&("".equals(danhao) || danhao == null)&&(sumaitong == null||"".equals(sumaitong))&&(!"".equals(bianma) && bianma != null)&&(leimu == null || "".equals(leimu))){
            	
            	stu=ht.find("from OrderTable where caigoutime is not null and sjc is null and (fenpei != 3 or fenpei is null) and datediff(day,time,'"+time+"')=0 and zhanghaoId = '"+dhgatezhanghao+"' and bianma = '"+bianma+"'");
            }
            else if((time != null && !"".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao != null && !"".equals(dhgatezhanghao))&&("".equals(danhao) || danhao == null)&&(sumaitong == null||"".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu != null && !"".equals(leimu))){
            	
            	stu=ht.find("from OrderTable where caigoutime is not null and sjc is null and (fenpei != 3 or fenpei is null) and datediff(day,time,'"+time+"')=0 and zhanghaoId = '"+dhgatezhanghao+"' and leimuid = '"+leimu+"'");
            }
            else if((time != null && !"".equals(time)) && (time1 != null && !"".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao != null && !"".equals(dhgatezhanghao))&&(!"".equals(danhao) && danhao != null)&&(sumaitong == null||"".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu == null || "".equals(leimu))){
            	
            	stu=ht.find("from OrderTable where caigoutime is not null and sjc is null and  (fenpei != 3 or fenpei is null) and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"') and zhanghaoId = '"+dhgatezhanghao+"' and danhao = '"+danhao+"'");
            }
            else if((time != null && !"".equals(time)) && (time1 != null && !"".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao != null && !"".equals(dhgatezhanghao))&&("".equals(danhao) || danhao == null)&&(sumaitong == null||"".equals(sumaitong))&&(!"".equals(bianma) && bianma != null)&&(leimu == null || "".equals(leimu))){
            	
            	stu=ht.find("from OrderTable where caigoutime is not null and sjc is null and (fenpei != 3 or fenpei is null) and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"') and zhanghaoId = '"+dhgatezhanghao+"' and bianma = '"+bianma+"'");
            }
            else if((time != null && !"".equals(time)) && (time1 != null && !"".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao != null && !"".equals(dhgatezhanghao))&&("".equals(danhao) || danhao == null)&&(sumaitong == null||"".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu != null && !"".equals(leimu))){
            	
            	stu=ht.find("from OrderTable where caigoutime is not null and sjc is null and (fenpei != 3 or fenpei is null) and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"') and zhanghaoId = '"+dhgatezhanghao+"' and leimuid = '"+leimu+"'");
            }
            else if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao == null || "".equals(dhgatezhanghao))&&(!"".equals(danhao) && danhao != null)&&(sumaitong == null || "".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu != null && !"".equals(leimu))){
            	
            	stu=ht.find("from OrderTable where  caigoutime is not null and sjc is null and (fenpei != 3 or fenpei is null) and danhao='"+danhao+"' and leimuid = '"+leimu+"'");
            }
            else if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao == null || "".equals(dhgatezhanghao))&&("".equals(danhao) || danhao == null)&&(sumaitong == null || "".equals(sumaitong))&&(!"".equals(bianma) && bianma != null)&&(leimu != null && !"".equals(leimu))){
            	
            	stu=ht.find("from OrderTable where  caigoutime is not null and sjc is null and (fenpei != 3 or fenpei is null) and bianma='"+bianma+"' and leimuid = '"+leimu+"'");
            }
            else if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao == null || "".equals(dhgatezhanghao))&&(!"".equals(danhao) && danhao != null)&&(sumaitong == null || "".equals(sumaitong))&&(!"".equals(bianma) && bianma != null)&&(leimu != null && !"".equals(leimu))){
            	
            	stu=ht.find("from OrderTable where  caigoutime is not null and sjc is null and (fenpei != 3 or fenpei is null) and danhao = '"+danhao+"' and bianma='"+bianma+"' and leimuid = '"+leimu+"'");
            }
            else if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao != null && !"".equals(dhgatezhanghao))&&("".equals(danhao) || danhao == null)&&(sumaitong == null || "".equals(sumaitong))&&(!"".equals(bianma) && bianma != null)&&(leimu != null && !"".equals(leimu))){
            	
            	stu=ht.find("from OrderTable where caigoutime is not null and sjc is null and  (fenpei != 3 or fenpei is null) and zhanghaoId = '"+dhgatezhanghao+"' and bianma='"+bianma+"' and leimuid = '"+leimu+"'");
            }        
            else if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao != null && !"".equals(dhgatezhanghao))&&("".equals(danhao) || danhao == null)&&(sumaitong == null || "".equals(sumaitong))&&("".equals(bianma) || bianma == null)&&(leimu != null && !"".equals(leimu))){
            	
            	stu=ht.find("from OrderTable where caigoutime is not null and sjc is null and (fenpei != 3 or fenpei is null) and zhanghaoId = '"+dhgatezhanghao+"' and danhao='"+danhao+"' and leimuid = '"+leimu+"'");
            }
            
            else if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao == null || "".equals(dhgatezhanghao))&&("".equals(danhao) || danhao == null)&&(Long.parseLong(sumaitong) == 0)&&("".equals(bianma) || bianma == null)&&(leimu == null || "".equals(leimu))){
            	stu=ht.find("from OrderTable where caigoutime is not null  and sjc is null and (fenpei != 3 or fenpei is null) and (sumaitong=0 or sumaitong is null) order by zhanghaoId desc");
            }
            else if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (dhgatezhanghao == null || "".equals(dhgatezhanghao))&&("".equals(danhao) || danhao == null)&&(Long.parseLong(sumaitong) == 1)&&("".equals(bianma) || bianma == null)&&(leimu == null || "".equals(leimu))){
            	stu=ht.find("from OrderTable where caigoutime is not null and sjc is null and (fenpei != 3 or fenpei is null) and sumaitong=1 order by zhanghaoId desc");
            }
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return stu;
    }
    //采购查看全部完成订单
    public List<OrderTable> getAllWanChengOrder(Long userid, String orderId, String time, String time1,String caigoutime,String caigoutime1,String bianma){
    	List<OrderTable> stu = null;
        if((time != null || !"".equals(time)) && time1 != null && !"".equals(time1) && (orderId == null || "".equals(orderId)) && ("".equals(caigoutime) || caigoutime == null)&&("".equals(caigoutime1) || caigoutime1 == null)&&("".equals(bianma) || bianma == null)){
        	
            stu = ht.find("from OrderTable a where caigoutime is not null and sjc is null  and a.fenpei = 1 and wancheng = 1  and a.caigouyuan = "+userid+" and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"') order by zhanghaoId");
        }
        if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (caigoutime != null && !"".equals(caigoutime)) && (caigoutime1 != null && !"".equals(caigoutime1)) && ("".equals(bianma) || bianma == null)){
        	
        	stu = ht.find("from OrderTable a where caigoutime is not null and sjc is null and fenpei = 1 and wancheng = 1  and a.caigouyuan = "+userid+" and (convert(varchar(10),caigoutime,120) between '"+caigoutime+"'and '"+caigoutime1+"') order by zhanghaoId");
        }
        if(("".equals(caigoutime) || caigoutime == null)&&("".equals(caigoutime1) || caigoutime1 == null) && (time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && orderId != null && !"".equals(orderId)&&("".equals(bianma) || bianma == null)){
        	
            stu = ht.find("from OrderTable a where caigoutime is not null and sjc is null and a.fenpei = 1 and wancheng = 1  and  caigouyuan = "+userid+" and orderId = '"+orderId+"' order by zhanghaoId");

        }
        if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId))&&(caigoutime == null || "".equals(caigoutime)) && (caigoutime1 == null || "".equals(caigoutime1)) && (bianma != null && !"".equals(bianma))){
        	
        	 stu = ht.find("from OrderTable a where caigoutime is not null  and sjc is null and a.fenpei = 1 and wancheng = 1 and caigouyuan = "+userid+" and bianma = '"+bianma+"' order by zhanghaoId"); 
        }
        else if((time != null && !"".equals(time))&& (time1 == null  || "".equals(time1)) && (orderId == null || "".equals(orderId)) && ("".equals(caigoutime) || caigoutime == null)&&("".equals(caigoutime1) || caigoutime1 == null)&&("".equals(bianma) || bianma == null))
        {
        	
            stu = ht.find("from OrderTable where caigoutime is not null and sjc is null and datediff(day,time,'"+time+"')=0 and caigouyuan = "+userid+" order by zhanghaoId desc");
        }
        else
        if((time == null || "".equals(time)) && (time1 == null  || "".equals(time1)) && (orderId == null || "".equals(orderId)) && ("".equals(caigoutime) || caigoutime == null)&&("".equals(caigoutime1) || caigoutime1 == null)&&("".equals(bianma) || bianma == null)){
        	
            stu = ht.find("from OrderTable a where caigoutime is not null  and sjc is null and a.fenpei = 1 and wancheng = 1  and caigouyuan = "+userid+" order by zhanghaoId");
        }
        return stu;
    }
    //纠纷总金额
    public List<OrderTable> getAllMoney(String orderId,String time,String time1,Long selcaigouyuan,Long leimus){
    
    	List<OrderTable> stu = null;
        try
        {
            if((time != null || !"".equals(time)) && time1 != null && !"".equals(time1) && (orderId == null || "".equals(orderId)) && (selcaigouyuan==0 || "".equals(selcaigouyuan))&&(leimus == null || "".equals(leimus))){
            	
                stu = ht.find("select round(sum(money),3) from OrderTable where fenpei = 1 and sjc is null and wancheng = 1 and jiufen = 1 and (convert(varchar(10),jiufentime,120) between '"+time+"'and '"+time1+"')");
            }
            else if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (selcaigouyuan==null || selcaigouyuan == 0l)&&(leimus != null && !"".equals(leimus))){
                    stu = ht.find("select round(sum(money),3) from OrderTable  where leimuid="+leimus+" and fenpei = 1  and sjc is null and wancheng = 1 and jiufen = 1");
                }
            	else
                if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (selcaigouyuan==null || selcaigouyuan == 0l)&&(leimus == null || "".equals(leimus))){
                    stu = ht.find("select round(sum(money),3) from OrderTable  where fenpei = 1  and sjc is null and wancheng = 1 and jiufen = 1");
                }
                else if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)) && (selcaigouyuan!=0)&&(leimus == null || "".equals(leimus))){
            	
            	stu = ht.find("select round(sum(money),3) from OrderTable where fenpei = 1 and sjc is null and wancheng = 1 and jiufen = 1 and caigouyuan = "+selcaigouyuan+"");
                }
                else if((time != null && !"".equals(time)) && (time1 != null || !"".equals(time1)) && (orderId == null || "".equals(orderId))&&(leimus == null || "".equals(leimus)) && (selcaigouyuan!=0)){
                	
                	stu = ht.find("select round(sum(money),3) from OrderTable where fenpei = 1  and sjc is null and wancheng = 1 and jiufen = 1 and caigouyuan = "+selcaigouyuan+" and (convert(varchar(10),jiufentime,120) between '"+time+"'and '"+time1+"')");
                 }
                else if(!"".equals(orderId) && orderId != null){
            	
                stu = ht.find("select round(sum(money),3) from OrderTable where fenpei = 1  and sjc is null  and wancheng = 1 and jiufen = 1 and orderId = ?", new Object[] {
                    orderId
                });
            }
           
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return stu;
    }
    //纠纷总货款
    public List<OrderTable> getAllHuoKuan(String orderId,String time,String time1,Long selcaigouyuan,Long leimus){
     
    	List<OrderTable> stu = null;
        try
        {
            if((time != null || !"".equals(time)) && time1 != null && !"".equals(time1) && (orderId == null || "".equals(orderId)) && (selcaigouyuan==0 || "".equals(selcaigouyuan))&&(leimus==null || "".equals(leimus))){
            	
                stu = ht.find("select round(sum(huokuan),3) from OrderTable where fenpei = 1 and sjc is null and wancheng = 1 and jiufen = 1 and (convert(varchar(10),jiufentime,120) between '"+time+"'and '"+time1+"')");
            }
            else
                if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId))&&(leimus==null || "".equals(leimus)) && (selcaigouyuan==null || selcaigouyuan == 0l)){
                    stu = ht.find("select round(sum(huokuan),3) from OrderTable  where leimuid="+leimus+" and fenpei = 1 and sjc is null  and wancheng = 1 and jiufen = 1");
                }
            	else
                if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId))&&(leimus==null || "".equals(leimus)) && (selcaigouyuan==null || selcaigouyuan == 0l)){
                    stu = ht.find("select round(sum(huokuan),3) from OrderTable  where fenpei = 1 and sjc is null  and wancheng = 1 and jiufen = 1");
                }
                else if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId))&&(leimus==null || "".equals(leimus)) && (selcaigouyuan!=0)){
            	
            	stu = ht.find("select round(sum(huokuan),3) from OrderTable where fenpei = 1 and wancheng = 1 and sjc is null and jiufen = 1 and caigouyuan = "+selcaigouyuan+"");
                }
                else if((time != null && !"".equals(time)) && (time1 != null || !"".equals(time1)) && (orderId == null || "".equals(orderId))&&(leimus==null || "".equals(leimus)) && (selcaigouyuan!=0)){
                	
                	stu = ht.find("select round(sum(huokuan),3) from OrderTable where fenpei = 1 and sjc is null and wancheng = 1 and jiufen = 1 and caigouyuan = "+selcaigouyuan+" and (convert(varchar(10),jiufentime,120) between '"+time+"'and '"+time1+"')");
                 }
                else if(!"".equals(orderId) && orderId != null){
            	
                stu = ht.find("select round(sum(huokuan),3) from OrderTable where fenpei = 1 and sjc is null and wancheng = 1 and jiufen = 1 and orderId = ?", new Object[] {
                    orderId
                });
            }
           
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return stu;
    }
    //纠纷总运费
    public List<OrderTable> getAllYunFei(String orderId,String time,String time1,Long selcaigouyuan,Long leimus){
     
    	List<OrderTable> stu = null;
        try
        {
            if((time != null || !"".equals(time)) && time1 != null && !"".equals(time1) && (orderId == null || "".equals(orderId)) && (selcaigouyuan==0 || "".equals(selcaigouyuan))&&(leimus==null || "".equals(leimus))){
            	
                stu = ht.find("select round(sum(yunfei),3) from OrderTable where fenpei = 1 and sjc is null and wancheng = 1 and jiufen = 1 and (convert(varchar(10),jiufentime,120) between '"+time+"'and '"+time1+"')");
            }
            else
                if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId))&&(leimus==null || "".equals(leimus)) && (selcaigouyuan==null || selcaigouyuan == 0l)){
                    stu = ht.find("select round(sum(yunfei),3) from OrderTable  where leimuid="+leimus+" and fenpei = 1 and sjc is null and wancheng = 1 and jiufen = 1");
                }
            	else
                if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId))&&(leimus==null || "".equals(leimus)) && (selcaigouyuan==null || selcaigouyuan == 0l)){
                    stu = ht.find("select round(sum(yunfei),3) from OrderTable  where fenpei = 1 and sjc is null and wancheng = 1 and jiufen = 1");
                }
                else if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId))&&(leimus==null || "".equals(leimus)) && (selcaigouyuan!=0)){
            	
            	stu = ht.find("select round(sum(yunfei),3) from OrderTable where fenpei = 1 and sjc is null and wancheng = 1 and jiufen = 1 and caigouyuan = "+selcaigouyuan+"");
                }
                else if((time != null && !"".equals(time)) && (time1 != null || !"".equals(time1)) && (orderId == null || "".equals(orderId))&&(leimus==null || "".equals(leimus)) && (selcaigouyuan!=0)){
                	
                	stu = ht.find("select round(sum(yunfei),3) from OrderTable where fenpei = 1 and sjc is null and wancheng = 1 and jiufen = 1 and caigouyuan = "+selcaigouyuan+" and (convert(varchar(10),jiufentime,120) between '"+time+"'and '"+time1+"')");
                 }
                else if(!"".equals(orderId) && orderId != null){
            	
                stu = ht.find("select round(sum(yunfei),3) from OrderTable where fenpei = 1 and sjc is null and wancheng = 1 and jiufen = 1 and orderId = ?", new Object[] {
                    orderId
                });
            }
           
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return stu;
    }
    //查看全部订单
    public String getYeAllOrder(String orderId,Long userid){
    	String stu = null;
    	if(orderId != null && !"".equals(orderId)){
    		stu = "from OrderTable where (fenpei !=3 or fenpei is null) and orderId = '"+orderId+"' and zhanghaoId in(select id from ZhangHao where yewuId = "+userid+")";
    	}else{
    		stu = "from OrderTable where (fenpei !=3 or fenpei is null) and zhanghaoId in(select id from ZhangHao where yewuId = "+userid+")";
    	}
    	return stu;
    }
    //查询全部订单
    public List<OrderTable> getAllOrder(){
    	return ht.find("from OrderTable where (danhao is not null and danhao !='') and yjsc is null");
    }
    //查询当月时间
    public List<OrderTable> getMonths(){
    	return ht.find("from OrderTable WHERE datediff(month,time,getdate())=0 and sjc is null");
    }
    //未入账和未签收查询
    public List<OrderTable> getWrzWqs(){
    	return ht.find("from OrderTable where (danhao is not null and danhao != '') and sjc is null and (ruzhang = 0 or ruzhang is null) and (qianshou = 0 or qianshou is null) and (yjcx is null or yjcx = 0)");
    }
    //查询全部订单
    public List<OrderTable> getQbdd(){
    	return ht.find("from OrderTable where yjcx=1 and sjc is null");
    }
    //订单号查询全部
    public Order_Detail getOrderIdAll(String orderId){
    	return ht.findFirst("from Order_Detail where orderId = ?",new Object[]{orderId});
    }
    //查询全部类目
    public List<Order_Detail> getAllCgs(){
    	return ht.find("from Order_Detail");
    }
  //订单好查询全部
    public List<OrderTable> getOrder(String orderId){
    	
    	return ht.find("from OrderTable  where (wancheng = 0 or wancheng is null) and daochu = 2 and orderId ='"+orderId+"'");
    }
    
    //账号编号查询全部
    public DhgateAccounts getDhgateId(Long dhgateid){
    	return ht.findFirst("from DhgateAccounts where id = ?", new Object[]{dhgateid});
    }
    //财务付款订单
  
    public String getPayment(){
    	String hql = "from OrderTable where daifahuo = 3 order by gongyunshang";
    	return hql;
    }
  //采购员查询纠纷订单
    public String getDisputeOrder(Long userId,String orderId,String time,String time1,Long leimus){
    	String stu = null;
        if((time != null || !"".equals(time)) && time1 != null && !"".equals(time1) && (orderId == null || "".equals(orderId))&&(leimus==null || "".equals(leimus))){
            stu = "from OrderTable where wancheng = 1 and jiufen = 1 and caigouyuan="+userId+"  and (convert(varchar(10),jiufentime,120) between '"+time+"'and '"+time1+"')";
        }
        if(!"".equals(orderId) && orderId != null){
            stu = "from OrderTable where wancheng = 1 and jiufen = 1 and caigouyuan="+userId+"  and orderId = "+orderId+"";
        }
        else
        if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId))&&(leimus != null && !"".equals(leimus))){ 
            stu = "from OrderTable where leimuid="+leimus+" and wancheng = 1 and jiufen = 1 and caigouyuan="+userId+"";
        }
        else
        if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId))&&(leimus == null || "".equals(leimus))){
            stu = "from OrderTable where  wancheng = 1 and jiufen = 1   and caigouyuan="+userId+"";
        }
        return stu;
    }
    //查询货款为空
    public String PayEmpty(Long userid,String orderId,String time,String time1){
    	String stu = null;
        try
        {
            if((time != null || !"".equals(time)) && time1 != null && !"".equals(time1) && (orderId == null || "".equals(orderId)))
                stu ="from OrderTable where (fenpei != 3 or fenpei is null) and sjc is null and (huokuan is null or huokuan = '' and huokuan != 0) and caigouyuan = "+userid+"   and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"') order by zhanghaoId desc";
            if(!"".equals(orderId) && orderId != null)
                stu = "from OrderTable where (fenpei != 3 or fenpei is null) and sjc is null and (huokuan is null or huokuan = '' and huokuan != 0) and caigouyuan = "+userid+"  and orderId = "+orderId+" order by zhanghaoId desc";
            else
            if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (orderId == null || "".equals(orderId)))
                stu = "from OrderTable where (fenpei != 3 or fenpei is null) and sjc is null and (huokuan is null or huokuan = '' and huokuan != 0) and caigouyuan = "+userid+"  order by zhanghaoId desc";
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return stu;
    }
    //国内单号为空
    public String SingleNumberEmpty(String guoneidanhao,String orderId,String wuping){
    	String stu = null;
        if((guoneidanhao != null || !"".equals(guoneidanhao)) && (orderId == null || "".equals(orderId)) && ("".equals(wuping) || wuping == null))
            stu = "from OrderTable where  (guoneidanhao is null or guoneidanhao = '') and daifahuo = 1 and guoneidanhao like '%"+guoneidanhao+"%' order by caigoutime,gongyunshang";
        if(("".equals(wuping) || wuping == null) && (guoneidanhao == null || "".equals(guoneidanhao)) && (orderId != null && !"".equals(orderId)))
            stu =  "from OrderTable where  (guoneidanhao is null or guoneidanhao = '') and daifahuo = 1 and orderId = '"+orderId+"' order by caigoutime,gongyunshang";
        if(("".equals(orderId) || orderId == null) && (guoneidanhao == null || "".equals(guoneidanhao)) && (wuping != null && !"".equals(wuping))){
        	stu =  "from OrderTable where (guoneidanhao is null or guoneidanhao = '') and daifahuo = 1 and wuping like '%"+wuping+"%' order by caigoutime,gongyunshang";
        }
        else if((guoneidanhao == null || "".equals(guoneidanhao)) && (orderId == null || "".equals(orderId)))
            stu =  "from OrderTable where (guoneidanhao is null or guoneidanhao = '') and daifahuo = 1 order by caigoutime,gongyunshang";
        return stu;
    }
    //仓库查询单号不为空
    public String getSingleNumberNotEmpty(String guoneidanhao,String orderId,String wuping){
    	String stu = null;
        if((guoneidanhao != null || !"".equals(guoneidanhao)) && (orderId == null || "".equals(orderId)) && ("".equals(wuping) || wuping == null))
            stu = "from OrderTable where (guoneidanhao is not null and guoneidanhao != '') and daifahuo = 1  and guoneidanhao like '%"+guoneidanhao+"%' order by fenpei,caigoutime";
        if(("".equals(wuping) || wuping == null) && (guoneidanhao == null || "".equals(guoneidanhao)) && (orderId != null && !"".equals(orderId)))
            stu = "from OrderTable where (guoneidanhao is not null and guoneidanhao != '') and daifahuo = 1  and orderId = '"+orderId+"' order by fenpei,caigoutime";
        if(("".equals(orderId) || orderId == null) && (guoneidanhao == null || "".equals(guoneidanhao)) && (wuping != null && !"".equals(wuping))){
        	stu = "from OrderTable where (guoneidanhao is not null and guoneidanhao != '') and daifahuo = 1  and wuping like '%"+wuping+"%' order by fenpei,caigoutime";
        }
        else if((guoneidanhao == null || "".equals(guoneidanhao)) && (orderId == null || "".equals(orderId)))
            stu = "from OrderTable where (guoneidanhao is not null and guoneidanhao != '') and daifahuo = 1 order by fenpei,caigoutime";
        return stu;
    }
    //仓库查询单号不为空
    public String getDaiFangQuOrder(String guoneidanhao,String orderId,String time,String time1){
    	String stu = null;
        if((orderId == null || "".equals(orderId)) && !"".equals(guoneidanhao) && guoneidanhao != null){
            stu = "from OrderTable  where (daochu = 0 or daochu = 1 or daochu is null) and (wancheng = 0 or wancheng is null)  and daifahuo = 2 and guoneidanhao ='"+guoneidanhao+"' order by guoneikuaidiId desc,guoneidanhao";
                
        }
        if(orderId != null && !"".equals(orderId)){
            stu = "from OrderTable  where (daochu = 0 or daochu = 1 or daochu is null) and (wancheng = 0 or wancheng is null) and daifahuo = 2  and orderId ='"+orderId+"'  order by guoneikuaidiId desc,guoneidanhao";
        }
        if((time != null && !"".equals(time))&&(time1!=null && !"".equals(time1))){
        	
        	 stu = "from OrderTable  where (convert(varchar(10),caigoutime,120) between '"+time+"'and '"+time1+"') and (daochu = 0 or daochu = 1 or daochu is null) and (wancheng = 0 or wancheng is null) and daifahuo = 2";
        }
        else
        if((guoneidanhao == null || "".equals(guoneidanhao)) && (orderId == null || "".equals(orderId)) && (time == null || "".equals(time))&&(time1==null || "".equals(time1))){
            stu = "from OrderTable  where (daochu = 0 or daochu = 1 or daochu is null) and (wancheng = 0 or wancheng is null) and daifahuo = 2";
        }
        return stu;
    }
    //财务查看带录单
    public String getSingle(String guoneidanhao,String orderId){
    	String stu = null;
        if((orderId == null || "".equals(orderId)) && !"".equals(guoneidanhao) && guoneidanhao != null)
            stu = "from OrderTable  where (wancheng = 0 or wancheng is null) and daochu = 2 and guoneidanhao ='"+guoneidanhao+"' order by fenpei,guoneikuaidiId desc";
               
        if(orderId != null && !"".equals(orderId))
            stu = "from OrderTable  where (wancheng = 0 or wancheng is null) and daochu = 2 and orderId ='"+orderId+"'  order by fenpei,guoneikuaidiId desc";
               
        else
        if((guoneidanhao == null || "".equals(guoneidanhao)) && (orderId == null || "".equals(orderId)))
            stu = "from OrderTable  where (wancheng = 0 or wancheng is null) and daochu = 2 order by fenpei,guoneikuaidiId desc";
        return stu;
    }
    //查看全部完成订单
    public String getComplete(String orderId,String guoneidanhao){
    	String stu = null;
        if((orderId == null || "".equals(orderId)) && !"".equals(guoneidanhao) && guoneidanhao != null)
        	   stu = "from OrderTable where fenpei != 3  and sjc is null and wancheng = 1 and guoneidanhao = '"+guoneidanhao+"'";
       if(!"".equals(orderId) && orderId != null)
           stu = "from OrderTable where fenpei != 3  and sjc is null and wancheng = 1 and orderId = '"+orderId+"'";
        else
        if((guoneidanhao == null || "".equals(guoneidanhao)) && (orderId == null || "".equals(orderId)))
            stu = "from OrderTable where fenpei != 3  and sjc is null and wancheng = 1";
        return stu;
    }
    //查看代发订单
    public String getOnOrder(String orderid,Long userid){
    	String stu = null;
    	if(orderid != null && !"".equals(orderid)){
    		stu = "from OrderTable where caigouyuan = "+userid+" and daifahuo = 4 and orderId = '"+orderid+"'";
    	}else{
    		stu = "from OrderTable where daifahuo = 4 and caigouyuan = "+userid+"";
    	}
    	return stu;
    }
    //查询纠纷订单
    public String getOnDispute(String orderid,Long userid,Long chuli,String time,String time1,Long zhanghaoId){
    	String stu = null;
    	if(orderid != null && !"".equals(orderid) && (chuli == null || "".equals(chuli)) && (time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (zhanghaoId==null || "".equals(zhanghaoId))){
    		stu = "from OrderTable where jiufen = 1 and orderId = '"+orderid+"' and zhanghaoId in(select id from ZhangHao where yewuId = "+userid+")";
    	}
   
    	else if((orderid == null || "".equals(orderid)) && (chuli == null || "".equals(chuli)) && (time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (zhanghaoId==null || "".equals(zhanghaoId))){
    	
    		stu = "from OrderTable where jiufen = 1 and sjc is null and zhanghaoId in(select id from ZhangHao where yewuId = "+userid+")";
    	}
    	else if((orderid == null || "".equals(orderid))  && (chuli == null || "".equals(chuli)) && (time != null && !"".equals(time)) && (time1 != null && !"".equals(time1)) && (zhanghaoId==null || "".equals(zhanghaoId))){
    		stu = "from OrderTable where (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"') and jiufen = 1  and zhanghaoId in(select id from ZhangHao where yewuId = "+userid+")";
    	}
    	else if((orderid == null || "".equals(orderid))  && (chuli == null || "".equals(chuli)) && (time != null && !"".equals(time)) && (time1 != null && !"".equals(time1)) && (zhanghaoId!=null && !"".equals(zhanghaoId))){
    		stu = "from OrderTable where zhanghaoId = "+zhanghaoId+" and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"') and jiufen = 1  and zhanghaoId in(select id from ZhangHao where yewuId = "+userid+")";
    	}
    	else if((orderid == null || "".equals(orderid))  && (chuli == null || "".equals(chuli)) && (time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (zhanghaoId!=null && !"".equals(zhanghaoId))){
    		
    		stu = "from OrderTable where zhanghaoId = "+zhanghaoId+"  and jiufen = 1  and zhanghaoId in(select id from ZhangHao where yewuId = "+userid+")";
    	}
    	else if((orderid == null || "".equals(orderid)) && (chuli == 0l) && (time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (zhanghaoId==null || "".equals(zhanghaoId))){
    	
    		stu = "from OrderTable where jiufen = 1 and sjc is null and (chuli = 0 or chuli is null) and zhanghaoId in(select id from ZhangHao where yewuId = "+userid+")";
    	}
    	else if((orderid == null || "".equals(orderid)) && (chuli == 1l) && (time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (zhanghaoId==null || "".equals(zhanghaoId))){
    	
    		stu = "from OrderTable where  jiufen = 1 and sjc is null and chuli = 1 and zhanghaoId in(select id from ZhangHao where yewuId = "+userid+")";
    	}
    
    	else if((orderid == null || "".equals(orderid))  && (chuli == null || "".equals(chuli)) && (time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (zhanghaoId==null || "".equals(zhanghaoId))){
    		stu = "from OrderTable where jiufen = 1  and zhanghaoId in(select id from ZhangHao where yewuId = "+userid+")";
    	}
    	return stu;
    }
    //查询纠纷订单
    public String getStock(String orderid){
    	String stu = null;
    	if(orderid != null && !"".equals(orderid)){
    		stu = "from OrderTable where getordersId = 2 and orderId = '"+orderid+"'";
    	}else{
    		stu = "from OrderTable where getordersId = 2";
    	}
    	return stu;
    }
    //订单号查询全部
    public List<OrderTable> getOrderNumber(String orderId){
    	return ht.find("from OrderTable where tuihuo = 2 and orderId = '"+orderId+"'");
    }
    //查看退货订单
    public String getReturnOfGoods(String danhao){
    	String stu = null;
    
    	if(danhao != null && !"".equals(danhao)){
    		stu = "from OrderTable where tuihuo = 2 and danhao like '%"+danhao+"%'";
    	}else if(danhao == null || "".equals(danhao)){
    
    		stu = "from OrderTable where tuihuo = 2";
    	}
    	return stu;
    }
  
    //美工得到订单
    public String getorders(String orderId,Long userid)
    {
    	
    	String stu = null;
    	if (orderId != null && !"".equals(orderId)) {
			stu = "from OrderTable where (ArtProcessing = 0 or ArtProcessing is null) and ArtistsGetOrders = 1 and orderId = '"+orderId+"' and zhanghaoId in(select id from ZhangHao where ClipArtId = "+userid+")";
		}else if(orderId == null || "".equals(orderId)){
			stu = "from OrderTable where (ArtProcessing = 0 or ArtProcessing is null) and ArtistsGetOrders = 1 and zhanghaoId in(select id from ZhangHao where ClipArtId = "+userid+")";
		}
    	return stu;
    }
    //处理完毕
    public String getDisposed(String orderId,Long userid){
    	String stu = null;
    	if (orderId != null && !"".equals(orderId)) {
			stu = "from OrderTable where ArtProcessing = 1 and ArtistsGetOrders = 1 and orderId = '"+orderId+"' and zhanghaoId in(select id from ZhangHao where ClipArtId = "+userid+")";
		}else{
			stu = "from OrderTable where ArtProcessing =1  and ArtistsGetOrders = 1 and zhanghaoId in(select id from ZhangHao where ClipArtId = "+userid+")";
		}
    	return stu;
    }
    //编码查询
    public OrderTable getCoding(String bianma){
    	return ht.findFirst("from OrderTable where wancheng = 1 and bianma = ?",new Object[]{bianma});
    }
    //订单号和运输单号查询全部
    public List<OrderTable> getOddNumber(String orderId,String danhao){
    	return ht.find("from OrderTable where orderId = '"+orderId+"' and danhao = '"+danhao+"'");
    }
    //导入单号为空
    public List<OrderTable> getOrderNull(String orderId){
    	return ht.find("from OrderTable where  (guoneidanhao is null or guoneidanhao = '') and daifahuo = 1 and orderId = '"+orderId+"'");
    }
    //得到不能处理的订单
    public String getCanNotHandle(String orderId,Long userid){
    	String stu = null;
    	if (orderId != null && !"".equals(orderId)){
			stu = "from OrderTable where ArtProcessing = 2 and orderId = '"+orderId+"' and zhanghaoId in(select id from ZhangHao where ClipArtId = "+userid+")";
		}else{
			stu = "from OrderTable where ArtProcessing = 2 and zhanghaoId in(select id from ZhangHao where ClipArtId = "+userid+")";
		}
    	return stu;
    }
}
