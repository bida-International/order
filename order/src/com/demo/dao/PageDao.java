package com.demo.dao;

import java.util.List;

public interface PageDao {
	public List selForPage(final String hql,final int countstart,final int length);
    public int getAllCount(String hql);
}
