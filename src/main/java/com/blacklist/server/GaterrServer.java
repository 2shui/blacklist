package com.blacklist.server;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blacklist.enums.GaterrHotLink;
import com.blacklist.utils.HttpClient;

/**
 * 
 * @author itblacklist.cn
 *
 */
@Service
public class GaterrServer {
	private static Logger log = LoggerFactory.getLogger(GaterrServer.class);
	
	private static final Map<String, List<GaterrHotLink>> typeMap = new HashMap<String, List<GaterrHotLink>>();
	{
		List<GaterrHotLink> music = Arrays.asList(GaterrHotLink.BAIDUYINYUE, GaterrHotLink.TINGBAN, GaterrHotLink.WANGYIMUSIC);
		List<GaterrHotLink> topic = Arrays.asList(GaterrHotLink.DOUBAN, GaterrHotLink.ZHIHU, GaterrHotLink.JIANSHU);
		List<GaterrHotLink> movie = Arrays.asList(GaterrHotLink.KAIYAN, GaterrHotLink.TUCHONG, GaterrHotLink.BILIBILI, GaterrHotLink.TINGBAN);
		typeMap.put("music", music);
		typeMap.put("topic", topic);
		typeMap.put("movie", movie);
	}

	public String getRandom(String type) {
		List<GaterrHotLink> all = typeMap.get(type);
		GaterrHotLink one = null == all ? GaterrHotLink.UNDEFAULT :all.get(new Random().nextInt(all.size()));
		switch (one) {
		case DOUBAN:
			return getDouban();
		case KAIYAN:
			return getKaiyan();
		case TUCHONG:
			return getTuchong();
		case ZHIHU:
			return getZhihu();
		case BILIBILI:
			return getBilibili();
		case BAIDUYINYUE:
			return getBaiduMusic();
		case TINGBAN:
			return getTingban();
		case JIANSHU:
			return getJianshu();
		case WANGYIMUSIC:
			return getWangyiMusic();
		default :
			return null;
		}
	}
	
	private String getDouban() {
		String uri = GaterrHotLink.DOUBAN.getUri();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		uri = uri + sdf.format(new Date());
		JSONObject jsonObject = JSON.parseObject(HttpClient.doGet(uri));
		JSONArray jsonArray = jsonObject.getJSONArray("posts");
		return jsonArray.getJSONObject(new Random().nextInt(jsonArray.size())).getString("original_url");
	}
	private String getKaiyan() {
		String url = GaterrHotLink.KAIYAN.getUri();
		JSONObject jsonObject = JSON.parseObject(HttpClient.doGet(url));
		JSONArray itemList = jsonObject.getJSONArray("itemList");
		List<Object> field = itemList.stream().filter(t -> "video".equals(((JSONObject) t).get("type")))
				.collect(Collectors.toList());
		JSONArray fieldArray = JSON.parseArray(JSON.toJSONString(field));
		JSONObject one = fieldArray.getJSONObject(new Random().nextInt(fieldArray.size()));
		return one.getJSONObject("data").getString("playUrl");
	}
	private String getTuchong() {
		String url = GaterrHotLink.TUCHONG.getUri();
		JSONObject jsonObject = JSON.parseObject(HttpClient.doGet(url));
		JSONArray feedList = jsonObject.getJSONArray("feedList");
		JSONObject one = feedList.getJSONObject(new Random().nextInt(feedList.size()));
		return one.getString("url");
	}
	private String getZhihu() {
		String url = GaterrHotLink.ZHIHU.getUri();
		JSONObject jsonObject = JSON.parseObject(HttpClient.doGet(url));
		JSONArray newsArray = jsonObject.getJSONArray("recent");
		JSONObject news = newsArray.getJSONObject(new Random().nextInt(newsArray.size()));
		
		// get new content
		JSONObject contentObject = JSON.parseObject(HttpClient.doGet(news.getString("url")));
		Document doc = Jsoup.parse(contentObject.getString("body"));
		Elements elements = doc.getElementsByTag("a");
		Element e = elements.get(new Random().nextInt(elements.size()));
		return e.attr("href");
	}
	private String getBilibili() {
		List<String> list = Arrays.asList("type1", "type3", "type4", "type5",
				"type11", "type13", "type23", "type36", "type119", "type129");
		String url = GaterrHotLink.BILIBILI.getUri();
		JSONObject jsonObject = JSON.parseObject(HttpClient.doGet(url));
		JSONObject typeObject = jsonObject.getJSONObject(list.get(new Random()
				.nextInt(list.size())));
		JSONObject one = typeObject.getJSONObject(String.valueOf(new Random()
				.nextInt(9)));
		return "http://www.bilibili.com/video/av" + one.getString("aid");
	}
	private String getBaiduMusic() {
		String url = GaterrHotLink.BAIDUYINYUE.getUri();
		return url;
	}
	private String getTingban() {
		List<String> categorys = Arrays.asList("",
				"http://www.tingban.cn/broadcast.html",
				"1339", "648", "32",
				"1331", "114", "1393", "201", "1001", "2296", "1338", "12",
				"664", "657", "655", "819", "818", "820", "195", "656", "662",
				"21", "18", "1241", "1", "1251", "1242");
		String url = categorys.get(new Random().nextInt(categorys.size()));
		String uri;
		if(url.contains("broadcast")) {
			uri = "http://www.tingban.cn/webapi/broadcast/search?classifyid=1&pagenum=1&pagesize=30";
		} else {
			uri = "http://www.tingban.cn/webapi/resource/search?cid=##"
					+ "&rtype=20000&sorttype=HOT_RANK_DESC&pagesize=30&pagenum=1";
			uri = uri.replace("##", url);
		}
		JSONObject obj = JSON.parseObject(HttpClient.doGet(uri));
		JSONArray dataList = obj.getJSONObject("result").getJSONArray("dataList");
		JSONObject one = dataList.getJSONObject(new Random().nextInt(dataList.size()));
		return "Tingban#" + one.getString("id");
	}
	private String getJianshu() {
		String url = GaterrHotLink.JIANSHU.getUri();
		try {
			Document doc = Jsoup.connect(url).timeout(3000).get();
			Element ele = doc.getElementById("list-container");
			if (null == ele)
				return null;
			Elements as = ele.getElementsByClass("title");
			return null == as ? null : "http://www.jianshu.com" + as.get(0).attr("href");
		} catch (IOException e) {
			log.error("jsoup get {} error:{}", url, e);
		}
		return null;
	}
	private String getWangyiMusic() {
		String url = GaterrHotLink.WANGYIMUSIC.getUri();
		return url + (new Random().nextInt(340000) + 60000);
	}
}
