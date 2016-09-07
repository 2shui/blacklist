package com.blacklist.utils;

import java.util.Arrays;

/** 
 * 描述: 入参校验器
 *
 * @author <a href="mailto:itblacklist@admin.com">admin</a>
 */
public class RequestValidator {

	public static boolean nullValueValidator(Object... objects) {
		return Arrays.asList(objects).stream().anyMatch(obj -> null == obj);
	}
}
