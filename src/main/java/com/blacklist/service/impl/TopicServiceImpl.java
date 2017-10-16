package com.blacklist.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blacklist.config.FreemarkerConfig;
import com.blacklist.config.WebConfig;
import com.blacklist.domain.Topic;
import com.blacklist.domain.Water;
import com.blacklist.repo.TopicRepo;
import com.blacklist.server.IndexServer;
import com.blacklist.service.TopicService;
import com.blacklist.utils.FreemarkerUtils;
import com.blacklist.utils.LuceneIKUtil;

@Service("topicService")
public class TopicServiceImpl implements TopicService {
	private Logger log = LoggerFactory.getLogger(TopicServiceImpl.class);
	@Autowired
	private TopicRepo topicRepo;
	@Autowired
	private IndexServer indexServer;
	
	@Override
	public Topic add(Topic topic) {
		topic = topicRepo.saveAndFlush(topic);
		// alimq 收费了
		//SMSProducer.getInstance().send(topic);
		try {
			LuceneIKUtil.getInstance().createIndex(
					indexServer.build(Arrays.asList(topic)), false);
		} catch (Exception e) {
			log.error("Add indexed error:{}", e);
		}
		FreemarkerUtils.staticTopic(topic);
		
		return topic;
	}
	
	@Override
	public List<Topic> search(String[] field, String key, int num) {
		List<Topic> topics = new ArrayList<Topic>();
		LuceneIKUtil index = LuceneIKUtil.getInstance();
		try {
			List<Document> documents = index.search(field, key, num);
			documents.stream().forEach(doc -> {
				Topic topic = new Topic();
				Long id = Long.parseLong(doc.get(WebConfig.id));
				topic.setId(id);
				topic.setCompany(doc.get(WebConfig.company));
				topic.setCity(doc.get(WebConfig.city));
				topic.setSketch(doc.get(WebConfig.sketch));
				
				Topic db = topicRepo.getOne(id);
				topic.setCreateTime(null == db ? null : db.getCreateTime());
				topics.add(topic);
			});
		} catch (IOException|ParseException e) {
			log.error("index search error:{}", e.getMessage());
		}
		return topics;
	}

	@Override
	public Topic get(Long id) {
		return topicRepo.findOne(id);
	}

	@Override
	@Transactional
	public void viewPage(Long id) {
		topicRepo.addAccessNum(id);
	}

	@Override
	public List<Topic> getAll() {
		return topicRepo.findAll();
	}

	@Override
	public List<Topic> findByStatus(Integer status) {
		return topicRepo.findByStatus(status);
	}

	@Override
	public Integer count(Integer status) {
		return topicRepo.countByStatus(status);
	}

	@Override
	public List<Topic> findByCreateTime(Date date) {
		if (null == date) {
			long current = System.currentTimeMillis();
			long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24)
					- TimeZone.getDefault().getRawOffset();
			date = new Date(zero);
		}
		return topicRepo.findByCreateTimeGreaterThan(date);
	}

	@Override
	public List<Topic> getLimit(Integer num, Sort sort) {
		Pageable page = new PageRequest(0, num, sort);
		return topicRepo.findByStatus(1, page);
	}
	
	
}
