package com.demo.biz.dhgate;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import sun.misc.BASE64Encoder;

import com.demo.entity.ZhangHao;
import com.demo.utils.ApplicationUtils;
import com.demo.utils.FileUtils;
import com.demo.utils.HttpClientUtils;

@Service
public class DhAlbumApiBiz {

	@Resource
	private DhCommonApiBiz dhCommonApiBiz;
	
	public JSONObject upload(String imgUrl, ZhangHao dhAccount) {
		String fileName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
		if (fileName.indexOf("?") > 0) {
			fileName = fileName.substring(0, fileName.indexOf("?"));
		}
		String prjPath = DhAlbumApiBiz.class.getClassLoader().getResource(".").getPath();
		String filePath = prjPath + "/tmp/" + fileName;
		File file = new File(filePath);
		
		try {
			if (!file.exists()) {
				FileUtils.createFile(prjPath + "/tmp", fileName);
			}
			FileUtils.copyURLToFile(new URL(imgUrl), file);
			
			InputStream in = new FileInputStream(file);
			byte[] data = new byte[in.available()];
			in.read(data);
			in.close();
			
			//对字节数组Base64编码
	        BASE64Encoder encoder = new BASE64Encoder();
	        String imageBase64 = encoder.encode(data);//返回Base64编码过的字节数组字符串
	
	        String apiUrl = (String) ApplicationUtils.get("dhgateApiUrl");
	        Map<String, String> paramMap = new HashMap<String, String>();
			if (!dhCommonApiBiz.putSystemParamsToParamMap(paramMap, dhAccount,
					"dh.album.img.upload", "2.0")) {
				return null;
			}
			paramMap.put("funType", "albu");
			paramMap.put("imageBannerName", dhAccount.getAccount());
			paramMap.put("imageBase64", imageBase64);
			paramMap.put("imageName", fileName);
			
			JSONObject respJson = HttpClientUtils.doPost(apiUrl, paramMap);
			if (respJson != null) {
				if (respJson.containsKey("code")) {
					if (respJson.getString("code").equals("2") || 
							respJson.getString("code").equals("40")) {
						dhCommonApiBiz.clearAccessToken(dhAccount);
						return null;
					} else if (!respJson.getString("code").equals("0")) {
						return null;
					}
				}
				
				JSONObject statusObj = respJson.getJSONObject("status");
				if (Integer.parseInt(statusObj.getString("code")) == 0) {
					return respJson;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			file.delete();
		}
		return null;
	}
}
