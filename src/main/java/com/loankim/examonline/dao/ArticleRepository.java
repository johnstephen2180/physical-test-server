package com.loankim.examonline.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.loankim.examonline.om.Article;

@Repository
public interface ArticleRepository extends MongoRepository<Article, Long> {
	

}
