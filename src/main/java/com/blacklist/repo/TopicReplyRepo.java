package com.blacklist.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.blacklist.domain.TopicReply;

@Repository
public interface TopicReplyRepo extends JpaRepository<TopicReply, Long> {

	public List<TopicReply> findByStatusAndTopicId(Integer status, Long topicId);
	
	@Modifying
	@Query(value = "update it_topic_reply t set t.up_num = t.up_num+1 where t.id=:id", nativeQuery = true)
	public int up(@Param("id")Long id);
	
	@Modifying
	@Query(value = "update it_topic_reply t set t.down_num = t.down_num+1 where t.id=:id", nativeQuery = true)
	public int down(@Param("id")Long id);
	
	/**
	 * @param topicIds
	 * @return count(1),topic_id,sum(up_num),sum(down_num)
	 */
	@Query(value="select count(1),topic_id,sum(up_num),sum(down_num) from it_topic_reply r"
			+ " where r.topic_id in ?1 group by r.topic_id", nativeQuery=true)
	public List getCountNumByTopic(List<Long> topicIds);
}
