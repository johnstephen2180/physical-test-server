package com.loankim.examonline.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.loankim.examonline.om.Examination;

@Repository
public interface ExaminationRepository extends MongoRepository<Examination, Long> {

}
