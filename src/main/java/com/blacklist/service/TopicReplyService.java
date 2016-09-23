package com.blacklist.service;

import java.util.List;

import com.blacklist.domain.TopicReply;

public interface TopicReplyService {
	
	public TopicReply addReply(TopicReply reply);
	
	public List<TopicReply> getReplys(Long topicId);
}
