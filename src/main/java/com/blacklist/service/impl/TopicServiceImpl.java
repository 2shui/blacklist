package com.blacklist.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blacklist.domain.Topic;
import com.blacklist.repo.TopicRepo;
import com.blacklist.service.TopicService;
import com.blacklist.utils.SMSProducer;

@Service("topicService")
public class TopicServiceImpl implements TopicService {
	@Autowired
	private TopicRepo topicRepo;
	
	@Override
	public Topic add(Topic topic) {
		SMSProducer.getInstance().send(topic);
		return topicRepo.saveAndFlush(topic);
	}

}
