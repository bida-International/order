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
 * �Զ�����Ʒ״̬, �Զ�ͬ����Ʒ״̬
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
		
		// �Զ���⹦����ʱ�Ȳ����ã�
		
		/*
		int pageSize = 100; // һ�μ��100����Ʒ
		int pageNum = 1;
		String orderBy = "checkTime asc";
		PageBean pageBean = productStatusDao.getAllByPage(null, null, null, null, null, orderBy, pageSize, pageNum);
		if (pageBean.getList() == null || pageBean.getList().isEmpty()) {
			return;
		}

		// ����Ʒ״̬
		System.out.println("---- �Զ�����Ʒ״̬ ----");
		List<ProductStatus> productStatusList = pageBean.getList();
		for (ProductStatus productStatus : productStatusList) {
			this.productStatusBiz.checkProductStatus(productStatus);
		}

		// ����Զ�ͬ��״̬���ܿ����Ƿ���
		int autoSynchProductStatus = (Integer) ApplicationUtils.get("autoSynchProductStatus");
		if (autoSynchProductStatus == 0) {
			return;
		}
		
		// ִ���Զ�ͬ��
		System.out.println("---- �Զ�ͬ����Ʒ״̬ ----");
		orderBy = "synchTime asc";
		Integer statusFlag = 0;
		List<ZhangHao> zhangHaoList = zhangHaoDao.getAll(DhCommonApiBiz.ACCOUNT_TYPE, null);
		for (ZhangHao zhangHao : zhangHaoList) {
			// ȡ���˺���ͬ����ʶΪ״̬��ͬ���Ĳ�Ʒ
			pageBean = productStatusDao.getAllByPage(null, null, statusFlag, null, zhangHao.getId(), orderBy, pageSize, pageNum);
			if (pageBean.getList() == null || pageBean.getList().isEmpty()) {
				continue;
			}
			// �����ϼܺ��¼ܲ����Ĳ�Ʒ
			List<ProductStatus> synchProductStatusList = pageBean.getList();
			List<String> upselfItemcodeList = new ArrayList<String>();
			List<String> downselfItemcodeList = new ArrayList<String>();
			List<Long> upselfIdList = new ArrayList<Long>();
			List<Long> downselfIdList = new ArrayList<Long>();
			for (ProductStatus synchProductStatus : synchProductStatusList) {
				if (synchProductStatus.getDhStatus() == 0 &&
						synchProductStatus.getAliStatus() == 1) { // �����ϼ��б�
					upselfItemcodeList.add(synchProductStatus.getDhId());
					upselfIdList.add(synchProductStatus.getId());
				} else if (synchProductStatus.getDhStatus() == 1 &&
						synchProductStatus.getAliStatus() == 0) { // �����¼��б�
					downselfItemcodeList.add(synchProductStatus.getDhId());
					downselfIdList.add(synchProductStatus.getId());
				}
			}
			
			Long synchTime = new Date().getTime();
			Integer synchResult = 0;
			// ִ���ϼ�
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
			// ִ���¼�
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
