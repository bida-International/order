package com.demo.action.msg;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.demo.action.BaseAction;
import com.demo.dao.DhMsgTemplateDao;
import com.demo.entity.DhMsgTemplate;
import com.demo.utils.Struts2Utils;

/**
 * վ��������ģ�����
 *
 */
@Controller("msg.dhMsgTemplateAction")
@Scope("prototype")
public class DhMsgTemplateAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Resource
	private DhMsgTemplateDao dhMsgTemplateDao;
	
	private DhMsgTemplate dhMsgTemplate;
	private Integer type = 1;
	
	private String previewTitle;
	private String previewContent;
	
	public String execute() {
		dhMsgTemplate = dhMsgTemplateDao.findUniqueByType(type); 
		if (dhMsgTemplate != null) {
			setPreview();
		}
		return SUCCESS;
	}
	
	public String save() {
		try {
			String title = Struts2Utils.getParameter("title");
			String content = Struts2Utils.getParameter("content");
			String status = Struts2Utils.getParameter("status");
			
			dhMsgTemplate = dhMsgTemplateDao.findUniqueByType(type); 
			if (dhMsgTemplate == null) {
				dhMsgTemplate = new DhMsgTemplate();
				dhMsgTemplate.setType(type);
			}
			dhMsgTemplate.setTitle(title);
			dhMsgTemplate.setContent(content);
			dhMsgTemplate.setStatus(Integer.parseInt(status));
			dhMsgTemplateDao.merge(dhMsgTemplate);
			Struts2Utils.renderJson("����ɹ�", true);
		} catch (Exception e) {
			e.printStackTrace();
			Struts2Utils.renderJson("����ʧ�ܣ�" + e.getMessage(), false);
		}
		return null;
	}

	private void setPreview() {
		String orderId = "1346889064";
		String danhao = "RB232655963CN";
		String trackDetail = "2014-05-08 19:04 �����д����ʼ����д�������, �ռľ��ռ�, Ӣ��, Collection, GB<br/>2014-05-10 20:37 ���Ź���, �����ܰ�����ⷢ, Ӣ��, Dispatching, GB<br/>2014-05-10 20:37 ���Ź���, �����ܰ����⿪��, Ӣ��, Opening, GB<br/>2014-05-16 09:06 ��������, �����ܰ�ֱ��ⷢ, Ӣ��, Departure from outward office of exchange, GB<br/>2014-05-16 16:09 ��������, �����ܰ����⿪��, Ӣ��, Opening, GB<br/>2014-05-17 05:55 PEK, ����, Ӣ��, GB<br/>2014-05-21 12:01 Ӣ��, δ��Ͷ, Ӣ��, Unsuccessful delivery, GB<br/>2014-05-23 11:05 Ӣ��, ��Ͷ, Ӣ��, Final delivery, GB<br/>";
		
		previewTitle = dhMsgTemplate.getTitle().replaceAll("#orderId#", orderId)
				.replaceAll("#danhao#", danhao);
		previewContent = dhMsgTemplate.getContent().replaceAll("#orderId#", orderId)
				.replaceAll("#danhao#", danhao).replaceAll("#trackDetail#", trackDetail)
				.replace("<br/>", "\r\n");
	}
	
	public DhMsgTemplate getDhMsgTemplate() {
		return dhMsgTemplate;
	}

	public void setDhMsgTemplate(DhMsgTemplate dhMsgTemplate) {
		this.dhMsgTemplate = dhMsgTemplate;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPreviewTitle() {
		return previewTitle;
	}

	public void setPreviewTitle(String previewTitle) {
		this.previewTitle = previewTitle;
	}

	public String getPreviewContent() {
		return previewContent;
	}

	public void setPreviewContent(String previewContent) {
		this.previewContent = previewContent;
	}
}
