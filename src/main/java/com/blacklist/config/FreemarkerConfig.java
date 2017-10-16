package com.blacklist.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix="freemarker")
@PropertySource("classpath:source.properties")
public class FreemarkerConfig {
	public static String ftlPath;
	public static String htmlPath;// 博客地址
	public static String topicPath;// 详情页地址
	public static String imgPath;
	public static String site;
	public static String imgSite;
	public static String getFtlPath() {
		return ftlPath;
	}
	public static void setFtlPath(String ftlPath) {
		FreemarkerConfig.ftlPath = ftlPath;
	}
	public static String getHtmlPath() {
		return htmlPath;
	}
	public static void setHtmlPath(String htmlPath) {
		FreemarkerConfig.htmlPath = htmlPath;
	}
	public static String getImgPath() {
		return imgPath;
	}
	public static void setImgPath(String imgPath) {
		FreemarkerConfig.imgPath = imgPath;
	}
	public static String getSite() {
		return site;
	}
	public static void setSite(String site) {
		FreemarkerConfig.site = site;
	}
	public static String getImgSite() {
		return imgSite;
	}
	public static void setImgSite(String imgSite) {
		FreemarkerConfig.imgSite = imgSite;
	}
	public static String getTopicPath() {
		return topicPath;
	}
	public static void setTopicPath(String topicPath) {
		FreemarkerConfig.topicPath = topicPath;
	}
	
}
