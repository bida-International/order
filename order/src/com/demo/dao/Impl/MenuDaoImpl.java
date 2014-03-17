package com.demo.dao.Impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.MenuDao;
import com.demo.entity.MyMenu;
@Repository
public class MenuDaoImpl extends BaseDaoImpl<MyMenu, Long> implements MenuDao{
	public MenuDaoImpl() {
		super(MyMenu.class);
	}
	public List<MyMenu> getByMenu(Integer role) {
		String sql = "from MyMenu a where a.role = ?";
		List<MyMenu> ls =  ht.find(sql,new Object[]{role});
		return ls;
	}
}
