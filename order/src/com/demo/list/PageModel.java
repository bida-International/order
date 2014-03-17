package com.demo.list;

import java.util.List;

public class PageModel<T> {
	private int pagecount=0;//总页数
	private int pageindex=1;//当前页
	private int count=0;//总记录
	private int pagecenter=10;//每页显示数
	private List<T> pagelist=null;//所有记录
	private int start=0;//开始
	public int getPageindex() {
		return pageindex;
	}
	public void setPageindex(int pageindex) {
		if(this.pagecount<pageindex)//总页数小于当前页
		{
			pageindex=this.pagecount;
		}
		else if(pageindex<=0)//当前页小于等于0
		{
			pageindex=1;
		}
		this.pageindex = pageindex;
		start=(this.pageindex-1)*pagecenter;//开始索引
	}
	public int getPagecenter() {
		return pagecenter;
	}
	public void setPagecenter(int pagecenter) {
		this.pagecenter = pagecenter;
	}
	public List<T> getFsd(){
		return getPagelist();
	}
	public List<T> getPagelist() {
		if(this.count<(start+pagecenter))
		{
		return pagelist.subList(start, count);
		}else
		{
			return pagelist.subList(start, start+pagecenter);//开始到结束空间
		}
	}
	public void setPagelist(List<T> pagelist) {
		this.count=pagelist.size();
		this.pagecount=(this.count+this.pagecenter-1)/this.pagecenter;//总页数
		this.pagelist = pagelist;
	}
	public int getPagecount() {
		return pagecount;
	}
	public int getCount() {
		return count;
	}
	public int getStart() {
		return start;
	}
	public void setPagecount(int pagecount) {
		this.pagecount=(this.count+this.pagecenter-1)/this.pagecenter;
		this.pagecount = pagecount;
	}
	public Integer getSize()
	{
		return pagelist.size();
	}
}
