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
		// 取可用的站内信模板列表
		List<DhMsgTemplate> msgTemplateList = dhMsgTemplateDao.getEnableTempateList();
		
		// 取topn个订单进行物流状态查询
		int topn = 16;
		Long queryBeforeTime = DateUtils.getAfterDaysDate(new Date(), -7).getTime(); // 查询填写时间距当前大于7天的订单
		List<OrderTable> orderList = orderTableDao.getTopnWaitQueryTrackOrders(topn, queryBeforeTime);
		for (OrderTable order : orderList) {
			// 取敦煌账号信息，用于发站内信
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

				// 查询该单号物流记录, 如果是已签收就不再调用17track接口
				DanhaoTrack danhaoTrack = danhaoTrackDao.findUniqueByDanhao(danhao);
				if (danhaoTrack != null && danhaoTrack.getPackageState().equals(3)) {
					continue;
				}
				
				// 调用17track查询接口
				QueryTrackResult queryResult = null;
				try {
					queryResult = trackApiBiz.doQuery(danhao, et);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				// 正常查到数据
				if (queryResult != null && queryResult.getRet().equals(1)) {
					// 更新上网状态
					if (!queryResult.getPackageState().equals(0) &&
							(order.getShangwang() == null || order.getShangwang().equals(0l))) {
						order.setShangwang(1l);
					}
					// 已签收
					if (queryResult.getPackageState().equals(3)) {
						qianshouCount++;
					}
					
					// 判断是否需要发送站内信通知
					int sendMsgFlag = 0; // 0.不发送，1.发送运输途中通知，2.发送到达待取通知，3.发送妥投通知
					if (danhaoTrack == null) {
						danhaoTrack = new DanhaoTrack();
						if (queryResult.getPackageState().equals(1)) { // 需要发送运输途中通知站内信
							sendMsgFlag = 1;
						}
					} else {
						if (!danhaoTrack.getPackageState().equals(2) 
								&& queryResult.getPackageState().equals(2)) { // 需要发送到达待取通知站内信
							sendMsgFlag = 2;
						} else if (!danhaoTrack.getPackageState().equals(3) 
								&& queryResult.getPackageState().equals(3)) { // 需要发送妥投通知站内信
							sendMsgFlag = 3;
						}
					}
					
					
					// 更新单号查询结果
					danhaoTrack.setPackageState(queryResult.getPackageState());
					danhaoTrack.setLatestTrackInfo(queryResult.getLatestTrackInfo());
					danhaoTrack.setAllTrackInfo(queryResult.getAllTrackInfo());
					danhaoTrack.setQueryTime(new Date());
					danhaoTrack.setOrderId(order.getOrderId());
					danhaoTrack.setDanhao(danhao);
					danhaoTrackDao.merge(danhaoTrack);
					
					// 站内信通知客户
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
									logger.debug("订单号:" + order.getOrderId() + ", 物流状态通知站内信发送异常 -- " + sendResult);
								} else {
									logger.debug("订单号:" + order.getOrderId() + ", 物流状态通知站内信发送成功");
								}
							}
						}
					}
				}
				queryCount++;
			}
			
			// 更新签收状态
			if (queryCount == qianshouCount) {
				// 调用敦煌请款Api
				JSONObject json = dhOrderApiExtBiz.moneyGet(order);
				if (json != null) {
					order.setQianshou(1l);
					logger.debug("自动调用请款api成功,订单号:" + order.getOrderId());
				} else {
					logger.debug("自动调用请款api失败,订单号:" + order.getOrderId());
				}
			}
			
			// 更新物流查询时间
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
