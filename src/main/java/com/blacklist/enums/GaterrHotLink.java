package com.blacklist.enums;

public enum GaterrHotLink {
	/**
	 * 根据日期查询文章
	 * https://github.com/ZongweiBai/DoubanOpenDaily/blob/b87473b90911e687d291e847b133b5c4c4a700a3/
	 * 		app/src/main/java/com/monosky/daily/constant/APIConstData.java
	 * https://moment.douban.com/api/stream/date/2017-05-01
	 * */
	DOUBAN("https://moment.douban.com/api/stream/date/"),
	/**
	 * 热门
	 * https://github.com/jokermonn/-Api/blob/master/Eyepetizer.md#title
	 * http://baobab.kaiyanapp.com/api/v4/discovery
	 * http://baobab.kaiyanapp.com/api/v4/discovery/hot
	 * */
	KAIYAN("http://baobab.kaiyanapp.com/api/v4/discovery/hot"),
	/**
	 * 图虫推荐
	 * https://github.com/jokermonn/-Api/blob/master/Tuchong.md
	 * https://api.tuchong.com/feed-app
	 * */
	TUCHONG("https://api.tuchong.com/feed-app"),
	/**
	 * 知乎日报
	 * https://github.com/izzyleung/ZhihuDailyPurify/wiki/
	 * 		%E7%9F%A5%E4%B9%8E%E6%97%A5%E6%8A%A5-API-%E5%88%86%E6%9E%90
	 * http://news-at.zhihu.com/api/3/news/hot
	 * */
	ZHIHU("http://news-at.zhihu.com/api/3/news/hot"),
	/**
	 * Bilibili
	 * http://api.bilibili.cn/index
	 * */
	BILIBILI("http://api.bilibili.cn/index"),
	/**
	 * 百度音乐
	 * http://fm.baidu.com
	 * */
	BAIDUYINYUE("http://fm.baidu.com"),
	/**
	 * 网易云音乐
	 * http://music.163.com/#/song?id=60000+
	 * */
	WANGYIMUSIC("http://music.163.com/#/song?id="),
	/**
	 * 听伴音频
	 * http://www.tingban.cn/
	 * */
	TINGBAN("http://www.tingban.cn/"),
	/**
	 * 简书7日热点
	 * http://www.jianshu.com/trending/weekly
	 * */
	JIANSHU("http://www.jianshu.com/trending/weekly"),
	UNDEFAULT("");

	private String uri;
	GaterrHotLink(String url) {
		this.uri = url;
	}
	public String getUri() {
		return uri;
	}
}
