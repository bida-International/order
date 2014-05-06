package com.demo.action.tools;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.demo.action.BaseAction;
import com.demo.biz.dhgate.DhCommonApiBiz;
import com.demo.biz.dhgate.DhProductApiBiz;
import com.demo.biz.tools.ProductStatusBiz;
import com.demo.dao.ZhangHaoDao;
import com.demo.dao.tools.ProductStatusDao;
import com.demo.entity.ZhangHao;
import com.demo.entity.tools.ProductStatus;
import com.demo.page.PageBean;
import com.demo.utils.Struts2Utils;

/**
 * 产品状态列表功能页面
 *
 */
@Controller("tools.productStatusAction")
@Scope("prototype")
public class ProdcutStatusAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	@Resource
	private ProductStatusBiz productStatusBiz;
	@Resource
	private DhProductApiBiz dhProductApiBiz;
	@Resource
	private ProductStatusDao productStatusDao;
	@Resource
	private ZhangHaoDao zhangHaoDao;
	
	private Long zhangHaoId = 0L;
	private Integer dhStatus = -1;
	private Integer aliStatus = -1;
	private Integer statusFlag = -1;
	private String urlKeyword;
	private Integer pageSize = 20;
	private Integer pageNum = 1;
	private PageBean pageBean;
	private List<ZhangHao> zhangHaoList;
	private String dhUrl;
	private String aliUrl;
	private Long id;
	
	public String execute() {
		this.initParams();
		String orderBy = "createTime desc";
		pageBean = productStatusDao.getAllByPage(dhStatus, aliStatus, statusFlag, 
				urlKeyword, zhangHaoId, orderBy, pageSize, pageNum);
		zhangHaoList = zhangHaoDao.getAll(DhCommonApiBiz.ACCOUNT_TYPE, null);
		return SUCCESS;
	}
	
	private void initParams() {
		if (zhangHaoId.equals(0L)) {
			zhangHaoId = null;
		}
		if (dhStatus == -1) {
			dhStatus = null;
		}
		if (aliStatus == -1) {
			aliStatus = null;
		}
		if (statusFlag == -1) {
			statusFlag = null;
		}
	}
	
	public String add() {
		zhangHaoList = zhangHaoDao.getAll(DhCommonApiBiz.ACCOUNT_TYPE, null);
		return INPUT;
	}
	
	public String save() {
		ZhangHao zhangHao = zhangHaoDao.get(zhangHaoId);
		String result = productStatusBiz.addProdcut(dhUrl, aliUrl, zhangHao);
		if (result.equals("success")) {
			return "list";
		} else {
			this.addActionError(result);
			zhangHaoList = zhangHaoDao.getAll(DhCommonApiBiz.ACCOUNT_TYPE, null);
			return INPUT;
		}
	}
	
	public String delete() {
		ProductStatus productStatus = productStatusDao.get(id);
		productStatusDao.delete(productStatus);
		Struts2Utils.renderJson("删除产品成功", true);
		return null;
	}
	
	public String upself() {
		ProductStatus productStatus = productStatusDao.get(id);
		ZhangHao dhAccount = zhangHaoDao.get(productStatus.getZhangHaoId());
		
		List<String> itemcodeList = new ArrayList<String>();
		itemcodeList.add(productStatus.getDhId());
		boolean success = dhProductApiBiz.upshelfProducts((String[]) itemcodeList.toArray(), dhAccount);
		if (success) {
			productStatusBiz.checkProductStatus(productStatus);
			Struts2Utils.renderJson("操作成功", true);
		} else {
			productStatusBiz.checkProductStatus(productStatus);
			Struts2Utils.renderJson("操作失败", true);
		}
		return null;
	}
	
	public String downself() {
		ProductStatus productStatus = productStatusDao.get(id);
		ZhangHao dhAccount = zhangHaoDao.get(productStatus.getZhangHaoId());
		
		List<String> itemcodeList = new ArrayList<String>();
		itemcodeList.add(productStatus.getDhId());
		boolean success = dhProductApiBiz.downshelfProducts((String[]) itemcodeList.toArray(), dhAccount);
		if (success) {
			productStatusBiz.checkProductStatus(productStatus);
			Struts2Utils.renderJson("操作成功", true);
		} else {
			productStatusBiz.checkProductStatus(productStatus);
			Struts2Utils.renderJson("操作失败", true);
		}
		return null;
	}
	
	public ProductStatusDao getProductStatusDao() {
		return productStatusDao;
	}

	public void setProductStatusDao(ProductStatusDao productStatusDao) {
		this.productStatusDao = productStatusDao;
	}

	public ZhangHaoDao getZhangHaoDao() {
		return zhangHaoDao;
	}

	public void setZhangHaoDao(ZhangHaoDao zhangHaoDao) {
		this.zhangHaoDao = zhangHaoDao;
	}

	public Long getZhangHaoId() {
		return zhangHaoId;
	}

	public void setZhangHaoId(Long zhangHaoId) {
		this.zhangHaoId = zhangHaoId;
	}

	public Integer getDhStatus() {
		return dhStatus;
	}

	public void setDhStatus(Integer dhStatus) {
		this.dhStatus = dhStatus;
	}

	public Integer getAliStatus() {
		return aliStatus;
	}

	public void setAliStatus(Integer aliStatus) {
		this.aliStatus = aliStatus;
	}

	public String getUrlKeyword() {
		return urlKeyword;
	}

	public void setUrlKeyword(String urlKeyword) {
		this.urlKeyword = urlKeyword;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public List<ZhangHao> getZhangHaoList() {
		return zhangHaoList;
	}

	public void setZhangHaoList(List<ZhangHao> zhangHaoList) {
		this.zhangHaoList = zhangHaoList;
	}

	public String getDhUrl() {
		return dhUrl;
	}

	public void setDhUrl(String dhUrl) {
		this.dhUrl = dhUrl;
	}

	public String getAliUrl() {
		return aliUrl;
	}

	public void setAliUrl(String aliUrl) {
		this.aliUrl = aliUrl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(Integer statusFlag) {
		this.statusFlag = statusFlag;
	}
	
}
