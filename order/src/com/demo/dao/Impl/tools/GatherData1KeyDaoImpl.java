package com.demo.dao.Impl.tools;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.demo.dao.PageDao;
import com.demo.dao.Impl.BaseDaoImpl;
import com.demo.dao.tools.GatherData1KeyDao;
import com.demo.entity.tools.GatherData1Key;
import com.demo.page.PageBean;

@Repository
public class GatherData1KeyDaoImpl extends BaseDaoImpl<GatherData1Key, Long> 
	implements GatherData1KeyDao {

	public GatherData1KeyDaoImpl() {
		super(GatherData1Key.class);
	}
	
	@Resource
	private PageDao pageDao;
	
	public GatherData1Key getByCreateTime(Long createTime) {
		return ht.findFirst("from GatherData1Key where create_time = ?", new Object[] {createTime});
	}

	public PageBean getAllByPage(Integer pageSize, Integer page) {
		PageBean pageBean = new PageBean(pageDao);
        String hql = "from GatherData1Key order by id desc";
        return pageBean.getFenYe(hql, pageSize, page);
    }
}
