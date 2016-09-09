package com.blacklist.service;

import java.util.List;

import com.blacklist.domain.Topic;

public interface TopicService {
	
	public Topic add(Topic topic);
	
	public List<Topic> search(String[] field, String key, int num);
	
	public Topic get(Long id);
	
	public void viewPage(Long id);
}
