package com.demo.action.tools;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.demo.action.BaseAction;
import com.demo.biz.tools.GatherData1Biz;
import com.demo.utils.Struts2Utils;

/**
 * �ɼ����ݹ���1: �ɼ�������ַ�Ĳ�Ʒ�б��ж���������ָ�������Ĳ�Ʒ����
 *
 */
@Controller("tools.gatherData1Action")
@Scope("prototype")
public class GatherData1Action extends BaseAction {

	private static final long serialVersionUID = 1L;

	@Resource
	private GatherData1Biz gatherData1Biz;
	
	private String targetUrl;
	private Integer minOrderNum;
	
	public String execute() {
		
		return SUCCESS;
	}
	
	public String startGather() {
		try {
			Long createTime = new Date().getTime();
			gatherData1Biz.startGather(targetUrl, minOrderNum, createTime);
			Struts2Utils.renderJson(createTime.toString(), true);
		} catch (Exception e) {
			e.printStackTrace();
			Struts2Utils.renderJson("�����쳣", false);
		}
		return null;
	}
	
	public String stopGather() {
		gatherData1Biz.stopGather();
		Struts2Utils.renderJson("�����ɼ�", true);
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
