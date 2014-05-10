package com.demo.action.tools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.demo.action.BaseAction;
import com.demo.bean.OptResult;
import com.demo.biz.dhgate.DhCategoryApiBiz;
import com.demo.biz.dhgate.DhCommonApiBiz;
import com.demo.biz.dhgate.DhProductApiBiz;
import com.demo.biz.dhgate.DhShippingApiBiz;
import com.demo.biz.tools.ProdPublishBiz;
import com.demo.biz.tools.ProdPublishThread;
import com.demo.dao.ZhangHaoDao;
import com.demo.dao.tools.ProdPublishLogDao;
import com.demo.entity.ZhangHao;
import com.demo.entity.tools.ProdPublishLog;
import com.demo.utils.FileUtils;
import com.demo.utils.Struts2Utils;

/**
 * 发布产品功能页面
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
	private ProdPublishBiz prodPublishBiz;
	@Resource
	private ProdPublishLogDao prodPublishLogDao;

	private Long zhangHaoId;
	private List<ZhangHao> zhangHaoList;
	private File excel;
	private String excelFileName;
	private String excelContentType;
	
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
		ZhangHao dhAccount = zhangHaoDao.get(zhangHaoId);
		String aliUrl = Struts2Utils.getParameter("aliUrl");
		String pubCateId = Struts2Utils.getParameter("pubCateId");
		String shippingTemplateId = Struts2Utils.getParameter("shippingTemplateId");
		String productGroupId = Struts2Utils.getParameter("productGroupId");
		List<String> aliUrlList = readAliUrlsFromExcel();
		String pubMode = Struts2Utils.getParameter("pubMode");
		
		if (pubMode.equals("1")) { // 发布单个产品
			OptResult optResult = prodPublishBiz.doPublish(aliUrl, pubCateId, 
				shippingTemplateId, productGroupId, dhAccount);
			boolean success = true;
			if (optResult.getResult() == 0) {
				success = false;
			}
			Struts2Utils.renderJson(optResult.getMsg(), success);
		} else if (pubMode.equals("2")) { // 批量发布产品
			if (aliUrlList == null || aliUrlList.isEmpty()) {
				Struts2Utils.renderJson("读取文件失败,请确认文件格式是否正确", false);
				return null;
			}
			
			String sessionId = Struts2Utils.getSessionId();
			Long startTime = new Date().getTime();
			ProdPublishThread publishThread = new ProdPublishThread(aliUrlList, pubCateId, 
					shippingTemplateId, productGroupId, dhAccount, sessionId, 
					prodPublishBiz, prodPublishLogDao);
			new Thread(publishThread).start();
			Struts2Utils.renderJson(startTime.toString(), true);
		} else {
			Struts2Utils.renderJson("发布模式错误", false);
		}
		
//		try {
//			String aliUrl = Struts2Utils.getParameter("aliUrl");
//			if (productStatusDao.isExist(aliUrl)) {
//				Struts2Utils.renderJson("该产品已在库中", false);
//				return null;
//			}
//			
//			ZhangHao dhAccount = zhangHaoDao.get(zhangHaoId);
//			
//			Product product = new Product();
//			product.setCatePubId(Struts2Utils.getParameter("pubCateId"));
//			product.setShippingModelId(Struts2Utils.getParameter("shippingTemplateId"));
//			
//			String productGroupId = Struts2Utils.getParameter("productGroupId");
//			if (!productGroupId.equals("0")) {
//				product.setProductGroupId(productGroupId);
//			}
//			
//			// 采集产品信息
//			OptResult optResult =  productHtmlReader.read(aliUrl, product, dhAccount);
//			if (optResult.getResult() == 0) {
//				Struts2Utils.renderJson(optResult.getMsg(), false);
//				return null;
//			}
//			
//			System.out.println(JSONObject.fromObject(product).toString());
//			// 发布产品
//			JSONObject respJson = dhProductApiBiz.add(product, dhAccount);
//			if (respJson == null) {
//				Struts2Utils.renderJson("调用发布接口时出错！", false);
//			} else {
//				String itemCode = respJson.getString("itemcode");
//				String dhProductUrl = "";
//				String dhImgUrl = "http://image.dhgate.com/" + product.getImageUrl() + "/1.0x0.jpg";
//				Integer dhStatus = 0;
//				JSONObject productDetail = dhProductApiBiz.getProduct(itemCode, dhAccount);
//				if (productDetail != null) {
//					dhProductUrl = "http://www.dhgate.com/" + productDetail.getJSONObject("product").getString("productUrl");
//					dhStatus = productDetail.getJSONObject("product").getInt("istate");
//				}
//				// 入库
//				ProductStatus productStatus = new ProductStatus();
//				productStatus.setDhId(itemCode);
//				productStatus.setDhUrl(dhProductUrl);
//				productStatus.setDhImgUrl(dhImgUrl);
//				productStatus.setDhStatus(dhStatus);
//				productStatus.setAliUrl(aliUrl);
//				productStatus.setAliStatus(1);
//				productStatus.setZhangHaoId(dhAccount.getId());
//				productStatus.setZhangHaoAccount(dhAccount.getAccount());
//				productStatus.setCreateTime(new Date().getTime());
//				productStatus.setCheckTime(new Date().getTime());
//				if (dhStatus == 2) {
//					productStatus.setStatusFlag(2);
//				} else if (dhStatus != 1) {
//					productStatus.setStatusFlag(0);
//				} else if (dhStatus == 1) {
//					productStatus.setStatusFlag(1);
//				}
//				productStatusDao.merge(productStatus);
//				
//				Struts2Utils.renderJson("发布产品成功！", true);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			Struts2Utils.renderJson("发生未知错误！", false);
//		}
		return null;
	}
	
	private List<String> readAliUrlsFromExcel() {
		if (excel == null) {
			return null;
		}

		String fileName = new Date().getTime() + ".xls";
		String prjPath = PubProductAction.class.getClassLoader().getResource(".").getPath();
		String filePath = prjPath + "/tmp/" + fileName;
		File file = new File(filePath);
		
		List<String> aliUrlList = new ArrayList<String>();
		try {
			if (!file.exists()) {
				FileUtils.createFile(prjPath + "/tmp/", fileName);
			}
			FileUtils.copyFile(excel, file);
			
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
			POIFSFileSystem fs = new POIFSFileSystem(in);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFCell cell = null;
			HSSFSheet st = wb.getSheetAt(0);
			for (int rowIndex = 1; rowIndex <= st.getLastRowNum(); rowIndex++) {
				HSSFRow row = st.getRow(rowIndex);
				cell = row.getCell(0);
				String aliUrl = cell.getStringCellValue();
				aliUrlList.add(aliUrl);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!file.exists()) {
				file.delete();
			}
		}
		return aliUrlList;
	}
	
	public String stopPublish() {
		String sessionId = Struts2Utils.getSessionId();
		prodPublishBiz.stopPublish(sessionId);
		Struts2Utils.renderJson("结束发布", true);
		return null;
	}
	
	public String getPercent() {
		String sessionId = Struts2Utils.getSessionId();
		String percent = prodPublishBiz.getPublishProgress(sessionId);
		Struts2Utils.renderJson(percent, true);
		return null;
	}
	
	public String getLog() {
		Long logQueryStartTime = Long.parseLong(Struts2Utils.getParameter("logQueryStartTime"));
		ProdPublishLog log = prodPublishLogDao.getOneErrLog(logQueryStartTime);
		if (log != null) {
			// lineNum,pageName,errMsg,createTime
			String msg = log.getLineNum() + "," + log.getAliUrl().substring(log.getAliUrl().lastIndexOf("/") + 1)
					+ "," + log.getMsg() + "," + log.getCreateTime();
			Struts2Utils.renderJson(msg, true);
		} else {
			Struts2Utils.renderJson("", false);
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

	public File getExcel() {
		return excel;
	}

	public void setExcel(File excel) {
		this.excel = excel;
	}

	public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}

	public String getExcelContentType() {
		return excelContentType;
	}

	public void setExcelContentType(String excelContentType) {
		this.excelContentType = excelContentType;
	}
}
