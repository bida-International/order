package com.demo.dao.user;

import java.util.List;

import com.demo.dao.BaseDao;
import com.demo.entity.user.YeWu;
import com.demo.entity.user.YeWu1;

public interface YeWu1Dao extends BaseDao<YeWu1, Long>{
	YeWu1 getByUserId(Long userId);
	//查询全部业务
	public List<YeWu1> getYeWuAll();
	//查询纠纷最久的那条时间
	public List<YeWu1> getTimes();
    //修改订单状态
    public void updateDisputeStatus(String time,Long userid);
	//查询站内信最久的那条时间
	public List<YeWu1> getMsgTimes();
	  //修改订单状态
    public void updateMsgStatus(String time,Long userid);
    //删除账号
    public void deleteMsgStatus(Long userid);
}