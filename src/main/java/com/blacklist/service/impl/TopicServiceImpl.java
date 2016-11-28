package com.blacklist.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blacklist.config.WebConfig;
import com.blacklist.domain.Topic;
import com.blacklist.repo.TopicRepo;
import com.blacklist.service.TopicService;
import com.blacklist.utils.LuceneIKUtil;
import com.blacklist.utils.SMSProducer;

@Service("topicService")
public class TopicServiceImpl implements TopicService {
	private Logger log = LoggerFactory.getLogger(TopicServiceImpl.class);
	@Autowired
	private TopicRepo topicRepo;
	
	@Override
	public Topic add(Topic topic) {
		topic = topicRepo.saveAndFlush(topic);
		SMSProducer.getInstance().send(topic);
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
				topic.setId(Long.parseLong(doc.get(WebConfig.id)));
				topic.setCompany(doc.get(WebConfig.company));
				topic.setCity(doc.get(WebConfig.city));
				topic.setSketch(doc.get(WebConfig.sketch));
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
		return topicRepo.findAll(page).getContent();
	}
	
	
}
