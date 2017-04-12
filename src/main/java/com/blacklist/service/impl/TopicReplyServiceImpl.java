package com.blacklist.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Override
	public TopicReply get(Long id) {
		return replyRepo.findOne(id);
	}

	@Override
	@Transactional
	public int up(Long id) {
		return replyRepo.up(id);
	}

	@Override
	@Transactional
	public int down(Long id) {
		return replyRepo.down(id);
	}

	@Override
	public Map<Long, TopicReply> getCountNum(List<Long> topicIds) {
		Map<Long, TopicReply> result = new HashMap<Long, TopicReply> ();
		@SuppressWarnings("unchecked")
		List<Object[]> list = replyRepo.getCountNumByTopic(topicIds);
		list.forEach(obj -> {
			Object[] arr = obj;
			TopicReply reply = new TopicReply();
			reply.setTopicId(Long.valueOf(arr[0].toString()));
			reply.setUpNum(Integer.valueOf(arr[2].toString()));
			reply.setDownNum(Integer.valueOf(arr[3].toString()));
			result.put(Long.valueOf(arr[1].toString()), reply);
		});
		return result;
	}
	
}
