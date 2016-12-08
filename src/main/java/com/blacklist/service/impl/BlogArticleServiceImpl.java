package com.blacklist.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blacklist.domain.BlogArticle;
import com.blacklist.repo.BlogArticleRepo;
import com.blacklist.service.BlogArticleService;

@Service("blogArticleService")
public class BlogArticleServiceImpl implements BlogArticleService {
	@Autowired
	BlogArticleRepo articleRepo;
	
	@Override
	public BlogArticle save(BlogArticle article) {
		return articleRepo.saveAndFlush(article);
	}

	@Override
	@Transactional
	public int view(Long id) {
		return articleRepo.addAccessNum(id);
	}

	@Override
	public List<BlogArticle> findAll(Sort sort) {
		return articleRepo.findAll(sort);
	}

}
