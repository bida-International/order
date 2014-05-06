package com.demo.action.tools;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.demo.action.BaseAction;
import com.demo.bean.OptResult;
import com.demo.biz.dhgate.DhCategoryApiBiz;
import com.demo.biz.dhgate.DhCommonApiBiz;
import com.demo.biz.dhgate.DhProductApiBiz;
import com.demo.biz.dhgate.DhShippingApiBiz;
import com.demo.biz.tools.ProductHtmlReader;
import com.demo.biz.tools.ProductStatusBiz;
import com.demo.dao.ZhangHaoDao;
import com.demo.dao.tools.ProductStatusDao;
import com.demo.entity.ZhangHao;
import com.demo.entity.tools.ProductStatus;
import com.demo.utils.Struts2Utils;
import com.demo.vo.dhgate.Product;

/**
 * 产品状态同步功能配置页面
 *
 */
@Controller("tools.pubProductAction")
@Scope("prototype")
public class PubProductAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	@Resource
	private ZhangHaoDao zhangHaoDao;
	@Resource
	private DhCategoryApiBiz dhCategoryApiBiz;
	@Resource
	private DhShippingApiBiz dhShippingApiBiz;
	@Resource
	private DhProductApiBiz dhProductApiBiz;
	@Resource
	private ProductHtmlReader productHtmlReader;
	@Resource
	private ProductStatusBiz productStatusBiz;
	@Resource
	private ProductStatusDao productStatusDao;

	private Long zhangHaoId;
	private List<ZhangHao> zhangHaoList;
	
	public String execute() {
		setZhangHaoList(zhangHaoDao.getAll(DhCommonApiBiz.ACCOUNT_TYPE, null));
		return SUCCESS;
	}
	
	/** 取子类目列表 */
	public String getCategorys() {
		ZhangHao dhAccount = zhangHaoDao.get(zhangHaoId);
		String parentId = Struts2Utils.getParameter("parentId");
		JSONObject json = dhCategoryApiBiz.getCategorys(parentId, dhAccount);
		if (json == null) {
			Struts2Utils.renderJson("获取类目列表失败，请刷新页面重试", false);
		} else {
			Struts2Utils.renderJson(json.toString(), true);
		}
		return null;
	}
	
	/** 取运费模板 */
	public String getShippingTemplates() {
		ZhangHao dhAccount = zhangHaoDao.get(zhangHaoId);
		JSONObject json = dhShippingApiBiz.getTemplates(dhAccount);
		if (json == null) {
			Struts2Utils.renderJson("获取运费模板列表失败，请刷新页面重试", false);
		} else {
			Struts2Utils.renderJson(json.toString(), true);
		}
		return null;
	}
	
	/** 取产品组列表 */
	public String getProductGroups() {
		ZhangHao dhAccount = zhangHaoDao.get(zhangHaoId);
		JSONObject json = dhProductApiBiz.getGroups(dhAccount);
		if (json == null) {
			Struts2Utils.renderJson("获取产品组列表失败，请刷新页面重试", false);
		} else {
			Struts2Utils.renderJson(json.toString(), true);
		}
		return null;
	}
	
	public String doPublish() {
		try {
			String aliUrl = Struts2Utils.getParameter("aliUrl");
			if (productStatusDao.isExist(aliUrl)) {
				Struts2Utils.renderJson("该产品已在库中", false);
				return null;
			}
			
			ZhangHao dhAccount = zhangHaoDao.get(zhangHaoId);
			
			Product product = new Product();
			product.setCatePubId(Struts2Utils.getParameter("pubCateId"));
			product.setShippingModelId(Struts2Utils.getParameter("shippingTemplateId"));
			
			String productGroupId = Struts2Utils.getParameter("productGroupId");
			if (!productGroupId.equals("0")) {
				product.setProductGroupId(productGroupId);
			}
			
			// 采集产品信息
			OptResult optResult =  productHtmlReader.read(aliUrl, product, dhAccount);
			if (optResult.getResult() == 0) {
				Struts2Utils.renderJson(optResult.getMsg(), false);
				return null;
			}
			
			System.out.println(JSONObject.fromObject(product).toString());
			// 发布产品
			JSONObject respJson = dhProductApiBiz.add(product, dhAccount);
			if (respJson == null) {
				Struts2Utils.renderJson("调用发布接口时出错！", false);
			} else {
				String itemCode = respJson.getString("itemcode");
				String dhProductUrl = "";
				String dhImgUrl = "http://image.dhgate.com/" + product.getImageUrl() + "/1.0x0.jpg";
				Integer dhStatus = 0;
				JSONObject productDetail = dhProductApiBiz.getProduct(itemCode, dhAccount);
				if (productDetail != null) {
					dhProductUrl = "http://www.dhgate.com/" + productDetail.getJSONObject("product").getString("productUrl");
					dhStatus = productDetail.getJSONObject("product").getInt("istate");
				}
				// 入库
				ProductStatus productStatus = new ProductStatus();
				productStatus.setDhId(itemCode);
				productStatus.setDhUrl(dhProductUrl);
				productStatus.setDhImgUrl(dhImgUrl);
				productStatus.setDhStatus(dhStatus);
				productStatus.setAliUrl(aliUrl);
				productStatus.setAliStatus(1);
				productStatus.setZhangHaoId(dhAccount.getId());
				productStatus.setZhangHaoAccount(dhAccount.getAccount());
				productStatus.setCreateTime(new Date().getTime());
				productStatus.setCheckTime(new Date().getTime());
				if (dhStatus == 2) {
					productStatus.setStatusFlag(2);
				} else if (dhStatus != 1) {
					productStatus.setStatusFlag(0);
				} else if (dhStatus == 1) {
					productStatus.setStatusFlag(1);
				}
				productStatusBiz.save(productStatus);
				
				Struts2Utils.renderJson("发布产品成功！", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Struts2Utils.renderJson("发生未知错误！", false);
		}
		return null;
	}
	
	public List<ZhangHao> getZhangHaoList() {
		return zhangHaoList;
	}

	public void setZhangHaoList(List<ZhangHao> zhangHaoList) {
		this.zhangHaoList = zhangHaoList;
	}

	public Long getZhangHaoId() {
		return zhangHaoId;
	}

	public void setZhangHaoId(Long zhangHaoId) {
		this.zhangHaoId = zhangHaoId;
	}
}
