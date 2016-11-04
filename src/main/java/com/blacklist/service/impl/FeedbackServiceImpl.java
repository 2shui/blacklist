package com.blacklist.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blacklist.domain.Feedback;
import com.blacklist.repo.FeedbackRepo;
import com.blacklist.service.FeedbackService;

@Service("feedbackService")
public class FeedbackServiceImpl implements FeedbackService {
	private Logger log = LoggerFactory.getLogger(FeedbackServiceImpl.class);
	@Autowired
	private FeedbackRepo feedbackRepo;
	
	@Override
	public Feedback save(Feedback feedback) {
		return feedbackRepo.saveAndFlush(feedback);
	}
}
