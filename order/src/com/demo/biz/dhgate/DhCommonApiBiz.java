package com.demo.biz.dhgate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.ZhangHaoDao;
import com.demo.entity.ZhangHao;
import com.demo.utils.ApplicationUtils;
import com.demo.utils.DES;
import com.demo.utils.HttpClientUtils;

/**
 * 敦煌网公共API服务
 *
 */
@Service
@Transactional
public class DhCommonApiBiz {

	private Logger logger = LoggerFactory.getLogger(getClass());

	public final static String ERR_TOKEN = "授权信息不可用";
	public final static String CONN_ERR = "与敦煌网服务器通信失败";

	public final static String ACCOUNT_TYPE = "dh";
	
	@Resource
	private ZhangHaoDao zhangHaoDao;

	/**
	 * 获取账号的access_token
	 * 
	 * @param account
	 * @return 返回null表示获取失败
	 */
	public String getAccessToken(String account) {
		ZhangHao dhAccount = zhangHaoDao.findUnique(account, ACCOUNT_TYPE);
		if (dhAccount == null) {
			return null;
		}

		return this.getAccessToken(dhAccount);
	}
	
	/**
	 * 获取账号的access_token
	 * @param dhgateAccount
	 * @return
	 */
	public String getAccessToken(ZhangHao dhAccount) {
		String accessToken = dhAccount.getAccess_token();
		if (accessToken != null && !accessToken.equals("")) {
			// 检查是否在有效期内
			boolean inAvailableTime = true;
			Long expireIn = dhAccount.getExpires_in(); // 单位: 毫秒
			Long updateTime = dhAccount.getUpdate_time(); // 单位: 秒
			if (expireIn == null || updateTime == null) {
				inAvailableTime = false;
			} else if (updateTime * 1000 + expireIn <= new Date().getTime() - 300000l) {
				inAvailableTime = false;
			}

			// 过期时使用refresh_token重新获取access_token
			if (!inAvailableTime && dhAccount.getRefresh_token() != null
					&& !dhAccount.getRefresh_token().equals("")) {
				accessToken = this.fetchAccessTokenByRefreshToken(dhAccount);
			}
		}
		// 前面的处理后仍为空，再使用账号密码直接获取
		if (accessToken == null && dhAccount.getPass() != null) {
			String result = this.fetchAccessToken(dhAccount.getAccount(), 
					new DES().getDesString(dhAccount.getPass()));
			if (result!= null && result.equals("success")) {
				accessToken = dhAccount.getAccess_token();
			}
		}
		return accessToken;
	}
	
	/**
	 * 清除授权信息，当授权信息不可用时清除
	 * @param dhAccount
	 */
	public void clearAccessToken(ZhangHao dhAccount) {
		dhAccount.setAccess_token(null);
		zhangHaoDao.merge(dhAccount);
	}

	/**
	 * 通过调用api获取用户的access_token
	 * 
	 * @param account
	 * @param pass
	 * @return 返回null或者包含error表示获取失败, success-成功
	 */
	public String fetchAccessToken(String account, String pass) {
		ZhangHao dhAccount = zhangHaoDao.findUnique(account, ACCOUNT_TYPE);
		if (dhAccount == null) {
			dhAccount = new ZhangHao();
			dhAccount.setAccount(account);
			dhAccount
					.setCreate_time(new java.sql.Date(new Date().getTime()));
		}

		String apiUrl = (String) ApplicationUtils.get("dhgateGetTokenUrl");
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("grant_type", "password");
		paramMap.put("username", account);
		paramMap.put("password", pass);
		paramMap.put("client_id", (String) ApplicationUtils.get("dhgateAppKey"));
		paramMap.put("client_secret",
				(String) ApplicationUtils.get("dhgateAppSecret"));
		paramMap.put("scope", "basic");

		JSONObject respJson = HttpClientUtils.doPost(apiUrl, paramMap);
		if (respJson != null) {
			if (!respJson.containsKey("error")) {
				logger.debug("respJson:" + respJson);
				String accessToken = respJson.getString("access_token");
				// 更新数据
				this.saveDhAccountTokenInfo(dhAccount, accessToken,
						respJson.getLong("expires_in"),
						respJson.getString("refresh_token"));
				try {
					this.fetchDhUserInfo(dhAccount);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return "success";
			} else {
				return "授权失败: " + respJson.getString("errorDescription");
			}
		}
		return null;
	}

	/**
	 * 使用refresh_token重新获取access_token
	 * 
	 * @param dhgateAccount
	 * @return
	 */
	private String fetchAccessTokenByRefreshToken(ZhangHao dhAccount) {
		String apiUrl = (String) ApplicationUtils.get("dhgateGetTokenUrl");
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("grant_type", "refresh_token");
		paramMap.put("refresh_token", dhAccount.getRefresh_token());
		paramMap.put("client_id", (String) ApplicationUtils.get("dhgateAppKey"));
		paramMap.put("client_secret",
				(String) ApplicationUtils.get("dhgateAppSecret"));
		paramMap.put("scope", "basic");

		JSONObject respJson = HttpClientUtils.doPost(apiUrl, paramMap);
		if (respJson != null && !respJson.containsKey("error")) {
			logger.debug("respJson:" + respJson);
			String accessToken = respJson.getString("access_token");
			// 更新数据
			this.saveDhAccountTokenInfo(dhAccount, accessToken,
					respJson.getLong("expires_in"),
					respJson.getString("refresh_token"));
			this.fetchDhUserInfo(dhAccount);
			return accessToken;
		}
		return null;
	}

	/**
	 * 添加公共的请求参数
	 * 
	 * @param paramMap
	 * @param dhgateAccount
	 * @param apiMethod
	 */
	public boolean putSystemParamsToParamMap(Map<String, String> paramMap,
			ZhangHao dhAccount, String apiMethod) {
		return this.putSystemParamsToParamMap(paramMap, dhAccount, apiMethod, "1.0");
	}
	
	/**
	 * 添加公共的请求参数
	 * 
	 * @param paramMap
	 * @param dhgateAccount
	 * @param apiMethod
	 */
	public boolean putSystemParamsToParamMap(Map<String, String> paramMap,
			ZhangHao dhAccount, String apiMethod, String version) {
		String accessToken = this.getAccessToken(dhAccount);
		if (accessToken == null) {
			return false;
		}
		paramMap.put("access_token", accessToken);
		paramMap.put("method", apiMethod);
		paramMap.put("timestamp", String.valueOf(new Date().getTime()));
		paramMap.put("v", version);
		return true;
	}

	/**
	 * 获取并保存用户(敦煌网)id和nickname
	 * 
	 * @param dhgateAccount
	 * @param accessToken
	 */
	private void fetchDhUserInfo(ZhangHao dhAccount) {
		if (dhAccount.getAccess_token() == null
				|| dhAccount.getUser_id() != null) {
			return;
		}

		String apiUrl = (String) ApplicationUtils.get("dhgateApiUrl");
		Map<String, String> paramMap = new HashMap<String, String>();
		this.putSystemParamsToParamMap(paramMap, dhAccount,
				"dh.user.base.get");

		JSONObject respJson = HttpClientUtils.doPost(apiUrl, paramMap);
		if (respJson != null
				&& respJson.getJSONObject("status").getString("message")
						.equalsIgnoreCase("OK")) {
			logger.debug("respJson:" + respJson);
			String dhUserId = respJson.getJSONObject("systemuserbase")
					.getString("systemuserid");
			String dhUserNickname = respJson.getJSONObject("systemuserbase")
					.getString("nickname");
			dhAccount.setUser_id(dhUserId);
			dhAccount.setUser_nickname(dhUserNickname);
			this.zhangHaoDao.merge(dhAccount);
		}
	}

	/**
	 * 保存用户授权信息
	 * 
	 * @return
	 */
	private void saveDhAccountTokenInfo(ZhangHao dhAccount,
			String accessToken, Long expiresIn, String refreshToken) {
		dhAccount.setAccess_token(accessToken);
		dhAccount.setExpires_in(expiresIn);
		dhAccount.setRefresh_token(refreshToken);
		dhAccount.setScope("basic");
		dhAccount.setUpdate_time(new Date().getTime() / 1000);
		this.zhangHaoDao.merge(dhAccount);
	}
	
	/**
	 * 获取敦煌账号列表
	 * @param bdUserId
	 * @return
	 */
	public List<ZhangHao> getDhAccounts(Long bdUserId) {
		return zhangHaoDao.getAll(ACCOUNT_TYPE, bdUserId);
	}
}
