package com.blacklist.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.blacklist.config.FreemarkerConfig;
import com.blacklist.config.SourceDescConfig;
import com.blacklist.config.WechatConfig;
import com.blacklist.domain.BlogArticle;
import com.blacklist.domain.Topic;
import com.blacklist.domain.enums.TopicEnum;
import com.blacklist.enums.WechatType;
import com.blacklist.repo.BlogArticleRepo;
import com.blacklist.repo.TopicRepo;
import com.blacklist.service.BlogArticleService;
import com.blacklist.service.TopicService;
import com.blacklist.utils.FreemarkerUtils;
import com.blacklist.utils.HttpClient;
import com.blacklist.utils.JsonUtil;
import com.blacklist.utils.LuceneIKUtil;
import com.blacklist.utils.Lunar;
import com.blacklist.utils.SHA1;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mysql.jdbc.StringUtils;

@Service
public class WechatService {
	private static Logger log = LoggerFactory.getLogger(WechatService.class);
	@Autowired
	IndexServer indexServer;
	@Autowired
	TopicService topicService;
	@Autowired
	private TopicRepo topicRepo;
	@Autowired
	BlogArticleRepo articleRepo;
	@Autowired
	BlogArticleService blogArticleService;
	
	private final Map<String, String> days = new HashMap<String, String>();
	{
		days.put("母后", "0217");
		days.put("泰山", "0430");
		days.put("兮兮", "0810");
		days.put("莉莉", "0906");
		days.put("倩倩", "1005");
		days.put("我丽", "1212");
		days.put("豆丁", "1216");
	}

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

	/**
	 * 全量索引
	 * @return
	 */
	private String funFullIndex(Map<String, String> map) {
		try {
			LuceneIKUtil.getInstance().createIndex(
					indexServer.build(topicRepo.findByStatus(TopicEnum.Status.NORMAL.getValue())), true);
			log.info("fullIndex success...");
		} catch (Exception e) {
			log.error("rebuild index error:{}", e);
			return buildError(map, "重建索引失败！");
		}
		return baseTextResponse(map).replace("###MSG###", "操作成功！");
	}
	
	/**
	 * 数据增长 cl 3 || countLast 3
	 * @param command
	 * @return
	 */
	private String funReportIncrease(Map<String, String> map, String command) {
		Integer count = topicRepo.countByStatus(TopicEnum.Status.NORMAL
				.getValue());
		Integer num = Integer.parseInt(command.split(" ")[1]);
		List<Topic> list = topicService.getLimit(num, new Sort(Direction.DESC,
				"id"));
		StringBuffer sb = new StringBuffer("count:" + count + "\t\n");
		list.forEach(topic -> {
			sb.append(topic.getSketch() + "#" + topic.getCompany() + "\t\n");
		});
		return baseTextResponse(map).replace("###MSG###", sb.toString());
	}
	
	/**
	 * 修改状态 eg:cg#s#123#2 || change status id:123 statusTo 2
	 * @param command
	 */
	private String funChangeReportState(Map<String, String> map, String command) {
		String[] str = command.split("#");
		topicRepo.updateStatus(Long.parseLong(str[2]), Integer.parseInt(str[3]));
		return baseTextResponse(map).replace("###MSG###", "操作成功！");
	}
	
	/**
	 * 静态化blog sb 111 || staticBlog id:111
	 * @param command
	 */
	private String funStaticBlog(Map<String, String> map, String command) {
		Long id = Long.parseLong(command.split(" ")[1]);
		BlogArticle article = articleRepo.findOne(id);
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String now = df.format(article.getCreateTime());
		
		Map<String, Object> ftlMap = new HashMap<String, Object>();
		ftlMap.put("title", article.getTitle());
		ftlMap.put("article", article);
		ftlMap.put("site", FreemarkerConfig.site);
		ftlMap.put("path", now);
		FreemarkerUtils.analysisTemplate(now, id+".html", ftlMap, null, null);
		return baseTextResponse(map).replace("###MSG###", "操作成功！");
	}
	
	/**
	 * 静态化博客列表页
	 * @param map
	 * @return
	 */
	private String funStaticBlogList(Map<String, String> map) {
		List<BlogArticle> articles = blogArticleService.findAll(new Sort(Direction.DESC, "id"));
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		articles.forEach(article->{
			article.setSource(df.format(article.getCreateTime()));
		});
		List<BlogArticle> list = new ArrayList<BlogArticle>(articles);
		Collections.sort(articles, new Comparator<BlogArticle> () {
			@Override
			public int compare(BlogArticle o1, BlogArticle o2) {
				return o2.getAccessNum() - o1.getAccessNum();
			}
		});
		List<BlogArticle> hot = articles.subList(0, 5);
		Map<String, Object> ftlMap = new HashMap<String, Object>();
		ftlMap.put("articles", list);
		ftlMap.put("site", FreemarkerConfig.site);
		ftlMap.put("hot", hot);
		FreemarkerUtils.analysisTemplate(null, "index.html", ftlMap, "blogList.ftl", null);
		return baseTextResponse(map).replace("###MSG###", "操作成功！");
	}
	
	/**
	 * 推送博客内容
	 * @param map
	 * @param command
	 * @return
	 */
	private String funSendBlog(Map<String, String> map, String command) {
		Long id = Long.parseLong(command.split(" ")[1]);
		BlogArticle article = articleRepo.findOne(id);
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String now = df.format(article.getCreateTime());
		
		Map<String, String> data = new HashMap<String, String> ();
		data.put("content", article.getContent());
		data.put("contentShow", article.getTitle());
		data.put("image", "http://img.itblacklist.cn/20161130/1480487508328.jpg");
		data.put("keyword", "JAVA");
		data.put("title", article.getTitle());
		data.put("userId", "100");
		data.put("userName", "IT黑名单");
		data.put("transferUrl", "http://blog.itblacklist.cn/"+now+"/"+article.getId()+".html");
		Map<String, String> params = new HashMap<String, String> ();
		params.put("article", JsonUtil.toString(data));
		
		String result = HttpClient.doPost("http://article.battcn.com/article/save", params);
		return baseTextResponse(map).replace("###MSG###", result);
	}
	
	/**
	 * 获取关注日期
	 * @param map
	 * @return
	 */
	private String funFindFllowDate(Map<String, String> map) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		Date now = new Date();
		String result = "";
		try {
			for (String key : days.keySet()) {
				Date feast = Lunar.lunarToSolar(year + days.get(key), false);
				if (feast.after(now)) {
					int cd = new Long((feast.getTime() - now.getTime())
							/ 1000 / 3600 / 24).intValue();
					if (cd < 30) {
						result += key + ":" + days.get(key) + "还有" + cd + "天\n";
					}
				}
			}
		} catch (Exception e) {
			log.error("lunar time format error:{}", e);
		}
		if(StringUtils.isEmptyOrWhitespaceOnly(result)) {
			result = "一个月内无提醒。";
		}
		return baseTextResponse(map).replace("###MSG###", result);
	}

	private String userFunSetDoc(Map<String, String> map, String command) {
		//c#setDOC \\d{4,4}[+-]
		//TODO
		return baseTextResponse(map).replace("###MSG###", "操作成功！");
	}
	
	private String userFunGetDoc(Map<String, String> map, String command) {
		//c#getDOC \\d+
		//TODO
		return baseTextResponse(map).replace("###MSG###", "xxxxx");
	}
	
	private String userFunDelDoc(Map<String, String> map, String command) {
		//c#getDOC \\d+
		//TODO
		return baseTextResponse(map).replace("###MSG###", "xxxxx");
	}
	
	private String userFunFindBlog(Map<String, String> map, String command) {
		//c#findBlog \\.+
		//TODO
		return baseTextResponse(map).replace("###MSG###", "xxxxx");
	}
	
	private boolean isAdmin(String user) {
		return WechatConfig.autoUser.equalsIgnoreCase(user);
	}
	
	/**
	 * 获取帮助信息
	 * @param map
	 * @return
	 */
	private String funHelp(Map<String, String> map) {
		String help = "fullIndex:全量索引\n"+
				"cl {num}:数据增长\n"+
				"cg#s#{id}#{state}:修改状态\n"+
				"sb {id}:静态化blog\n"+
				"sbl:静态化blog列表\n"+
				"日历:查询关注日期\n"+
				"推送博客 {id}:推送博客";
		return baseTextResponse(map).replace("###MSG###", help);
	}
	
	/**
	 * 获取命令说明
	 * @param map
	 * @param param
	 * @return
	 */
	private String userFunExplanation(Map<String, String> map, String param) {
		String arg = param.split(" ")[1];
		String desc = SourceDescConfig.explanation.get(arg);
		desc = null == desc ? "无[" + param + "]相关命令,请查证后输入." : desc;
		return baseTextResponse(map).replace("###MSG###", desc);
	}
	
	/**
	 * 获取用户帮助信息
	 * @param map
	 * @return
	 */
	private String userFunHelp(Map<String, String> map) {
		String help = "You can user command like: c#[command [param...]].\n"+
				"The command details and the explanation is as follows:\n\n"+
				"c#help\t\t\t获取帮助信息\n"+
				"c#help param\t\t获取相关命令的详细说明,param值有[setDOC getDoc delDoc findBlog]\n"+
				"c#setDOC MMdd+|-\t设置关注日期(DOC. date of concern),阳历6月6号为0606+,阴历9月9日为0909-\n"+
				"c#getDOC [num]\t\t获取当天起{num}天(默认30天)内的关注日期\n"+
				"c#delDOC MMdd+|-\t删除关注日期\n"+
				"c#findBlog {key}\t查找{key}相关博客\n"+
				"OR\n直接输入查找相关公司名单";
		return baseTextResponse(map).replace("###MSG###", help);
	}
	
	private String buildResponse(Map<String, String> map) {
		String content = map.get("Content");
		if(content.matches("c#help .+")) {
			return userFunExplanation(map, content);
		} else if(content.matches("c#setDOC \\d{4,4}[+-]")) {
			return userFunSetDoc(map, content);
		} else if("c#getDOC".equalsIgnoreCase(content) || content.matches("c#getDOC \\d+")){
			return userFunGetDoc(map, content);
		} else if(content.matches("c#delDOC \\d{4,4}[+-]")) {
			return userFunDelDoc(map, content);
		} else if(content.matches("c#findBlog .+")) {
			return userFunFindBlog(map, content);
		} else if("c#help".equalsIgnoreCase(content)) {
			return userFunHelp(map);
		} else if(isAdmin(map.get("FromUserName"))) {
			if("fullIndex".equalsIgnoreCase(content)) {
				return funFullIndex(map);
			} else if(content.matches("cl \\d+")) {
				return funReportIncrease(map, content);
			} else if(content.matches("cg#s#\\d+#\\d+")) {
				return funChangeReportState(map, content);
			} else if(content.matches("sb \\d+")) {
				return funStaticBlog(map, content);
			} else if(content.equalsIgnoreCase("sbl")) {
				return funStaticBlogList(map);
			} else if(content.equalsIgnoreCase("日历")) {
				return funFindFllowDate(map);
			} else if("help".equals(content)) {
				return funHelp(map);
			} else if(content.matches("推送博客 \\d+")) {
				return funSendBlog(map, content);
			} else {
				return buildNotSupportResponse(map);
			}
		} else {
			return baseTextResponse(map).replace("###MSG###", "未知命令");
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
