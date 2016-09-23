package com.blacklist.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
}
