package com.demo.page;

public interface PageBiz
{

    public abstract PageBean selForPage(int pageSize, int page, String orderId, String time, String time1, String dhgatezhanghao,String danhao,String sumaitong,String bianma,String leimu,Long chuli);
   
    //采购管理员得到订单
    public abstract PageBean selCaiGouAdminWanChengOrder(int pageSize, int page, Long userid, String orderId, String time, String time1,String caigoutime,String caigoutime1,String bianma,String gongyunshang,String wuping);
    //查看待发货
    public abstract PageBean selCaiGouAdminDaiFaHuo(int pageSize, int page, Long userid, String orderId,String time, String time1,String caigoutime,String caigoutime1,String wuping,String gongyunshang);
    //查看问题订单
    public abstract PageBean selAllWenTiOrder(int pageSize, int page, String orderId);
   
    //采购完成订单
    public abstract PageBean selCaiGouWanChengOrder(int pageSize, int page, Long userid, String orderId, String time, String time1,String caigoutime,String caigoutime1,String bianma);
 
    //业务得到修改订单
    public abstract PageBean selYeWuDeDaoOrder(int pageSize, int page, String orderId, String gongyunshang, Long selzhanghao, String danhao);
    //速卖通已经入单
    public abstract PageBean selSuMaiTongRuDan(int pageSize, int page, Long userid, String orderId, String time, String time1);
    //业务查看未入单订单
    public abstract PageBean selSuMaiTongWeiRuDan(int pageSize, int page, String orderId, String time, String time1);
    //业务速卖通代发
    public abstract PageBean selSuMaiTongDaiFa(int pageSize, int page, String orderId, String time, String time1);

    public abstract PageBean selGuKeDaiFaHuo(int pageSize, int page,Long userid, String orderId);

    public abstract PageBean selYiJingRuDan(int pageSize, int page, String orderId,Long userId);
   
    //仓库单号为空
    public abstract PageBean selCangKuDanHaoWeiKong(int pageSize, int page, String orderId, String guoneidanhao,String wuping);
    //管理员分配订单
    public abstract PageBean selAdminFenPeiOrder(int pageSize, int page, String orderId);
    
    public abstract PageBean selAdminDeDaoOrder(int pageSize, int page, String orderId);
    //管理员修改订单
    public abstract PageBean selAdminUpOrder(int pageSize, int page,String orderId, String danhao, String xujia);
    //管理员查看全部单号
    public abstract PageBean selAdminDanHao(int pageSize, int page,String orderId,String time,String time1);
    //管理员查看退货订单
    public abstract PageBean selAdminTuiHuo(int pageSize, int page,String orderId,String danhao,Long chuli);
    //查看采购未完成订单
    public abstract PageBean selCaiGouWeiWanCheng(int pageSize, int page,String orderId,Long caigouyuan);
    //管理员查看速卖通订单
    public abstract PageBean selAdminSuMaiTongOrder(int pageSize, int page,String orderId,String time,String time1);
    //管理员查看全部员工账号
    public abstract PageBean selAdminZhangHao(int pageSize, int page,String username);
    //管理员查看敦煌账号
    public abstract PageBean selDhgate(int pageSize, int page,String username);
    //管理员查看采购全部完成订单
    public abstract PageBean selAdminCaiGouWanChengOrder(int pageSize, int page,Long caigouyuan,String orderId,String time,String time1);
    //查看运费为空
    public abstract PageBean selYeWuYunFeiNull(int pageSize, int page,String orderId,String time,String time1);
    //查看全部国家运费
    public abstract PageBean selGuoJiaYunFei(int pageSize, int page, Long quyu, String guojia);
    //客户查看出货中的订单
    public PageBean selChuHuo(int pageSize, int page, Long denglu, String orderId,String guoneidanhao);
  //管理员查看速卖通已经完成订单
    public PageBean selSuMaiTong(int pageSize, int page, String orderId,String time,String time1);
    //业务查看修改顾客入账情况 
    public PageBean selRuZhang(int pageSize, int page, String orderId,String time,String time1);
    //业务得到顾客全部完成订单
    public PageBean selWanChengOrder(int pageSize, int page, String orderId);
    //采购管理员得到订单
    public PageBean selCaiGouDeDaoOrder(int pageSize, int page, Long userid, String orderId,String time,String time1,String zhanghaoId);
    //采购得到订单
    public PageBean selDeDaoOrder(int pageSize, int page, Long userid, String orderId,String time,String time1,String zhanghaoId,Long leimu);
  //顾客得到退货订单
    public PageBean selTuiHuo(int pageSize, int page, Long userid, String orderId,String danhao,String chuli);
    //顾客得到退货订单
    public PageBean selChaKanAll(int pageSize, int page, String danhao,Long chuli,Long userid);
  //得到业务返回订单
    public PageBean selYeWuFanHui(int pageSize, int page, String danhao,String orderId);
    //全部未入账
    public PageBean selAllWeiRuZhang(int pageSize, int page, String orderId, String time, String time1, String dhgatezhanghao,String danhao);
    //查看信
    public PageBean selAllKanXin(int pageSize, int page, Long userid);
  //查看未读信
    public PageBean selAllWeiDu(int pageSize, int page, Long userid);
    //查看库存订单
    public PageBean selKuCunOrder(int pageSize, int page,Long userid,String orderId);
    //运费为空
    public PageBean selYunFeiNull(int pageSize, int page,Long userid,String orderId,String time,String time1);
    //货款为空 
    public PageBean selHuoKuanNull(int pageSize, int page,Long userid,String orderId,String time,String time1);
  //采购查看退货
    public PageBean selKanTuiHuo(int pageSize, int page,Long userid,String orderId,String danhao,String chuli);
  //纠纷订单
    public PageBean selChaKanJiuFenOrder(int pageSize, int page,Long userid,String orderId,String time,String time1);
    //查询全部客户
    public PageBean selGuKeName(int pageSize, int page,String name);
    //查询金额详情
    public PageBean selYcDetail(int pageSize, int page,String time,String time1);
    //查看
    public PageBean selXqYuCun(int pageSize, int page,Long userid);
    //查看分配 
    public PageBean selAllFenPei(int pageSize, int page,Long userid,String orderId);
    //查看问题订单
    public PageBean selWenTiOrder(int pageSize, int page,Long userid,String orderId,Long dhgatezhanghao);
    //业务查看全部订单
    public PageBean selYeAllOrder(int pageSize, int page,String orderId,Long userid);
    //发件人查询全部
    public PageBean selFjjl(int pageSize, int page,String fjr);
    //财务查看付款订单
    public PageBean selgetPayment(int pageSize, int page);
    //采购查看纠纷订单
    public PageBean selDisputeOrder(int pageSize, int page,Long userid,String orderId,String time,String time1,Long leimus);
    //查询货款为空
    public PageBean selPayEmpty(int pageSize, int page,Long userid,String orderId,String time,String time1);
    //仓库得到国内运输单号为空
    public PageBean selSingleNumberEmpty(int pageSize, int page,String guoneidanhao,String orderId,String wuping);
    //仓库得到国内运输单号不为空
    public PageBean selSingleNumberNotEmpty(int pageSize, int page,String guoneidanhao,String orderId,String wuping);
    //仓库待放区订单
    public PageBean selDaiFangQuOrder(int pageSize, int page,String guoneidanhao,String orderId,String time,String time1);
    //财务带入单
    public PageBean selDaiRudan(int pageSize, int page,String guoneidanhao,String orderId);
    //查询全部类目
    public PageBean selAllCgs(int pageSize, int page);
    //查询全部类目
    public PageBean selComplete(int pageSize, int page,String orderId,String guoneidanhao);
    //查看代发产品
    public PageBean selOnbehalf(int pageSize, int page,String orderId,Long userid);
  //查看全部纠纷订单
    public PageBean selDispute(int pageSize, int page,String orderId,Long userid,Long chuli,String time,String time1,Long zhanghaoId);
    //查看全部纠纷订单
    public PageBean selFilter(int pageSize, int page,Long caigouyuan, String orderId, String time, String time1);
    //查看库存
    public PageBean selStock(int pageSize, int page,String orderId);
    //查看退货订单
    public PageBean selReturnOfGoods(int pageSize, int page,String danhao);
    //查看退货订单
    public PageBean selProcessingReturns(int pageSize, int page,String danhao,Long userid);
  //查看是否处理
    public PageBean selDisputes(int pageSize, int page,String orderId,String time,String time1,Long selcaigouyuan,String leimus);
    //美工得到订单
    public PageBean selArtistsGetOrders(int pageSize, int page,String orderId,Long userid);
    //美工得到处理完毕订单
    public PageBean selDisposed(int pageSize,int page,String orderId,Long userid);
    //得到未完成
    public PageBean selUnfinish(int pageSize,int page,String time,String time1);
    //审核文档
    public PageBean selAudit(int pageSize,int page,String time,String time1);
    //完成文档
    public PageBean selCompletes(int pageSize,int page,String time,String time1);
    //不能处理订单
    public PageBean selCanNotHandle(int pageSize,int page,String OrderId,Long userid);
}
