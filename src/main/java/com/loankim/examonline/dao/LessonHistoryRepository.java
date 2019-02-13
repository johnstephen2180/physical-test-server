package com.loankim.examonline.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.loankim.examonline.om.LessonHistory;

@Repository
public interface LessonHistoryRepository extends MongoRepository<LessonHistory, String> {

	Page<LessonHistory> getLessionHistoryByAccountId(long accountId, Pageable page);


	@Query(value = "{}")
	Page<LessonHistory> getLessionHistory(Pageable page);
}
