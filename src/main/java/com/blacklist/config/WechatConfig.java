package com.blacklist.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix="wechat")
@PropertySource("classpath:source.properties")
public class WechatConfig {
	public static String appid;
	public static String secret;
	public static long expiresTime;
	public static String token;
	public static String autoUser;
	
	public static String getAppid() {
		return appid;
	}
	public static void setAppid(String appid) {
		WechatConfig.appid = appid;
	}
	public static String getSecret() {
		return secret;
	}
	public static void setSecret(String secret) {
		WechatConfig.secret = secret;
	}
	public static long getExpiresTime() {
		return expiresTime;
	}
	public static void setExpiresTime(long expiresTime) {
		WechatConfig.expiresTime = expiresTime;
	}
	public static String getToken() {
		return token;
	}
	public static void setToken(String token) {
		WechatConfig.token = token;
	}
	public static String getAutoUser() {
		return autoUser;
	}
	public static void setAutoUser(String autoUser) {
		WechatConfig.autoUser = autoUser;
	}
	
}
