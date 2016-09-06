package com.blacklist.utils;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 类功能描述: Json工具类
 *
 * @version 1.0
 * @author 12519
 * @createDate 2016年8月4日 上午10:20:59
 */
public final class JsonUtil {

	private static ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();

	public static ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	public static final String toString(Object o) {
		try {
			return objectMapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			throw new SystemException(e.toString());
		}
	}

}
