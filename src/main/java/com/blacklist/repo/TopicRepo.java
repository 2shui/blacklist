package com.blacklist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blacklist.domain.Topic;

@Repository
public interface TopicRepo extends JpaRepository<Topic, Long> {

}
