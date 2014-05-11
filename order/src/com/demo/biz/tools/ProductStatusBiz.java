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
	 * ��Ӳ�Ʒ
	 * @param dhUrl �ػͲ�Ʒҳ���ַ
	 * @param aliUrl ����ͨ��Ʒҳ���ַ
	 * @param zhangHao �����˺�
	 * @param success-�ɹ�, ����-��������
	 */
	public String addProdcut(String dhUrl, String aliUrl, ZhangHao zhangHao) {
		String dhId = dhUrl.substring(dhUrl.lastIndexOf("/") + 1, dhUrl.lastIndexOf(".htm"));
		try {
			Integer.parseInt(dhId);
		} catch (Exception e) {
			return "���������޷������ػͲ�Ʒ���룬����ػͲ�Ʒҳ���ַ�Ƿ���д��ȷ";
		}
		return this.addProduct(dhId, dhUrl, aliUrl, zhangHao);
	}
	
	/**
	 * ��Ӳ�Ʒ
	 * @param dhId �ػͲ�Ʒ����
	 * @param dhUrl �ػͲ�Ʒҳ���ַ
	 * @param aliUrl ����ͨ��Ʒҳ���ַ
	 * @param zhangHao �����˺�
	 * @param success-�ɹ�, ����-��������
	 */
	public String addProduct(String dhId, String dhUrl, String aliUrl, 
			ZhangHao zhangHao) {
		if (productStatusDao.isExist(dhUrl, aliUrl)) {
			return "�������󣺲�Ʒҳ���ַ�Ѵ��ڣ������ظ����";
		}
		
		// ��Ʒ���
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
	 * ���״̬
	 * @param productStatus
	 */
	public void checkProductStatus(ProductStatus productStatus) {
		// ��ȡ�ػͲ�Ʒ״̬
		String dhHtml = HttpClientUtils.doGetHtml(productStatus.getDhUrl());
		Integer dhStatus = this.getDhStatusByHtml(productStatus.getDhId(), dhHtml);
		String dhImgUrl = this.getDhImgUrlByHtml(dhHtml);
		
		// ��ȡ����ͨ��Ʒ״̬
		String aliHtml = HttpClientUtils.doGetHtml(productStatus.getAliUrl());
		Integer aliStatus = this.getAliStatusByHtml(aliHtml);
		String aliImgUrl = this.getAliImgUrlByHtml(aliHtml);
		
		// ��Ʒ�������
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
	 * ��ȡ�ػͲ�Ʒ״̬
	 * @param dhId
	 * @param html
	 * @return 0.�¼�, 1.�ϼ�, 2.��ȡʧ��
	 */
	public Integer getDhStatusByHtml(String dhId, String html) {
		// ����Ƿ��ǲ�Ʒҳ��
		if (!html.contains("var _itemcode = '" + dhId + "'")) {
			return 2;
		}
		// ������¼�״̬
		if (html.contains("Buy it Now")) {
			return 1;
		} else {
			return 0;
		}
	}
	
	/**
	 * ��ȡ����ͨ��Ʒ״̬
	 * @param html
	 * @return 0.�¼�, 1.�ϼ�, 2.��ȡʧ��
	 */
	public Integer getAliStatusByHtml(String html) {
		// ����Ƿ��ǲ�Ʒҳ��
		if (!html.contains("<h1 class=\"product-name\"")) {
			return 2;
		}
		
		// ������¼�״̬
		if (html.contains("window.runParams.offline=false;")) {
			return 1;
		} else if (html.contains("window.runParams.offline=true;")) {
			return 0;
		} else {
			return 2;
		}
	}
	
	/**
	 * ��ȡ�ػͲ�ƷͼƬ��ַ
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
	 * ��ȡ����ͨ��ƷͼƬ��ַ
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
