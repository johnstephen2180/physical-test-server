package com.loankim.examonline.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loankim.examonline.dao.SequenceRepository;

@Service
public class AutoIncrementService implements InitializingBean {
	private static final String EXAMINATION_ID = "exam_id";
	private static final String QUESTION_ID = "question_id";
	private static final String ACCOUNT_ID = "account_id";

	@Autowired
	private SequenceRepository sequenceRepository;


	@Override
	public void afterPropertiesSet() throws Exception {
		sequenceRepository.createSequenceDocument(EXAMINATION_ID, 1000);
		sequenceRepository.createSequenceDocument(QUESTION_ID, 1);
		sequenceRepository.createSequenceDocument(ACCOUNT_ID, 1000);

	}


	public long genExamId() {
		return sequenceRepository.getNextSequenceId(EXAMINATION_ID);
	}


	public long genQuestionId() {
		return sequenceRepository.getNextSequenceId(QUESTION_ID);
	}
	
	public long gendAccountId() {
		return sequenceRepository.getNextSequenceId(ACCOUNT_ID);
	}

}
