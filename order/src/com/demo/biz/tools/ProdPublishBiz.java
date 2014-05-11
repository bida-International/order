package com.demo.biz.tools;

import java.util.Date;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.demo.bean.OptResult;
import com.demo.biz.dhgate.DhProductApiBiz;
import com.demo.dao.tools.ProductStatusDao;
import com.demo.entity.ZhangHao;
import com.demo.entity.tools.ProductStatus;
import com.demo.utils.ApplicationUtils;
import com.demo.vo.dhgate.Product;

@Service
public class ProdPublishBiz {

	@Resource
	private ProductStatusDao productStatusDao;
	@Resource
	private ProductHtmlReader productHtmlReader;
	@Resource
	private DhProductApiBiz dhProductApiBiz;
	@Resource
	private ProductStatusBiz productStatusBiz;
	
	public OptResult doPublish(String aliUrl, String pubCateId,
			String shippingTemplateId, String productGroupId, ZhangHao dhAccount) {
		try {
			if (productStatusDao.isExist(aliUrl)) {
				return new OptResult(0, "该产品已在产品库中");
			}
			
			Product product = new Product();
			product.setCatePubId(pubCateId);
			product.setShippingModelId(shippingTemplateId);
			
			if (productGroupId != null && !productGroupId.equals("0")) {
				product.setProductGroupId(productGroupId);
			}
			
			// 采集产品信息
			OptResult optResult =  productHtmlReader.read(aliUrl, product, dhAccount);
			if (optResult.getResult() == 0) {
				return optResult;
			}
			
			System.out.println(JSONObject.fromObject(product).toString());
			// 发布产品
			JSONObject respJson = dhProductApiBiz.add(product, dhAccount);
			if (respJson == null) {
				return new OptResult(0, "调用发布接口时出错，请检查授权信息是否可用或者网络通信是否正常");
			}
			
			String errMsg = null;
			JSONObject statusObj = respJson.getJSONObject("status");
			if (statusObj.containsKey("subErrors") &&
					!statusObj.getString("subErrors").equals("null")) {
				JSONArray errJsonArr = statusObj.getJSONArray("subErrors");
				if (errJsonArr.size() > 0) {
					errMsg = errJsonArr.getJSONObject(0).getString("message");
				}
			}
			if (errMsg != null) {
				return new OptResult(0, errMsg);
			}
			
			Integer code = Integer.parseInt(statusObj.getString("code"));
			if (code != 0) {
				errMsg = statusObj.getString("message");
				return new OptResult(0, errMsg);
			}
			
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
			productStatusBiz.addProdcut(dhProductUrl, aliUrl, dhAccount);
			ProductStatus productStatus = new ProductStatus();
			productStatus.setDhId(itemCode);
			productStatus.setDhUrl(dhProductUrl);
			productStatus.setDhImgUrl(dhImgUrl);
			productStatus.setDhStatus(dhStatus);
			productStatus.setAliUrl(aliUrl);
			productStatus.setAliImgUrl(productStatusBiz.getAliImgUrl(aliUrl));
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
			productStatusDao.merge(productStatus);
			return new OptResult(1, "发布产品成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new OptResult(0, "发生异常:" + e.getMessage());
		}
	}
	
	/**
	 * 获取批量发布产品的进度
	 * @param sessionId
	 * @return
	 */
	public String getPublishProgress(String sessionId) {
		Object obj = ApplicationUtils.get("ProdPublish_Percent_" + sessionId);
		if (obj == null) {
			return "0.0%,0,0";
		} else {
			return (String) obj;
		}
	}
	
	/**
	 * 停止批量发布产品
	 * @param sessionId
	 */
	public void stopPublish(String sessionId) {
		ApplicationUtils.put("ProdPublish_StopFlag_"
				+ sessionId, 1);
	}
}
