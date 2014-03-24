package com.demo.action.tools;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.demo.action.BaseAction;
import com.demo.biz.tools.GatherData1Biz;
import com.demo.dao.tools.GatherData1KeyDao;
import com.demo.entity.tools.GatherData1Key;
import com.demo.utils.Struts2Utils;

/**
 * 采集数据工具1: 采集所给地址的产品列表中订单数大于指定数量的产品链接
 *
 */
@Controller("tools.gatherData1Action")
@Scope("prototype")
public class GatherData1Action extends BaseAction {

	private static final long serialVersionUID = 1L;

	@Resource
	private GatherData1Biz gatherData1Biz;
	@Resource
	private GatherData1KeyDao gatherData1KeyDao;
	
	private String targetUrl;
	private Integer minOrderNum;
	
	public String execute() {
		
		return SUCCESS;
	}
	
	public String startGather() {
		try {
			GatherData1Key gatherData1Key = gatherData1KeyDao.getByGatherKey(targetUrl);
			if (gatherData1Key == null) {
				gatherData1Key = new GatherData1Key();
				gatherData1Key.setGatherKey(targetUrl);
				gatherData1Key.setCreateTime(new Date().getTime());
			}
			gatherData1Key.setQueryParam(minOrderNum.toString());
			gatherData1Key.setUpdateTime(new Date().getTime());
			gatherData1KeyDao.merge(gatherData1Key);
			
			gatherData1Biz.startGather(gatherData1Key);
			
			Long createTime = gatherData1Key.getCreateTime();
			Struts2Utils.renderJson(createTime.toString(), true);
		} catch (Exception e) {
			e.printStackTrace();
			Struts2Utils.renderJson("发生异常", false);
		}
		return null;
	}
	
	public String stopGather() {
		gatherData1Biz.stopGather();
		Struts2Utils.renderJson("结束采集", true);
		return null;
	}
	
	public String getPercent() {
		String percent = gatherData1Biz.getPercent();
		Struts2Utils.renderJson(percent, true);
		return null;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public Integer getMinOrderNum() {
		return minOrderNum;
	}

	public void setMinOrderNum(Integer minOrderNum) {
		this.minOrderNum = minOrderNum;
	}
	
}
