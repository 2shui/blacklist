package com.blacklist.service;

import java.util.List;
import java.util.Map;

import com.blacklist.domain.TopicReply;

public interface TopicReplyService {
	
	public TopicReply addReply(TopicReply reply);
	
	public List<TopicReply> getReplys(Long topicId);
	
	public TopicReply get(Long id);
	
	public int up(Long id);
	
	public int down(Long id);
	
	public Map<Long, TopicReply> getCountNum(List<Long> topicIds);
}
