package com.demo.dao;

import org.springframework.transaction.annotation.Transactional;

public interface BaseDao<T, K> { 
	/**
	 * 新加或更新保存
	 * @param t
	 * @return
	 */
	@Transactional
	public T merge(T t);
	@Transactional
	public void delete(T id);
	/**
	 * 根据主键加载
	 * @param id
	 * @return
	 */
	public T get(K id);
}
