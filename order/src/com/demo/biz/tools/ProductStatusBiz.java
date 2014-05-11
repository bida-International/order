package com.demo.biz.tools;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.demo.dao.tools.ProductStatusDao;
import com.demo.entity.ZhangHao;
import com.demo.entity.tools.ProductStatus;
import com.demo.utils.HttpClientUtils;

@Service
public class ProductStatusBiz {

	@Resource
	private ProductStatusDao productStatusDao;
	
	/**
	 * 添加产品
	 * @param dhUrl 敦煌产品页面地址
	 * @param aliUrl 速卖通产品页面地址
	 * @param zhangHao 所属账号
	 * @param success-成功, 其它-发生错误
	 */
	public String addProdcut(String dhUrl, String aliUrl, ZhangHao zhangHao) {
		String dhId = dhUrl.substring(dhUrl.lastIndexOf("/") + 1, dhUrl.lastIndexOf(".htm"));
		try {
			Integer.parseInt(dhId);
		} catch (Exception e) {
			return "发生错误：无法解析敦煌产品编码，请检查敦煌产品页面地址是否填写正确";
		}
		return this.addProduct(dhId, dhUrl, aliUrl, zhangHao);
	}
	
	/**
	 * 添加产品
	 * @param dhId 敦煌产品编码
	 * @param dhUrl 敦煌产品页面地址
	 * @param aliUrl 速卖通产品页面地址
	 * @param zhangHao 所属账号
	 * @param success-成功, 其它-发生错误
	 */
	public String addProduct(String dhId, String dhUrl, String aliUrl, 
			ZhangHao zhangHao) {
		if (productStatusDao.isExist(dhUrl, aliUrl)) {
			return "发生错误：产品页面地址已存在，不能重复添加";
		}
		
		// 产品入库
		ProductStatus productStatus = new ProductStatus();
		productStatus.setDhId(dhId);
		productStatus.setDhUrl(dhUrl);
		productStatus.setAliUrl(aliUrl);
		productStatus.setCreateTime(new Date().getTime());
		productStatus.setZhangHaoId(zhangHao.getId());
		productStatus.setZhangHaoAccount(zhangHao.getAccount());
		this.checkProductStatus(productStatus);
		return "success";
	}
	
	/**
	 * 检测状态
	 * @param productStatus
	 */
	public void checkProductStatus(ProductStatus productStatus) {
		// 获取敦煌产品状态
		String dhHtml = HttpClientUtils.doGetHtml(productStatus.getDhUrl());
		Integer dhStatus = this.getDhStatusByHtml(productStatus.getDhId(), dhHtml);
		String dhImgUrl = this.getDhImgUrlByHtml(dhHtml);
		
		// 获取速卖通产品状态
		String aliHtml = HttpClientUtils.doGetHtml(productStatus.getAliUrl());
		Integer aliStatus = this.getAliStatusByHtml(aliHtml);
		String aliImgUrl = this.getAliImgUrlByHtml(aliHtml);
		
		// 产品入库或更新
		productStatus.setDhStatus(dhStatus);
		productStatus.setDhImgUrl(dhImgUrl);
		productStatus.setAliStatus(aliStatus);
		productStatus.setAliImgUrl(aliImgUrl);
		productStatus.setCheckTime(new Date().getTime());
		if (dhStatus == 2 || aliStatus == 2) {
			productStatus.setStatusFlag(2);
		} else if (dhStatus != aliStatus) {
			productStatus.setStatusFlag(0);
		} else if (dhStatus == aliStatus) {
			productStatus.setStatusFlag(1);
		}
		productStatusDao.merge(productStatus);
	}
	
	public void save(ProductStatus productStatus) {
		productStatusDao.merge(productStatus);
	}
	
	/**
	 * 获取敦煌产品状态
	 * @param dhId
	 * @param html
	 * @return 0.下架, 1.上架, 2.获取失败
	 */
	public Integer getDhStatusByHtml(String dhId, String html) {
		// 检查是否是产品页面
		if (!html.contains("var _itemcode = '" + dhId + "'")) {
			return 2;
		}
		// 检查上下架状态
		if (html.contains("Buy it Now")) {
			return 1;
		} else {
			return 0;
		}
	}
	
	/**
	 * 获取速卖通产品状态
	 * @param html
	 * @return 0.下架, 1.上架, 2.获取失败
	 */
	public Integer getAliStatusByHtml(String html) {
		// 检查是否是产品页面
		if (!html.contains("<h1 class=\"product-name\"")) {
			return 2;
		}
		
		// 检查上下架状态
		if (html.contains("window.runParams.offline=false;")) {
			return 1;
		} else if (html.contains("window.runParams.offline=true;")) {
			return 0;
		} else {
			return 2;
		}
	}
	
	/**
	 * 获取敦煌产品图片地址
	 * @param html
	 * @return
	 */
	public String getDhImgUrlByHtml(String html) {
		String regx = "<li s-init=\"(.*)\"></li>";
		Pattern p = Pattern.compile(regx);
		Matcher macher = p.matcher(html);
		
		if (macher.find()) {
			return macher.group(1);
		}
		return null;
	}
	
	public String getAliImgUrl(String aliUrl) {
		String html = HttpClientUtils.doGetHtml(aliUrl);
		return this.getAliImgUrlByHtml(html);
	}
	
	/**
	 * 获取速卖通产品图片地址
	 * @param html
	 * @return
	 */
	public String getAliImgUrlByHtml(String html) {
		String regx = "<li class=\"image-nav-item\"(.*)src=\"(.*?)\"/></span></li>";
		Pattern p = Pattern.compile(regx);
		Matcher macher = p.matcher(html);
		
		String imgUrl = null;
		if (macher.find()) {
			imgUrl = macher.group(2);
			imgUrl = imgUrl.substring(0, imgUrl.lastIndexOf("_"));
			return imgUrl;
		}
		
		regx = "<img(.*?)src=\"(.*?)\" data-role=\"thumb\" />";
		p = Pattern.compile(regx);
		macher = p.matcher(html);
		if (macher.find()) {
			imgUrl = macher.group(2);
			imgUrl = imgUrl.substring(0, imgUrl.lastIndexOf("_"));
		}
		return imgUrl;
	}
}
