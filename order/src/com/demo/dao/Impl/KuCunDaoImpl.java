package com.demo.dao.Impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.KuCunDao;
import com.demo.entity.KuCunTable;
@Repository
public class KuCunDaoImpl extends BaseDaoImpl<KuCunTable, Long> implements KuCunDao{
		public KuCunDaoImpl() {
			super(KuCunTable.class);
		}
		//��ǺŲ�ѯȫ��
		public KuCunTable getKuCunAll(String biaoji,String orderId){
			return ht.findFirst("from KuCunTable where biaoji = ? and orderId = ?",new Object[]{biaoji,orderId});
		}
		//�����Ų�ѯȫ��
		public KuCunTable getOrderAll(String orderid){
			return ht.findFirst("from KuCunTable where orderId= ?",new Object[]{orderid});
		}
		//�ñ�Ų�ѯȫ��
		public KuCunTable getBiaoHao(String biaoji){
			return ht.findFirst("from KuCunTable where biaoji = ?",new Object[]{biaoji});
		}
		//��Ʒ��useriid��ѯ
		public List<KuCunTable> getGoodsUserId(String goods){
			return ht.find("from KuCunTable where wuping = ?",new Object[]{goods});
		}
		//�ɹ�Ա�鿴ȫ����涩��
		public String getStockOrder(Long userid,String time,String time1,String bianma,String orderId){
			String hql = null;
			if ((time != null && !"".equals(time)) && (time1 != null && !"".equals(time1)) && (bianma == null || "".equals(bianma)) && (orderId == null || "".equals(orderId))) {
				hql = "from KuCunTable where (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"')";
			}
			else if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (bianma != null && !"".equals(bianma)) && (orderId == null || "".equals(orderId))){
				hql = "from KuCunTable where coding = '"+bianma+"'";
			}
			else if((time != null && !"".equals(time)) && (time1 != null && !"".equals(time1)) && (bianma != null && !"".equals(bianma)) && (orderId == null || "".equals(orderId))){
				hql = "from KuCunTable where coding = '"+bianma+"' and (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"')";
			}
			else if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (bianma == null || "".equals(bianma)) && (orderId != null && !"".equals(orderId))){
				hql = "from KuCunTable where orderId = '"+orderId+"'";
			}
			else if((time == null || "".equals(time)) && (time1 == null || "".equals(time1)) && (bianma == null || "".equals(bianma)) && (orderId == null || "".equals(orderId))){
				hql = "from KuCunTable";	
			}
			return hql;
		}
		//����Ա�鿴ȫ����涩��
		public String getStockOrderAll(String time,String time1){
			String hql = null;
			if ((time != null && !"".equals(time)) && (time1 != null && !"".equals(time1))) {
				hql = "from KuCunTable where (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"')";
			}
			else if((time == null || "".equals(time)) && (time1 == null || "".equals(time1))){
				hql = "from KuCunTable";	
			}
			return hql;
		}
		//��涩���ܽ��
		public List<KuCunTable> getTheTotalAmount(String time,String time1){
			List<KuCunTable> stu = null;
			if((time != null && !"".equals(time)) && (time1 != null && !"".equals(time1))){
				stu = ht.find("select count(totalprice) from KuCunTable where (convert(varchar(10),time,120) between '"+time+"'and '"+time1+"')");
			}
			else if((time == null || "".equals(time)) && (time1 == null || "".equals(time1))){
				stu = ht.find("select count(totalprice) from KuCunTable");
			}
			return stu;
		}
		//��涩���ܽ��
		public List<KuCunTable> getUserId(){
		
			List<KuCunTable> stu = ht.find("from KuCunTable");
			return stu;
		}
		//ͼƬ���ƺ��û������ѯ
		public List<KuCunTable> getThePictureName(String picturename,Long userid){
			 List<KuCunTable> stu = ht.find("from KuCunTable where uploadFile = '"+picturename+"' and userid = "+userid+"");
			 return stu;
		}
		//����Ų�ѯ
		public List<KuCunTable> getKuCunId(Long kucunid){
			List<KuCunTable> ss = ht.find("from KuCunTable where id = "+kucunid+"");
			return ss;
		}
	}

