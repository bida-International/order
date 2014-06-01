package com.demo.biz.tools;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import com.demo.bean.OptResult;
import com.demo.dao.tools.ProdPublishLogDao;
import com.demo.entity.ZhangHao;
import com.demo.entity.tools.ProdPublishLog;
import com.demo.utils.ApplicationUtils;

public class ProdPublishThread implements Runnable {

	private ProdPublishBiz prodPublishBiz;
	private ProdPublishLogDao prodPublishLogDao;
	
	private List<String> aliUrlList;
	private String pubCateId;
	private String shippingTemplateId;
	private String productGroupId;
	private ZhangHao dhAccount;
	private String sessionId;
	
	private Integer doneNum = 0;
	private Integer totalNum;
	private Integer okNum = 0; // 发布成功个数
	private Integer errNum = 0; // 发布失败个数
	
	public ProdPublishThread(List<String> aliUrlList, String pubCateId, String shippingTemplateId, 
			String productGroupId, ZhangHao dhAccount, String sessionId, 
			ProdPublishBiz prodPublishBiz, ProdPublishLogDao prodPublishLogDao) {
		this.aliUrlList = aliUrlList;
		this.pubCateId = pubCateId;
		this.shippingTemplateId = shippingTemplateId;
		this.productGroupId = productGroupId;
		this.dhAccount = dhAccount;
		this.sessionId = sessionId;
		this.prodPublishBiz = prodPublishBiz;
		this.prodPublishLogDao = prodPublishLogDao;
	}
	
	@Override
	public void run() {
		this.setProgress("0.0%");
		this.setStopFlag(0);
		
		totalNum = aliUrlList.size();
		for (int i = 0; i < aliUrlList.size(); i++) {
			if (getStopFlag() == 1) {
				this.setProgress("100.0%");
				return;
			}
			String aliUrl = aliUrlList.get(i);
			OptResult optResult = prodPublishBiz.doPublish(aliUrl, pubCateId, 
					shippingTemplateId, productGroupId, dhAccount);
			if (optResult.getResult() == 1) {
				okNum++;
			} else {
				errNum++;
			}
			// 更新进度
			doneNum = doneNum + 1;
			this.updateProgress();
			// 保存发布日志
			ProdPublishLog log = new ProdPublishLog();
			log.setAliUrl(aliUrl);
			log.setLineNum(i + 1);
			log.setZhangHaoId(dhAccount.getId());
			log.setZhangHaoAccount(dhAccount.getAccount());
			log.setSessionId(sessionId);
			log.setResult(optResult.getResult());
			log.setMsg(optResult.getMsg());
			log.setCreateTime(new Date().getTime());
			prodPublishLogDao.merge(log);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void updateProgress() {
		Double percent = Double.parseDouble(doneNum.toString()) 
				/ Double.parseDouble(totalNum.toString()) * 100;
		if (percent > 100d) {
			percent = 100d;
		}
		String percentStr = new DecimalFormat("#0.0").format(percent) + "%";
		this.setProgress(percentStr);
	}
	
	private void setProgress(String percent) {
		ApplicationUtils.put("ProdPublish_Percent_" + sessionId, 
				percent + "," + okNum + "," + errNum);
	}
	
	private void setStopFlag(Integer flag) {
		ApplicationUtils.put("ProdPublish_StopFlag_" + sessionId, flag);
	}
	
	private Integer getStopFlag() {
		Object obj = ApplicationUtils.get("ProdPublish_StopFlag_"
				+ sessionId);
		if (obj == null) {
			return 0;
		} else {
			return (Integer) obj;
		}
	}
	
}
