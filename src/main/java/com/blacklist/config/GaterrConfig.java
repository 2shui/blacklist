package com.blacklist.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix="gaterr.search")
@PropertySource("classpath:gaterr.properties")
@Deprecated
public class GaterrConfig {
	public static String gurl;

	public static String getGurl() {
		return gurl;
	}

	public static void setGurl(String gurl) {
		GaterrConfig.gurl = gurl;
	}
	
}
