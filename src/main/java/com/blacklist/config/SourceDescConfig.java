package com.blacklist.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix="source")
@PropertySource("classpath:desc.properties")
public class SourceDescConfig {
	public static Map<String, String> explanation = new HashMap<String, String> ();
	
	
	public Map<String, String> getExplanation() {
		return explanation;
	}

	public void setExplanation(Map<String, String> explanation) {
		SourceDescConfig.explanation = explanation;
	}
}
