package com.demo.dao;

import java.util.List;

import com.demo.entity.ZhangHao;
import com.demo.entity.order.OrderTable;
import com.demo.page.PageBean;

public interface ZhangHaoDao extends BaseDao<ZhangHao, Long> {
	public abstract List<ZhangHao> getAllZhangHao();

    public abstract ZhangHao getZhangHao(String s);

    public abstract ZhangHao getZhangHaoId(Long long1);
    //敦煌账号
    public abstract String getdhgate(String s);
    //账号查询全部
    public abstract List<ZhangHao> getZhangHaoName(String zhanghaoname);
    //账号编号查询全部
    public abstract List<ZhangHao> getZhangHaoIdAll(Long id);
    //账号名称查询全部
    public ZhangHao findUniqueByAccount(String account);
    // 获取一个账号信息
    public abstract ZhangHao findUnique(String account, String accountType);
    // 查询账号列表
    public abstract List<ZhangHao> getAll(String accountType, Long bdUserId);
    // 分页查询账号列表
    public abstract PageBean getAllByPage(int pageSize, int page, String accountType);
    // 根据同步时间取前几条记录 
    public List<ZhangHao> getTopnByOrderUpdateTime(int topn);
}
