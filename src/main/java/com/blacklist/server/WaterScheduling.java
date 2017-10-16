package com.blacklist.server;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.blacklist.domain.Water;
import com.blacklist.repo.WaterRepo;
import com.blacklist.utils.FreemarkerUtils;


//@Configuration
//@EnableScheduling
public class WaterScheduling {
//	private static Logger log = LoggerFactory.getLogger(WaterScheduling.class);
//	@Autowired
//	WaterRepo waterRepo;
//	
//	@Scheduled(cron = "0 0/1 * * * ?")
//	public void scheduler() {
//		List<Water> list = waterRepo.findByUrlNull();
//		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
//		try {
//			Document doc;
//			Elements es;
//			for (Water w : list) {
//				if( w.getId()<20 || w.getId()>25 || !w.getReferrer().contains("baijiahao"))
//					continue;
//				doc = Jsoup.connect(w.getReferrer()).timeout(3000).get();
//				es = doc.getElementsByClass("article-title");
//				String content = "";
//				es = doc.getElementsByClass("article-content");
//				if (es.size() > 0) {
//					content = es.get(0).html();
//				} else {
//					continue;
//				}
//				Map<String, String> map = new HashMap<String, String>();
//				map.put("title", w.getTitle());
//				map.put("content", content);
//				map.put("referrer", w.getReferrer());
//
//				String now = df.format(w.getCreateTime());
//				w.setUrl(now + "/" + w.getId() + ".html");
//				waterRepo.save(w);
//				FreemarkerUtils.analysisTemplate(now, w.getId() + ".html", map, "water.ftl", null);
//			}
//		} catch (Exception e) {
//			log.error("sattic water html error:{}", e);
//		}
//	}
}
