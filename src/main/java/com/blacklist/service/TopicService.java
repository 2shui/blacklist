package com.blacklist.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;

import com.blacklist.domain.Topic;

public interface TopicService {
	
	public Topic add(Topic topic);
	
	public List<Topic> search(String[] field, String key, int num);
	
	public Topic get(Long id);
	
	public void viewPage(Long id);
	
	public List<Topic> getAll();
	
	public List<Topic> findByStatus(Integer status);
	
	public List<Topic> findByCreateTime(Date date);
	
	public Integer count(Integer status);
	
	public List<Topic> getLimit(Integer num, Sort sort);
}
