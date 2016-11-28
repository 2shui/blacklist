package com.blacklist.service;

import com.blacklist.domain.BlogArticle;

public interface BlogArticleService {
	public BlogArticle save(BlogArticle article);
	
	public int view(Long id);
}
