package com.demo.core.config;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * site-config配置文件实体
 *
 */
@XmlRootElement(name = "site-config")
@XmlType(propOrder = {
		"dhgateAppKey", "dhgateAppSecret", "dhgateApiUrl", "dhgateGetTokenUrl",
		"aliRedirectUri", "aliApiUrl", "trackApiUrl"})
public class SiteConfig {
	
	public static final String PATH = "/config/site-config.xml";
	
	private String dhgateAppKey;
	private String dhgateAppSecret;
	private String dhgateApiUrl;
	private String dhgateGetTokenUrl;
	private String aliRedirectUri;
	private String aliApiUrl;
	private String trackApiUrl;
	
	public String getDhgateAppKey() {
		return dhgateAppKey;
	}
	public void setDhgateAppKey(String dhgateAppKey) {
		this.dhgateAppKey = dhgateAppKey;
	}
	public String getDhgateAppSecret() {
		return dhgateAppSecret;
	}
	public void setDhgateAppSecret(String dhgateAppSecret) {
		this.dhgateAppSecret = dhgateAppSecret;
	}
	public String getDhgateApiUrl() {
		return dhgateApiUrl;
	}
	public void setDhgateApiUrl(String dhgateApiUrl) {
		this.dhgateApiUrl = dhgateApiUrl;
	}
	public String getDhgateGetTokenUrl() {
		return dhgateGetTokenUrl;
	}
	public void setDhgateGetTokenUrl(String dhgateGetTokenUrl) {
		this.dhgateGetTokenUrl = dhgateGetTokenUrl;
	}
	public String getAliRedirectUri() {
		return aliRedirectUri;
	}
	public void setAliRedirectUri(String aliRedirectUri) {
		this.aliRedirectUri = aliRedirectUri;
	}
	public String getAliApiUrl() {
		return aliApiUrl;
	}
	public void setAliApiUrl(String aliApiUrl) {
		this.aliApiUrl = aliApiUrl;
	}
	public String getTrackApiUrl() {
		return trackApiUrl;
	}
	public void setTrackApiUrl(String trackApiUrl) {
		this.trackApiUrl = trackApiUrl;
	}
	
}
