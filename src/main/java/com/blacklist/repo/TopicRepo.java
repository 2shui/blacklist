package com.blacklist.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.blacklist.domain.Topic;

@Repository
public interface TopicRepo extends JpaRepository<Topic, Long> {

	@Modifying
	@Query(value = "update it_topic t set t.access_num = t.access_num+1 where t.id=:id", nativeQuery = true)
	public int addAccessNum(@Param("id") Long id);
	
	public Topic findByIdAndStatus(Long id, Integer status);
	
	public List<Topic> findByStatus(Integer status);
	
	public List<Topic> findByCompanyLike(String company);
}
