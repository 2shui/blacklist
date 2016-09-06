package com.blacklist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableTransactionManagement
public class Main {
	/**
	 * <p>
	 * 方法描述: 启动器
	 * </p>
	 * jvm ops: -P ${env} <br/>
	 * For example:<br/>
	 * &emsp;&emsp;mvn package -P dev
	 * 
	 * @param args
	 * @author L440
	 * @createDate 2016年7月15日 下午1:10:13
	 */
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
	
	public void addInterceptors(InterceptorRegistry registry) {
//    	registry.addInterceptor(new ProxyInterceptor()).addPathPatterns("/**/**");
    }
}
