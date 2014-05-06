package com.demo.dao.Impl.tools;

import org.springframework.stereotype.Repository;

import com.demo.dao.Impl.BaseDaoImpl;
import com.demo.dao.tools.ProdImgUploadLogDao;
import com.demo.entity.tools.ProdImgUploadLog;

@Repository
public class ProdImgUploadLogDaoImpl extends BaseDaoImpl<ProdImgUploadLog, Long> 
	implements ProdImgUploadLogDao {

	public ProdImgUploadLogDaoImpl() {
		super(ProdImgUploadLog.class);
	}
	
	public ProdImgUploadLog findUniqueByAliImgUrl(String aliImgUrl, Long zhangHaoId) {
		String hql = "from ProdImgUploadLog where aliImgUrl = ? and zhangHaoId = ?";
		return ht.findFirst(hql, new Object[] { aliImgUrl, zhangHaoId });
	}

}
