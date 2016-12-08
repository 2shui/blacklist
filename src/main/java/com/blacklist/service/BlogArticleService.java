package com.blacklist.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.blacklist.domain.BlogArticle;

public interface BlogArticleService {
	public List<BlogArticle> findAll(Sort sort);
	public BlogArticle save(BlogArticle article);
	
	public int view(Long id);
}
