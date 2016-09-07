package com.blacklist.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix="alimq")
@PropertySource("classpath:source.properties")
public class AliMQConfig {
	public static String topic;
	public static String producerId;
	public static String consumerId;
	public static String accessKey;
	public static String secretKey;
	public static String timeoutMillis;
	public static String ONSAddr;
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getProducerId() {
		return producerId;
	}
	public void setProducerId(String producerId) {
		this.producerId = producerId;
	}
	public String getConsumerId() {
		return consumerId;
	}
	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}
	public String getAccessKey() {
		return accessKey;
	}
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public String getTimeoutMillis() {
		return timeoutMillis;
	}
	public void setTimeoutMillis(String timeoutMillis) {
		this.timeoutMillis = timeoutMillis;
	}
	public String getONSAddr() {
		return ONSAddr;
	}
	public void setONSAddr(String oNSAddr) {
		ONSAddr = oNSAddr;
	}
	
	
	
}
