package com.demo.biz.tools;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.tools.GatherData1KeyDao;
import com.demo.dao.tools.GatherData1ResultDao;
import com.demo.entity.tools.GatherData1Key;
import com.demo.utils.ApplicationUtils;
import com.demo.utils.Struts2Utils;

@Service
@Transactional
public class GatherData1Biz {

	@Resource
	private GatherData1KeyDao gatherData1KeyDao;
	@Resource
	private GatherData1ResultDao gatherData1ResultDao;
	
	public void startGather(String targetUrl, Integer minOrderNum, Long createTime) {

		GatherData1Key gatherKey = new GatherData1Key();
		gatherKey.setGatherKey(targetUrl);
		gatherKey.setQueryParam(minOrderNum.toString());
		gatherKey.setCreateTime(createTime);
		gatherData1KeyDao.merge(gatherKey);
		
		String sessionId = Struts2Utils.getSessionId();
		new Thread(new GatherData1Thread(targetUrl, minOrderNum, gatherKey, 
				gatherData1ResultDao, sessionId)).start();
	}
	
	public void stopGather() {
		this.setStopFlag(1);
	}
	
	public String getPercent() {
		return (String) ApplicationUtils.get("GatherData1_Percent_"
				+ Struts2Utils.getSessionId());
	}

	/**
	 * 设置停止采集标记
	 * @param stopFlag 0.继续; 1.停止,
	 */
	private void setStopFlag(Integer stopFlag) {
		ApplicationUtils.put("GatherData1_StopFlag_"
				+ Struts2Utils.getSessionId(), stopFlag);
	}
}
