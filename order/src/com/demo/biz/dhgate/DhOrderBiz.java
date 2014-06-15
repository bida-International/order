package com.demo.biz.dhgate;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.demo.biz.OrderStatLmBiz;
import com.demo.biz.OrderStatZhBiz;
import com.demo.dao.HuiLvDao;
import com.demo.dao.LeiMuDao;
import com.demo.dao.OrderTableDao;
import com.demo.dao.ZhangHaoDao;
import com.demo.dao.Courier.YunFeiTableDao;
import com.demo.dao.user.YeWu1Dao;
import com.demo.entity.HuiLvTable;
import com.demo.entity.LeiMuTable;
import com.demo.entity.ZhangHao;
import com.demo.entity.Courier.YunFeiTable;
import com.demo.entity.order.OrderTable;
import com.demo.entity.user.YeWu1;
import com.demo.utils.ApplicationUtils;
import com.demo.utils.DateUtils;
import com.demo.utils.HttpClientUtils;
@Service
@Transactional
public class DhOrderBiz {
	@Resource
	private OrderTableDao orderTableDao;
	@Resource
	private HuiLvDao huiLvDao;
	@Resource
	private ZhangHaoDao zhangHaoDao;
	@Resource
	private YunFeiTableDao yunFeiTableDao;
	@Resource
	private DhCommonApiBiz dhCommonApiBiz;
	@Resource
	private DhProductApiBiz dhProductApiBiz;
	@Resource
	private LeiMuDao leiMuDao;
	@Resource
	private OrderStatZhBiz orderStatZhBiz;
	@Resource
	private OrderStatLmBiz orderStatLmBiz;
	@Resource
	private YeWu1Dao yewu1dao;
	//��������
	public int addOrder(ZhangHao dhAccount, JSONObject json, OrderTable order){
		try {
			if (order != null) {
				return 0;
			}
			
			String ff = DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss:SSS");
	    	List<HuiLvTable> hh = huiLvDao.getHuiTime(ff);
			List<ZhangHao> zh = zhangHaoDao.getZhangHaoIdAll(dhAccount.getId());

			String orderId = json.getString("rfxno");
			order = new OrderTable();
			order.setOrderId(orderId);
			order.setOrdersn(this.getOrderSn());
			order.setAddress1(json.getString("addressline1")); // ��ַ
			// ȡ����
			String country = json.getString("country");
			YunFeiTable yunFei = yunFeiTableDao.getCorresponding(country);
			if (yunFei != null) {
				country = yunFei.getGuojia();
			}
			order.setCountry(country);
			order.setCity(json.getString("city")); // ����
			order.setProvince(json.getString("state")); // ��
			order.setPostcode(json.getString("postalcode")); // �ʱ�
			order.setTelephone(json.getString("tel")); // �绰
			order.setReceiver(json.getString("firstname") + " " + json.getString("lastname")); // �ջ���
			// ȡ�����ַ
			String guowaidizhi = "Contact Name: " + order.getReceiver() + "\r\n"
					+ "Address: " + order.getAddress1() + "\r\n"
					+ "City: " + order.getCity() + "\r\n"
					+ "State: " + order.getProvince() + "\r\n" 
					+ "Country: " + order.getCountry() + "\r\n"
					+ "Postal Code: " + order.getPostcode() + "\r\n"
					+ "Phone Number: " + order.getTelephone();
			order.setGuowaidizhi(guowaidizhi);
			order.setShippingtype(json.getString("shippingtype")); // ���䷽ʽ
			// ȡ��������, ȡ���
			JSONObject orderDetail = this.getOrder(orderId, dhAccount);
			JSONObject rfxDto =null;
			if (orderDetail.containsKey("rfxDto")) {
				rfxDto = orderDetail.getJSONObject("rfxDto");
			} else if (orderDetail.containsKey("rfxVo")) {
				rfxDto = orderDetail.getJSONObject("rfxVo");
			}
			if (rfxDto == null) {
				return 0;
			}
			
			Double totalPriceOfProduct = rfxDto.getDouble("totalpriceofproduct"); // ��Ʒ���
			Double shipcost = rfxDto.getDouble("shipcost"); // �˷�
			Double commissionAmount = rfxDto.getDouble("commissionAmount"); // Ӷ�� 
			Double money = totalPriceOfProduct + shipcost - commissionAmount;  // ʵ�ս��
			money = Double.parseDouble(new DecimalFormat("#.00").format(money));
			order.setMoney(money);
			
			String buyerId = rfxDto.getString("buyerid"); // ���id
			order.setBuyerId(buyerId);
					
			// ȡ������Ʒ
			JSONObject orderProduct = this.getOrderProduct(json.getString("rfxid"), dhAccount);
			JSONArray products = orderProduct.getJSONArray("lstProductDTO");
			String wuping = ""; // ��Ʒ
			String bianma = ""; // ����
			String remark = ""; // ��ұ�ע
			for (int i = 0; i < products.size(); i++) {
				JSONObject product = products.getJSONObject(i);
				// ������Ʒ
				if (!wuping.equals("")) {
					wuping += "+";
				}
				wuping += product.getString("productname")
						+  "(����:" + product.getString("quantity") + "; " ;
				// ��Ʒ��������
				if (!product.getString("proAttrDTOLst").equals("null")) {
					JSONArray productAttrs = product.getJSONArray("proAttrDTOLst");
					for (int j = 0; j < productAttrs.size(); j++) {
						if (!productAttrs.getJSONObject(j).getString("valDTO").equals("null")) {
							wuping += productAttrs.getJSONObject(j).getJSONObject("valDTO").getString("attrValue") + ";";
						}
					}
				}
				wuping += ")";
				// ȡ����
				if (!bianma.equals("")) {
					bianma += ",";
				}
				bianma = product.getString("itemcode");
				// ȡ��ұ�ע
				if (!remark.equals("")) {
					remark += ",";
				}
				if (!product.getString("specification").equals("null")) {
					remark +=  product.getString("specification");
				}
			}
			if(zh.size()!=0){
	      	  order.setArtistsGetOrdersId(zh.get(0).getClipArtId());
	        }
		    if(hh.size() != 0){
	        	for (int j = 0; j < hh.size(); j++) {
					order.setHuilv(hh.get(0).getHuilv());
				}
	        }
			order.setFenpei(1l);
	        order.setQianshou(0l);
	        order.setShangwang(0l);
	        order.setRuzhang(0l);
	        order.setGetordersId(1l);
	        order.setBusinessId(1l);
	        order.setArtistsGetOrders(1l);
			// ȡ��Ŀ
			LeiMuTable leiMu = this.getOrderLeiMu(orderProduct, dhAccount);
			if (leiMu != null) {
				order.setLeimuid(leiMu.getId());
				order.setCaigouyuan(leiMu.getUserid());
			}
			order.setWuping(wuping);
			order.setBianma(bianma);
			order.setRemark(remark);
			order.setZhanghaoId(dhAccount.getId());
			order.setTime(new Date());
			orderTableDao.merge(order);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	// ���¾��׶���
	public int updateJiufenOrder(ZhangHao dhAccount, JSONObject json, OrderTable order) {
		if (order == null) {
			return 0;
		}
		
	 	// �޸ľ���״̬
 		if (order.getJiufen() == null || order.getJiufen().equals(0l)) {
	    	YeWu1 ss = yewu1dao.getTimes().get(0);//��ѯ���ʱ��
			String ff = DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss:SSS");
	    	order.setJiufentime(ff);
	    	order.setJiufen(1l);
	    	order.setYewuId(ss.getUserid());
	    	orderTableDao.merge(order);
	    	
	    	ss.setDisputeTime(ff);
	    	yewu1dao.merge(ss);
	    	
	    	// ����ͳ������
			orderStatZhBiz.updateStatData(order.getTime(), dhAccount); //�˺�
			LeiMuTable lm = leiMuDao.get(order.getLeimuid());
			if (lm != null)	{
				orderStatLmBiz.updateStatData(order.getTime(), lm);//��Ŀ
			}
	    	return 1;
 		}
		return 0;
	}
	
	//���ȡ���˿�����
	public int updateOrder(ZhangHao dhAccount, JSONObject json, OrderTable order) {
		if (order == null) {
			return 0;
		}
		
		try {
			order.setJiufen(0l);
			order.setJiufentime(null);
			orderTableDao.merge(order);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	//���׳ɹ�
	public int updateSuccess(ZhangHao dhAccount, JSONObject json, OrderTable order) {
		if (order == null) {
			return 0;
		}
        
		try {
			if (order.getRuzhang() == null || order.getRuzhang().equals(0l)) {
				JSONObject orderDetail = this.getOrder(order.getOrderId(), dhAccount);
				JSONObject rfxDto =null;
				if (orderDetail.containsKey("rfxDto")) {
					rfxDto = orderDetail.getJSONObject("rfxDto");
				} else if (orderDetail.containsKey("rfxVo")) {
					rfxDto = orderDetail.getJSONObject("rfxVo");
				}
				if (rfxDto == null) {
					return 0;
				}
				Double totalrefund = rfxDto.getDouble("totalrefund"); // �ܹ����˿��	
			
				order.setRuzhang(1l);
				order.setRuzhangtime(new Date());
				order.setTuikuan(totalrefund);
				orderTableDao.merge(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	//ȡ�˿�
	public int updateTuiKuan(ZhangHao dhAccount, JSONObject json, OrderTable order) {
		if (order == null) {
			return 0;
		}

		try {
			if (order.getTuikuan() == null || order.getTuikuan().equals(0l)) {
				JSONObject orderDetail = this.getOrder(order.getOrderId(), dhAccount);
				JSONObject rfxDto =null;
				if (orderDetail.containsKey("rfxDto")) {
					rfxDto = orderDetail.getJSONObject("rfxDto");
				} else if (orderDetail.containsKey("rfxVo")) {
					rfxDto = orderDetail.getJSONObject("rfxVo");
				}
				if (rfxDto == null) {
					return 0;
				}
				Double totalrefund = rfxDto.getDouble("totalrefund"); // �ܹ����˿��	
				
				order.setTuikuan(totalrefund);
				orderTableDao.merge(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * ��ȡһ��������Ϣ
	 * @param rfxno ������
	 * @param dhAccount
	 * @return
	 */
	public JSONObject getOrder(String rfxno, ZhangHao dhAccount) {
		String apiUrl = (String) ApplicationUtils.get("dhgateApiUrl");
		Map<String, String> paramMap = new HashMap<String, String>();
		if (!dhCommonApiBiz.putSystemParamsToParamMap(paramMap, dhAccount,
				"dh.order.get")) {
			return null;
		}
		paramMap.put("rfxno", rfxno);

		JSONObject respJson = HttpClientUtils.doPost(apiUrl, paramMap);
		if (respJson != null) {
			if (respJson.containsKey("code") 
					&& Integer.parseInt(respJson.getString("code")) != 0) {
				return null;
			}
			
			JSONObject statusObj = respJson.getJSONObject("status");
			if (Integer.parseInt(statusObj.getString("code")) == 0) {
				return respJson;
			}
		}
		return null;
	}
	
	/**
	 * ��ȡ������Ŀ
	 * @param orderProduct
	 * @return
	 */
	public LeiMuTable getOrderLeiMu(JSONObject orderProduct, ZhangHao dhAccount) {
		// ȡ��Ʒ����
		String itemCode = orderProduct.getJSONArray("lstProductDTO").getJSONObject(0).getString("itemcode");
		JSONObject product = dhProductApiBiz.getProduct(itemCode, dhAccount);
		if (product == null) {
			return null;
		}
		// ȡ��ƷcatePubId��Ӧ��userId��ִ�з���
		String catePubId = product.getJSONObject("product").getString("catePubId");
		String parentCatePubId = catePubId.substring(0, 3);
		LeiMuTable leiMu = leiMuDao.getByCateId(parentCatePubId);
		if (leiMu == null) {
			return null;
		}
		return leiMu;
	}
	
	/**
	 * ��ȡ�����Ĳ�Ʒ��Ϣ
	 * @param rfxid ��������id
	 * @param dhAccount
	 * @return
	 */
	public JSONObject getOrderProduct(String rfxid, ZhangHao dhAccount) {
		String apiUrl = (String) ApplicationUtils.get("dhgateApiUrl");
		Map<String, String> paramMap = new HashMap<String, String>();
		if (!dhCommonApiBiz.putSystemParamsToParamMap(paramMap, dhAccount,
				"dh.order.product.get")) {
			return null;
		}
		paramMap.put("rfxid", rfxid);

		JSONObject respJson = HttpClientUtils.doPost(apiUrl, paramMap);
		if (respJson != null) {
			if (respJson.containsKey("code") 
					&& Integer.parseInt(respJson.getString("code")) != 0) {
				return null;
			}
			
			JSONObject statusObj = respJson.getJSONObject("status");
			if (Integer.parseInt(statusObj.getString("code")) == 0) {
				return respJson;
			}
		}
		return null;
	}
	
	private String getOrderSn() {
		return new SimpleDateFormat("yyyy-MM-dd-HHmmss-SSS").format(new Date())
				+ new Random().nextInt(1000);
	}
}
