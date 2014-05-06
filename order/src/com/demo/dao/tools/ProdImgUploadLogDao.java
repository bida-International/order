package com.demo.dao.tools;

import com.demo.dao.BaseDao;
import com.demo.entity.tools.ProdImgUploadLog;

public interface ProdImgUploadLogDao extends BaseDao<ProdImgUploadLog, Long> {

	public ProdImgUploadLog findUniqueByAliImgUrl(String aliImgUrl, Long zhangHaoId);
}
