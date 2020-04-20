package com.loankim.examonline.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.loankim.examonline.om.Question;

/**
 * @author LamHM
 *
 */
@Repository
public interface QuestionRepository extends MongoRepository<Question, Long> {
	
	List<Question> findQuestionByExamId(long examId);
}
