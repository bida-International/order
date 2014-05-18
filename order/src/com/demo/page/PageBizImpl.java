
package com.demo.page;

import java.util.List;

import javax.annotation.Resource;

import oracle.net.aso.f;

import org.springframework.stereotype.Service;

import com.demo.action.msg.FenpeiConfigAction;
import com.demo.dao.DocumentDao;
import com.demo.dao.GuoJiaDao;
import com.demo.dao.IncomeDao;
import com.demo.dao.KuCunDao;
import com.demo.dao.LeiMuDao;
import com.demo.dao.OrderDao;
import com.demo.dao.OrderTableDao;
import com.demo.dao.Order_DetailDao;
import com.demo.dao.SpendingDao;
import com.demo.dao.XieXinDao;
import com.demo.dao.YuCunDao;
import com.demo.dao.ZhangHaoDao;
import com.demo.dao.user.BusinessDao;
import com.demo.dao.user.CaiGouDao;
import com.demo.dao.user.GuKeDao;
import com.demo.dao.user.UserDao;
import com.demo.entity.YunCun;
import com.demo.entity.order.Order_Detail;

@Service
public class PageBizImpl extends PageBean implements PageBiz
{
	private static final long serialVersionUID = 1L;
	@Resource
    private OrderDao orderDao;
	@Resource
    private UserDao userDao;
	@Resource
    private ZhangHaoDao zhangHaoDao;
	@Resource
    private GuoJiaDao guoJiaDao;
	@Resource
    private OrderTableDao orderTableDao;
	@Resource
    private XieXinDao xieXinDao;
	@Resource
    private GuKeDao guKeDao;
	@Resource
    private YuCunDao yuCunDao;
	@Resource
    private Order_DetailDao order_DetailDao;
	@Resource
    private LeiMuDao leiMuDao;
	@Resource
    private SpendingDao spendingdao;
	@Resource
    private KuCunDao kuCunDao;
	@Resource
    private DocumentDao documentDao;
	@Resource
    private CaiGouDao caigoudao;
	@Resource
    private IncomeDao incomedao;
	@Resource
    private BusinessDao businessdao;
	//����Ա�鿴ȫ������
	public PageBean selForPage(int pageSize, int page, String orderId,String time, String time1, String dhgatezhanghao, String danhao,String sumaitong, String bianma, Long leimu,String country,Double tpm,String guoneidanhao,String gongyunshang)
    {
    	 String hql = orderDao.getAllOrder(orderId, time, time1, dhgatezhanghao,danhao,sumaitong,bianma,leimu,country,tpm,guoneidanhao,gongyunshang);
         return getFenYe(hql, pageSize, page);
    }

    //�ɹ�����Ա��ɶ���
    public PageBean selCaiGouAdminWanChengOrder(int pageSize, int page, Long userid, String orderId, String time, String time1,String caigoutime,String caigoutime1,String bianma,String gongyunshang,String wuping)
    {
        String hql = orderDao.getCaiGouAdminWanChengOrder(userid, orderId, time, time1,caigoutime,caigoutime1,bianma,gongyunshang,wuping);
        return getFenYe(hql, pageSize, page);
    }
    //�ɹ�������
    public PageBean selCaiGouAdminDaiFaHuo(int pageSize, int page, Long userid, String orderId, String time, String time1,String caigoutime,String caigoutime1,String wuping,String gongyunshang)
    {
        String hql = orderTableDao.getDaiFaHuo(userid, orderId, time, time1,caigoutime,caigoutime1,wuping,gongyunshang);
        return getFenYe(hql, pageSize, page);
    }
    //�鿴���ⶩ��
    public PageBean selAllWenTiOrder(int pageSize, int page, String orderId,String time,String time1)
    {
        String hql = orderDao.getWenTiOrder(orderId,time,time1);
        return getFenYe(hql, pageSize, page);
    }
    //�ɹ���ɶ���
    public PageBean selCaiGouWanChengOrder(int pageSize, int page, Long userid, String orderId, String time, String time1,String caigoutime,String caigoutime1,String bianma)
    {
        String hql = orderDao.getDeDaoOrder(userid, orderId, time, time1,caigoutime,caigoutime1,bianma);
        return getFenYe(hql, pageSize, page);
    }

    //ҵ��õ��޸Ķ���
    public PageBean selYeWuDeDaoOrder(int pageSize, int page, String orderId, String gongyunshang, Long selzhanghao, String danhao,String time,String time1)
    {
        String hql = orderDao.getYeWuOrder(orderId, gongyunshang, selzhanghao, danhao,time,time1);
        return getFenYe(hql, pageSize, page);
    }
    //ҵ������ͨ�Ѿ��뵥
    public PageBean selSuMaiTongRuDan(int pageSize, int page, Long userid, String orderId, String time, String time1)
    {
        String hql = orderTableDao.getYeWuOrder(orderId, time, time1);
        return getFenYe(hql, pageSize, page);
    }
    //ҵ��鿴δ�뵥����
    public PageBean selSuMaiTongWeiRuDan(int pageSize, int page, String orderId, String time, String time1)
    {
        String hql = orderTableDao.getWeiRuOrder(orderId, time, time1);
        return getFenYe(hql, pageSize, page);
    }
    //ҵ������ͨ����
    public PageBean selSuMaiTongDaiFa(int pageSize, int page, String orderId, String time, String time1)
    {
        String hql = orderTableDao.getChaKanDaiFa(orderId, time, time1);
        return getFenYe(hql, pageSize, page);
    }
    //�˿ʹ�����
    public PageBean selGuKeDaiFaHuo(int pageSize, int page,Long userid, String orderId)
    {
        String hql = orderTableDao.getDaiFaHuo(orderId,userid);
        return getFenYe(hql, pageSize, page);
    }
    //�˿��Ѿ�����
    public PageBean selYiJingRuDan(int pageSize, int page, String orderId,Long userId)
    {
        String hql = orderTableDao.getYiJingFaHuo(orderId,userId);
        return getFenYe(hql, pageSize, page);
    }
    //����Ϊ��
    public PageBean selCangKuDanHaoWeiKong(int pageSize, int page, String orderId, String guoneidanhao,String wuping)
    {
        //String hql = orderTableDao.getWeiKong(guoneidanhao, orderId,wuping);
        return null;//getFenYe(hql, pageSize, page);
    }
    //����Ա���䶩��
    public PageBean selAdminFenPeiOrder(int pageSize, int page, String orderId){
    	String hql = orderDao.getAllFenPei(orderId);
    	return getFenYe(hql, pageSize, page);
    }
    //�ɹ�����Ա�õ�����
    public  PageBean selAdminDeDaoOrder(int pageSize, int page, String orderId){
    	String hql = orderDao.getAllFenPei(orderId);
    	return getFenYe(hql, pageSize, page);
    }
    //����Ա�޸Ķ���
    public PageBean selAdminUpOrder(int pageSize, int page,String orderId, String danhao, String xujia){
    	String hql = orderDao.getOrder(orderId, danhao, xujia);
    	return getFenYe(hql, pageSize, page);
    }
    //����Ա�鿴ȫ������
    public PageBean selAdminDanHao(int pageSize, int page,String orderId,String time,String time1){
    	String hql = orderDao.getDanHao(orderId, time, time1);
    	return getFenYe(hql, pageSize, page);
    }
    //�鿴����Ա�˻�����
    public PageBean selAdminTuiHuo(int pageSize, int page,String orderId,String danhao,Long chuli){
    	String hql = orderDao.getTuiKuanAll(orderId,danhao,chuli);
    	return getFenYe(hql, pageSize, page);
    }
    //����Ա�鿴�ɹ�δ��ɶ���
    public PageBean selCaiGouWeiWanCheng(int pageSize, int page,String orderId,Long caigouyuan){
    	String hql = orderDao.getCaiGouWeiWanCheng(orderId,caigouyuan);
    	return getFenYe(hql, pageSize, page);
    }
    //����Ա�鿴����ͨ����
    public  PageBean selAdminSuMaiTongOrder(int pageSize, int page,String orderId,String time,String time1){
    	String hql = orderTableDao.getAdminSuMaiTong(orderId,time,time1);
    	return getFenYe(hql, pageSize, page);
    }
    //����Ա�鿴ȫ��Ա���˺�
    public PageBean selAdminZhangHao(int pageSize, int page,String username){
    	String hql = userDao.getzhanghao(username);
    	return getFenYe(hql, pageSize, page);
    }
    //�鿴�ػ��˺�
    public PageBean selDhgate(int pageSize, int page,String name){
    	String hql = zhangHaoDao.getdhgate(name);
    	return getFenYe(hql, pageSize, page);
    }
    //����Ա�鿴�ɹ���ɶ���
    public PageBean selAdminCaiGouWanChengOrder(int pageSize, int page,Long caigouyuan,String orderId,String time,String time1){
    	String hql = orderDao.getCaiGouAll(caigouyuan, orderId, time, time1);
    	return getFenYe(hql, pageSize, page);
    }
    //ҵ��鿴�˷�Ϊ��
    public PageBean selYeWuYunFeiNull(int pageSize, int page,String orderId,String time,String time1){
    	String hql = orderDao.getYunFeiNull(orderId, time, time1);
    	return getFenYe(hql, pageSize, page);
    }
    //�鿴ȫ�������˷�
    public  PageBean selGuoJiaYunFei(int pageSize, int page, Long quyu, String guojia){
    	String hql = guoJiaDao.getGuoJia(quyu, guojia);
    	return getFenYe(hql, pageSize, page);
    }
    //�ͻ��鿴�����еĶ���
    public PageBean selChuHuo(int pageSize, int page, Long denglu, String orderId,String guoneidanhao){
    	String hql = orderTableDao.getChuHuoOrder(denglu, orderId, guoneidanhao);
    	return getFenYe(hql, pageSize, page);
    }
  //����Ա�鿴����ͨ�Ѿ���ɶ���
    public PageBean selSuMaiTong(int pageSize, int page, String orderId,String time,String time1){
    	String hql = orderTableDao.getSuMaiTong(orderId, time, time1);
    	return getFenYe(hql, pageSize, page);
    }
    //ҵ��鿴�޸Ĺ˿�������� 
    public PageBean selRuZhang(int pageSize, int page, String orderId,String time,String time1){
    	String hql = orderTableDao.getRuZhang(orderId, time, time1);
    	return getFenYe(hql, pageSize, page);
    }
  //ҵ��õ��˿�ȫ����ɶ���
    public PageBean selWanChengOrder(int pageSize, int page, String danhao){
    	String hql = orderTableDao.getWanChengOrder(danhao);
    	return getFenYe(hql, pageSize, page);
    }
    //�ɹ�����Ա�õ�����
    public PageBean selCaiGouDeDaoOrder(int pageSize, int page, Long userid, String orderId,String time,String time1,String zhanghaoId){
    	String hql = orderTableDao.getCaiGouAdminDeDaoOrder(userid, orderId, time, time1,zhanghaoId);
    	return getFenYe(hql, pageSize, page);
    }
    //�ɹ��õ�����
    public PageBean selDeDaoOrder(int pageSize, int page, Long userid, String orderId,String time,String time1,String zhanghaoId, Long leimu){
    	
    	String hql = orderDao.getDeOrder(userid, orderId, time, time1,zhanghaoId,leimu);
    	return getFenYe(hql, pageSize, page);
    }
    //�˿͵õ��˻�����
    public PageBean selTuiHuo(int pageSize, int page, Long userid, String orderId,String danhao,String chuli){
    	String hql = orderTableDao.getTuiHuoAll(userid,orderId,danhao,chuli);
    	return getFenYe(hql, pageSize, page);
    }
    //�˿͵õ��˻�����
    public PageBean selChaKanAll(int pageSize, int page, String danhao,Long chuli,Long userid){
    	String hql = orderTableDao.getTuiHuoAll(danhao,chuli,userid);
    	return getFenYe(hql, pageSize, page);
    }
  //�õ�ҵ�񷵻ض���
    public PageBean selYeWuFanHui(int pageSize, int page, String guoneidanhao,String orderId){
    	String hql = orderTableDao.getFanHuiOrder(guoneidanhao, orderId);
    	return getFenYe(hql, pageSize, page);
    }
    //ȫ��δ����
    public PageBean selAllWeiRuZhang(int pageSize, int page, String orderId, String time, String time1, String dhgatezhanghao,String danhao){
    	String hql = orderDao.getAllWeiRuZhang(orderId, time, time1, dhgatezhanghao, danhao);
    	return getFenYe(hql, pageSize, page);
    }
  //�鿴��
    public PageBean selAllKanXin(int pageSize, int page, Long userid){
    	String hql = xieXinDao.getAllKanXin(userid);
    	return getFenYe(hql, pageSize, page);
    }
  //�鿴δ����
    public PageBean selAllWeiDu(int pageSize, int page, Long userid){
    	String hql = xieXinDao.getAllXinXi(userid);
    	return getFenYe(hql, pageSize, page);
    }
    //�鿴��涩��
    public PageBean selKuCunOrder(int pageSize, int page,Long userid, String orderId){
    	String hql = orderTableDao.getKuCunOrder(userid,orderId);
    	return getFenYe(hql, pageSize, page);
    }
    //����Ա�鿴����Ϊ��
    public PageBean selHuoKuanNull(int pageSize, int page,Long userid,String orderId,String time,String time1){
    	String hql = orderTableDao.getHuoKuanAllNum(userid, orderId, time, time1);
    	return getFenYe(hql, pageSize, page);
    }
    //�˷�Ϊ��
    public PageBean selYunFeiNull(int pageSize, int page,Long userid,String orderId,String time,String time1){
    	String hql = orderTableDao.getYunFeiAllNum(userid, orderId, time, time1);
    	return getFenYe(hql, pageSize, page);
    }
    //�ɹ��鿴�˻�
    public PageBean selKanTuiHuo(int pageSize, int page,Long userid,String orderId,String danhao){
    	String hql = orderTableDao.getTuiHuo(userid, orderId, danhao);
    	return getFenYe(hql, pageSize, page);
    }
    public PageBean selChaKanJiuFenOrder(int pageSize, int page,Long userid,String orderId,String time,String time1){
    	String hql = orderTableDao.getJiuFenNum(userid, orderId, time, time1);
    	return getFenYe(hql, pageSize, page);
    }
    //��ѯȫ���ͻ�
    public PageBean selGuKeName(int pageSize, int page,String name){
    	String hql = guKeDao.getGuKeName(name);
    	return getFenYe(hql, pageSize, page);
    }
    //��ѯ�������
    public PageBean selYcDetail(int pageSize, int page,String time,String time1){
    	String hql = yuCunDao.getYuCun(time, time1);
    	return getFenYe(hql, pageSize, page);
    }
    //�鿴�������
    public PageBean selXqYuCun(int pageSize, int page,Long userid){
    	String hql = yuCunDao.getXqYuCun(userid);
    	return getFenYe(hql, pageSize, page);
    }
    //�鿴�������
    public PageBean selAllFenPei(int pageSize, int page,Long userid,String orderId){
    	String hql = orderTableDao.getAllFenPei(userid, orderId);
    	return getFenYe(hql, pageSize, page);
    }
    //�鿴���ⶩ��
    public PageBean selWenTiOrder(int pageSize, int page,Long userid,String orderId,Long dhgatezhanghao){
    	String hql = orderTableDao.getWenTiOrder(userid, orderId,dhgatezhanghao);
    	return getFenYe(hql, pageSize, page);
    }
    //ҵ��鿴ȫ������
    public PageBean selYeAllOrder(int pageSize, int page,String orderId,Long userid){
    	String hql = orderTableDao.getYeAllOrder(orderId,userid);
    	return getFenYe(hql, pageSize, page);
    }
    //�����˲�ѯȫ��
    public PageBean selFjjl(int pageSize, int page,String fjr){
    	String hql = xieXinDao.getFjjl(fjr);
    	return getFenYe(hql, pageSize, page);
    }
    //����鿴�����
    public PageBean selgetPayment(int pageSize, int page){
    	String hql = orderTableDao.getPayment();
    	return getFenYe(hql, pageSize, page);
    }
  //�ɹ��鿴���׶���
    public PageBean selDisputeOrder(int pageSize, int page,Long userid,String orderId,String time,String time1,Long leimus){	
    	String hql= orderTableDao.getDisputeOrder(userid, orderId, time, time1, leimus);
    	return getFenYe(hql, pageSize, page);
    }
    //��ѯ����Ϊ��
    public PageBean selPayEmpty(int pageSize, int page,Long userid,String orderId,String time,String time1){
    	String hql = orderTableDao.PayEmpty(userid, orderId, time, time1);
    	return getFenYe(hql, pageSize, page);
    }
    //�ֿ�õ��������䵥��Ϊ��
    public PageBean selSingleNumberEmpty(int pageSize, int page,String guoneidanhao,String orderId,String wuping){
    	String hql = orderTableDao.SingleNumberEmpty(guoneidanhao, orderId, wuping);
    	return getFenYe(hql, pageSize, page);
    }
    //�ֿ�õ��������䵥�Ų�Ϊ��
    public PageBean selSingleNumberNotEmpty(int pageSize, int page,String guoneidanhao,String orderId,String wuping){
    	String hql = orderTableDao.getSingleNumberNotEmpty(guoneidanhao, orderId, wuping);
    	return getFenYe(hql, pageSize, page);
    }
    //�ֿ����������
    public PageBean selDaiFangQuOrder(int pageSize, int page,String guoneidanhao,String orderId,String time,String time1){
    	String hql = orderTableDao.getDaiFangQuOrder(guoneidanhao, orderId,time,time1);
    	return getFenYe(hql, pageSize, page);
    }
    //������뵥
    public PageBean selDaiRudan(int pageSize, int page,String guoneidanhao,String orderId){
    	String hql = orderTableDao.getSingle(guoneidanhao, orderId);
    	return getFenYe(hql, pageSize, page);
    }
  //��ѯȫ����Ŀ
    public PageBean selAllCgs(int pageSize, int page){
    	String hql = order_DetailDao.getAllCategory();
    	return getFenYe(hql, pageSize, page);
    }
    //��ѯȫ����Ŀ
    public PageBean selComplete(int pageSize, int page,String orderId,String guoneidanhao){
    	String hql = orderTableDao.getComplete(orderId, guoneidanhao);
    	return getFenYe(hql, pageSize, page);
    }
    //�鿴������Ʒ
    public PageBean selOnbehalf(int pageSize, int page,String orderid,Long userid){
    	String hql = orderTableDao.getOnOrder(orderid,userid);
    	return getFenYe(hql, pageSize, page);
    }
    //�鿴ȫ�����׶���
    public PageBean selDispute(int pageSize, int page,String orderId,Long userid,Long chuli,String time,String time1,Long zhanghaoId){
    	String hql = orderTableDao.getOnDispute(orderId, userid,chuli,time,time1,zhanghaoId);
    	return getFenYe(hql, pageSize, page);
    }
    //�鿴ȫ�����׶���
    public PageBean selFilter(int pageSize, int page,Long caigouyuan, String orderId, String time, String time1){
    	String hql = orderDao.getFilter(caigouyuan, orderId, time, time1);
    	return getFenYe(hql, pageSize, page);
    }
    //�鿴���
    public PageBean selStock(int pageSize, int page,String orderId){
    	String hql = orderTableDao.getStock(orderId);
    	return getFenYe(hql, pageSize, page);
    }
    //�鿴�˻�����
    public PageBean selReturnOfGoods(int pageSize, int page,String danhao){
    	String hql = orderTableDao.getReturnOfGoods(danhao);
    	return getFenYe(hql, pageSize, page);
    }
    //�鿴�˻�����
    public PageBean selProcessingReturns(int pageSize, int page,String danhao,Long userid){
    	String hql = orderTableDao.getProcessingReturns(danhao, userid);
    	return getFenYe(hql, pageSize, page);
    }
    //�鿴�Ƿ���
    public PageBean selDisputes(int pageSize, int page,String orderId,String time,String time1,Long selcaigouyuan,String leimus,Long disputes){
    	String hql = orderDao.getAllDispute(orderId, time, time1, selcaigouyuan, leimus,disputes);
    	return getFenYe(hql, pageSize, page);
    }
    //�����õ�����
    public PageBean selArtistsGetOrders(int pageSize, int page,String orderId,Long userid){
    	String hql = orderTableDao.getorders(orderId, userid);
    	return getFenYe(hql, pageSize, page);
    }
    //�����õ�������϶���
    public PageBean selDisposed(int pageSize,int page,String orderId,Long userid){
    	String hql = orderTableDao.getDisposed(orderId, userid);
    	return getFenYe(hql, pageSize, page);
    }
    //�õ�δ���
    public PageBean selUnfinish(int pageSize,int page,String time,String time1){
    	String hql = documentDao.getUnfinish(time, time1);
    	return getFenYe(hql, pageSize, page);
    }
    //����ĵ�
    public PageBean selAudit(int pageSize,int page,String time,String time1){
    	String hql = documentDao.getAudit(time, time1);
    	return getFenYe(hql, pageSize, page);
    }
    //����ĵ�
    public PageBean selCompletes(int pageSize,int page,String time,String time1){
    	String hql = documentDao.getCompleted(time, time1);
    	return getFenYe(hql, pageSize, page);
    }
    //���ܴ�����
    public PageBean selCanNotHandle(int pageSize,int page,String OrderId,Long userid){
    	String hql = orderTableDao.getCanNotHandle(OrderId, userid);
    	return getFenYe(hql, pageSize, page);
    }
    //�ɹ�Ա�鿴��涩��
    public PageBean selStockOrder(int pageSize,int page,Long userid,String time,String time1,String bianma,String orderId){
    	String hql = kuCunDao.getStockOrder(userid,time,time1,bianma,orderId);
    	return getFenYe(hql, pageSize, page);
    }
    //����Ա�鿴��涩��
    public PageBean selStockOrderAll(int pageSize,int page,String time,String time1){
    	String hql = kuCunDao.getStockOrderAll(time, time1);
    	return getFenYe(hql, pageSize, page);
    }
    //�ɹ��鿴���ⶩ��
    public PageBean selIssuesOrders(int pageSize,int page,String orderid,Long userid,String time,String time1){
    	String hql = orderTableDao.getIssuesOrders(orderid, userid,time,time1);
    	return getFenYe(hql, pageSize, page);
    }
    //��ѯȫ����Ŀ 
    public PageBean getCategory(int pageSize,int page){
    	String hql = orderTableDao.getCategory();
    	return getFenYe(hql, pageSize, page);
    }
    //ҵ��õ�ȫ������
    public PageBean getOrders(int pageSize,int page,String orderid,Long userid){
    	String hql = orderTableDao.getBusinessOrders(orderid, userid);
    	return getFenYe(hql, pageSize, page);
    }
    //ҵ��õ��Ѿ�������
    public PageBean getBusinessDisposed(int pageSize,int page,String orderid,Long userid){
    	String hql = orderTableDao.getBusinessDisposed(orderid, userid);
    	return getFenYe(hql, pageSize, page);
    }
    //�鿴ȫ���ɹ��˺�
    public PageBean getAllCgZh(int pageSize,int page){
    	String hql = caigoudao.getAllCaiGou();
    	return getFenYe(hql, pageSize, page);
    }
    //�ɹ�����Ա��ѯȫ����˶���
    public PageBean getSingleAudit(int pageSize,int page,String orderid,Long userid){
    	String hql = orderTableDao.getSingleAudit(orderid, userid);
    	return getFenYe(hql, pageSize, page);
    }
    //ҵ��鿴���״������
    public PageBean getDisputesDealtWith(int pageSize,int page,String orderid,Long userid,String time,String time1){
    	String hql = orderTableDao.getDisputesDealtWith(orderid, userid, time, time1);
    	return getFenYe(hql, pageSize, page);
    }
    //ҵ��鿴����δ������϶���
    public PageBean getDisputependingBefore(int pageSize,int page,String orderid,Long userid,String time,String time1){
    	String hql = orderTableDao.getDisputependingBefore(orderid, userid, time, time1);
    	return getFenYe(hql, pageSize, page);
    }
    //�鿴ҵ��ҵ��
    public PageBean  getBusinessPerformance(int pageSize,int page,Long yewuid,String orderid,String time,String time1){
    	String hql = orderTableDao.getBusinessPerformance(yewuid, orderid, time, time1);
    	return getFenYe(hql, pageSize, page);
    }
    //�鿴ȫ��֧��
    public PageBean getSpending(int pageSize,int page,String time,String time1){
    	String hql = spendingdao.getAllSpeng(time, time1);
    	return getFenYe(hql, pageSize, page);
    }
    //�鿴ȫ������
    public PageBean getInCome(int pageSize,int page,String time,String time1){
    	String hql = incomedao.getIncome(time, time1);
    	return getFenYe(hql, pageSize, page);
    }
    //�ɹ�����Ա�鿴�˻�
    public PageBean selReturnGoods(int pageSize, int page,Long userid,String orderId,String danhao,Long caigouyuan){
    	String hql = orderTableDao.getPurchasing(userid, orderId, danhao,caigouyuan);
    	return getFenYe(hql, pageSize, page);
    }
  //�鿴δ��ɶ���
    public PageBean getOutstandingOrders(int pageSize,int page,Long userid,String orderId,Long caigouyuan){
    	String hql = orderTableDao.getOutstandingOrders(userid, orderId, caigouyuan);
    	return getFenYe(hql, pageSize, page);
    }
    //����ͨ¼��
    public PageBean getIntoaSingle(int pageSize,int page,Long userid,Long caigouyuan,String orderId,String time,String time1){
    	String hql = orderTableDao.getIntoaSingle(userid, caigouyuan, orderId, time, time1);
    	return getFenYe(hql, pageSize, page);
    }
    //�鿴�����
    public PageBean getLargeOrder(int pageSize,int page,String orderId,String time,String time1){
    	String hql = businessdao.getlargeorder(orderId, time, time1);
    	return getFenYe(hql, pageSize, page);
    }

}
