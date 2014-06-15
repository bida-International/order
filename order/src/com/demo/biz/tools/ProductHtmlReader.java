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
 * ������ͨ�Ĳ�Ʒҳ���ȡ��Ҫ�������ػ����Ĳ�Ʒ��Ϣ
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
	 * ��ȡ����ͨ��Ʒҳ�棬���ɶػͲ�Ʒ��Ϣ����
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
			return new OptResult(0, "������ͨ��Ʒ�����ϼ�״̬");
		}

		List<ProdAttr> prodAttrListFromSku = new ArrayList<ProdAttr>(); // ��sku���Խ��������Ĳ�Ʒ�����б�
		// ���ɲ�Ʒsku���Զ�����
		OptResult setResult = this.setProdSkusSpec(html, product, prodAttrListFromSku);
		if (setResult.getResult() == 0) {
			return setResult;
		}
		
		// ���ɲ�ƷͼƬ�б�
		List<ProdAttach> prodAttachList = this.getProdAttachs(html);
		if (prodAttachList.isEmpty()) {
			return new OptResult(0, "��ȡ������ƷͼƬ��Ϣ");
		}
		product.setImageUrl(prodAttachList.get(0).getFileUrl());
		product.setImageMd5(prodAttachList.get(0).getImageMd5());
		product.setProdAttachList(prodAttachList);
		
		// ���ɲ�Ʒ������Ϣ
		setResult = this.setProdAttrList(html, product, prodAttrListFromSku); 
		if (setResult.getResult() == 0) {
			return setResult;
		}
		
		product.setProdBase(this.getProdBase(html, aliUrl)); // ���ɲ�Ʒ������Ϣ
		product.setProdInventory(this.getProdInventory()); // ���ɲ�Ʒ������Ϣ
		product.setProdPackage(this.getProdPackage(html)); // ���ɲ�Ʒ������Ϣ
		
		// ���ɲ�Ʒ����������Ϣ
		Integer priceConfigType = 2; // ͳһ���ü۸�
		if (product.getProdSkuList()!= null && product.getProdSkuList().size() > 1) {
			priceConfigType = 1; // �ֱ����ü۸�
		}
		product.setProdSaleSetting(this.getProdSaleSetting(priceConfigType)); 
		
		product.setVaildday(90); // ���ɲ�Ʒ��Ч����Ϣ
		product.setSupplierId(dhAccount.getUser_id()); // ��������id��Ϣ
		product.setProdWholesaleRangeList(this.getProdWolesaleRangeList()); // ���ɲ�Ʒ��������Ϣ
		return new OptResult(1, "��Ʒ��Ϣ�����ɹ�");
	}
	
	
	/** ȡͼƬ�б� */
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
	
	/** ȡ����ͨͼƬ�б� */
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
		
		// ֻ��һ��ͼƬ�����
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
	
	/** �ϴ�ͼƬ���ػ� ���ز���1: 0.ʧ��  ���ز���2: imgUrl ���ز���3: imgMd5 */
	private String[] uploadImg(String imgUrl) {
		// �ȴ��ϴ���ʷ��־�����ѯ
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
			// �����ϴ���־
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
	
	/** ���ò�Ʒ�����б� */
	private OptResult setProdAttrList(String html, Product product, List<ProdAttr> prodAttrListFromSku) {
		// ������ͨhtml��ȡ����ʾ��Ʒ�����б���Ƕ�html����
		String regx = "<div class=\"ui-box ui-box-normal product-params\">([\\s\\S]*)<div id=\"custom-description\"";
		Pattern p = Pattern.compile(regx);
		Matcher macher = p.matcher(html);
		String itemSpecHtml = "";
		if (macher.find()) {
			itemSpecHtml = macher.group(1);
		}

		// �Ӳ�Ʒ�����б�htmlȡ������ͨ�����������б�
		List<String> attrNameList = new ArrayList<String>();
		regx = "<dt>(.*?)</dt>";
		p = Pattern.compile(regx);
		macher = p.matcher(itemSpecHtml);
		while (macher.find()) {
			String attrName = macher.group(1).replace(":", "").trim();
			// ȥ��span��ǩ
			if (attrName.contains("<span")) {
				attrName = attrName.substring(attrName.indexOf(">") + 1, attrName.lastIndexOf("</span>"));
			}
			// ������ͨ��ػ͵���������Ӧ��ϵ���ҵ������Ķػ�������
			String dhAttrName = Constants.prodAttrNameMap.get(attrName);
			if (dhAttrName != null) {
				attrName = dhAttrName;
			}
			attrNameList.add(attrName);
		}
		if (attrNameList.isEmpty()) {
			return new OptResult(1, "�޲�Ʒ����");
		}
		
		// �Ӳ�Ʒ�����б�htmlȡ������ͨ������ֵ
		List<String> attrValList = new ArrayList<String>();
		regx = "<dd(.*?)>(.*?)</dd>";
		p = Pattern.compile(regx);
		macher = p.matcher(itemSpecHtml);
		while (macher.find()) {
			attrValList.add(macher.group(2).trim());
		}

		// ������ͨ����ת���ɶػ�����
		List<ProdAttr> prodAttrList = new ArrayList<ProdAttr>();
		
		Long customAttrId = 11l; // �Զ�������ID, �̶�Ϊ11
		Long customAttrValId = 1l; // �Զ�������ֵID, ����
		Integer customAttrNum = 0; // �Զ������Ը���, ���10��
		ProdAttr customProdAttr = new ProdAttr(); // �Զ�������
		customProdAttr.setAttrId(customAttrId);
		customProdAttr.setIsbrand(0);
		customProdAttr.setProdAttrValList(new ArrayList<ProdAttrVal>());

		// ��������ͨ�����б�
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
			
			// �ڶػ����ҵ���Ӧ����������ID��ֵID
			ProdAttr prodAttr = this.getProdAttrFromDhgate(attrName, attrVal);
			if (prodAttr != null && prodAttr.getProdAttrValList() != null) { // �ҵ���Ӧ��ϵͳ����
				prodAttrList.add(prodAttr);
			} else { // �Ҳ���ʱ������������Ϊ�Զ�������
				if (customAttrNum == 10) { // ���10���Զ�������
					continue;
				} else if (attrVal.length() > 40) { // ����ֵ�����Ϊ40������ʱ����Ӹ�����
					continue;
				} else if (attrName.toLowerCase().contains("brand")) { // �Զ������������ܰ���brand
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
		
		// �����Ŀ�������Ƿ�������
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
				if (jsonAttr.getString("lineAttrName").equalsIgnoreCase("brand")) { // Ʒ�����Ե�������
					prodAttr.setIsbrand(1);
					prodAttrVal.setAttrValId(99l);
					prodAttrVal.setLineAttrvalName("");
					prodAttrVal.setLineAttrvalNameCn("");
					prodAttrValList.add(prodAttrVal);
				} else { // ��������ȡ��һ������ֵ
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
		return new OptResult(1, "���ò�Ʒ���Գɹ�");
	}
	
	/**
	 * ������������ֵ���Ҷػ�ϵͳ��Ӧ���Լ�ֵ��Ϣ��������ָ���Ķ�����ʽ����
	 * @param attrName
	 * @param attrVals �������ֵ�ö��Ÿ���
	 * @return
	 */
	private ProdAttr getProdAttrFromDhgate(String attrName, String attrVals) {
		
		// ������ͨ���������滻�ɶ�Ӧ�Ķػ��������ƣ���:Brand Name�滻��Brand
		String dhAttrName = Constants.prodAttrNameMap.get(attrName);
		if (dhAttrName != null) {
			attrName = dhAttrName;
		}
		String[] attrValArr = attrVals.split(",");
		
		// �ػ��ϸò�Ʒ������Ŀ��ϵͳ���������б��������б�ͨ�����öػͻ�ȡ��Ŀ����api�����
		JSONArray jsonAttrList = this.getCateAttribueList();
		System.out.println(jsonAttrList.toString());
		
		for (int i = 0; i < jsonAttrList.size(); i++) {
			
			JSONObject jsonAttr = jsonAttrList.getJSONObject(i); // �����б�
			
			// �ҵ���Ӧ����������
			if (jsonAttr.getString("lineAttrName").equalsIgnoreCase(attrName) 
					|| jsonAttr.getString("lineAttrNameCn").equalsIgnoreCase(attrName)) {

				Long attrId = jsonAttr.getLong("attrId");
				ProdAttr prodAttr = new ProdAttr();
				prodAttr.setAttrId(attrId);
				if (attrName.equalsIgnoreCase("brand")) { // Ʒ������ֱ�Ӹ�ֵ������
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

				int buyAttr = jsonAttr.getInt("buyAttr"); // 1.�ǹ�������, 0.���ǹ�������
				JSONArray jsonAttrValList = jsonAttr.getJSONArray("valueList"); // �����Ե�ֵ�б�
				// ����ϵͳ����ĸ�����ֵ�б�
				for (int j = 0; j < jsonAttrValList.size(); j++) {
					JSONObject jsonAttrVal = jsonAttrValList.getJSONObject(j);
					String lineAttrvalName = jsonAttrVal.get("lineAttrvalName").toString().toLowerCase();
					String lineAttrvalNameCn = jsonAttrVal.get("lineAttrvalNameCn").toString().toLowerCase();
					
					// Ѱ�Ҷ�Ӧ������ֵ
					boolean matched = false;
					for (String attrVal : attrValArr) {
						attrVal = attrVal.trim();
						if (buyAttr == 1) { // �������Բ����ϸ�ƥ��
							if (lineAttrvalName.equalsIgnoreCase(attrVal)
									|| lineAttrvalNameCn.equalsIgnoreCase(attrVal)) {
								matched = true;
								break;
							}
						} else { // �ǹ������Բ���ģ��ƥ��
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
						
						if (jsonAttr.getInt("type") == 1) { // ����������ֵ
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
	
	/** ȡ���Լ�ֵ */
//	private ProdAttrVal getProdAttrVal(String attrName, String attrVal) {
//		ProdAttrVal prodAttrVal = null;
//		
//		// ������ͨ��ػ͵���������Ӧ��ϵ���ҵ���ȷ�Ķػ�������
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
//				// �Զ�������ֵ
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
	
	/** ȡ��Ŀ�µĲ�Ʒ�����б� */
	private JSONArray getCateAttribueList() {
		JSONObject json = dhCategoryApiBiz.getCategory(pubCateId, dhAccount);
		if (json != null && json.getJSONObject("categoryPublish").containsKey("attributeList")) {
			return json.getJSONObject("categoryPublish").getJSONArray("attributeList");
		}
		return null;
	}
	
	/** ��������Ƿ��Ѿ���Ӻ�(�����Ŀ��������) */
	private boolean attrHasAdded(Long attrId, List<ProdAttr> prodAttrList) {
		for (ProdAttr prodAttr : prodAttrList) {
			if (prodAttr.getAttrId().equals(attrId)) {
				return true;
			}
		}
		return false;
	}
	
	/** ȡ��Ʒ�������� */
	private ProdBase getProdBase(String html, String url) {
		String productId = this.getProductId(url);
		ProdBase prodBase = new ProdBase();
		prodBase.setHtmlContent(this.getDetailHtmlContent(productId));
		prodBase.setKeyWords(this.getKeywords(html));
		prodBase.setProductName(this.getProductName(html));
		prodBase.setShortDesc(this.getShortDesc(html));
		return prodBase;
	}
	
	/** ȡ��Ʒid */
	private String getProductId(String url) {
		return url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".htm"));
	}
	
	/** ȡ��Ʒ���� */
	private String getDetailHtmlContent(String productId) {
		String url = "http://www.aliexpress.com/getDescModuleAjax.htm?productId=" + productId 
				+ "&productSrc=&_=" + new Date().getTime();
		String html = HttpClientUtils.doGetHtml(url);
		html = html.replaceAll("\r\n\r\n\n \n    window.productDescription='", "")
			.replaceAll("';\n \n\n", "");
		html = this.replaceImgStr(html);
		// �Զ����滻����
		if (pubConfig.getProdDescOrgin() != null 
				&& !pubConfig.getProdDescOrgin().equals("")
				&& pubConfig.getProdDescReplaced() != null) {
			html = html.replaceAll(pubConfig.getProdDescOrgin(), 
					pubConfig.getProdDescReplaced());
		}
		// ��ǰ׺
		if (pubConfig.getProdDescPrefix() != null 
				&& !pubConfig.getProdDescPrefix().equals("")) {
			html = pubConfig.getProdDescPrefix() + html;
		}
		// �Ӻ�׺
		if (pubConfig.getProdDescSuffix() != null 
				&& !pubConfig.getProdDescSuffix().equals("")) {
			html = html + pubConfig.getProdDescSuffix();
		}
		return html;
	}
	
	/** �滻���������ͼƬ��ַ */
	private String replaceImgStr(String resource) {  
		 try {
			 Parser myParser = new Parser(resource);
			 String filterStr = "img"; // ������ȡ���Ǳ�ǩΪimg��Ԫ��
		             
			 NodeFilter filter = new TagNameFilter(filterStr); // ���������ǩ
			 NodeList nodeList = myParser.extractAllNodesThatMatch(filter); // ��ȡ����img�б�
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
	
	/** ȡ��Ʒ�ؼ��� */
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
	
	/** ȡ��Ʒ���� */
	private String getProductName(String html) {
		String regx = "<h1 class=\"product-name\"(.*?)>(.*?)</h1>";
		Pattern p = Pattern.compile(regx);
		Matcher macher = p.matcher(html);
		if (macher.find()) {
			String prodName = macher.group(2);
			
			// ���˲��ܰ������ַ�
			prodName = filterWord(prodName);
			
			// �Զ����滻����
			if (pubConfig.getProdNameOrgin() != null 
					&& !pubConfig.getProdNameOrgin().equals("")
					&& pubConfig.getProdNameReplaced() != null) {
				prodName = prodName.replaceAll(pubConfig.getProdNameOrgin(), 
						pubConfig.getProdNameReplaced());
			}
			// ��ǰ׺
			if (pubConfig.getProdNamePrefix() != null 
					&& !pubConfig.getProdNamePrefix().equals("")) {
				prodName = pubConfig.getProdNamePrefix() + prodName;
			}
			// �Ӻ�׺
			if (pubConfig.getProdNameSuffix() != null 
					&& !pubConfig.getProdNameSuffix().equals("")) {
				prodName = prodName + pubConfig.getProdNameSuffix();
			}
			return prodName;
		}
		return null;
	}
	
	/** ȡ��Ʒ������� */
	private String getShortDesc(String html) {
		String regx = "<meta name=\"description\" content=\"([\\s\\S]*)<meta name=\"";
		Pattern p = Pattern.compile(regx);
		Matcher macher = p.matcher(html);
		if (macher.find()) {
			String shortDesc = macher.group(1).replaceAll("\" />\r\n", "");
			// ���˲��ܰ������ַ�
			shortDesc = filterWord(shortDesc);
			return shortDesc;
		}
		return null;
	}
	
	/** ���˲��ܰ������ַ� */
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
	
	/** ȡ��Ʒ������Ϣ */
	private ProdInventory getProdInventory() {
		ProdInventory prodInventory = new ProdInventory();
		prodInventory.setInventoryStatus(0);
		return prodInventory;
	}
	
	/** ȡ������Ϣ */
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
	
	/** ȡ��Ʒ�������� */
	private ProdSaleSetting getProdSaleSetting(Integer priceConfigType) {
		ProdSaleSetting prodSaleSetting = new ProdSaleSetting();
		prodSaleSetting.setLeadingTime(5);
		prodSaleSetting.setMaxSaleQty(9999);
		prodSaleSetting.setPriceConfigType(priceConfigType);
		return prodSaleSetting;
	}
	
	/** ���ò�Ʒsku, �Զ����� */
	private OptResult setProdSkusSpec(String html, Product product, List<ProdAttr> prodAttrListFromSku) {
		String regx = "var skuProducts=(.*?);\n";
		Pattern p = Pattern.compile(regx);
		Matcher macher = p.matcher(html);
		String skuStr = "";
		if (macher.find()) {
			skuStr = macher.group(1);
		}
		
		JSONArray skuJsonArr = JSONArray.fromObject(skuStr); // �õ�skuStr��һ��json�ַ���, ����ת����JSON�������
		
		String prodSkuHtml = getProdSkuHtml(html); // ȡ��ʾsku�������Ե��Ƕ�html
		List<String> customSkuAttrList = new ArrayList<String>(); // ����Զ��幺������
		
		// �ж���һ��sku����ʱ,˵���ò�Ʒ�й�������,��html�н�����sku��������, ������Ϊϵͳ������Զ��幺������
		// ��ϵͳ���幺�����Լ���prodAttrListFromSku��, ���ֻ֧��һ���Զ��幺������
		if (skuJsonArr.size() > 1) { 
			this.readSkuAttrList(prodAttrListFromSku, customSkuAttrList, prodSkuHtml);
		}

		List<ProdSku> prodSkuList = new ArrayList<ProdSku>(); // Sku��Ϣ�б�
		List<ProdSpecSelfDef> prodSpecList = new ArrayList<ProdSpecSelfDef>(); // �Զ������б�
		Integer customAttrId = 9999; // �Զ�������id
		Integer customAttrValId = 1000; // �Զ���(���)����ֵid, ��1000��ʼ, ���10����1009
		
		for (int i = 0; i < skuJsonArr.size(); i++) {
			JSONObject skuJson = skuJsonArr.getJSONObject(i);
			JSONObject skuVal = skuJson.getJSONObject("skuVal"); 
			Double retailPrice = skuVal.getDouble("skuPrice") * 1.3; // ���ۼ۸� 1.3��
			retailPrice = Double.parseDouble(new DecimalFormat("#0.00").format(retailPrice));
			
			ProdSku prodSku = new ProdSku();
			prodSku.setInventory(0);
			prodSku.setSaleStatus(1);
			prodSku.setRetailPrice(retailPrice);

			String skuPropIds = skuJson.getString("skuPropIds");
			if (!skuPropIds.equals("")) { // ��Ϊ�ձ�ʾ��sku����, ����ж��sku���Ի��Զ��Ÿ���
				if (customSkuAttrList.size() > 1) {
					return new OptResult(0, "��֧���ж���Զ���(sku)�������ԵĲ�Ʒ");
				}

				List<ProdSkuAttrval> prodSkuAttrvalList = new ArrayList<ProdSkuAttrval>();
				
				String[] skuPropIdArr = skuPropIds.split(","); // ����ͨ��Ʒsku����Id����
				for (int j = 0; j < skuPropIdArr.length; j++) {
					
					String skuAttrName = getSkuAttrName(j, prodSkuHtml); // ȡ����ͨsku��������
					String skuAttrValue = getSkuAttrValue(j + 1, skuPropIdArr[j], html); // ȡ����ͨsku����ֵ
					if (skuAttrName == null || skuAttrValue == null) {
						return new OptResult(0, "��������ͨ��Ʒsku��Ϣʱ��������, �������ֵΪnull");
					}
					
					// ����ͨ��������ת���ɶػ���������
					String dhAttrName = Constants.prodAttrNameMap.get(skuAttrName);
					if (dhAttrName != null) {
						skuAttrName = dhAttrName;
					}

					ProdSkuAttrval prodSkuAttrval = new ProdSkuAttrval();
					if (!customSkuAttrList.contains(skuAttrName)) { // ϵͳ���幺������
						
						ProdAttr prodAttr = this.getProdAttrFromDhgate(skuAttrName, skuAttrValue);
						if (prodAttr != null && prodAttr.getProdAttrValList() != null) {
							prodSkuAttrval.setAttrId(prodAttr.getAttrId().intValue());
							prodSkuAttrval.setAttrValId(prodAttr.getProdAttrValList().get(0).getAttrValId().intValue());
							prodSkuAttrval.setSizeSpecType(1);
							prodSkuAttrvalList.add(prodSkuAttrval);
						}
						
					} else { // �Զ��幺������
						
						prodSkuAttrval.setAttrId(customAttrId);
						
						Integer specAttrValId = getAttrValIdFromProdSpecList(skuAttrValue, prodSpecList);
						if (specAttrValId != null) { // �����Զ���(���)����ֵ
							prodSkuAttrval.setAttrValId(specAttrValId);
							prodSkuAttrval.setSizeSpecType(3);
							
						} else {
							prodSkuAttrval.setAttrValId(customAttrValId);
							prodSkuAttrval.setSizeSpecType(3);
							customAttrValId++;
							// ����Զ�����
							ProdSpecSelfDef prodSpec = new ProdSpecSelfDef();
							prodSpec.setAttrValName(skuAttrValue);
							prodSpecList.add(prodSpec);
						}
						
						prodSkuAttrvalList.add(prodSkuAttrval);
					}
					
//					if (prodSpecList.size() > 10) {
//						return new OptResult(0, "��֧�ֳ���10���Զ������Զ��幺������ֵ���Ĳ�Ʒ");
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
		
		return new OptResult(1, "���óɹ�");
	}

	/** ���һ������ֵ�������Զ��������Ƿ��Ѿ�����,�������,�����Զ�������ֵID */
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
	
	/** ��������������Ϣ,���ֳ�ϵͳ������Զ��幺������ */
	private void readSkuAttrList(List<ProdAttr> prodAttrListFromSku,
			List<String> customSkuAttrList, String prodSkuHtml) {
		int skuAttrCount = this.getSkuAttrCount(prodSkuHtml);
		for (int i = 0; i < skuAttrCount; i++) {
			String attrName = this.getSkuAttrName(i, prodSkuHtml);
			// ����ͨ��������ת���ɶػ���������
			String dhAttrName = Constants.prodAttrNameMap.get(attrName);
			if (dhAttrName != null) {
				attrName = dhAttrName;
			}
			
			// ����ֵ�б�
			List<String> attrValList = this.getSkuAttrValueList(i + 1, prodSkuHtml);
			String attrVals = "";
			for (String attrVal : attrValList) {
				attrVals += attrVal + ",";
			}
			
			// ���Ҷ�Ӧ�ĶػͶ�������, �����Ҳ�����Ϊ�Զ��幺������
			ProdAttr prodAttr = this.getProdAttrFromDhgate(attrName, attrVals);
			if (prodAttr == null || prodAttr.getProdAttrValList() == null) {
				customSkuAttrList.add(attrName);
			} else if (prodAttr.getProdAttrValList() != null
					&& prodAttr.getProdAttrValList().size() > 0) { 
				prodAttrListFromSku.add(prodAttr);
			}
		}
	}
	
	/** ȡ����ʾ�������Ե��Ƕ�html */
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
	
	/** ��ȡ�������Ը��� */
	private Integer getSkuAttrCount(String prodSkuHtml) {
		if (prodSkuHtml == null) {
			return null;
		}
		try {
			 Parser myParser = new Parser(prodSkuHtml);
			 String filterStr = "dt"; // ������ȡ���Ǳ�ǩΪdt��Ԫ��
		             
			 NodeFilter filter = new TagNameFilter(filterStr); // ���������ǩ
			 NodeList nodeList = myParser.extractAllNodesThatMatch(filter); // ��ȡ����img�б�
			 return nodeList.size();
		 } catch (Exception e) {
			 e.printStackTrace();
		}
		return 0;
	}
	
	/** ȡ��һ������������, index��0��ʼ */
	private String getSkuAttrName(int index, String prodSkuHtml) {
		if (prodSkuHtml == null) {
			return null;
		}
		try {
			 Parser myParser = new Parser(prodSkuHtml);
			 String filterStr = "dt"; // ������ȡ���Ǳ�ǩΪdt��Ԫ��
		             
			 NodeFilter filter = new TagNameFilter(filterStr); // ���������ǩ
			 NodeList nodeList = myParser.extractAllNodesThatMatch(filter); // ��ȡ����img�б�
			 return nodeList.elementAt(index).toPlainTextString().replace(":", "").trim();
		 } catch (Exception e) {
			 e.printStackTrace();
		}
		return null;
	}
	
	/** ȡ���ڼ����������ԵĿ�ѡֵ�б�, index��1��ʼ */
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
	
	/** ��ȡĳ���������Ե�ĳ��ֵid��ֵ */
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
	
	/** �����۸����� */
	private List<ProdWholesaleRange> getProdWolesaleRangeList() {
		if (pubConfig.getProdBulkPriceSet() == null ||
				pubConfig.getProdBulkPriceSet().equals("")) {
			return null;
		}
		
		// ȷ�ϻ��з�
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
	
	/** �����۸������Ƿ��Ѵ��� */
	private boolean isExistSaleRange(Integer startQty, List<ProdWholesaleRange> prodSaleRangeList) {
		for (ProdWholesaleRange prodSaleRange : prodSaleRangeList) {
			if (prodSaleRange.getStartQty().equals(startQty)) {
				return true;
			}
		}
		return false;
	}
	
}
