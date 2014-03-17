package com.demo.biz.track;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.utils.ApplicationUtils;
import com.demo.utils.HttpClientUtils;

@Service
@Transactional
public class TrackApiBiz {
	
	private static Map<Integer, String> queryStateMap = new HashMap<Integer, String>();
	static {
		queryStateMap.put(0, "�޷�����");
		queryStateMap.put(1, "������������Ϣ");
		queryStateMap.put(2, "������������Ϣ");
		queryStateMap.put(3, "��վ����");
		queryStateMap.put(4, "�������");
		queryStateMap.put(5, "��ѯ��������ʹ�û������");
		queryStateMap.put(6, "�Է�������æ");
		queryStateMap.put(7, "�Է�������æʹ�û������");
	}

	/**
	 * 
	 * @param danhao ����
	 * @param et �������� 0.��������
	 */
	public QueryTrackResult doQuery(String danhao, String et) {
		QueryTrackResult queryResult = new QueryTrackResult();
		String apiUrl = ApplicationUtils.get("trackApiUrl") + "?num="
				+ danhao + "&et="
				+ et + "&pt=0";
		
		JSONObject respJson = HttpClientUtils.doGet(apiUrl);
		if (respJson != null) {
			queryResult.setRet(respJson.getInt("ret"));
			if (!queryResult.getRet().equals(1)) {
				return queryResult;
			}

			JSONObject data = respJson.getJSONObject("dat");
			if (et.equals("0")) {
				this.setPostalQueryTrackResult(queryResult, data);
			} else {
				this.setExpressQueryTrackResult2(queryResult, data);
			}
		}
		return queryResult;
	}
	
	
	private void setPostalQueryTrackResult(QueryTrackResult queryResult, 
			JSONObject data) {
		Integer packageState = null;
		String latestTrackInfo = null;
		String allTrackInfo = null;
		if (data.containsKey("f") && !data.getString("f").equals("null")) {
			packageState = data.getInt("f");
		}
		if (data.containsKey("z") && !data.getString("z").equals("null")) {
			JSONObject zJson = data.getJSONObject("z");
			latestTrackInfo = zJson.getString("a") + " " + zJson.getString("b");
		}
		if (data.containsKey("y") && !data.getString("y").equals("null")) {
			allTrackInfo += "�ռ����ң�<br/>";
			JSONArray yJsonArr = data.getJSONArray("y");
			for (int i = 0; i < yJsonArr.size(); i++) {
				allTrackInfo += yJsonArr.getJSONObject(i).getString("a") 
						+ " " + yJsonArr.getJSONObject(i).getString("b") + "<br/>";
			}
			allTrackInfo += "<br/>";
		}
		if (data.containsKey("x") && !data.getString("x").equals("null")) {
			allTrackInfo += "�������ң�<br/>";
			JSONArray xJsonArr = data.getJSONArray("x");
			for (int i = 0; i < xJsonArr.size(); i++) {
				allTrackInfo += xJsonArr.getJSONObject(i).getString("a") 
						+ " " + xJsonArr.getJSONObject(i).getString("b") + "<br/>";
			}
			allTrackInfo += "<br/>";
		}

		queryResult.setPackageState(packageState);
		queryResult.setLatestTrackInfo(latestTrackInfo);
		queryResult.setAllTrackInfo(allTrackInfo);
	}
	
	private void setExpressQueryTrackResult2(QueryTrackResult queryResult, 
			JSONObject data) {
		Integer packageState = null;
		String latestTrackInfo = null;
		String allTrackInfo = null;
		if (data.containsKey("e") && !data.getString("e").equals("null")) {
			packageState = data.getInt("e");
		}
		if (data.containsKey("z") && !data.getString("z").equals("null")) {
			JSONObject zJson = data.getJSONObject("z");
			latestTrackInfo = zJson.getString("a") + " " + zJson.getString("b");
		}
		if (data.containsKey("y") && !data.getString("y").equals("null")) {
			JSONArray yJsonArr = data.getJSONArray("y");
			for (int i = 0; i < yJsonArr.size(); i++) {
				allTrackInfo += yJsonArr.getJSONObject(i).getString("a") 
						+ " " + yJsonArr.getJSONObject(i).getString("b") + "<br/>";
			}
		}

		queryResult.setPackageState(packageState);
		queryResult.setLatestTrackInfo(latestTrackInfo);
		queryResult.setAllTrackInfo(allTrackInfo);
	}
}
