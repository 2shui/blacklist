package com.blacklist.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 
 *
 */
public class HttpClient {
	private static Logger log = LoggerFactory.getLogger(HttpClient.class);

	public static String doGet(String url, Map<String, String> parameters) {
		// TODO
		return null;
	}

	public static String doPost(String url, Map<String, String> parameters) {
		String result = "";
		BufferedReader in = null;
		PrintWriter out = null;
		StringBuffer sb = new StringBuffer();
		String params = "";

		try {
			if (null != parameters) {
				boolean firstParam = true;
				for (String name : parameters.keySet()) {
					firstParam = false;
					sb.append(name)
							.append("=")
							.append(java.net.URLEncoder.encode(
									parameters.get(name), "UTF-8"));
					if (!firstParam) {
						sb.append("&");
					}
				}
				params = sb.toString();
			}
			java.net.URL connURL = new java.net.URL(url);
			java.net.HttpURLConnection httpConn = (HttpURLConnection) connURL
					.openConnection();
			httpConn.setRequestProperty("Accept", "*/*");
			httpConn.setRequestProperty("Connection", "Keep-Alive");
			httpConn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);
			out = new PrintWriter(httpConn.getOutputStream());
			out.write(params);
			out.flush();
			in = new BufferedReader(new InputStreamReader(
					httpConn.getInputStream(), "UTF-8"));
			String line;
			while (null != (line = in.readLine())) {
				result += line;
			}
		} catch (Exception e) {
			log.error("http post error:{}", e);
		} finally {
			if (null != out) {
				out.close();
			}
			try {
				if (null != in) {
					in.close();
				}
			} catch (IOException e) {
				log.error("http post close InputStream error:{}", e);
				e.printStackTrace();
			}
		}

		return result;
	}
}
