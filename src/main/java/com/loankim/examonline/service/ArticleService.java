package com.loankim.examonline.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loankim.examonline.dao.ArticleRepository;
import com.loankim.examonline.om.Article;

@Service
public class ArticleService implements InitializingBean{

	@Autowired
	private ArticleRepository articleRepo;
	@Override
	public void afterPropertiesSet() throws Exception {
		Article at = new Article();
		at.setId(1);
		at.setTitle("hello");
		articleRepo.save(at);
		
	}

}
