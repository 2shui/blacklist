package com.blacklist.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blacklist.domain.TopicReply;
import com.blacklist.domain.enums.TopicReplyEnum;
import com.blacklist.repo.TopicReplyRepo;
import com.blacklist.service.TopicReplyService;

@Service("topicReplyService")
public class TopicReplyServiceImpl implements TopicReplyService {
	private Logger log = LoggerFactory.getLogger(TopicReplyServiceImpl.class);
	@Autowired
	private TopicReplyRepo replyRepo;
	
	@Override
	public List<TopicReply> getReplys(Long topicId) {
		return replyRepo.findByStatusAndTopicId(TopicReplyEnum.Status.NORMAL.getValue(), topicId);
	}

	@Override
	public TopicReply addReply(TopicReply reply) {
		return replyRepo.saveAndFlush(reply);
	}
	
}
