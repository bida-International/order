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
		queryStateMap.put(0, "无法跟踪");
		queryStateMap.put(1, "正常并查有信息");
		queryStateMap.put(2, "正常但尚无信息");
		queryStateMap.put(3, "网站错误");
		queryStateMap.put(4, "处理错误");
		queryStateMap.put(5, "查询发生错误使用缓存对象");
		queryStateMap.put(6, "对方服务器忙");
		queryStateMap.put(7, "对方服务器忙使用缓存对象");
	}

	/**
	 * 
	 * @param danhao 单号
	 * @param et 渠道类型 0.国际邮政
	 */
	public QueryTrackResult doQuery(String danhao, String et) {
		QueryTrackResult queryResult = null;
		//String apiUrl = ApplicationUtils.get("trackApiUrl") + "?num="
		String apiUrl = "http://api-r.17track.net:8088/Rest/HandlerTrack.ashx?num="
				+ danhao + "&et="
				+ et + "&pt=0";
		System.out.println(apiUrl);
		
		JSONObject respJson = HttpClientUtils.doGet(apiUrl);
		if (respJson != null) {
			System.out.println(respJson);
			queryResult = new QueryTrackResult();
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
		String allTrackInfo = "";
		if (data.containsKey("f") && !data.getString("f").equals("null")) {
			packageState = data.getInt("f");
		}
		if (data.containsKey("z") && !data.getString("z").equals("null")) {
			JSONObject zJson = data.getJSONObject("z");
			latestTrackInfo = zJson.getString("a") + " " + zJson.getString("b");
		}
		if (data.containsKey("x") && !data.getString("x").equals("null")) {
			//allTrackInfo += "发件国家：\r\n";
			JSONArray xJsonArr = data.getJSONArray("x");
			for (int i = xJsonArr.size() - 1; i >= 0; i--) {
				allTrackInfo += xJsonArr.getJSONObject(i).getString("a") 
						+ " " + xJsonArr.getJSONObject(i).getString("b") + "<br/>";
			}
		}
//		if (data.containsKey("y") && !data.getString("y").equals("null")) {
//			//allTrackInfo += "收件国家：\r\n";
//			JSONArray yJsonArr = data.getJSONArray("y");
//			for (int i = yJsonArr.size() - 1; i >= 0 ; i--) {
//				allTrackInfo += yJsonArr.getJSONObject(i).getString("a") 
//						+ " " + yJsonArr.getJSONObject(i).getString("b") + "<br/>";
//			}
//		}

		queryResult.setPackageState(packageState);
		queryResult.setLatestTrackInfo(latestTrackInfo);
		queryResult.setAllTrackInfo(allTrackInfo);
	}
	
	private void setExpressQueryTrackResult2(QueryTrackResult queryResult, 
			JSONObject data) {
		Integer packageState = null;
		String latestTrackInfo = null;
		String allTrackInfo = "";
		if (data.containsKey("e") && !data.getString("e").equals("null")) {
			packageState = data.getInt("e");
		}
		if (data.containsKey("z") && !data.getString("z").equals("null")) {
			JSONObject zJson = data.getJSONObject("z");
			latestTrackInfo = zJson.getString("a") + " " + zJson.getString("b");
		}
		if (data.containsKey("y") && !data.getString("y").equals("null")) {
			JSONArray yJsonArr = data.getJSONArray("y");
			for (int i = yJsonArr.size() - 1; i >= 0 ; i--) {
				allTrackInfo += yJsonArr.getJSONObject(i).getString("a") 
						+ " " + yJsonArr.getJSONObject(i).getString("b") + "<br/>";
			}
		}

		queryResult.setPackageState(packageState);
		queryResult.setLatestTrackInfo(latestTrackInfo);
		queryResult.setAllTrackInfo(allTrackInfo);
	}
}
