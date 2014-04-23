package com.demo.dao.Impl.tools;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.demo.dao.PageDao;
import com.demo.dao.Impl.BaseDaoImpl;
import com.demo.dao.tools.ProductStatusDao;
import com.demo.entity.tools.ProductStatus;
import com.demo.page.PageBean;

@Repository
public class ProductStatusDaoImpl extends BaseDaoImpl<ProductStatus, Long> implements
		ProductStatusDao {

	public ProductStatusDaoImpl() {
		super(ProductStatus.class);
	}
	
	@Resource
	private PageDao pageDao;

	public PageBean getAllByPage(Integer dhStatus, Integer aliStatus, Integer statusFlag,
			String urlKeyword, Long zhangHaoId, String orderBy, Integer pageSize, Integer page) {
		PageBean pageBean = new PageBean(pageDao);
		String conditions = " where 1=1";
		if (dhStatus != null) {
			conditions += " and dhStatus=" + dhStatus;
		}
		if (aliStatus != null) {
			conditions += " and aliStatus=" + aliStatus;
		}
		if (statusFlag != null) {
			conditions += " and statusFlag=" + statusFlag;
		}
		if (zhangHaoId != null) {
			conditions += " and zhangHaoId=" + zhangHaoId;
		}
		if (urlKeyword != null) {
			conditions += " and (dhUrl like '%" + urlKeyword + "%' or aliUrl like '%" + urlKeyword + "%')";
		}
		
		String hql = "from ProductStatus" + conditions;
        if (orderBy == null) {
        	hql += " order by createTime desc";
        } else {
        	hql += " order by " + orderBy;
        }
        return pageBean.getFenYe(hql, pageSize, page);
	}
	
	public boolean isExist(String dhUrl, String aliUrl) {
		String hql = "from ProductStatus where dhUrl like ? or aliUrl like ?";
		List<ProductStatus> list = ht.find(hql, new Object[] {dhUrl + "%", aliUrl + "%"});
		if (list == null || list.isEmpty()) {
			return false;
		}
		return true;
	}
	
	public void batchUpdateSynchResult(List<Long> idList, Long synchTime, Integer synchResult) {
		String hql = "update ProductStatus set synchTime = ?, synchResult = ? where id in ?";
		ht.bulkUpdate(hql, new Object[] {synchTime, synchResult, idList.toArray()});
	}
}
