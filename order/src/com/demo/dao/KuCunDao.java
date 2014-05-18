package com.demo.dao;


import java.util.List;

import com.demo.entity.KuCunTable;

public interface KuCunDao  extends BaseDao<KuCunTable,Long>{
	//��ǺŲ�ѯȫ��
	public KuCunTable getKuCunAll(String biaoji,String orderId);
	//�����Ų�ѯȫ��
	public KuCunTable getOrderAll(String orderid);
	//�ñ�Ų�ѯȫ��
	public KuCunTable getBiaoHao(String biaoji);
	//��Ʒ��useriid��ѯ
	public List<KuCunTable> getGoodsUserId(String goods);
	//�ɹ�Ա�鿴ȫ����涩��
	public String getStockOrder(Long userid,String time,String time1,String bianma,String orderId);
	//����Ա�鿴ȫ����涩��
	public String getStockOrderAll(String time,String time1);
	//��涩���ܽ��
	public List<KuCunTable> getTheTotalAmount(String time,String time1);
	//��涩���ܽ��
	public List<KuCunTable> getUserId();
	//ͼƬ���ƺ��û������ѯ
	public List<KuCunTable> getThePictureName(String picturename,Long userid);
	//����Ų�ѯ
	public List<KuCunTable> getKuCunId(Long kucunid);

}
