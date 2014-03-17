package com.demo.dao;

import java.util.List;

import com.demo.entity.MyMenu;

public interface MenuDao extends BaseDao<MyMenu, Long>{
	List<MyMenu> getByMenu(Integer role);
}
