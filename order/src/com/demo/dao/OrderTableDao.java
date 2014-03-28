/*jadclipse*/// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) radix(10) lradix(10) 
// Source File Name:   OrderTableDao.java

package com.demo.dao;

import com.demo.entity.Courier.YunFeiTable;
import com.demo.entity.order.DhgateAccounts;
import com.demo.entity.order.OrderTable;
import com.demo.entity.order.Order_Detail;

import java.util.List;

// Referenced classes of package com.demo.dao:
//            BaseDao

public interface OrderTableDao extends BaseDao<OrderTable,Long>
{
	//采购查看利润小于0
    public abstract List<OrderTable> getCaiGouLiRun(Long long1, String s, String s1, String s2,String zhanghaoId);

    public abstract List<OrderTable> getCaiGouLiRunInterval(Long long1, String s, String s1, String s2);
    //采购管理员得到订单
    public abstract String getCaiGouAdminDeDaoOrder(Long long1, String s, String s1, String s2,String zhanghaoId);
    
    //紧急订单
    public abstract List<OrderTable> getXiuGaiOrder(Long long1);
    
    public abstract List<OrderTable> getCaiGouAdminDeDaoOrder1(Long long1, String s, String s1);
    //采购查看待发货
    public abstract String getDaiFaHuo(Long long1, String s, String s1, String s2,String caigoutime,String caigoutime1,String wuping,String gongyunshang);
    //单号不为空
    public abstract List<OrderTable> getChaXunDaiFaHuo(String s, String s1,String wuping);

    public abstract List<OrderTable> getChaXunDaiFaHuo1(String s, String s1);
    //仓库查看待放区
    public abstract List<OrderTable> getDaiFangQu(String s, String s1);

    public abstract List<OrderTable> getTopTen();

    public abstract List<OrderTable> getWeiKong(String s, String s1,String wuping);

    public abstract List<OrderTable> getGuoNeiDanHao(String s);
    //管理员查看速卖通订单
    public abstract String getAdminSuMaiTong(String s, String s1, String s2);
    //业务查看已经入单
    public abstract String getYeWuOrder(String s, String s1, String s2);
    //业务查看未入单产品
    public abstract String getWeiRuOrder(String s, String s1, String s2);
    //业务查看代发
    public abstract String getChaKanDaiFa(String s, String s1, String s2);

    public abstract OrderTable getSelYunShu(String s);

    public abstract String getDaiFaHuo(String s,Long userId);

    public abstract String getYiJingFaHuo(String s,Long userId);
    //管理员查看全部完成订单
    public abstract List<OrderTable> getSelWanCheng(String s, String s1);

    public abstract String getSelTwoTble();//多表查询
    
    public abstract List<OrderTable> getDaoChu(Long dc);
    //业务查看待入单
    public List<OrderTable> getDaiRuDan(String guoneidanhao,String orderId);//查询待录单
    //采购员查看纠纷详情
    public List<OrderTable> getJiuFen(Long userId,String orderId,String time,String time1,Long leimus);
    //采购员查询纠纷订单
    public String getDisputeOrder(Long userId,String orderId,String time,String time1,Long leimus);
    //查看编码是否一样
    public List<OrderTable> getBianMa(String bianma);
    //纠纷总金额
    public List<OrderTable> getJiuFenMoney(Long userId,String orderId,String time,String time1);
    //客户查询出货订单
    public String  getChuHuoOrder(Long denglu,String orderId,String guoneidanhao);
    //管理员查询全部订单
    public abstract List<OrderTable> getChaKanOrder(String orderId,String time,String time1,String dhgatezhanghao,String danhao,String sumaitong,String bianma,Long leimu);
    //管理员查看速卖通订单
    public String getSuMaiTong(String orderId,String time,String time1);
    //业务查看要修改入账的订单
    public String getRuZhang(String orderId,String time,String time1);
    //业务得到顾客全部完成订单
    public String getWanChengOrder(String orderId);
    //完成时间
    public List<OrderTable> getDaoChuYunFei(String time,String time1);
    //顾客查看退货 
    public String getTuiHuoAll(Long userid,String orderId,String danhao,String chuli);
    //用时间查询
    public List<OrderTable> getTime(String time);
    //查看全部退货
    public String getTuiHuoAll(String danhao,Long chuli,Long userid);
    //查看业务返回订单
    public String getFanHuiOrder(String guoneidanhao,String orderId);

    //查询订单表中没有的采购
    public List<OrderTable> getCaiGouNull();
    //查看库存订单
    public abstract String getKuCunOrder(Long userid,String s);
    //时间查询全部
    public List<OrderTable> getHuiLv(String time,String time1);
    //管理员查看运费为空个数
    public List<OrderTable> getYunFeiAll(Long userid,String orderId,String time,String time1);
    //货款为空个数
    public List<OrderTable> getHuoKuanAll(Long userid,String orderId,String time,String time1);
    //运费为空
    public String getYunFeiAllNum(Long userid,String orderId,String time,String time1);
    //货款为空
    public String getHuoKuanAllNum(Long userid,String orderId,String time,String time1);
    //采购查看退货
    public String getTuiHuo(Long userid,String orderId,String danhao,String chuli);
    //管理员查看纠纷个数
    public String getJiuFenNum(Long userid,String orderid,String time,String time1);
    //登录编号查询全部
    public OrderTable getDenglu(Long userid);
    //查询时间差
    public String getTime(String time,String caigoutime);
    //查看全部分配订单
    public String getAllFenPei(Long userid,String orderId);
    //查看全部分配订单
    public String getWenTiOrder(Long userid,String orderId,Long dhgatezhanghao);
   //查看全部订单
    public List<OrderTable> getAllOrder(String orderId, String time, String time1, String dhgatezhanghao,String danhao,String sumaitong,String bianma,Long leimu);
    //采购查看全部完成订单
    public List<OrderTable> getAllWanChengOrder(Long userid, String orderId, String time, String time1,String caigoutime,String caigoutime1,String bianma);
    //纠纷总金额
    public List<OrderTable> getAllMoney(String orderId,String time,String time1,Long selcaigouyuan,Long leimus,Long disputes);
    //纠纷总货款
    public List<OrderTable> getAllHuoKuan(String orderId,String time,String time1,Long selcaigouyuan,Long leimus,Long disputes);
    //纠纷总运费
    public List<OrderTable> getAllYunFei(String orderId,String time,String time1,Long selcaigouyuan,Long leimus,Long disputes);
    //查看全部订单
    public String getYeAllOrder(String orderId,Long userid);
    //查询全部订单
    public List<OrderTable> getAllOrder();
    //查询当月时间
    public List<OrderTable> getMonths();
    //未入账和未签收查询
    public List<OrderTable> getWrzWqs();
    //查询全部订单
    public List<OrderTable> getQbdd();
    //导出纠纷订单
    public List<OrderTable> getJfOrder(Long userId);
    //订单号查询全部
    public Order_Detail getOrderIdAll(String orderId);
  //查询全部类目
    public List<Order_Detail> getAllCgs();
    //订单好查询全部
    public List<OrderTable> getOrder(String orderId);
    //账号编号查询全部
    public DhgateAccounts getDhgateId(Long dhgateid);
    //财务要付款订单
    public String getPayment();
    //查询货款为空
    public String PayEmpty(Long userid,String orderId,String time,String time1);
    //国内单号为空
    public String SingleNumberEmpty(String guoneidanhao,String orderId,String wuping);
    //仓库查询单号不为空
    public String getSingleNumberNotEmpty(String guoneidanhao,String orderId,String wuping);
    //仓库查询单号不为空
    public String getDaiFangQuOrder(String guoneidanhao,String orderId,String time,String time1);
    //财务查看带录单
    public String getSingle(String guoneidanhao,String orderId);
    //查看全部完成订单
    public String getComplete(String orderId,String guoneidanhao);
    //查看代发订单
    public String getOnOrder(String orderid,Long userid);
    //查询纠纷订单
    public String getOnDispute(String orderid,Long userid,Long chuli,String time,String time1,Long zhanghaoId);
    //查询纠纷订单
    public String getStock(String orderid);
    //查看退货订单
    public String getReturnOfGoods(String danhao);
  //查看退货订单
    public String getProcessingReturns(String danhao,Long userid);
    //美工得到订单
    public String getorders(String orderId,Long userid);
    //处理完毕
    public String getDisposed(String orderId,Long userid);
    //编码查询
    public List<OrderTable> getCoding(String bianma);
    //订单号和运输单号查询全部
    public List<OrderTable> getOddNumber(String orderId,String danhao);
    //订单号查询全部
    public List<OrderTable> getOrderNumber(String orderId);
    //导入单号为空
    public List<OrderTable> getOrderNull(String orderId);
    //得到不能处理的订单
    public String getCanNotHandle(String orderId,Long userid);
    //订单号查询全部订单
    public List<OrderTable> getOrderAll(String orderId);
    //采购查看问题订单
    public String getIssuesOrders(String orderid,Long userid);
}

