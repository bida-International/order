package com.demo.biz.tools;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.demo.dao.tools.GatherData1ResultDao;
import com.demo.entity.tools.GatherData1Key;
import com.demo.entity.tools.GatherData1Result;
import com.demo.utils.ApplicationUtils;
import com.demo.utils.HttpClientUtils;

public class GatherData1Thread implements Runnable {

	private String firstTargetUrl;
	private Integer minOrderNum;
	private GatherData1Key gatherKey;
	private GatherData1ResultDao gatherData1ResultDao;
	private String sessionId;
	
	public GatherData1Thread(GatherData1Key gatherData1Key, GatherData1ResultDao gatherData1ResultDao,
			String sessionId) {
		this.firstTargetUrl = gatherData1Key.getGatherKey();
		this.minOrderNum = Integer.parseInt(gatherData1Key.getQueryParam());
		this.gatherKey = gatherData1Key;
		this.gatherData1ResultDao = gatherData1ResultDao;
		this.sessionId = sessionId;
	}
	
	@Override
	public void run() {
		// 初始采集进度信息
		this.setTotalNum(0);
		this.setDoneNum(0);
		this.setGatherNum(0);
		this.setPercent("0.0%");
		this.setStopFlag(0);
		
		this.gatherOnePage(firstTargetUrl);
	}
	
	private void gatherOnePage(String targetUrl) {
		if (this.getStopFlag() == 1) {
			return;
		}
		// 请求html数据
		String html = HttpClientUtils.doGetHtml(targetUrl);
		
		String regx = null;
		Pattern p = null;
		Matcher macher = null;
		
		// 取记录总数
		if (this.getTotalNum() == 0) {
			Integer totalNum= 0;
			Integer maxTotalNum = 36 * 262;
			regx = "<strong class=\"search-count\">(.*?)</strong>";
			p = Pattern.compile(regx);
			macher = p.matcher(html);
			while (macher.find()) {
				totalNum = Integer.parseInt(macher.group(1).replace(",", ""));
			}
			if (totalNum == 0 || totalNum > maxTotalNum) {
				totalNum = maxTotalNum;
			}
			this.setTotalNum(totalNum);
		}

		// 取链接和订单数
		Integer doneNum = 0;
		Integer gartherNum = 0;
		regx = "<a class=\"order-num-a(.*?)href=\"(.*?)\" (.*?)><em title=\"Total Orders\"> Orders (.*?)</em>";
		p = Pattern.compile(regx);
		macher = p.matcher(html);
		while (macher.find()) {
			doneNum++;
			String link = macher.group(2).trim().split("#")[0];
			String orderNum = macher.group(4).trim();
			orderNum = orderNum.substring(1, orderNum.length() - 1);
			if (Integer.parseInt(orderNum) > minOrderNum) {
				GatherData1Result gatherResult = gatherData1ResultDao.findUnique(gatherKey.getCreateTime(), link);
				if (gatherResult == null) {
					gatherResult = new GatherData1Result();
					gatherResult.setKeyCreateTime(gatherKey.getCreateTime());
					gatherResult.setLink(link);
				}
				gatherResult.setOrderNum(Integer.parseInt(orderNum)); // 已存在的只更新订单数, 不重复入库
				gatherData1ResultDao.merge(gatherResult);
				gartherNum++;
			} else { // 结束
				this.setPercent("100.0%");
				return;
			}
		}
		this.updateDoneNum(doneNum);
		this.updateGatherNum(gartherNum);
		this.updatePercent();

		// 取下一页地址
		String nextPageUrl = null;
		regx = "<a rel=\"nofollow\" href=\"(.*?)\" class=\"page-next ui-pagination-next\">Next</a>";
		p = Pattern.compile(regx);
		macher = p.matcher(html);
		while (macher.find()) {
			nextPageUrl = macher.group(1).trim().replace("&amp;", "&");
		}
		// 继续取下一页
		if (nextPageUrl != null) {
			this.gatherOnePage(nextPageUrl);
		} else {
			this.setPercent("100.0%");
		}
	}
	
	/**
	 * 要遍历的记录数
	 */
	private void setTotalNum(Integer totalNum) {
		ApplicationUtils.put("GatherData1_TotalNum_" + sessionId, totalNum);
	}
	private Integer getTotalNum() {
		return (Integer) ApplicationUtils.get("GatherData1_TotalNum_" + sessionId);
	}
	
	/**
	 * 已完成遍历的数量
	 * @param doneNum
	 */
	private void setDoneNum(Integer doneNum) {
		ApplicationUtils.put("GatherData1_DoneNum_" + sessionId, doneNum);
	}
	private Integer getDoneNum() {
		return  (Integer) ApplicationUtils.get("GatherData1_DoneNum_" + sessionId);
	}
	private void updateDoneNum(Integer addNum) {
		this.setDoneNum(this.getDoneNum() + addNum);
	}
	
	/**
	 * 采集下来的数量
	 * @param gatherNum
	 */
	private void setGatherNum(Integer gatherNum) {
		ApplicationUtils.put("GatherData1_GatherNum_" + sessionId, gatherNum);
	}
	private Integer getGatherNum() {
		return (Integer) ApplicationUtils.get("GatherData1_GatherNum_" + sessionId);
	}
	private void updateGatherNum(Integer addNum) {
		this.setGatherNum(this.getGatherNum() + addNum);
	}
	
	/**
	 * 采集进度
	 * @param percent
	 */
	private void setPercent(String percent) {
		ApplicationUtils.put("GatherData1_Percent_" + sessionId, percent);
	}
	public String getPercent() {
		return (String) ApplicationUtils.get("GatherData1_Percent_" + sessionId);
	}
	private void updatePercent() {
		Integer totalNum = this.getTotalNum();
		Integer doneNum = this.getDoneNum();
		Double percent = Double.parseDouble(doneNum.toString()) 
				/ Double.parseDouble(totalNum.toString()) * 100;
		if (percent > 100d) {
			percent = 100d;
		}
		this.setPercent(new DecimalFormat("#.0").format(percent) + "%");
	}
	
	/**
	 * 设置停止采集标记
	 * @param stopFlag 0.继续; 1.停止,
	 */
	private void setStopFlag(Integer stopFlag) {
		ApplicationUtils.put("GatherData1_StopFlag_" + sessionId, stopFlag);
	}
	private Integer getStopFlag() {
		return (Integer) ApplicationUtils.get("GatherData1_StopFlag_" + sessionId);
	}
}
