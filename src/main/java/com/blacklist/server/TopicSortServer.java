package com.blacklist.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blacklist.domain.Topic;
import com.blacklist.domain.TopicReply;
import com.blacklist.service.TopicReplyService;

/**
 * 计算爆料热度<br/>
 * log(v^1.1+10r+4u+3ur^0.9-2d^1.3+dr^0.9)+t/W
 * <br/>
 * view<br/>
 * reply<br/>
 * up<br/>
 * down<br/>
 * 
 * @author itblacklist.cn
 *
 */
@Service
public class TopicSortServer {
	private static Logger log = LoggerFactory.getLogger(TopicSortServer.class);
	
	private static final int TIME = 524288000;

	private static final int VIEW = 1;
	private static final int REPLY = 10;
	private static final int AGREE_TOPIC = 4;
	private static final int AGREE_REPLY = 3;
	private static final int OPPOSE = -2;
	private static final int OPPOSE_REPLY = 1;

	private static final double POWER_VIEW = 1.1;
	private static final double POWER_REPLY = 1;
	private static final double POWER_AGREE_TOPIC = 1;
	private static final double POWER_AGREE_REPLY = 0.9;
	private static final double POWER_OPPOSE = 1.3;
	private static final double POWER_OPPOSE_REPLY = 0.9;

	@Autowired
	private TopicReplyService topicReplyService;

	public List<Topic> sort(List<Topic> list) {
		List<Long> topicIds = new ArrayList<Long>();
		list.forEach(topic -> topicIds.add(topic.getId()));
		Map<Long, TopicReply> map = topicReplyService.getCountNum(topicIds);
		list.forEach(topic -> {
			topic.setContact(String.valueOf(getScore(topic, map)));
		});
		Collections.sort(list, new Comparator<Topic>() {
			@Override
			public int compare(Topic o1, Topic o2) {
				return o2.getContact().compareTo(o1.getContact());
			}
		});
		return list;
	}

	private double getScore(Topic topic, Map<Long, TopicReply> map) {
		TopicReply reply = map.get(topic.getId());
		if (null == reply) {
			reply = new TopicReply();
			reply.setDownNum(0);
			reply.setUpNum(0);
			reply.setTopicId(0L);
		}
		double score = VIEW * Math.pow(topic.getAccessNum(), POWER_VIEW)
				+ REPLY * Math.pow(reply.getTopicId(), POWER_REPLY)
				+ AGREE_TOPIC * Math.pow(topic.getEulogyNum(), POWER_AGREE_TOPIC) 
				+ OPPOSE * Math.pow(topic.getRefuteNum(), POWER_OPPOSE) 
				+ AGREE_REPLY * Math.pow(reply.getUpNum(), POWER_AGREE_REPLY)
				+ OPPOSE_REPLY * Math.pow(reply.getDownNum(), POWER_OPPOSE_REPLY);
		score = score < 0 ? 1 : (score < 1 ? 1 + score / 10 : score);
		return Math.log10(score) + topic.getCreateTime().getTime() / TIME;
	}
}
