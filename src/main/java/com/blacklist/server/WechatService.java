package com.blacklist.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blacklist.config.WechatConfig;
import com.blacklist.enums.WechatType;
import com.blacklist.service.TopicService;
import com.blacklist.utils.LuceneIKUtil;
import com.blacklist.utils.SHA1;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class WechatService {
	private static Logger log = LoggerFactory.getLogger(WechatService.class);
	@Autowired
	IndexServer indexServer;
	@Autowired
	TopicService topicService;

	public String getToken() throws IOException {
		if ((System.currentTimeMillis() / 1000 - WechatConfig.expiresTime) > 7000) {
			String pathUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
					+ WechatConfig.appid
					+ "&secret="
					+ WechatConfig.secret;
			URL url = new URL(pathUrl);
			HttpURLConnection httpConn = (HttpURLConnection) url
					.openConnection();
			httpConn.setDoOutput(true);// 使用 URL 连接进行输出
			httpConn.setDoInput(true);// 使用 URL 连接进行输入
			httpConn.setUseCaches(false);// 忽略缓存
			httpConn.setRequestMethod("GET");// 设置URL请求方法
			httpConn.setRequestProperty("Accept", "*/*"); // 设置接收数据的格式
			httpConn.connect();

			int length = (int) httpConn.getContentLength();// 获取长度
			InputStream is = httpConn.getInputStream();
			if (length != -1) {
				byte[] data = new byte[length];
				byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(temp)) > 0) {
					System.arraycopy(temp, 0, data, destPos, readLen);
					destPos += readLen;
				}
				String result = new String(data, "UTF-8");
				JsonObject json = new JsonParser().parse(result)
						.getAsJsonObject();
				WechatConfig.token = json.get("access_token")
						.getAsString();
				WechatConfig.expiresTime = System.currentTimeMillis() / 1000;
			}
		}

		System.out.println("wechat access token is:" + WechatConfig.token);
		return WechatConfig.token;
	}

	public boolean validate(String signature, String timestamp, String nonce) {
		List<String> list = new ArrayList<String>();
		list.add(timestamp);
		list.add(nonce);
		list.add(WechatConfig.token);
		Collections.sort(list);
		String temp = String.join("", list);
		if (signature.equals(SHA1.str2SHA1(temp))) {
			return true;
		}
		return false;
	}

	public Map<String, String> parseReq(HttpServletRequest request) {
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			InputStream inputStream = request.getInputStream();
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			Element root = document.getRootElement();
			@SuppressWarnings("unchecked")
			List<Element> elementList = root.elements();
			for (Element e : elementList) {
				resultMap.put(e.getName(), cut(e.getText()));
			}
			inputStream.close();
			inputStream = null;
		} catch (IOException | DocumentException e1) {
			log.error("wechat parseReq error:{}", e1.getMessage());
			e1.printStackTrace();
		}

		return resultMap;
	}

	public boolean validate() {
		return false;
	}
	
	private String cut(String str) {
		return str.replace("<![CDATA[", "").replace("]]>", "");
	}
	
	private String baseTextResponse(Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		sb.append("<ToUserName><![CDATA[" + map.get("FromUserName")
				+ "]]></ToUserName>");
		sb.append("<FromUserName><![CDATA[" + map.get("ToUserName")
				+ "]]></FromUserName>");
		sb.append("<CreateTime>" + System.currentTimeMillis() / 1000
				+ "</CreateTime>");
		sb.append("<MsgType><![CDATA[text]]></MsgType>");
		sb.append("<Content><![CDATA[###MSG###]]></Content>");
		sb.append("</xml>");
		return sb.toString();
	}

	private String buildNotSupportResponse(Map<String, String> map) {
		return baseTextResponse(map).replace("###MSG###", "不支持的消息！");
	}

	private String buildWelcome(Map<String, String> map) {
		return baseTextResponse(map).replace("###MSG###", "然而，你并不知道怎么玩");
	}
	
	private String buildError(Map<String, String> map, String err) {
		return baseTextResponse(map).replace("###MSG###", err);
	}

	private String buildResponse(Map<String, String> map) {
		if(WechatConfig.autoUser.equalsIgnoreCase(map.get("FromUserName"))) {
			if("fullIndex".equalsIgnoreCase(map.get("Content"))) {
				try {
					LuceneIKUtil.getInstance().createIndex(
							indexServer.build(topicService.getAll()), true);
					log.info("fullIndex success...");
				} catch (Exception e) {
					log.error("rebuild index error:{}", e);
					return buildError(map, e.getMessage());
				}
				return baseTextResponse(map).replace("###MSG###", "操作成功！");
			} else {
				return buildNotSupportResponse(map);
			}
		} else {
			log.info("from user is:{}", map.get("FromUserName"));
			return baseTextResponse(map).replace("###MSG###", "都说了你不知道怎么玩");
		}
	}

	public String buildResponse(Map<String, String> map,
			WechatType type) {
		switch (type) {
		case TEXT:
			return buildResponse(map);
		case EVENT:
			return buildWelcome(map);
		default:
			return buildNotSupportResponse(map);
		}
	}
}
