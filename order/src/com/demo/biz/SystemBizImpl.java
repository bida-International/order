package com.demo.biz;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.demo.utils.AppException;
import com.demo.dao.user.UserDao;
import com.demo.entity.user.UserInfo;

/**
 * 系统业务处理
 * @author Administrator
 *
 */
@Service
public class SystemBizImpl extends BaseBizImpl implements SystemBiz{
	@Resource
	private UserDao userDao;
	/* (non-Javadoc)
	 * @see com.exam.biz.SystemBiz#checkUserLogin(java.lang.String, java.lang.String, java.lang.String)
	 */
	public UserInfo checkUserLogin(String username, String pwd, String ip){
		UserInfo user = userDao.getByAccount(username);
		if(user == null){
			throw new AppException("帐号" + username + "不存在!");
		}
		if(!user.getPwd().equals(pwd)){
			throw new AppException("帐号密码不符!");
		}
		//TODO:记录日志
		return user;
	}
}
