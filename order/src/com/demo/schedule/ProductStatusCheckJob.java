package com.demo.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;

import com.demo.biz.dhgate.DhCommonApiBiz;
import com.demo.biz.dhgate.DhProductApiBiz;
import com.demo.biz.tools.ProductStatusBiz;
import com.demo.dao.ZhangHaoDao;
import com.demo.dao.tools.ProductStatusDao;
import com.demo.entity.ZhangHao;
import com.demo.entity.tools.ProductStatus;
import com.demo.page.PageBean;
import com.demo.utils.ApplicationUtils;

/**
 * 自动检测产品状态, 自动同步产品状态
 *
 */
public class ProductStatusCheckJob implements InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
	}
	
	@Resource
	private ProductStatusBiz productStatusBiz;
	@Resource
	private ProductStatusDao productStatusDao;
	@Resource
	private ZhangHaoDao zhangHaoDao;
	@Resource
	private DhProductApiBiz dhProductApiBiz;
	
	public void execute() {
		
		// 自动检测功能暂时先不启用，
		
		/*
		int pageSize = 100; // 一次检测100个产品
		int pageNum = 1;
		String orderBy = "checkTime asc";
		PageBean pageBean = productStatusDao.getAllByPage(null, null, null, null, null, orderBy, pageSize, pageNum);
		if (pageBean.getList() == null || pageBean.getList().isEmpty()) {
			return;
		}

		// 检测产品状态
		System.out.println("---- 自动检测产品状态 ----");
		List<ProductStatus> productStatusList = pageBean.getList();
		for (ProductStatus productStatus : productStatusList) {
			this.productStatusBiz.checkProductStatus(productStatus);
		}

		// 检查自动同步状态功能开关是否开启
		int autoSynchProductStatus = (Integer) ApplicationUtils.get("autoSynchProductStatus");
		if (autoSynchProductStatus == 0) {
			return;
		}
		
		// 执行自动同步
		System.out.println("---- 自动同步产品状态 ----");
		orderBy = "synchTime asc";
		Integer statusFlag = 0;
		List<ZhangHao> zhangHaoList = zhangHaoDao.getAll(DhCommonApiBiz.ACCOUNT_TYPE, null);
		for (ZhangHao zhangHao : zhangHaoList) {
			// 取该账号下同步标识为状态不同步的产品
			pageBean = productStatusDao.getAllByPage(null, null, statusFlag, null, zhangHao.getId(), orderBy, pageSize, pageNum);
			if (pageBean.getList() == null || pageBean.getList().isEmpty()) {
				continue;
			}
			// 分组上架和下架操作的产品
			List<ProductStatus> synchProductStatusList = pageBean.getList();
			List<String> upselfItemcodeList = new ArrayList<String>();
			List<String> downselfItemcodeList = new ArrayList<String>();
			List<Long> upselfIdList = new ArrayList<Long>();
			List<Long> downselfIdList = new ArrayList<Long>();
			for (ProductStatus synchProductStatus : synchProductStatusList) {
				if (synchProductStatus.getDhStatus() == 0 &&
						synchProductStatus.getAliStatus() == 1) { // 列入上架列表
					upselfItemcodeList.add(synchProductStatus.getDhId());
					upselfIdList.add(synchProductStatus.getId());
				} else if (synchProductStatus.getDhStatus() == 1 &&
						synchProductStatus.getAliStatus() == 0) { // 列入下架列表
					downselfItemcodeList.add(synchProductStatus.getDhId());
					downselfIdList.add(synchProductStatus.getId());
				}
			}
			
			Long synchTime = new Date().getTime();
			Integer synchResult = 0;
			// 执行上架
			if (!upselfItemcodeList.isEmpty()) {
				boolean synchSuccess = dhProductApiBiz.upshelfProducts(
						(String[]) upselfItemcodeList.toArray(), zhangHao);
				if (synchSuccess) {
					synchResult = 1;
				} else {
					synchResult = 0;
				}
				productStatusDao.batchUpdateSynchResult(upselfIdList, synchTime, synchResult);
			}
			// 执行下架
			if (!upselfItemcodeList.isEmpty()) {
				boolean synchSuccess = dhProductApiBiz.downshelfProducts(
						(String[]) downselfItemcodeList.toArray(), zhangHao);
				if (synchSuccess) {
					synchResult = 1;
				} else {
					synchResult = 0;
				}
				productStatusDao.batchUpdateSynchResult(downselfIdList, synchTime, synchResult);
			}
		}
		*/
	}

}
