package com.demo.biz;

import com.demo.entity.user.UserInfo;

public interface SystemBiz {

	/**
	 * 检查用户登录，失败时抛出异常
	 * @param account
	 * @param pwd
	 * @param ip
	 */
	public abstract UserInfo checkUserLogin(String username, String pwd, String ip);

}