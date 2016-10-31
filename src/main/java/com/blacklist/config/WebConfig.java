package com.blacklist.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix="fullIndex")
@PropertySource("classpath:source.properties")
public class WebConfig {
	public static String path;// index path
	public static String company;// index 公司名
	public static String city;// index city
	public static String id;
	public static String sketch;
	public static float companyPoint;
	public static float cityPoint;
	public static float sketchPoint;
	public static String site;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public float getCompanyPoint() {
		return companyPoint;
	}

	public void setCompanyPoint(float companyPoint) {
		this.companyPoint = companyPoint;
	}

	public float getCityPoint() {
		return cityPoint;
	}

	public void setCityPoint(float cityPoint) {
		this.cityPoint = cityPoint;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getSketch() {
		return sketch;
	}

	public void setSketch(String sketch) {
		this.sketch = sketch;
	}

	public float getSketchPoint() {
		return sketchPoint;
	}

	public void setSketchPoint(float sketchPoint) {
		WebConfig.sketchPoint = sketchPoint;
	}

	public static String getSite() {
		return site;
	}

	public static void setSite(String site) {
		WebConfig.site = site;
	}
	
}
