package com.demo.bean;

import java.util.HashMap;
import java.util.Map;

public class Constants {

	/** ����ͨ��ػ������Զ��������������Ӧ��ϵ, keyΪ����ͨ���, valueΪ�ػ͹�� */
	public static Map<String, String> prodAttrNameMap = new HashMap<String, String>();
	static {
		// TODO ����������
		prodAttrNameMap.put("Brand Name", "Brand");
		prodAttrNameMap.put("Size", "Size type");
		prodAttrNameMap.put("Item Type", "Product type");
		prodAttrNameMap.put("Jewelry Sets Type", "Jewelry Settings Type");
	}
}
