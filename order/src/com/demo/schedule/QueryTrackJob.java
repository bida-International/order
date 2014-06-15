package com.demo.schedule;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.demo.biz.dhgate.DhCommonApiBiz;
import com.demo.biz.dhgate.DhMsgApiBiz;
import com.demo.biz.dhgate.DhOrderApiExtBiz;
import com.demo.biz.dhgate.DhOrderBiz;
import com.demo.biz.track.QueryTrackResult;
import com.demo.biz.track.TrackApiBiz;
import com.demo.dao.DanhaoTrackDao;
import com.demo.dao.DhMsgTemplateDao;
import com.demo.dao.KuaiDiFangShiDao;
import com.demo.dao.OrderTableDao;
import com.demo.dao.ZhangHaoDao;
import com.demo.entity.DhMsgTemplate;
import com.demo.entity.KuaiDiFangShi;
import com.demo.entity.ZhangHao;
import com.demo.entity.order.DanhaoTrack;
import com.demo.entity.order.OrderTable;
import com.demo.utils.DateUtils;

public class QueryTrackJob implements InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
	}

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private OrderTableDao orderTableDao;
	@Resource
	private TrackApiBiz trackApiBiz;
	@Resource
	private DanhaoTrackDao danhaoTrackDao;
	@Resource
	private KuaiDiFangShiDao kuaiDiFangShiDao;
	@Resource
	private DhOrderApiExtBiz dhOrderApiExtBiz;
	@Resource
	private DhOrderBiz dhOrderBiz;
	@Resource
	private ZhangHaoDao zhangHaoDao;
	@Resource
	private DhMsgTemplateDao dhMsgTemplateDao;
	@Resource
	private DhMsgApiBiz dhMsgApiBiz;
	
	public void execute() {
		// ȡ���õ�վ����ģ���б�
		List<DhMsgTemplate> msgTemplateList = dhMsgTemplateDao.getEnableTempateList();
		
		// ȡtopn��������������״̬��ѯ
		int topn = 16;
		Long queryBeforeTime = DateUtils.getAfterDaysDate(new Date(), -7).getTime(); // ��ѯ��дʱ��൱ǰ����7��Ķ���
		List<OrderTable> orderList = orderTableDao.getTopnWaitQueryTrackOrders(topn, queryBeforeTime);
		for (OrderTable order : orderList) {
			// ȡ�ػ��˺���Ϣ�����ڷ�վ����
			ZhangHao dhAccount = zhangHaoDao.getZhangHaoId(order.getZhanghaoId());
			if (!dhAccount.getAccount_type().equals(DhCommonApiBiz.ACCOUNT_TYPE)) {
				dhAccount = null;
			}
			
			KuaiDiFangShi kuaiDiFangShi = kuaiDiFangShiDao.get(order.getKuaidifangshiId());
			String et = kuaiDiFangShi.getTrackNum();
			String[] danhaoArr = order.getDanhao().split(",");
			int queryCount = 0;
			int qianshouCount = 0;
			for (String danhao : danhaoArr) {
				danhao = danhao.trim();
				if (danhao.equals("")) {
					continue;
				}

				// ��ѯ�õ���������¼, �������ǩ�վͲ��ٵ���17track�ӿ�
				DanhaoTrack danhaoTrack = danhaoTrackDao.findUniqueByDanhao(danhao);
				if (danhaoTrack != null && danhaoTrack.getPackageState().equals(3)) {
					continue;
				}
				
				// ����17track��ѯ�ӿ�
				QueryTrackResult queryResult = null;
				try {
					queryResult = trackApiBiz.doQuery(danhao, et);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				// �����鵽����
				if (queryResult != null && queryResult.getRet().equals(1)) {
					// ��������״̬
					if (!queryResult.getPackageState().equals(0) &&
							(order.getShangwang() == null || order.getShangwang().equals(0l))) {
						order.setShangwang(1l);
					}
					// ��ǩ��
					if (queryResult.getPackageState().equals(3)) {
						qianshouCount++;
					}
					
					// �ж��Ƿ���Ҫ����վ����֪ͨ
					int sendMsgFlag = 0; // 0.�����ͣ�1.��������;��֪ͨ��2.���͵����ȡ֪ͨ��3.������Ͷ֪ͨ
					if (danhaoTrack == null) {
						danhaoTrack = new DanhaoTrack();
						if (queryResult.getPackageState().equals(1)) { // ��Ҫ��������;��֪ͨվ����
							sendMsgFlag = 1;
						}
					} else {
						if (!danhaoTrack.getPackageState().equals(2) 
								&& queryResult.getPackageState().equals(2)) { // ��Ҫ���͵����ȡ֪ͨվ����
							sendMsgFlag = 2;
						} else if (!danhaoTrack.getPackageState().equals(3) 
								&& queryResult.getPackageState().equals(3)) { // ��Ҫ������Ͷ֪ͨվ����
							sendMsgFlag = 3;
						}
					}
					
					
					// ���µ��Ų�ѯ���
					danhaoTrack.setPackageState(queryResult.getPackageState());
					danhaoTrack.setLatestTrackInfo(queryResult.getLatestTrackInfo());
					danhaoTrack.setAllTrackInfo(queryResult.getAllTrackInfo());
					danhaoTrack.setQueryTime(new Date());
					danhaoTrack.setOrderId(order.getOrderId());
					danhaoTrack.setDanhao(danhao);
					danhaoTrackDao.merge(danhaoTrack);
					
					// վ����֪ͨ�ͻ�
					if (sendMsgFlag != 0 && dhAccount != null) {
						DhMsgTemplate msgTemplate = getMsgTemplate(msgTemplateList, sendMsgFlag);
						if (msgTemplate != null) {
							String orderId = order.getOrderId();
							String trackDetail = danhaoTrack.getAllTrackInfo();
							
							String msgTitle = msgTemplate.getTitle().replaceAll("#orderId#", orderId)
									.replaceAll("#danhao#", danhao);
							String msgContent = msgTemplate.getContent().replaceAll("#orderId#", orderId)
									.replaceAll("#danhao#", danhao).replaceAll("#trackDetail#", trackDetail)
									.replaceAll("\r\n", "<br/>").replaceAll("\r", "<br/>").replaceAll("\n", "<br/>");
							String recieverId = getOrderBuyerId(order, dhAccount);
							if (recieverId != null) {
								String sendResult = dhMsgApiBiz.sendMsg(msgTitle, msgContent, recieverId, dhAccount);
								if (!sendResult.equals("success")) {
									logger.debug("������:" + order.getOrderId() + ", ����״̬֪ͨվ���ŷ����쳣 -- " + sendResult);
								} else {
									logger.debug("������:" + order.getOrderId() + ", ����״̬֪ͨվ���ŷ��ͳɹ�");
								}
							}
						}
					}
				}
				queryCount++;
			}
			
			// ����ǩ��״̬
			if (queryCount == qianshouCount) {
				// ���öػ����Api
				JSONObject json = dhOrderApiExtBiz.moneyGet(order);
				if (json != null) {
					order.setQianshou(1l);
					logger.debug("�Զ��������api�ɹ�,������:" + order.getOrderId());
				} else {
					logger.debug("�Զ��������apiʧ��,������:" + order.getOrderId());
				}
			}
			
			// ����������ѯʱ��
			order.setLastQueryTrackTime(new Date().getTime());
			orderTableDao.merge(order);
		}
	}
	
	private String getOrderBuyerId(OrderTable order, ZhangHao dhAccount) {
		if (order.getBuyerId() != null) {
			return order.getBuyerId();
		}
		JSONObject orderJson = dhOrderBiz.getOrder(order.getOrderId(), dhAccount);
		if (orderJson != null) {
			JSONObject rfxDto =null;
			if (orderJson.containsKey("rfxDto")) {
				rfxDto = orderJson.getJSONObject("rfxDto");
			} else if (orderJson.containsKey("rfxVo")) {
				rfxDto = orderJson.getJSONObject("rfxVo");
			}
			if (rfxDto == null) {
				return null;
			}
			return rfxDto.getString("buyerid");
		}
		return null;
	}
	
	private DhMsgTemplate getMsgTemplate(List<DhMsgTemplate> msgTemplateList, Integer type) {
		if (msgTemplateList == null || msgTemplateList.isEmpty()) {
			return null;
		}
		for (DhMsgTemplate msgTemplate : msgTemplateList) {
			if (msgTemplate.getType().equals(type)) {
				return msgTemplate;
			}
		}
		return null;
	}
}
