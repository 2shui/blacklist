package com.blacklist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blacklist.domain.BlogArticle;

@Repository
public interface BlogArticleRepo extends JpaRepository<BlogArticle, Long> {

}
