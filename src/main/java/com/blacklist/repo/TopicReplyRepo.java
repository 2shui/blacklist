package com.blacklist.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blacklist.domain.TopicReply;

@Repository
public interface TopicReplyRepo extends JpaRepository<TopicReply, Long> {

	public List<TopicReply> findByStatusAndTopicId(Integer status, Long topicId);
}
