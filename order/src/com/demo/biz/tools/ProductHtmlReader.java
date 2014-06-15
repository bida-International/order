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

import com.demo.bean.Constants;
import com.demo.bean.OptResult;
import com.demo.bean.pubprod.PubConfig;
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
	private PubConfig pubConfig;
	
	/**
	 * 读取速卖通产品页面，生成敦煌产品信息对象
	 * @param aliUrl
	 * @param product
	 * @param dhAccount
	 * @param pubConfig
	 * @return
	 */
	public OptResult read(String aliUrl, Product product, 
			ZhangHao dhAccount, PubConfig pubConfig) {
		this.dhAccount = dhAccount;
		this.pubCateId = product.getCatePubId();
		this.pubConfig = pubConfig;

		String html = HttpClientUtils.doGetHtml(aliUrl);
		Integer aliStatus = productStatusBiz.getAliStatusByHtml(html);
		if (aliStatus != 1) {
			return new OptResult(0, "该速卖通产品不是上架状态");
		}

		List<ProdAttr> prodAttrListFromSku = new ArrayList<ProdAttr>(); // 从sku属性解析出来的产品属性列表
		// 生成产品sku和自定义规格
		OptResult setResult = this.setProdSkusSpec(html, product, prodAttrListFromSku);
		if (setResult.getResult() == 0) {
			return setResult;
		}
		
		// 生成产品图片列表
		List<ProdAttach> prodAttachList = this.getProdAttachs(html);
		if (prodAttachList.isEmpty()) {
			return new OptResult(0, "获取不到产品图片信息");
		}
		product.setImageUrl(prodAttachList.get(0).getFileUrl());
		product.setImageMd5(prodAttachList.get(0).getImageMd5());
		product.setProdAttachList(prodAttachList);
		
		// 生成产品属性信息
		setResult = this.setProdAttrList(html, product, prodAttrListFromSku); 
		if (setResult.getResult() == 0) {
			return setResult;
		}
		
		product.setProdBase(this.getProdBase(html, aliUrl)); // 生成产品基础信息
		product.setProdInventory(this.getProdInventory()); // 生成产品备货信息
		product.setProdPackage(this.getProdPackage(html)); // 生成产品包裹信息
		
		// 生成产品销售属性信息
		Integer priceConfigType = 2; // 统一设置价格
		if (product.getProdSkuList()!= null && product.getProdSkuList().size() > 1) {
			priceConfigType = 1; // 分别设置价格
		}
		product.setProdSaleSetting(this.getProdSaleSetting(priceConfigType)); 
		
		product.setVaildday(90); // 生成产品有效期信息
		product.setSupplierId(dhAccount.getUser_id()); // 生成卖家id信息
		product.setProdWholesaleRangeList(this.getProdWolesaleRangeList()); // 生成产品批发价信息
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
	
	/** 设置产品属性列表 */
	private OptResult setProdAttrList(String html, Product product, List<ProdAttr> prodAttrListFromSku) {
		// 从速卖通html中取出显示产品属性列表的那段html代码
		String regx = "<div class=\"ui-box ui-box-normal product-params\">([\\s\\S]*)<div id=\"custom-description\"";
		Pattern p = Pattern.compile(regx);
		Matcher macher = p.matcher(html);
		String itemSpecHtml = "";
		if (macher.find()) {
			itemSpecHtml = macher.group(1);
		}

		// 从产品属性列表html取（速卖通）属性名称列表
		List<String> attrNameList = new ArrayList<String>();
		regx = "<dt>(.*?)</dt>";
		p = Pattern.compile(regx);
		macher = p.matcher(itemSpecHtml);
		while (macher.find()) {
			String attrName = macher.group(1).replace(":", "").trim();
			// 去掉span标签
			if (attrName.contains("<span")) {
				attrName = attrName.substring(attrName.indexOf(">") + 1, attrName.lastIndexOf("</span>"));
			}
			// 从速卖通与敦煌的属性名对应关系中找到真正的敦煌属性名
			String dhAttrName = Constants.prodAttrNameMap.get(attrName);
			if (dhAttrName != null) {
				attrName = dhAttrName;
			}
			attrNameList.add(attrName);
		}
		if (attrNameList.isEmpty()) {
			return new OptResult(1, "无产品属性");
		}
		
		// 从产品属性列表html取（速卖通）属性值
		List<String> attrValList = new ArrayList<String>();
		regx = "<dd(.*?)>(.*?)</dd>";
		p = Pattern.compile(regx);
		macher = p.matcher(itemSpecHtml);
		while (macher.find()) {
			attrValList.add(macher.group(2).trim());
		}

		// 将速卖通属性转换成敦煌属性
		List<ProdAttr> prodAttrList = new ArrayList<ProdAttr>();
		
		Long customAttrId = 11l; // 自定义属性ID, 固定为11
		Long customAttrValId = 1l; // 自定义属性值ID, 递增
		Integer customAttrNum = 0; // 自定义属性个数, 最多10个
		ProdAttr customProdAttr = new ProdAttr(); // 自定义属性
		customProdAttr.setAttrId(customAttrId);
		customProdAttr.setIsbrand(0);
		customProdAttr.setProdAttrValList(new ArrayList<ProdAttrVal>());

		// 遍历速卖通属性列表
		for (int i = 0; i < attrNameList.size(); i++) {
			String attrName = attrNameList.get(i);
			String attrVal = attrValList.get(i);
			
			boolean existInSkuAttr = false;
			for (ProdAttr skuProdAttr : prodAttrListFromSku) {
				if (skuProdAttr.getProdAttrValList().get(0).getAttrName().equalsIgnoreCase(attrName)) {
					existInSkuAttr = true;
					break;
				}
			}
			if (existInSkuAttr) {
				continue;
			}
			
			// 在敦煌上找到对应的属性名称ID和值ID
			ProdAttr prodAttr = this.getProdAttrFromDhgate(attrName, attrVal);
			if (prodAttr != null && prodAttr.getProdAttrValList() != null) { // 找到对应的系统属性
				prodAttrList.add(prodAttr);
			} else { // 找不到时，将该属性作为自定义属性
				if (customAttrNum == 10) { // 最多10个自定义属性
					continue;
				} else if (attrVal.length() > 40) { // 属性值长度最长为40，超过时不添加该属性
					continue;
				} else if (attrName.toLowerCase().contains("brand")) { // 自定义属性名不能包含brand
					continue;
				}
				
				if (prodAttr == null) {
					prodAttr = new ProdAttr();
					prodAttr.setAttrId(customAttrId);
					prodAttr.setIsbrand(0);
				}
				List<ProdAttrVal> prodAttrValList = new ArrayList<ProdAttrVal>();
				ProdAttrVal prodAttrVal = new ProdAttrVal();
				prodAttrVal.setAttrId(customAttrId);
				prodAttrVal.setAttrName(attrName);
				prodAttrVal.setAttrValId(customAttrValId);
				prodAttrVal.setLineAttrvalName(attrVal);
				prodAttrVal.setLineAttrvalNameCn(attrVal);
				prodAttrValList.add(prodAttrVal);
				customProdAttr.getProdAttrValList().add(prodAttrVal);
				prodAttr.setProdAttrValList(prodAttrValList);
				customAttrValId++;
				customAttrNum++;
			}
		}
		
		for (ProdAttr skuProdAttr : prodAttrListFromSku) {
			prodAttrList.add(skuProdAttr);
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
				
				ProdAttr prodAttr = new ProdAttr();
				prodAttr.setAttrId(jsonAttr.getLong("attrId"));

				List<ProdAttrVal> prodAttrValList = new ArrayList<ProdAttrVal>();
				ProdAttrVal prodAttrVal = new ProdAttrVal();
				prodAttrVal.setAttrId(jsonAttr.getLong("attrId"));
				prodAttrVal.setAttrName(jsonAttr.getString("lineAttrName"));
				if (jsonAttr.getString("lineAttrName").equalsIgnoreCase("brand")) { // 品牌属性单独处理
					prodAttr.setIsbrand(1);
					prodAttrVal.setAttrValId(99l);
					prodAttrVal.setLineAttrvalName("");
					prodAttrVal.setLineAttrvalNameCn("");
					prodAttrValList.add(prodAttrVal);
				} else { // 其它属性取第一个属性值
					JSONObject jsonAttrVal = jsonAttr.getJSONArray("valueList").getJSONObject(0);
					prodAttr.setIsbrand(0);
					prodAttrVal.setAttrValId(jsonAttrVal.getLong("attrValId"));
					prodAttrVal.setLineAttrvalName(jsonAttrVal.getString("lineAttrvalName"));
					prodAttrVal.setLineAttrvalNameCn(jsonAttrVal.getString("lineAttrvalNameCn"));
					prodAttrValList.add(prodAttrVal);
				}
				prodAttr.setProdAttrValList(prodAttrValList);
				prodAttrList.add(prodAttr);
			}
		}
		product.setProdAttrList(prodAttrList);
		return new OptResult(1, "设置产品属性成功");
	}
	
	/**
	 * 根据属性名和值查找敦煌系统对应属性及值信息，并且以指定的对象形式返回
	 * @param attrName
	 * @param attrVals 多个属性值用逗号隔开
	 * @return
	 */
	private ProdAttr getProdAttrFromDhgate(String attrName, String attrVals) {
		
		// 将速卖通属性名称替换成对应的敦煌属性名称，如:Brand Name替换成Brand
		String dhAttrName = Constants.prodAttrNameMap.get(attrName);
		if (dhAttrName != null) {
			attrName = dhAttrName;
		}
		String[] attrValArr = attrVals.split(",");
		
		// 敦煌上该产品所属类目的系统定义属性列表，该属性列表通过调用敦煌获取类目详情api来获得
		JSONArray jsonAttrList = this.getCateAttribueList();
		System.out.println(jsonAttrList.toString());
		
		for (int i = 0; i < jsonAttrList.size(); i++) {
			
			JSONObject jsonAttr = jsonAttrList.getJSONObject(i); // 属性列表
			
			// 找到对应的属性名称
			if (jsonAttr.getString("lineAttrName").equalsIgnoreCase(attrName) 
					|| jsonAttr.getString("lineAttrNameCn").equalsIgnoreCase(attrName)) {

				Long attrId = jsonAttr.getLong("attrId");
				ProdAttr prodAttr = new ProdAttr();
				prodAttr.setAttrId(attrId);
				if (attrName.equalsIgnoreCase("brand")) { // 品牌属性直接赋值并返回
					prodAttr.setIsbrand(1);
					List<ProdAttrVal> prodAttrValList = new ArrayList<ProdAttrVal>();
					ProdAttrVal prodAttrVal = new ProdAttrVal();
					prodAttrVal.setAttrId(attrId);
					prodAttrVal.setAttrName(attrName);
					prodAttrVal.setAttrValId(attrId);
					prodAttrVal.setLineAttrvalName(attrValArr[0]);
					prodAttrVal.setLineAttrvalNameCn(attrValArr[0]);
					prodAttrValList.add(prodAttrVal);
					prodAttr.setProdAttrValList(prodAttrValList);
					return prodAttr;
				} else {
					prodAttr.setIsbrand(0);
				}
				
				List<ProdAttrVal> prodAttrValList = new ArrayList<ProdAttrVal>();

				int buyAttr = jsonAttr.getInt("buyAttr"); // 1.是购买属性, 0.不是购买属性
				JSONArray jsonAttrValList = jsonAttr.getJSONArray("valueList"); // 该属性的值列表
				// 遍历系统定义的该属性值列表
				for (int j = 0; j < jsonAttrValList.size(); j++) {
					JSONObject jsonAttrVal = jsonAttrValList.getJSONObject(j);
					String lineAttrvalName = jsonAttrVal.get("lineAttrvalName").toString().toLowerCase();
					String lineAttrvalNameCn = jsonAttrVal.get("lineAttrvalNameCn").toString().toLowerCase();
					
					// 寻找对应的属性值
					boolean matched = false;
					for (String attrVal : attrValArr) {
						attrVal = attrVal.trim();
						if (buyAttr == 1) { // 购买属性采用严格匹配
							if (lineAttrvalName.equalsIgnoreCase(attrVal)
									|| lineAttrvalNameCn.equalsIgnoreCase(attrVal)) {
								matched = true;
								break;
							}
						} else { // 非购买属性采用模糊匹配
							if (lineAttrvalName.contains(attrVal.toLowerCase())
									|| lineAttrvalNameCn.contains(attrVal.toLowerCase())
									|| attrVal.toLowerCase().contains(lineAttrvalName)
									|| attrVal.toLowerCase().contains(lineAttrvalNameCn)) {
								matched = true;
								break;
							}
						}
					}
					if (matched) {
						ProdAttrVal prodAttrVal = new ProdAttrVal();
						prodAttrVal.setAttrId(attrId);
						prodAttrVal.setAttrName(attrName);
						prodAttrVal.setAttrValId(jsonAttrVal.getLong("attrValId"));
						prodAttrVal.setLineAttrvalName(jsonAttrVal.getString("lineAttrvalName"));
						prodAttrVal.setLineAttrvalNameCn(jsonAttrVal.getString("lineAttrvalNameCn"));
						prodAttrValList.add(prodAttrVal);
						
						if (jsonAttr.getInt("type") == 1) { // 允许多个属性值
							continue;
						} else {
							break;
						}
					}
				}
				
				if (!prodAttrValList.isEmpty()) {
					prodAttr.setProdAttrValList(prodAttrValList);
				}
				return prodAttr;
			}
		}
		return null;
	}
	
	/** 取属性及值 */
//	private ProdAttrVal getProdAttrVal(String attrName, String attrVal) {
//		ProdAttrVal prodAttrVal = null;
//		
//		// 从速卖通与敦煌的属性名对应关系中找到正确的敦煌属性名
//		String dhAttrName = Constants.prodAttrNameMap.get("attrName");
//		if (dhAttrName != null) {
//			attrName = dhAttrName;
//		}
//		
//		JSONArray jsonAttrList = this.getCateAttribueList();
//		for (int i = 0; i < jsonAttrList.size(); i++) {
//			JSONObject jsonAttr = jsonAttrList.getJSONObject(i);
//			if (jsonAttr.getString("lineAttrName").equalsIgnoreCase(attrName) ||
//					jsonAttr.getString("lineAttrNameCn").equalsIgnoreCase(attrName)) {
//				
//				prodAttrVal = new ProdAttrVal();
//				prodAttrVal.setAttrId(jsonAttr.getLong("attrId"));
//				prodAttrVal.setAttrName(attrName);
//				
//				JSONArray jsonAttrValList = jsonAttr.getJSONArray("valueList");
//				for (int j = 0; j < jsonAttrValList.size(); j++) {
//					JSONObject jsonAttrVal = jsonAttrValList.getJSONObject(j);
//					if (jsonAttrVal.get("lineAttrvalName").toString().toLowerCase().startsWith(attrVal.toLowerCase()) ||
//							jsonAttrVal.get("lineAttrvalNameCn").toString().toLowerCase().startsWith(attrVal.toLowerCase())) {
//						prodAttrVal.setAttrValId(jsonAttrVal.getLong("attrValId"));
//						prodAttrVal.setLineAttrvalName(jsonAttrVal.getString("lineAttrvalName"));
//						prodAttrVal.setLineAttrvalNameCn(jsonAttrVal.getString("lineAttrvalNameCn"));
//						return prodAttrVal;
//					}
//				}
//				
//				// 自定义属性值
//				if (attrName.equalsIgnoreCase("brand")) {
//					prodAttrVal.setAttrValId(99l);
//				} else {
//					prodAttrVal.setAttrValId(0l);
//				}
//				prodAttrVal.setLineAttrvalName(attrVal);
//				prodAttrVal.setLineAttrvalNameCn(attrVal);
//				return prodAttrVal;
//			}
//		}
//		return null;
//	}
	
	/** 取类目下的产品属性列表 */
	private JSONArray getCateAttribueList() {
		JSONObject json = dhCategoryApiBiz.getCategory(pubCateId, dhAccount);
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
		html = this.replaceImgStr(html);
		// 自定义替换处理
		if (pubConfig.getProdDescOrgin() != null 
				&& !pubConfig.getProdDescOrgin().equals("")
				&& pubConfig.getProdDescReplaced() != null) {
			html = html.replaceAll(pubConfig.getProdDescOrgin(), 
					pubConfig.getProdDescReplaced());
		}
		// 加前缀
		if (pubConfig.getProdDescPrefix() != null 
				&& !pubConfig.getProdDescPrefix().equals("")) {
			html = pubConfig.getProdDescPrefix() + html;
		}
		// 加后缀
		if (pubConfig.getProdDescSuffix() != null 
				&& !pubConfig.getProdDescSuffix().equals("")) {
			html = html + pubConfig.getProdDescSuffix();
		}
		return html;
	}
	
	/** 替换详情里面的图片地址 */
	private String replaceImgStr(String resource) {  
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
		return resource;
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
			String prodName = macher.group(2);
			
			// 过滤不能包含的字符
			prodName = filterWord(prodName);
			
			// 自定义替换处理
			if (pubConfig.getProdNameOrgin() != null 
					&& !pubConfig.getProdNameOrgin().equals("")
					&& pubConfig.getProdNameReplaced() != null) {
				prodName = prodName.replaceAll(pubConfig.getProdNameOrgin(), 
						pubConfig.getProdNameReplaced());
			}
			// 加前缀
			if (pubConfig.getProdNamePrefix() != null 
					&& !pubConfig.getProdNamePrefix().equals("")) {
				prodName = pubConfig.getProdNamePrefix() + prodName;
			}
			// 加后缀
			if (pubConfig.getProdNameSuffix() != null 
					&& !pubConfig.getProdNameSuffix().equals("")) {
				prodName = prodName + pubConfig.getProdNameSuffix();
			}
			return prodName;
		}
		return null;
	}
	
	/** 取产品简短描述 */
	private String getShortDesc(String html) {
		String regx = "<meta name=\"description\" content=\"([\\s\\S]*)<meta name=\"";
		Pattern p = Pattern.compile(regx);
		Matcher macher = p.matcher(html);
		if (macher.find()) {
			String shortDesc = macher.group(1).replaceAll("\" />\r\n", "");
			// 过滤不能包含的字符
			shortDesc = filterWord(shortDesc);
			return shortDesc;
		}
		return null;
	}
	
	/** 过滤不能包含的字符 */
	private String filterWord(String sourceStr) {
		String filterWords = "lingerie,costume,lingeries,costumes,underwear,bras,bra,underwears,bra set,bra sets,underwire,underwires, babydolls, babydoll,jackets,jacket.blazer,trench";
		String[] filterWordArr = filterWords.split(",");
		String[] sourceStrPartArr = sourceStr.split(" ");
		sourceStr = " " + sourceStr + " ";
		for (String sourceStrPart : sourceStrPartArr) {
			for (String filterWord : filterWordArr) {
				if (sourceStrPart.equalsIgnoreCase(filterWord)) {
					sourceStr = sourceStr.replaceAll(" " + sourceStrPart + " ", "");
				}
			}
		}
		sourceStr = sourceStr.trim();
		return sourceStr;
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
	private ProdSaleSetting getProdSaleSetting(Integer priceConfigType) {
		ProdSaleSetting prodSaleSetting = new ProdSaleSetting();
		prodSaleSetting.setLeadingTime(5);
		prodSaleSetting.setMaxSaleQty(9999);
		prodSaleSetting.setPriceConfigType(priceConfigType);
		return prodSaleSetting;
	}
	
	/** 设置产品sku, 自定义规格 */
	private OptResult setProdSkusSpec(String html, Product product, List<ProdAttr> prodAttrListFromSku) {
		String regx = "var skuProducts=(.*?);\n";
		Pattern p = Pattern.compile(regx);
		Matcher macher = p.matcher(html);
		String skuStr = "";
		if (macher.find()) {
			skuStr = macher.group(1);
		}
		
		JSONArray skuJsonArr = JSONArray.fromObject(skuStr); // 得到skuStr是一个json字符串, 将它转换成JSON数组对象
		
		String prodSkuHtml = getProdSkuHtml(html); // 取显示sku购买属性的那段html
		List<String> customSkuAttrList = new ArrayList<String>(); // 存放自定义购买属性
		
		// 有多了一组sku设置时,说明该产品有购买属性,从html中解析出sku购买属性, 并区分为系统定义和自定义购买属性
		// 将系统定义购买属性加入prodAttrListFromSku中, 最多只支持一个自定义购买属性
		if (skuJsonArr.size() > 1) { 
			this.readSkuAttrList(prodAttrListFromSku, customSkuAttrList, prodSkuHtml);
		}

		List<ProdSku> prodSkuList = new ArrayList<ProdSku>(); // Sku信息列表
		List<ProdSpecSelfDef> prodSpecList = new ArrayList<ProdSpecSelfDef>(); // 自定义规格列表
		Integer customAttrId = 9999; // 自定义属性id
		Integer customAttrValId = 1000; // 自定义(规格)属性值id, 从1000开始, 最多10个到1009
		
		for (int i = 0; i < skuJsonArr.size(); i++) {
			JSONObject skuJson = skuJsonArr.getJSONObject(i);
			JSONObject skuVal = skuJson.getJSONObject("skuVal"); 
			Double retailPrice = skuVal.getDouble("skuPrice") * 1.3; // 零售价格 1.3倍
			retailPrice = Double.parseDouble(new DecimalFormat("#0.00").format(retailPrice));
			
			ProdSku prodSku = new ProdSku();
			prodSku.setInventory(0);
			prodSku.setSaleStatus(1);
			prodSku.setRetailPrice(retailPrice);

			String skuPropIds = skuJson.getString("skuPropIds");
			if (!skuPropIds.equals("")) { // 不为空表示有sku属性, 如果有多个sku属性会以逗号隔开
				if (customSkuAttrList.size() > 1) {
					return new OptResult(0, "不支持有多个自定义(sku)购买属性的产品");
				}

				List<ProdSkuAttrval> prodSkuAttrvalList = new ArrayList<ProdSkuAttrval>();
				
				String[] skuPropIdArr = skuPropIds.split(","); // 速卖通产品sku属性Id数组
				for (int j = 0; j < skuPropIdArr.length; j++) {
					
					String skuAttrName = getSkuAttrName(j, prodSkuHtml); // 取速卖通sku属性名称
					String skuAttrValue = getSkuAttrValue(j + 1, skuPropIdArr[j], html); // 取速卖通sku属性值
					if (skuAttrName == null || skuAttrValue == null) {
						return new OptResult(0, "解析速卖通产品sku信息时发生错误, 解析后的值为null");
					}
					
					// 速卖通属性名称转换成敦煌属性名称
					String dhAttrName = Constants.prodAttrNameMap.get(skuAttrName);
					if (dhAttrName != null) {
						skuAttrName = dhAttrName;
					}

					ProdSkuAttrval prodSkuAttrval = new ProdSkuAttrval();
					if (!customSkuAttrList.contains(skuAttrName)) { // 系统定义购买属性
						
						ProdAttr prodAttr = this.getProdAttrFromDhgate(skuAttrName, skuAttrValue);
						if (prodAttr != null && prodAttr.getProdAttrValList() != null) {
							prodSkuAttrval.setAttrId(prodAttr.getAttrId().intValue());
							prodSkuAttrval.setAttrValId(prodAttr.getProdAttrValList().get(0).getAttrValId().intValue());
							prodSkuAttrval.setSizeSpecType(1);
							prodSkuAttrvalList.add(prodSkuAttrval);
						}
						
					} else { // 自定义购买属性
						
						prodSkuAttrval.setAttrId(customAttrId);
						
						Integer specAttrValId = getAttrValIdFromProdSpecList(skuAttrValue, prodSpecList);
						if (specAttrValId != null) { // 已有自定义(规格)属性值
							prodSkuAttrval.setAttrValId(specAttrValId);
							prodSkuAttrval.setSizeSpecType(3);
							
						} else {
							prodSkuAttrval.setAttrValId(customAttrValId);
							prodSkuAttrval.setSizeSpecType(3);
							customAttrValId++;
							// 添加自定义规格
							ProdSpecSelfDef prodSpec = new ProdSpecSelfDef();
							prodSpec.setAttrValName(skuAttrValue);
							prodSpecList.add(prodSpec);
						}
						
						prodSkuAttrvalList.add(prodSkuAttrval);
					}
					
//					if (prodSpecList.size() > 10) {
//						return new OptResult(0, "不支持超过10个自定义规格（自定义购买属性值）的产品");
//					}
				}
				if (prodSkuAttrvalList.size() == skuPropIdArr.length) {
					prodSku.setProdSkuAttrvalList(prodSkuAttrvalList);
				} else {
					prodSku = null;
				}
			}
			
			if (prodSku != null && !prodSkuList.contains(prodSku)) {
				prodSkuList.add(prodSku);
			}
		}
		
		product.setProdSkuList(prodSkuList);
		
		if (!prodSpecList.isEmpty())
			product.setProdSpecSelfDefList(prodSpecList);
		
		return new OptResult(1, "设置成功");
	}

	/** 检查一个属性值在现有自定义规格中是否已经存在,如果存在,返回自定义属性值ID */
	private Integer getAttrValIdFromProdSpecList(String attrVal, List<ProdSpecSelfDef> prodSpecList) {
		for (int i = 0; i < prodSpecList.size(); i++) {
			if (prodSpecList.get(i).getAttrValName().equalsIgnoreCase(attrVal)) {
				if (i >= 10)
					return Integer.parseInt("10" + i);
				else
					return Integer.parseInt("100" + i);
			}
		}
		return null;
	}
	
	/** 解析购买属性信息,区分出系统定义和自定义购买属性 */
	private void readSkuAttrList(List<ProdAttr> prodAttrListFromSku,
			List<String> customSkuAttrList, String prodSkuHtml) {
		int skuAttrCount = this.getSkuAttrCount(prodSkuHtml);
		for (int i = 0; i < skuAttrCount; i++) {
			String attrName = this.getSkuAttrName(i, prodSkuHtml);
			// 速卖通属性名称转换成敦煌属性名称
			String dhAttrName = Constants.prodAttrNameMap.get(attrName);
			if (dhAttrName != null) {
				attrName = dhAttrName;
			}
			
			// 属性值列表
			List<String> attrValList = this.getSkuAttrValueList(i + 1, prodSkuHtml);
			String attrVals = "";
			for (String attrVal : attrValList) {
				attrVals += attrVal + ",";
			}
			
			// 查找对应的敦煌定义属性, 若查找不到则划为自定义购买属性
			ProdAttr prodAttr = this.getProdAttrFromDhgate(attrName, attrVals);
			if (prodAttr == null || prodAttr.getProdAttrValList() == null) {
				customSkuAttrList.add(attrName);
			} else if (prodAttr.getProdAttrValList() != null
					&& prodAttr.getProdAttrValList().size() > 0) { 
				prodAttrListFromSku.add(prodAttr);
			}
		}
	}
	
	/** 取出显示购买属性的那段html */
	private String getProdSkuHtml(String html) {
		String regx = "<div id=\"product-info-sku\">([\\s\\S]*)<dl id=\"product-info-quantity\"";
		Pattern p = Pattern.compile(regx);
		Matcher macher = p.matcher(html);
		if (macher.find()) {
			String prodSkuHtml = macher.group(1);
			prodSkuHtml = prodSkuHtml.substring(0, prodSkuHtml.lastIndexOf("</div>"));
			return prodSkuHtml;
		}
		return null;
	}
	
	/** 获取购买属性个数 */
	private Integer getSkuAttrCount(String prodSkuHtml) {
		if (prodSkuHtml == null) {
			return null;
		}
		try {
			 Parser myParser = new Parser(prodSkuHtml);
			 String filterStr = "dt"; // 这里析取得是标签为dt的元素
		             
			 NodeFilter filter = new TagNameFilter(filterStr); // 过滤这个标签
			 NodeList nodeList = myParser.extractAllNodesThatMatch(filter); // 抽取所有img列表
			 return nodeList.size();
		 } catch (Exception e) {
			 e.printStackTrace();
		}
		return 0;
	}
	
	/** 取出一个购买属性名, index从0开始 */
	private String getSkuAttrName(int index, String prodSkuHtml) {
		if (prodSkuHtml == null) {
			return null;
		}
		try {
			 Parser myParser = new Parser(prodSkuHtml);
			 String filterStr = "dt"; // 这里析取得是标签为dt的元素
		             
			 NodeFilter filter = new TagNameFilter(filterStr); // 过滤这个标签
			 NodeList nodeList = myParser.extractAllNodesThatMatch(filter); // 抽取所有img列表
			 return nodeList.elementAt(index).toPlainTextString().replace(":", "").trim();
		 } catch (Exception e) {
			 e.printStackTrace();
		}
		return null;
	}
	
	/** 取出第几个购买属性的可选值列表, index从1开始 */
	private List<String> getSkuAttrValueList(int index, String html) {
		String regx = "<a(.*?) id=\"sku-" + index + "-(.*?)\"(.*?)>(.*?)</a>";
		Pattern p = Pattern.compile(regx);
		Matcher macher = p.matcher(html);
		
		List<String> skuAttrValueList = new ArrayList<String>();
		String attrHtml = "";
		while (macher.find()) {
			attrHtml = macher.group(4);
			
			String attrValName = null;
			if (attrHtml.contains("<img")) {
				String regx2 = "<img(.*?)title=\"(.*?)\"(.*?)bigpic=\"(.*?)\"";
				Pattern p2 = Pattern.compile(regx2);
				Matcher macher2 = p2.matcher(attrHtml);
				if (macher2.find()) {
					attrValName = macher2.group(2);
				}
			} else {
				String regx2 = "<span>(.*?)</span>";
				Pattern p2 = Pattern.compile(regx2);
				Matcher macher2 = p2.matcher(attrHtml);
				if (macher2.find()) {
					attrValName = macher2.group(1);
				}
			}
			if (attrValName != null) {
				skuAttrValueList.add(attrValName);
			}
		}
		return skuAttrValueList;
	}
	
	/** 获取某个购买属性的某个值id的值 */
	private String getSkuAttrValue(int index, String skuPropId, String html) {
		String regx = "<a(.*?) id=\"sku-" + index + "-" + skuPropId + "\"(.*?)>(.*?)</a>";
		Pattern p = Pattern.compile(regx);
		Matcher macher = p.matcher(html);
		String attrHtml = "";
		if (macher.find()) {
			attrHtml = macher.group(3);
		}
		
		String attrValName = null;
		if (attrHtml.contains("<img")) {
			regx = "<img(.*?)title=\"(.*?)\"(.*?)bigpic=\"(.*?)\"";
			p = Pattern.compile(regx);
			macher = p.matcher(attrHtml);
			if (macher.find()) {
				attrValName = macher.group(2);
			}
		} else {
			regx = "<span>(.*?)</span>";
			p = Pattern.compile(regx);
			macher = p.matcher(attrHtml);
			if (macher.find()) {
				attrValName = macher.group(1);
			}
		}
		
		return attrValName;
	}
	
	/** 批发价格设置 */
	private List<ProdWholesaleRange> getProdWolesaleRangeList() {
		if (pubConfig.getProdBulkPriceSet() == null ||
				pubConfig.getProdBulkPriceSet().equals("")) {
			return null;
		}
		
		// 确认换行符
		String enterChar = "\n";
		if (pubConfig.getProdBulkPriceSet().contains("\r\n")) {
			enterChar = "\r\n";
		} else if (pubConfig.getProdBulkPriceSet().contains("\r")) {
			enterChar = "\r";
		} else if (pubConfig.getProdBulkPriceSet().contains("\n")) {
			enterChar = "\n";
		}
		
		List<ProdWholesaleRange> prodSaleRangeList = new ArrayList<ProdWholesaleRange>();
		String[] bulkPriceSetArr = pubConfig.getProdBulkPriceSet().split(enterChar);
		for (String bulkPriceSet : bulkPriceSetArr) {
			
			bulkPriceSet = bulkPriceSet.trim();
			if (bulkPriceSet.equals("")) {
				continue;
			}
			
			String[] bulkPriceSetParams = bulkPriceSet.split(",");
			Integer startQty = Integer.parseInt(bulkPriceSetParams[0]);
			Double discount = Double.parseDouble(bulkPriceSetParams[1]) / 100d;
			discount = Double.parseDouble(new DecimalFormat("#0.00").format(discount));
			
			if (!isExistSaleRange(startQty, prodSaleRangeList)) {
				ProdWholesaleRange prodSaleRange = new ProdWholesaleRange();
				prodSaleRange.setStartQty(startQty);
				prodSaleRange.setDiscount(discount);
				prodSaleRangeList.add(prodSaleRange);
			}
		}
		if (!prodSaleRangeList.isEmpty()) {
			return prodSaleRangeList;
		}
		return null;
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
	
}
