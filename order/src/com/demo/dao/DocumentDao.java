package com.demo.dao;

import java.util.List;

import com.demo.entity.DocumentTable;
import com.demo.entity.Express.YunFeiTable;
import com.demo.entity.order.OrderTable;

public interface DocumentDao  extends BaseDao<DocumentTable,Long>{
	//����Ա�õ�δ���
	public String getUnfinish(String time,String time1);
	//��Ų�ѯȫ��
	public List<DocumentTable> getDocumentId(Long id);
	//����ĵ�
	public String getAudit(String time,String time1);
	//����ĵ�
	public String getCompleted(String time,String time1);
	//�ĵ���ѯ
	public List<DocumentTable> getDuplicateContent(String title);
}
