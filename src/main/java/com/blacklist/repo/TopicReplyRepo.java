package com.blacklist.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.blacklist.domain.TopicReply;

@Repository
public interface TopicReplyRepo extends JpaRepository<TopicReply, Long> {

	public List<TopicReply> findByStatusAndTopicId(Integer status, Long topicId);
	
	@Modifying
	@Query(value = "update it_topic_reply t set t.up_num = t.up_num+1 where t.id=:id", nativeQuery = true)
	public int up(Long id);
	
	@Modifying
	@Query(value = "update it_topic_reply t set t.down_num = t.down_num+1 where t.id=:id", nativeQuery = true)
	public int down(Long id);
}
