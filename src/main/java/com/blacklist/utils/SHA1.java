package com.blacklist.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1 {
	/**
	 * SHA1加密
	 * */
	public static String str2SHA1(String str) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(str.getBytes());
			byte[] msgDigest = digest.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < msgDigest.length; i++) {
				String shaHex = Integer.toHexString(msgDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					sb.append(0);
				}
				sb.append(shaHex);
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
}
