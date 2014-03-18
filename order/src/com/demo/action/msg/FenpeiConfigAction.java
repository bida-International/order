package com.demo.action.msg;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.demo.action.BaseAction;
import com.demo.biz.dhgate.DhCommonApiBiz;
import com.demo.biz.msg.DhMsgBiz;
import com.demo.dao.ZhangHaoDao;
import com.demo.dao.user.YeWu1Dao;
import com.demo.entity.ZhangHao;
import com.demo.entity.user.YeWu1;
/**
 * 站内信自动分配设置, 目前只针对敦煌账号
 *
 */
@Controller("msg.fenpeiConfigAction")
@Scope("prototype")
public class FenpeiConfigAction extends BaseAction  implements ServletRequestAware{

	private static final long serialVersionUID = 1L;

    @Resource
    private ZhangHaoDao zhangHaoDao;
	@Resource
	private YeWu1Dao yeWu1Dao;
	@Resource
	private DhCommonApiBiz dhCommonApiBiz;
    @Resource
    private DhMsgBiz dhMsgBiz;
    
    private List<ZhangHao> dhAccounts;
	private List<YeWu1> yeWus;
	
	private Long[] id;
	private Long[] msgFenpeiUserId;
	private String[] msgFenpeiUserName;
	  private HttpServletRequest request;
	public String execute() {
		dhAccounts = dhCommonApiBiz.getDhAccounts(null);
		yeWus = yeWu1Dao.getYeWuAll();
		return SUCCESS;
	}
	
	public String save() {
		for (int i = 0; i < id.length; i++) {
			ZhangHao account = zhangHaoDao.get(id[i]);
			Long userId = msgFenpeiUserId[i];
			if (userId == null || userId.equals("")) {
				account.setBd_user_id(null);
				account.setBd_user_name(null);
			} else {
				account.setBd_user_id(msgFenpeiUserId[i]);
				account.setBd_user_name(msgFenpeiUserName[i]);
			}
			zhangHaoDao.merge(account);
		}
		
		// 执行一次分配, 针对敦煌账号
		List<ZhangHao> dhAccounts = dhCommonApiBiz.getDhAccounts(null);
		for (ZhangHao dhAccount : dhAccounts) {
			dhMsgBiz.fenpeiMsg(dhAccount);
		}
		
		request.getSession().setAttribute("alertMsg", "保存自动分配设置成功！");
		return "reload";
	}


	public List<YeWu1> getYeWus() {
		return yeWus;
	}

	public void setYeWus(List<YeWu1> yeWus) {
		this.yeWus = yeWus;
	}

	public Long[] getId() {
		return id;
	}

	public void setId(Long[] id) {
		this.id = id;
	}

	public Long[] getMsgFenpeiUserId() {
		return msgFenpeiUserId;
	}

	public void setMsgFenpeiUserId(Long[] msgFenpeiUserId) {
		this.msgFenpeiUserId = msgFenpeiUserId;
	}

	public String[] getMsgFenpeiUserName() {
		return msgFenpeiUserName;
	}

	public void setMsgFenpeiUserName(String[] msgFenpeiUserName) {
		this.msgFenpeiUserName = msgFenpeiUserName;
	}

	public List<ZhangHao> getDhAccounts() {
		return dhAccounts;
	}

	public void setDhAccounts(List<ZhangHao> dhAccounts) {
		this.dhAccounts = dhAccounts;
	}
   public void setServletRequest(HttpServletRequest arg0)
    {
        request = arg0;
    }
}
