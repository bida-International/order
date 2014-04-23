package com.demo.action.tools;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.demo.action.BaseAction;
import com.demo.core.config.ConfigHelper;
import com.demo.core.config.SiteConfig;

/**
 * ��Ʒ״̬ͬ����������ҳ��
 *
 */
@Controller("tools.productStatusConfigAction")
@Scope("prototype")
public class ProductStatusConfigAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private SiteConfig siteConfig = ConfigHelper.getSiteConfig();

	private Integer autoSynchProductStatus;
	
	public String execute() {
		
		return SUCCESS;
	}
	
	public String save() {
		siteConfig.setAutoSynchProductStatus(autoSynchProductStatus);
		ConfigHelper.saveSiteConfig();
		this.addActionMessage("�������ñ���ɹ�!");
		return execute();
	}
	
	public SiteConfig getSiteConfig() {
		return siteConfig;
	}

	public void setSiteConfig(SiteConfig siteConfig) {
		this.siteConfig = siteConfig;
	}

	public Integer getAutoSynchProductStatus() {
		return autoSynchProductStatus;
	}

	public void setAutoSynchProductStatus(Integer autoSynchProductStatus) {
		this.autoSynchProductStatus = autoSynchProductStatus;
	}
	
	
}
