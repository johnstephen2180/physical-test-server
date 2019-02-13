package com.loankim.examonline.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.loankim.examonline.om.Account;

@Repository
public interface AccountRepository extends MongoRepository<Account, Long> {

	Account findByUsername(String username);


	Account findByClientId(long clientId);
}
