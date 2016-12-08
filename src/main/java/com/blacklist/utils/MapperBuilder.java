package com.blacklist.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapperBuilder {
	private static Logger log = LoggerFactory.getLogger(MapperBuilder.class);

	public static Map<String, Object> buildMap(Object obj, String... keys) {
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] fields = obj.getClass().getDeclaredFields();
		List<String> target = Arrays.asList(keys);
		
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				if(target.size()<1 || target.contains(field.getName())) {
					map.put(field.getName(), field.get(obj));
				}
			}
		} catch (IllegalArgumentException e) {
			log.error("class {} Access Illegal Argument.", obj.getClass());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			log.error("class {} Access Illegal.", obj.getClass());
			e.printStackTrace();
		}
		return map;
	}
	
	public static Map<String, Object> buildMapExclude(Object obj,
			String... keys) {
		Map<String, Object> map = buildMap(obj);
		Arrays.asList(keys).stream().forEach(k -> map.remove(k));
		return map;
	}
}
