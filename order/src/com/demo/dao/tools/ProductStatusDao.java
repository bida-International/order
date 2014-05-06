package com.demo.dao.tools;

import java.util.List;

import com.demo.dao.BaseDao;
import com.demo.entity.tools.ProductStatus;
import com.demo.page.PageBean;

public interface ProductStatusDao extends BaseDao<ProductStatus, Long> {

	public PageBean getAllByPage(Integer dhStatus, Integer aliStatus, Integer statusFlag,
			String urlKeyword, Long zhangHaoId, String orderBy, Integer pageSize, Integer page);
	
	public boolean isExist(String dhUrl, String aliUrl);
	
	public boolean isExist(String aliUrl);
	
	public void batchUpdateSynchResult(List<Long> idList, Long synchTime, Integer synchResult);
}
