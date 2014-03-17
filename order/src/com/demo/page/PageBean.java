package com.demo.page;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import com.demo.dao.PageDao;
import com.demo.entity.order.OrderTable;

public class PageBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//要返回的某一页的记录列表
	@SuppressWarnings("unchecked")
	private List list;
//	总记录数
	private int allRow;
	//总页数
	private int allPage;
	//当前页
	private int currentPage;
	//每页记录数
	private int pageSize;
//	是否为第一页
	@SuppressWarnings("unused")
	private boolean isFirstPage;
	//是否为最后一页
	@SuppressWarnings("unused")
	private boolean isLastPage;
	//是否有前一页
	@SuppressWarnings("unused")
	private boolean hasUpPage;
	//是否有下一页
	 @Resource
	 private PageDao pageDao;
	@SuppressWarnings("unused")
	private boolean hasNextPage;

	public PageBean() {
		
	}
	
	public PageBean(PageDao pageDao) {
		this.pageDao = pageDao;
	}
	
	/*
	 * 初始化分页信息
	 */
	public void init() {
		this.isFirstPage = isFirstPage();
		this.isLastPage = isLastPage();
		this.hasUpPage = isHasUpPage();
		this.hasNextPage = isHasNextPage();
	}

	////下一页
	public boolean isHasNextPage() {
		return currentPage != allPage;
	}

	//上一页
	public boolean isHasUpPage() {
		return currentPage != 1;
	}

	//第一页
	public boolean isFirstPage() {
		return (currentPage == 1);
	}

	//最后一页
	public boolean isLastPage() {
		return currentPage == allPage;
	}

	@SuppressWarnings("unchecked")
	public List getList() {
		return list;
	}

	@SuppressWarnings("unchecked")
	public void setList(List list) {
		this.list = list;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getAllPage() {
		return allPage;
	}

	public void setAllPage(int allPage) {
		this.allPage = allPage;
	}

	public int getAllRow() {
		return allRow;
	}

	public void setAllRow(int allRow) {
		this.allRow = allRow;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * 计算总页数，静态方法，外部直接通过类名调用
	 * @param pageSize 每页记录数
	 * @param allRow 总记录数
	 * @return 总页数
	 */
	public static int countPage(final int pageSize, final int allRow) {
		int allPage = allRow % pageSize == 0 ? allRow / pageSize : allRow/ pageSize + 1;
		return allPage;
	}
	/**
	 * 计算当前页开始记录
	 * @param pageSize 每页记录数
	 * @param currentPage 当前第几页
	 * @return 当前页开始记录号
	 */
	public static int countStart(final int pageSize, final int currentPage) {
		final int countstart = pageSize * (currentPage - 1);
		return countstart;
	}


	/**
	 * 计算当前页，若为0或者请求的URL中没有“?page=”,则用1代替
	 * @param page 传入的参数（为0则返回1）
	 * @return 当前页
	 */
	public static int countCurrentPage(int page) {
		int startPage = (page == 0 ? 1 : page);
		return startPage;
	}
	//分页调用
	public PageBean getFenYe(String hql,int pageSize,int page){
//		总记录数
		int allRow=pageDao.getAllCount(hql);
//		总页数
		int allPage=PageBean.countPage(pageSize, allRow);
		 int currentPage=PageBean.countCurrentPage(page);
		////当前开始记录
		final int countstart=PageBean.countStart(pageSize, currentPage);
		
		////每页记录数
		final int length=pageSize;
		
		//“一页”的记录
		List list=pageDao.selForPage(hql, countstart, length);
		
		//把分页信息保存到Bean中
		PageBean pageBean=new PageBean();
		pageBean.setPageSize(pageSize);
		pageBean.setCurrentPage(currentPage);
		pageBean.setAllRow(allRow);
		pageBean.setAllPage(allPage);
		pageBean.setList(list);
		pageBean.init();
		return pageBean;
	}
}