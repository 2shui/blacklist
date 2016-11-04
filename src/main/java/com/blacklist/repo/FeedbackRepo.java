package com.blacklist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blacklist.domain.Feedback;

@Repository
public interface FeedbackRepo extends JpaRepository<Feedback, Long> {

}
