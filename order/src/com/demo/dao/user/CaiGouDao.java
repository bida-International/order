package com.demo.dao.user;
import java.util.List;

import com.demo.dao.BaseDao;
import com.demo.entity.user.CaiGou;

public interface CaiGouDao extends BaseDao<CaiGou, Long>{
	CaiGou getByUserId(Long userId);
	//��ѯȫ���ɹ�
	List getCaiGou();
	//�鿴ȫ���ɹ�
	public String getAllCaiGou();
	//�ɹ���Ų�ѯȫ��
	public List<CaiGou> getSelId(Long id);
	//�ɹ�����Ա��Ų�ѯȫ��
	
	public List<CaiGou> getAdCaiGou(Long aduserid);
	
}