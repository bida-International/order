package com.demo.bean;

import java.util.HashMap;
import java.util.Map;

public class Constants {

	/** 速卖通与敦煌网的自定义规格名称特殊对应关系, key为速卖通规格, value为敦煌规格 */
	public static Map<String, String> prodAttrNameMap = new HashMap<String, String>();
	static {
		// TODO 待补充完善
		prodAttrNameMap.put("Brand Name", "Brand");
		prodAttrNameMap.put("Size", "Size type");
		prodAttrNameMap.put("Item Type", "Product type");
		prodAttrNameMap.put("Jewelry Sets Type", "Jewelry Settings Type");
	}
}
