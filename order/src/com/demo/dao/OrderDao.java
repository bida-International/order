
package com.demo.dao;

import com.demo.entity.Courier.YunFeiTable;
import com.demo.entity.order.OrderTable;
import com.demo.list.PageData;
import java.util.List;


public interface OrderDao extends BaseDao<OrderTable,Long>
{
	//查看全部订单
    public abstract String getAllOrder(String s, String s1, String s2, String s3,String danhao,String sumaitong,String bianma,String leimu,Long chuli);

    public abstract OrderTable getDingDao(String s);

    public abstract String getOrder(String s, String s1, String s2);
    //分配订单
    public abstract String getAllFenPei(String s);
    //管理员查看采购完成的订单
    public abstract String getCaiGouAll(Long long1, String s, String s1, String s2);
    //采购得到订单
    public abstract String getDeOrder(Long long1,String s, String s1, String s2,String zhanghaoId,Long leimu);

    public abstract List<OrderTable> getCaiGouDaoChuOrder(Long long1);
    //业务得到修改订单
    public abstract String getYeWuOrder(String s, String s1, Long s2, String s3);

    public abstract List<OrderTable> getWanChengOrder(String s);
    //管理员查看总金额
    public abstract List<OrderTable> getAllMoney(String s, String s1, String s2, String s3,String danhao,String sumaitong,String bianma,String leimu);
    //管理员查看总运费
    public abstract List<OrderTable> getAllYunFei(String s, String s1, String s2, String s3,String danhao,String sumaitong,String bianma,String leimu);
    //管理员查看总货款
    public abstract List<OrderTable> getAllHuoKuan(String s, String s1, String s2, String s3,String danhao,String sumaitong,String bianma,String leimu);
    //管理员查看总退款
    public abstract List<OrderTable> getAllTuiKuan(String s, String s1, String s2, String s3,String danhao,String sumaitong,String bianma,String leimu);
    //管理员查看纠纷个数
    public abstract List<OrderTable> getJiuFenNum(String s, String s1, String s2, String s3,String danhao,String sumaitong,String bianma,String leimu);

    public abstract List<OrderTable> getJiuFen(String s, String s1, String s2,Long selcaigouyuan,String leimus);
    //查看纠纷订单
    public abstract String getAllDispute(String s, String s1, String s2,Long selcaigouyuan,String leimus);
    //查看单号
    public abstract String getDanHao(String s, String s1, String s2);

    public abstract List<OrderTable> getAllOrderId(String s);
    //管理员查看运费为空
    public abstract List<OrderTable> getYunFeiNullNum(String s, String s1, String s2, String s3,String danhao,String sumaitong,String bianma,String leimu);
    //管理员查看货款为空
    public abstract List<OrderTable> getHuoKuanNullNum(String s, String s1, String s2, String s3,String danhao,String sumaitong,String bianma,String leimu);
    //业务查询运费为空
    public abstract String getYunFeiNull(String s, String s1, String s2);

    public abstract List<OrderTable> getHuoKuanNull(String s, String s1, String s2);
    //用编号查询全部
    public abstract List<OrderTable> getSelId(Long long1);
    //采购完成订单
    public abstract String getDeDaoOrder(Long long1, String s, String s1, String s2,String caigoutime,String caigoutime1,String bianma);

    public abstract List<OrderTable> getDengHuiXin(Long long1, String s, String s1);

    public abstract List<OrderTable> getSuMaiTong(Long long1, String s, String s1);

    public abstract List<OrderTable> getSelTiaoJiao(String s, String s1, String s2);
    //查看退货订单
    public abstract String getTuiKuanAll(String s, String danhao,Long chuli);
    //查看问题订单
    public abstract String getWenTiOrder(String s);

    public abstract List<OrderTable> getCaiGouHuoKuanNull(Long long1, String s, String s1, String s2);
    //查看采购未完成订单
    public abstract String getCaiGouWeiWanCheng(String s,Long caigouyuan);
    //采购管理员完成订单
    public abstract String getCaiGouAdminWanChengOrder(Long long1, String s, String s1, String s2,String caigoutime,String caigoutime1,String bianma,String gongyunshang,String wuping);

    public abstract List<OrderTable> getCaiGouAllMoney(Long long1, String s, String s1, String s2);

    public abstract List<OrderTable> getCaiGouAllYunFei(Long long1, String s, String s1, String s2);

    public abstract List<OrderTable> getCaiGouAllHuoKuan(Long long1, String s, String s1, String s2);

    public abstract List<OrderTable> getCaiGouAllTuiKuan(Long long1, String s, String s1, String s2);

    public abstract List<OrderTable> getCaiGouAllJiuFen(Long long1, String s, String s1, String s2);

    public abstract List<OrderTable> getCaiGouAdminAllMoney(Long long1, String s, String s1, String s2);

    public abstract List<OrderTable> getDaiFaHuoMoney(Long long1, String s, String s1, String s2);

    public abstract List<OrderTable> getCaiGouAdminAllYunFei(Long long1, String s, String s1, String s2);

    public abstract List<OrderTable> getCaiGouAdminAllHuoKuan(Long long1, String s, String s1, String s2);

    public abstract List<OrderTable> getCaiGouAdminAllTuiKuan(Long long1, String s, String s1, String s2);

    public abstract List<OrderTable> getCaiGouAdminAllJiuFen(Long long1, String s, String s1, String s2);

    public abstract List<OrderTable> getCaiGouTuiKuan(Long long1, String s, String s1, String s2);

    public abstract List<OrderTable> getCaiGouJiuFenMoney(Long long1, String s, String s1, String s2);
    //管理员查看纠纷总额
    public abstract List<OrderTable> getCaiGouAllJiuFenMoney(String s, String s1, String s2, String s3,String danhao,String sumaitong,String bianma,String leimu);

    public abstract List<OrderTable> getOldDaoRu(Long long1);

    public abstract List<OrderTable> getOldUnDaoRu(Long long1);
    //利润小于0
    public abstract List<OrderTable> getLiRun(String s, String s1, String s2,Double tuikuan
    	    ,Double huokuan,Double yunfei,Double money,Double huilv);

    public abstract List<OrderTable> getLiRunInterval(String s, String s1, String s2);

    public abstract List<OrderTable> getZongLiRun(String s, String s1, String s2, String s3,String danhao,String sumaitong,String bianma,String leimu);

    public abstract List<OrderTable> getSelAllId(Long long1);

    //用导出查询全部
    public abstract List<OrderTable> getDaoChuAll();
    //导出运费为空
    public abstract List<OrderTable> getDaoChuYunFei();
    //导出运输单号为空
    public abstract List<OrderTable> getDanHaoYunShuNull();
    //国家查询
    public abstract List<YunFeiTable> getGuoJia(String guojia);
    //本公司订单号查询
    public abstract List<OrderTable> getChaKanOrder(String orderId);
  //本公司订单号查询
    public abstract List<OrderTable> getChaKanKeHu(String orderId);
    //管理员查看亏本
    public  List<OrderTable> getChaKanZero(String orderId,String time,String time1,String dhgatezhanghao,String danhao);
    //业务导出发给客户的运费
    public List<OrderTable> getDaoChuKeHuYunFei(Long id,String time,String time1);
    //管理员 导出发给客户的运费
    public List<OrderTable> getAdminYunFei(Long id,String time);
    //总的未入账
    public List<OrderTable> getWeiRuZhang(String orderId, String time, String time1, String zhanghao,String danhao,String sumaitong,String bianma,String leimu);
    //查询全部未入账
    public String getAllWeiRuZhang(String orderId, String time, String time1, String zhanghao,String danhao);
    //单号查看全部
    public List<OrderTable> getAllDanHao(String danhao,String orderId);
    //采购筛选查询
    public String getFilter(Long caigouyuan, String orderId, String time, String time1);
}
