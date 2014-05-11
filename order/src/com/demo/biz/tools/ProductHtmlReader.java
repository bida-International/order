package com.demo.biz.tools;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeList;
import org.springframework.stereotype.Service;

import com.demo.bean.OptResult;
import com.demo.biz.dhgate.DhAlbumApiBiz;
import com.demo.biz.dhgate.DhCategoryApiBiz;
import com.demo.dao.tools.ProdImgUploadLogDao;
import com.demo.entity.ZhangHao;
import com.demo.entity.tools.ProdImgUploadLog;
import com.demo.utils.HttpClientUtils;
import com.demo.vo.dhgate.Product;
import com.demo.vo.dhgate.product.ProdAttach;
import com.demo.vo.dhgate.product.ProdAttr;
import com.demo.vo.dhgate.product.ProdAttrVal;
import com.demo.vo.dhgate.product.ProdBase;
import com.demo.vo.dhgate.product.ProdInventory;
import com.demo.vo.dhgate.product.ProdPackage;
import com.demo.vo.dhgate.product.ProdSaleSetting;
import com.demo.vo.dhgate.product.ProdSku;
import com.demo.vo.dhgate.product.ProdSkuAttrval;
import com.demo.vo.dhgate.product.ProdSpecSelfDef;
import com.demo.vo.dhgate.product.ProdWeightRangeVo;
import com.demo.vo.dhgate.product.ProdWholesaleRange;

/**
 * 从速卖通的产品页面读取出要发布到敦煌网的产品信息
 *
 */
@Service
public class ProductHtmlReader {

	@Resource
	private DhAlbumApiBiz dhAlbumApiBiz;
	@Resource
	private DhCategoryApiBiz dhCategoryApiBiz;
	@Resource
	private ProdImgUploadLogDao prodImgUploadLogDao;
	@Resource
	private ProductStatusBiz productStatusBiz;
	
	private ZhangHao dhAccount;
	private String pubCateId;
	
	public OptResult read(String aliUrl, Product product, ZhangHao dhAccount) {
		this.dhAccount = dhAccount;
		this.pubCateId = product.getCatePubId();

		String html = HttpClientUtils.doGetHtml(aliUrl);
		Integer aliStatus = productStatusBiz.getAliStatusByHtml(html);
		if (aliStatus != 1) {
			return new OptResult(0, "该速卖通产品不是上架状态");
		}
		
		OptResult setResult = this.setProdSkusSpecAndSaleRanges(html, product); // 产品sku, 自定义规格和价格区间
		if (setResult.getResult() == 0) {
			return setResult;
		}
		
		// 产品图片列表
		List<ProdAttach> prodAttachList = this.getProdAttachs(html);
		if (prodAttachList.isEmpty()) {
			return new OptResult(0, "获取不到产品图片信息");
		}
		product.setImageUrl(prodAttachList.get(0).getFileUrl());
		product.setImageMd5(prodAttachList.get(0).getImageMd5());
		product.setProdAttachList(prodAttachList);
		
		product.setProdAttrList(this.getProdAttrs(html)); // 产品属性列表
		product.setProdBase(this.getProdBase(html, aliUrl)); // 产品基础信息
		product.setProdInventory(this.getProdInventory()); // 产品备货信息
		product.setProdPackage(this.getProdPackage(html)); // 产品包裹信息
		product.setProdSaleSetting(this.getProdSaleSetting()); // 产品销售属性信息
		product.setVaildday(90); // 产品有效期
		product.setSupplierId(dhAccount.getUser_id()); // 卖家id
		return new OptResult(1, "产品信息解析成功");
	}
	
	/** 取图片列表 */
	private List<ProdAttach> getProdAttachs(String html) {
		List<ProdAttach> prodAttachList = new ArrayList<ProdAttach>();
		List<String> aliImgUrls = this.getAliImgUrls(html);
		for (String imgUrl : aliImgUrls) {
			String[] uploadResult = this.uploadImg(imgUrl);
			if (!uploadResult[0].equals("0")) {
				ProdAttach prodAttach = new ProdAttach();
				prodAttach.setFileUrl(uploadResult[1]);
				prodAttach.setImageMd5(uploadResult[2]);
				prodAttachList.add(prodAttach);
			}
		}
		return prodAttachList;
	}
	
	/** 取速卖通图片列表 */
	private List<String> getAliImgUrls(String html) {
		String regx = "<li class=\"image-nav-item\"(.*)src=\"(.*?)\"/></span></li>";
		Pattern p = Pattern.compile(regx);
		Matcher macher = p.matcher(html);
		
		List<String> imgUrls = new ArrayList<String>();
		while (macher.find()) {
			String imgUrl = macher.group(2);
			imgUrl = imgUrl.substring(0, imgUrl.lastIndexOf("_"));
			imgUrls.add(imgUrl);
		}
		
		// 只有一张图片的情况
		if (imgUrls.isEmpty()) {
			regx = "<img(.*?)src=\"(.*?)\" data-role=\"thumb\" />";
			p = Pattern.compile(regx);
			macher = p.matcher(html);
			if (macher.find()) {
				String imgUrl = macher.group(2);
				imgUrl = imgUrl.substring(0, imgUrl.lastIndexOf("_"));
				imgUrls.add(imgUrl);
			}
		}
		
		return imgUrls;
	}
	
	/** 上传图片到敦煌 返回参数1: 0.失败  返回参数2: imgUrl 返回参数3: imgMd5 */
	private String[] uploadImg(String imgUrl) {
		// 先从上传历史日志里面查询
		ProdImgUploadLog uploadLog = prodImgUploadLogDao.findUniqueByAliImgUrl(imgUrl, dhAccount.getId());
		if (uploadLog != null) {
			return new String[] {"1", uploadLog.getDhImgUrl(), uploadLog.getDhImgMd5()};
		}
		
		JSONObject respJson = dhAlbumApiBiz.upload(imgUrl, dhAccount);
		if (respJson == null) {
			return new String[] {"0", "", ""};
		}
		
		String result = respJson.getJSONObject("productImg").getString("result");
		String dhImgUrl = respJson.getJSONObject("productImg").getString("l_imgurl");
		String dhImgMd5 = respJson.getJSONObject("productImg").getString("l_imgmd5");
		if (dhImgUrl.equals("null") || dhImgMd5.equals("null")) {
			result = "0";
		} else {
			// 保存上传日志
			uploadLog = new ProdImgUploadLog();
			uploadLog.setAliImgUrl(imgUrl);
			uploadLog.setDhImgUrl(dhImgUrl);
			uploadLog.setDhImgMd5(dhImgMd5);
			uploadLog.setZhangHaoId(dhAccount.getId());
			uploadLog.setUploadTime(new Date().getTime());
			prodImgUploadLogDao.merge(uploadLog);
		}
		return new String[] {result, dhImgUrl, dhImgMd5};
	}
	
	/** 取产品属性列表 */
	private List<ProdAttr> getProdAttrs(String html) {
		String regx = "<div class=\"ui-box ui-box-normal product-params\">([\\s\\S]*)<div id=\"custom-description\"";
		Pattern p = Pattern.compile(regx);
		Matcher macher = p.matcher(html);
		String itemSpecHtml = "";
		if (macher.find()) {
			itemSpecHtml = macher.group(1);
		}

		List<String> attrNameList = new ArrayList<String>();
		regx = "<dt>(.*?)</dt>";
		p = Pattern.compile(regx);
		macher = p.matcher(itemSpecHtml);
		while (macher.find()) {
			String attrName = macher.group(1).replace(":", "").trim();
			if (attrName.contains("Brand") || attrName.contains("brand")) {
				attrName = "brand";
			}
			attrNameList.add(attrName);
		}
		
		if (attrNameList.isEmpty()) {
			return null;
		}
		List<String> attrValList = new ArrayList<String>();
		regx = "<dd(.*?)>(.*?)</dd>";
		p = Pattern.compile(regx);
		macher = p.matcher(itemSpecHtml);
		while (macher.find()) {
			attrValList.add(macher.group(2).trim());
		}

		List<ProdAttr> prodAttrList = new ArrayList<ProdAttr>(); // 最后生成的属性列表
		
		ProdAttr customProdAttr = new ProdAttr(); // 自定义属性
		Long customAttrId = 11l;
		Long customAttrValId = 1l;
		Integer customAttrNum = 0; // 自定义属性个数, 最多
		customProdAttr.setAttrId(customAttrId);
		customProdAttr.setIsbrand(0);
		customProdAttr.setProdAttrValList(new ArrayList<ProdAttrVal>());

		// 遍历属性列表, 找到对应属性名称ID和值ID
		for (int i = 0; i < attrNameList.size(); i++) {
			String attrName = attrNameList.get(i);
			String attrVal = attrValList.get(i);
			ProdAttr prodAttr = new ProdAttr();
			if (attrName.equalsIgnoreCase("brand")) {
				prodAttr.setIsbrand(1);
			} else {
				prodAttr.setIsbrand(0);
			}
			List<ProdAttrVal> prodAttrValList = new ArrayList<ProdAttrVal>();
			ProdAttrVal prodAttrVal = this.getProdAttrVal(attrName, attrVal); // 取对应的属性值
			if (prodAttrVal != null) { // 系统定义属性
				prodAttrValList.add(prodAttrVal);
				prodAttr.setProdAttrValList(prodAttrValList);
				prodAttr.setAttrId(prodAttrVal.getAttrId());
				prodAttrList.add(prodAttr);
			} else { // 自定义属性
				if (customAttrNum == 10) { // 最多10个自定义属性
					continue;
				}
				if (attrName.equalsIgnoreCase("brand")) {
					continue;
				}
				
				// 检查属性值长度, 不超过40
				while (attrVal.length() > 40) {
					if (attrVal.indexOf(",") > 0) {
						attrVal = attrVal.substring(0, attrVal.lastIndexOf(","));
					} else {
						attrVal = attrVal.substring(0, 39);
					}
				}
				
				prodAttrVal = new ProdAttrVal();
				prodAttrVal.setAttrId(customAttrId);
				prodAttrVal.setAttrName(attrName);
				prodAttrVal.setAttrValId(customAttrValId);
				prodAttrVal.setLineAttrvalName(attrVal);
				prodAttrVal.setLineAttrvalNameCn(attrVal);
				customProdAttr.getProdAttrValList().add(prodAttrVal);
				customAttrValId++;
				customAttrNum++;
			}
		}
		if (!customProdAttr.getProdAttrValList().isEmpty()) {
			prodAttrList.add(customProdAttr);
		}
		
		// 检查类目必填项是否都已设置
		JSONArray jsonAttrList = this.getCateAttribueList();
		for (int i = 0; i < jsonAttrList.size(); i++) {
			JSONObject jsonAttr = jsonAttrList.getJSONObject(i);
			if (jsonAttr.getInt("required") == 1 &&
					!this.attrHasAdded(jsonAttr.getLong("attrId"), prodAttrList)) {
				Long attrValId = 0l;
				ProdAttr prodAttr = new ProdAttr();
				if (jsonAttr.getString("lineAttrName").equalsIgnoreCase("brand")) {
					prodAttr.setIsbrand(1);
					attrValId = 99l;
				} else {
					prodAttr.setIsbrand(0);
				}
				prodAttr.setAttrId(jsonAttr.getLong("attrId"));
				List<ProdAttrVal> prodAttrValList = new ArrayList<ProdAttrVal>();
				ProdAttrVal prodAttrVal = new ProdAttrVal();
				prodAttrVal.setAttrId(jsonAttr.getLong("attrId"));
				prodAttrVal.setAttrName(jsonAttr.getString("lineAttrName"));
				prodAttrVal.setAttrValId(attrValId);
				prodAttrVal.setLineAttrvalName("");
				prodAttrVal.setLineAttrvalNameCn("");
				prodAttrValList.add(prodAttrVal);
				prodAttr.setProdAttrValList(prodAttrValList);
				prodAttrList.add(prodAttr);
			}
		}
		return prodAttrList;
	}
	
	/** 取属性及值 */
	private ProdAttrVal getProdAttrVal(String attrName, String attrVal) {
		ProdAttrVal prodAttrVal = null;
		
		JSONArray jsonAttrList = this.getCateAttribueList();
		for (int i = 0; i < jsonAttrList.size(); i++) {
			JSONObject jsonAttr = jsonAttrList.getJSONObject(i);
			if (jsonAttr.getString("lineAttrName").equals(attrName) ||
					jsonAttr.getString("lineAttrNameCn").equals(attrName)) {
				
				prodAttrVal = new ProdAttrVal();
				prodAttrVal.setAttrId(jsonAttr.getLong("attrId"));
				prodAttrVal.setAttrName(attrName);
				
				JSONArray jsonAttrValList = jsonAttr.getJSONArray("valueList");
				for (int j = 0; j < jsonAttrValList.size(); j++) {
					JSONObject jsonAttrVal = jsonAttrValList.getJSONObject(j);
					if (jsonAttrVal.get("lineAttrvalName").equals(attrVal) ||
							jsonAttrVal.get("lineAttrvalNameCn").equals(attrVal)) {
						prodAttrVal.setAttrValId(jsonAttrVal.getLong("attrValId"));
						prodAttrVal.setLineAttrvalName(jsonAttrVal.getString("lineAttrvalName"));
						prodAttrVal.setLineAttrvalNameCn(jsonAttrVal.getString("lineAttrvalNameCn"));
						return prodAttrVal;
					}
				}
				
				// 自定义属性值
				prodAttrVal.setAttrValId(0l);
				prodAttrVal.setLineAttrvalName(attrVal);
				prodAttrVal.setLineAttrvalNameCn(attrVal);
				return prodAttrVal;
			}
		}
		return null;
	}
	
	/** 取类目下的产品属性列表 */
	private JSONArray getCateAttribueList() {
		JSONObject json = dhCategoryApiBiz.getCategory(pubCateId, dhAccount);
		System.out.println(json.toString());
		if (json != null && json.getJSONObject("categoryPublish").containsKey("attributeList")) {
			return json.getJSONObject("categoryPublish").getJSONArray("attributeList");
		}
		return null;
	}
	
	/** 检查属性是否已经添加好(针对类目必填属性) */
	private boolean attrHasAdded(Long attrId, List<ProdAttr> prodAttrList) {
		for (ProdAttr prodAttr : prodAttrList) {
			if (prodAttr.getAttrId().equals(attrId)) {
				return true;
			}
		}
		return false;
	}
	
	/** 取产品基本属性 */
	private ProdBase getProdBase(String html, String url) {
		String productId = this.getProductId(url);
		ProdBase prodBase = new ProdBase();
		prodBase.setHtmlContent(this.getDetailHtmlContent(productId));
		prodBase.setKeyWords(this.getKeywords(html));
		prodBase.setProductName(this.getProductName(html));
		prodBase.setShortDesc(this.getShortDesc(html));
		return prodBase;
	}
	
	/** 取产品id */
	private String getProductId(String url) {
		return url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".htm"));
	}
	
	/** 取产品详情 */
	private String getDetailHtmlContent(String productId) {
		String url = "http://www.aliexpress.com/getDescModuleAjax.htm?productId=" + productId 
				+ "&productSrc=&_=" + new Date().getTime();
		String html = HttpClientUtils.doGetHtml(url);
		html = html.replaceAll("\r\n\r\n\n \n    window.productDescription='", "")
			.replaceAll("';\n \n\n", "");
		return this.replaceImgStr(html);
	}
	
	/** 替换详情里面的图片地址 */
	private  String replaceImgStr(String resource) {  
		 try {
			 Parser myParser = new Parser(resource);
			 String filterStr = "img"; // 这里析取得是标签为img的元素
		             
			 NodeFilter filter = new TagNameFilter(filterStr); // 过滤这个标签
			 NodeList nodeList = myParser.extractAllNodesThatMatch(filter); // 抽取所有img列表
			 for (int i = 0; i < nodeList.size(); i++) {
				 ImageTag img = (ImageTag) nodeList.elementAt(i);
				 String[] uploadResult = this.uploadImg(img.getImageURL());
				 if (!uploadResult[0].equals("0")) {
					String newImgUrl = "http://image.dhgate.com/" + uploadResult[1] + "/1.0x0.jpg";	
					resource= resource.replace(img.getImageURL(), newImgUrl);
				}
			 }
		
		 } catch (Exception e) {
			 e.printStackTrace();
		}
		return  resource;
	}
	
	/** 取产品关键字 */
	private String getKeywords(String html) {
		String regx = "<meta name=\"keywords\" content=\"(.*?)\"";
		Pattern p = Pattern.compile(regx);
		Matcher macher = p.matcher(html);
		if (macher.find()) {
			String keywords = macher.group(1);
			if (keywords.length() > 100) {
				keywords = keywords.substring(0, 99);
			}
			return keywords;
		}
		return null;
	}
	
	/** 取产品名称 */
	private String getProductName(String html) {
		String regx = "<h1 class=\"product-name\"(.*?)>(.*?)</h1>";
		Pattern p = Pattern.compile(regx);
		Matcher macher = p.matcher(html);
		if (macher.find()) {
			return macher.group(2);
		}
		return null;
	}
	
	/** 取产品简短描述 */
	private String getShortDesc(String html) {
		String regx = "<meta name=\"description\" content=\"([\\s\\S]*)<meta name=\"";
		Pattern p = Pattern.compile(regx);
		Matcher macher = p.matcher(html);
		if (macher.find()) {
			return macher.group(1).replaceAll("\" />\r\n", "");
		}
		return null;
	}
	
	/** 取产品备货信息 */
	private ProdInventory getProdInventory() {
		ProdInventory prodInventory = new ProdInventory();
		prodInventory.setInventoryStatus(0);
		return prodInventory;
	}
	
	/** 取包裹信息 */
	private ProdPackage getProdPackage(String html) {
		String regx = "<div class=\"ui-box ui-box-normal pnl-packaging\">([\\s\\S]*)<div id=\"transaction-history\"";
		Pattern p = Pattern.compile(regx);
		Matcher macher = p.matcher(html);
		String packagingHtml = "";
		if (macher.find()) {
			packagingHtml = macher.group(1);
		}
		
		List<String> attrNameList = new ArrayList<String>();
		regx = "<dt>(.*?)</dt>";
		p = Pattern.compile(regx);
		macher = p.matcher(packagingHtml);
		while (macher.find()) {
			attrNameList.add(macher.group(1).replace(":", "").trim());
		}
		
		if (attrNameList.isEmpty()) {
			return null;
		}
		List<String> attrValList = new ArrayList<String>();
		regx = "<dd(.*?)>(.*?)</dd>";
		p = Pattern.compile(regx);
		macher = p.matcher(packagingHtml);
		while (macher.find()) {
			attrValList.add(macher.group(2).trim());
		}

		ProdPackage prodPackage = new ProdPackage();
		prodPackage.setLots(1);
		prodPackage.setPackingQuantity(1);
		for (int i = 0; i < attrNameList.size(); i++) {
			String attrName = attrNameList.get(i);
			String attrVal = attrValList.get(i);
			if (attrName.equals("Unit Type")) {
				prodPackage.setMeasureId(attrVal);
			} else if (attrName.equals("Package Weight")) {
				String temp = attrVal.substring(0, attrVal.indexOf(" ("));
				prodPackage.setGrossWeight(Double.parseDouble(temp.replace("kg", "")));
			} else if (attrName.equals("Package Size")) {
				String temp = attrVal.substring(0, attrVal.indexOf(" ("));
				String[] parts = temp.split(" x ");
				Double length = Double.parseDouble(parts[0].replace("cm", "").trim());
				Double width = Double.parseDouble(parts[1].replace("cm", "").trim());
				Double height = Double.parseDouble(parts[2].replace("cm", "").trim());
				prodPackage.setWidth(width);
				prodPackage.setLength(length);
				prodPackage.setHeight(height);
			}
		}
		
		//List<ProdWeightRangeVo> prodWeightRangeDto = new ArrayList<ProdWeightRangeVo>();
		ProdWeightRangeVo prodWeightRangeVo = new ProdWeightRangeVo();
		Double baseQt = prodPackage.getLength() * prodPackage.getWidth() * prodPackage.getHeight();
		prodWeightRangeVo.setBaseQt(baseQt.longValue());
		prodWeightRangeVo.setIsOnlyWeight("1");
		prodWeightRangeVo.setStepQt(1l);
		prodWeightRangeVo.setStepWeight(prodPackage.getGrossWeight());
		//prodWeightRangeDto.add(prodWeightRangeVo);
		prodPackage.setProdWeightRangeDto(prodWeightRangeVo);
		return prodPackage;
	}
	
	/** 取产品销售属性 */
	private ProdSaleSetting getProdSaleSetting() {
		ProdSaleSetting prodSaleSetting = new ProdSaleSetting();
		prodSaleSetting.setLeadingTime(5);
		prodSaleSetting.setMaxSaleQty(9999);
		prodSaleSetting.setPriceConfigType(2);
		return prodSaleSetting;
	}
	
	/** 设置产品sku, 自定义规格和批发价格 */
	private OptResult setProdSkusSpecAndSaleRanges(String html, Product product) {
		String regx = "var skuProducts=(.*?);";
		Pattern p = Pattern.compile(regx);
		Matcher macher = p.matcher(html);
		String skuStr = "";
		if (macher.find()) {
			skuStr = macher.group(1);
		}
		
		List<ProdSku> prodSkuList = new ArrayList<ProdSku>();
		List<ProdSpecSelfDef> prodSpecList = new ArrayList<ProdSpecSelfDef>();
		List<ProdWholesaleRange> prodSaleRangeList = new ArrayList<ProdWholesaleRange>();
		Integer attrId = 9999;
		Integer attrValId = 1000;
		
		JSONArray skuJsonArr = JSONArray.fromObject(skuStr);
		for (int i = 0; i < skuJsonArr.size(); i++) {
			JSONObject skuJson = skuJsonArr.getJSONObject(i);
			String skuPropIds = skuJson.getString("skuPropIds");
			if (skuPropIds.contains(",")) { // 多个自定义规格
				return new OptResult(0, "暂不支持多个自定义规格的产品");
			}
			
			JSONObject skuVal = skuJson.getJSONObject("skuVal"); 
			Double retailPrice = skuVal.getDouble("skuPrice") * 1.3; // 零售价格 1.3倍
			
			ProdSku prodSku = new ProdSku();
			prodSku.setInventory(0);
			prodSku.setSaleStatus(1);
			prodSku.setRetailPrice(retailPrice);
			prodSkuList.add(prodSku);
			
			if (skuVal.containsKey("bulkOrder")) { // 包含批发信息
				Integer startQty = skuVal.getInt("bulkOrder");
				if (!isExistSaleRange(startQty, prodSaleRangeList)) {
					Double bulkPrice = skuVal.getDouble("skuBulkPrice") * 1.3; // 批发价格 * 1.3倍
					Double discount = (retailPrice - bulkPrice) / retailPrice;
					discount = Double.parseDouble(new DecimalFormat("#0.00").format(discount));
					
					ProdWholesaleRange prodSaleRange = new ProdWholesaleRange();
					prodSaleRange.setStartQty(startQty);
					prodSaleRange.setDiscount(discount);
					prodSaleRangeList.add(prodSaleRange);
				}
			}
			
			if (!skuPropIds.equals("")) { // 包含自定义规格
				List<ProdSkuAttrval> prodSkuAttrvalList = new ArrayList<ProdSkuAttrval>();
				ProdSkuAttrval prodSkuAttrval = new ProdSkuAttrval();
				prodSkuAttrval.setAttrId(attrId);
				prodSkuAttrval.setAttrValId(attrValId);
				prodSkuAttrval.setSizeSpecType(3);
				prodSkuAttrvalList.add(prodSkuAttrval);
				prodSku.setProdSkuAttrvalList(prodSkuAttrvalList);
				attrValId++;
				
				prodSpecList.add(this.getProdSpec(skuPropIds, html));
			}
		}
		
		product.setProdSkuList(prodSkuList);
		if (!prodSaleRangeList.isEmpty())
			product.setProdWholesaleRangeList(prodSaleRangeList);
		if (!prodSpecList.isEmpty())
			product.setProdSpecSelfDefList(prodSpecList);
		return new OptResult(1, "设置成功");
	}
	
	/** 批发价格设置是否已存在 */
	private boolean isExistSaleRange(Integer startQty, List<ProdWholesaleRange> prodSaleRangeList) {
		for (ProdWholesaleRange prodSaleRange : prodSaleRangeList) {
			if (prodSaleRange.getStartQty().equals(startQty)) {
				return true;
			}
		}
		return false;
	}
	
	/** 取自定义规格属性 */
	private ProdSpecSelfDef getProdSpec(String skuPropIds, String html) {
		String regx = "<a(.*?) id=\"sku-1-" + skuPropIds + "\"(.*?)>(.*?)</a>";
		Pattern p = Pattern.compile(regx);
		Matcher macher = p.matcher(html);
		String attrHtml = "";
		if (macher.find()) {
			attrHtml = macher.group(3);
		}
		
		String attrValName = "";
		String imgUrl = "";
		if (attrHtml.contains("<img")) {
			regx = "<img(.*?)title=\"(.*?)\"(.*?)bigpic=\"(.*?)\"";
			p = Pattern.compile(regx);
			macher = p.matcher(attrHtml);
			if (macher.find()) {
				attrValName = macher.group(2);
				imgUrl = macher.group(4);
			}
		} else {
			regx = "<span>(.*?)</span>";
			p = Pattern.compile(regx);
			macher = p.matcher(attrHtml);
			if (macher.find()) {
				attrValName = macher.group(1);
			}
		}
		
		//  上传图片
		if (!imgUrl.equals("")) {
			String[] uploadResult = this.uploadImg(imgUrl);
			if (!uploadResult[0].equals("0")) {
				imgUrl = uploadResult[1];
			}
		}
		if (imgUrl.equals("")) {
			imgUrl = null;
		}
		
		ProdSpecSelfDef prodSpec = new ProdSpecSelfDef();
		prodSpec.setAttrValName(attrValName);
		prodSpec.setPicUrl(imgUrl);
		return prodSpec;
	}
}
