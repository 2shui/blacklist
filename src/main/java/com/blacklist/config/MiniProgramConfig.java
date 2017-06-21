package com.blacklist.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix="miniprogram")
@PropertySource("classpath:source.properties")
public class MiniProgramConfig {
	public static String appid;
	public static String appSecret;
	public static String templateId;
	
	public static String userName163;
	public static String pwd163;
	public static String userNameGmail;
	public static String pwdGmail;
	public static String userName126;
	public static String pwd126;
	public static String getAppid() {
		return appid;
	}
	public static void setAppid(String appid) {
		MiniProgramConfig.appid = appid;
	}
	public static String getAppSecret() {
		return appSecret;
	}
	public static void setAppSecret(String appSecret) {
		MiniProgramConfig.appSecret = appSecret;
	}
	public static String getTemplateId() {
		return templateId;
	}
	public static void setTemplateId(String templateId) {
		MiniProgramConfig.templateId = templateId;
	}
	public static String getUserName163() {
		return userName163;
	}
	public static void setUserName163(String userName163) {
		MiniProgramConfig.userName163 = userName163;
	}
	public static String getPwd163() {
		return pwd163;
	}
	public static void setPwd163(String pwd163) {
		MiniProgramConfig.pwd163 = pwd163;
	}
	public static String getUserNameGmail() {
		return userNameGmail;
	}
	public static void setUserNameGmail(String userNameGmail) {
		MiniProgramConfig.userNameGmail = userNameGmail;
	}
	public static String getPwdGmail() {
		return pwdGmail;
	}
	public static void setPwdGmail(String pwdGmail) {
		MiniProgramConfig.pwdGmail = pwdGmail;
	}
	public static String getUserName126() {
		return userName126;
	}
	public static void setUserName126(String userName126) {
		MiniProgramConfig.userName126 = userName126;
	}
	public static String getPwd126() {
		return pwd126;
	}
	public static void setPwd126(String pwd126) {
		MiniProgramConfig.pwd126 = pwd126;
	}
	
}
