/*jadclipse*/// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) radix(10) lradix(10) 
// Source File Name:   OrderTable.java

package com.demo.entity.order;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import oracle.sql.CHAR;
@Entity
@Table(name="orderinfo")
public class OrderTable implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Long id;//编号
    private java.util.Date time;//上传时间

	private String orderId;//订单号 
    private Double money;//买家付款金额
    private String yunshu;//类目
    private String danhao;//国际运输单号
    private Double yunfei;//运费
    private Double huokuan;//采购金额
    private String guojia;//速卖通地址
    private Long ArtistsGetOrders;//美工得到订单 
    private String remark;//备注
    private java.util.Date caigoutime;//采购时间
    private Long guoneiwangzhanId;//国内网站编号
    private String wuping;//物品
    private Long daifahuo;//待发货 0,未发货,1待发货2.待放区,3.点击到财务付款,4.代发订单
    private String guowaidizhi;//国外地址
    private String guoneidanhao;//国内运输单号
    private Long sumaitong;//速卖通 0.未录单,1.已入单
    private Long denghuixin;
    private Long wancheng;//0.未完成，1.已经完成
    private Long jiufen;//0.未纠纷,1.已经纠纷
    private Long xiugai;//0.未修改,1.已经修改
    private Long fenpei;//0.未分配,1.已经分配,2.问题订单,3客户上传的订单
    private Long shangwang;//0.未上网,1.已经上网
    private Long qianshou;//0.未签收,1.已经签收
    private Long ruzhang;//0.未入账,1.已经入账
    private Long kuaidifangshiId;//国际快递方式
    private Long caigouyuan;//采购员编号
    private String gongyunshang;//供运商信息
    private Double tuikuan;//退款
    private Double huilv;//纠纷
    private Long dengluId;//业务编号
    private Long zhanghaoId;//敦煌账号编号
    private Long guoneikuaidiId;//国内快递编号
    private Long tuihuo;//是否退货0.否 1.是,2.返回到仓库退件菜单 
    private String name;//顾客姓名
    private Long daochu;//0.未导出 1.导出 2.传给业务,3返回给仓库
    private Long jingji;//紧急订单0.不紧急,1.紧急
    private Long kehuId;//客户编号
    private Date wanchengtime;//完成时间
    private Long kucun;//是否是库存,0.不是库存，1.是库存
    private Double zhongliang;//重量
    private String dizhi;//地址
    private String bianma;//编码 
    private Long yunfeidaochu;//0.未导出 1.已经导出
    private Long guojiaId;//国家 
    private Date jiufentime;//纠纷时间
    private Long leimuid;//类目编号
    private Long ArtistsGetOrdersId;//美工编号
    private Long fawan;//是否发完  0.没发完 1.发完,2.问题订单分配
    private Long num;//数量
    private Long chuli;//是否处理 0.否 1.是
    private java.util.Date ruzhangtime;//入账时间
    private String timecha;//时间差
    private Long yjsc;//已经上传的订单
    private Long yjcx;//已经查询
    private Long sjc;//时间戳
    private String dggjdh;//多个国际单号
    private String dcsj;//订单导出时间
    private Long cgjf;//采购纠纷是否导出
    private Long order_type;//类型
    private String ordersn;
    private String address1;//地址
    private String city;
    private String province;
    private String postcode;
    private String telephone;
    private String receiver;
    private Long paydate;
    private String shippingtype; 
    private String country;
    private Long getordersId;//1.得到订单0.没有得到订单,2库存 
    private Long ArtProcessing;//美工是否处理
    private java.util.Date ReturnTime;//退货时间
    
    public java.util.Date getReturnTime() {
		return ReturnTime;
	}

	public void setReturnTime(java.util.Date returnTime) {
		ReturnTime = returnTime;
	}

	public Long getArtistsGetOrdersId() {
		return ArtistsGetOrdersId;
	}

	public void setArtistsGetOrdersId(Long artistsGetOrdersId) {
		ArtistsGetOrdersId = artistsGetOrdersId;
	}

	public Long getArtistsGetOrders() {
		return ArtistsGetOrders;
	}

	public void setArtistsGetOrders(Long artistsGetOrders) {
		ArtistsGetOrders = artistsGetOrders;
	}

	public Long getArtProcessing() {
		return ArtProcessing;
	}

	public void setArtProcessing(Long artProcessing) {
		ArtProcessing = artProcessing;
	}

	public java.util.Date getTime() {
		return time;
	}

	public void setTime(java.util.Date time) {
		this.time = time;
	}
	public Long getGetordersId() {
		return getordersId;
	}

	public void setGetordersId(Long getordersId) {
		this.getordersId = getordersId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	@Id
    @GeneratedValue
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }
	public Long getOrder_type() {
		return order_type;
	}

	public void setOrder_type(Long orderType) {
		order_type = orderType;
	}

	public String getOrdersn() {
		return ordersn;
	}

	public void setOrdersn(String ordersn) {
		this.ordersn = ordersn;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public Long getPaydate() {
		return paydate;
	}

	public void setPaydate(Long paydate) {
		this.paydate = paydate;
	}

	public String getShippingtype() {
		return shippingtype;
	}

	public void setShippingtype(String shippingtype) {
		this.shippingtype = shippingtype;
	}

	public Long getCgjf() {
		return cgjf;
	}

	public void setCgjf(Long cgjf) {
		this.cgjf = cgjf;
	}

	public String getDcsj() {
		return dcsj;
	}

	public void setDcsj(String dcsj) {
		this.dcsj = dcsj;
	}

	public String getDggjdh() {
		return dggjdh;
	}

	public void setDggjdh(String dggjdh) {
		this.dggjdh = dggjdh;
	}

	public Long getSjc() {
		return sjc;
	}

	public void setSjc(Long sjc) {
		this.sjc = sjc;
	}

	public Long getYjcx() {
		return yjcx;
	}

	public void setYjcx(Long yjcx) {
		this.yjcx = yjcx;
	}

	public Long getYjsc() {
		return yjsc;
	}

	public void setYjsc(Long yjsc) {
		this.yjsc = yjsc;
	}

	public String getTimecha() {
		return timecha;
	}

	public void setTimecha(String timecha) {
		this.timecha = timecha;
	}

	public java.util.Date getRuzhangtime() {
		return ruzhangtime;
	}

	public void setRuzhangtime(java.util.Date ruzhangtime) {
		this.ruzhangtime = ruzhangtime;
	}

	public Long getChuli() {
		return chuli;
	}

	public void setChuli(Long chuli) {
		this.chuli = chuli;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}
	public Long getFawan() {
		return fawan;
	}

	public void setFawan(Long fawan) {
		this.fawan = fawan;
	}

	public Long getLeimuid() {
		return leimuid;
	}

	public void setLeimuid(Long leimuid) {
		this.leimuid = leimuid;
	}

	public Date getJiufentime() {
		return jiufentime;
	}

	public void setJiufentime(Date jiufentime) {
		this.jiufentime = jiufentime;
	}

	public Long getGuojiaId() {
		return guojiaId;
	}

	public void setGuojiaId(Long guojiaId) {
		this.guojiaId = guojiaId;
	}

	public Long getYunfeidaochu() {
		return yunfeidaochu;
	}

	public void setYunfeidaochu(Long yunfeidaochu) {
		this.yunfeidaochu = yunfeidaochu;
	}

	public String getBianma() {
		return bianma;
	}

	public void setBianma(String bianma) {
		this.bianma = bianma;
	}

	public String getDizhi() {
		return dizhi;
	}

	public void setDizhi(String dizhi) {
		this.dizhi = dizhi;
	}

	public Double getZhongliang() {
		return zhongliang;
	}

	public void setZhongliang(Double zhongliang) {
		this.zhongliang = zhongliang;
	}

	public Long getKucun() {
		return kucun;
	}

	public void setKucun(Long kucun) {
		this.kucun = kucun;
	}

	public Date getWanchengtime() {
		return wanchengtime;
	}

	public void setWanchengtime(Date wanchengtime) {
		this.wanchengtime = wanchengtime;
	}

	public Long getKehuId() {
		return kehuId;
	}

	public void setKehuId(Long kehuId) {
		this.kehuId = kehuId;
	}

	public Long getJingji() {
		return jingji;
	}

	public void setJingji(Long jingji) {
		this.jingji = jingji;
	}


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Long getGuoneikuaidiId()
    {
        return guoneikuaidiId;
    }

    public void setGuoneikuaidiId(Long guoneikuaidiId)
    {
        this.guoneikuaidiId = guoneikuaidiId;
    }

    public Long getKuaidifangshiId()
    {
        return kuaidifangshiId;
    }

    public void setKuaidifangshiId(Long kuaidifangshiId)
    {
        this.kuaidifangshiId = kuaidifangshiId;
    }
  
    public java.util.Date getCaigoutime() {
		return caigoutime;
	}

	public void setCaigoutime(java.util.Date caigoutime) {
		this.caigoutime = caigoutime;
	}

	public Long getGuoneiwangzhanId()
    {
        return guoneiwangzhanId;
    }

    public void setGuoneiwangzhanId(Long guoneiwangzhanId)
    {
        this.guoneiwangzhanId = guoneiwangzhanId;
    }

    public String getWuping()
    {
        return wuping;
    }

    public void setWuping(String wuping)
    {
        this.wuping = wuping;
    }

    public String getGuowaidizhi()
    {
        return guowaidizhi;
    }

    public void setGuowaidizhi(String guowaidizhi)
    {
        this.guowaidizhi = guowaidizhi;
    }

    public String getGuoneidanhao()
    {
        return guoneidanhao;
    }

    public void setGuoneidanhao(String guoneidanhao)
    {
        this.guoneidanhao = guoneidanhao;
    }
    public Long getDengluId()
    {
        return dengluId;
    }

    public void setDengluId(Long dengluId)
    {
        this.dengluId = dengluId;
    }

 
    public Long getZhanghaoId() {
		return zhanghaoId;
	}

	public void setZhanghaoId(Long zhanghaoId) {
		this.zhanghaoId = zhanghaoId;
	}
    public String getOrderId()
    {
        return orderId;
    }

    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }

    public String getYunshu()
    {
        return yunshu;
    }

    public void setYunshu(String yunshu)
    {
        this.yunshu = yunshu;
    }

    public String getDanhao()
    {
        return danhao;
    }

    public void setDanhao(String danhao)
    {
        this.danhao = danhao;
    }

    public Double getYunfei()
    {
        return yunfei;
    }

    public void setYunfei(Double yunfei)
    {
        this.yunfei = yunfei;
    }

    public Double getMoney()
    {
        return money;
    }

    public void setMoney(Double money)
    {
        this.money = money;
    }

    public Double getHuokuan()
    {
        return huokuan;
    }

    public void setHuokuan(Double huokuan)
    {
        this.huokuan = huokuan;
    }

    public String getGuojia()
    {
        return guojia;
    }

    public void setGuojia(String guojia)
    {
        this.guojia = guojia;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public Long getDaifahuo() {
		return daifahuo;
	}

	public void setDaifahuo(Long daifahuo) {
		this.daifahuo = daifahuo;
	}

	public Long getSumaitong() {
		return sumaitong;
	}

	public void setSumaitong(Long sumaitong) {
		this.sumaitong = sumaitong;
	}

	public Long getDenghuixin() {
		return denghuixin;
	}

	public void setDenghuixin(Long denghuixin) {
		this.denghuixin = denghuixin;
	}

	public Long getWancheng() {
		return wancheng;
	}

	public void setWancheng(Long wancheng) {
		this.wancheng = wancheng;
	}

	public Long getJiufen() {
		return jiufen;
	}

	public void setJiufen(Long jiufen) {
		this.jiufen = jiufen;
	}

	public Long getXiugai() {
		return xiugai;
	}

	public void setXiugai(Long xiugai) {
		this.xiugai = xiugai;
	}

	public Long getFenpei() {
		return fenpei;
	}

	public void setFenpei(Long fenpei) {
		this.fenpei = fenpei;
	}

	public Long getShangwang() {
		return shangwang;
	}

	public void setShangwang(Long shangwang) {
		this.shangwang = shangwang;
	}

	public Long getQianshou() {
		return qianshou;
	}

	public void setQianshou(Long qianshou) {
		this.qianshou = qianshou;
	}

	public Long getRuzhang() {
		return ruzhang;
	}

	public void setRuzhang(Long ruzhang) {
		this.ruzhang = ruzhang;
	}

	public Long getTuihuo() {
		return tuihuo;
	}

	public void setTuihuo(Long tuihuo) {
		this.tuihuo = tuihuo;
	}

	public Long getDaochu() {
		return daochu;
	}

	public void setDaochu(Long daochu) {
		this.daochu = daochu;
	}

	public Long getCaigouyuan()
    {
        return caigouyuan;
    }

    public void setCaigouyuan(Long caigouyuan)
    {
        this.caigouyuan = caigouyuan;
    }

    public String getGongyunshang()
    {
        return gongyunshang;
    }

    public void setGongyunshang(String gongyunshang)
    {
        this.gongyunshang = gongyunshang;
    }

    public Double getTuikuan()
    {
        return tuikuan;
    }

    public void setTuikuan(Double tuikuan)
    {
        this.tuikuan = tuikuan;
    }

    public Double getHuilv()
    {
        return huilv;
    }

    public void setHuilv(Double huilv)
    {
        this.huilv = huilv;
    }


}
