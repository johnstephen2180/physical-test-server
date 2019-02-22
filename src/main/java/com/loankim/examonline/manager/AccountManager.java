package com.loankim.examonline.manager;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.loankim.examonline.dao.AccountRepository;
import com.loankim.examonline.om.Account;

@Service
public class AccountManager implements InitializingBean {
	@Autowired
	private AccountRepository accountRepo;

	private Map<Long, Account> accountMap;


	@Override
	public void afterPropertiesSet() throws Exception {
		accountMap = new HashMap<>();
	}


	public Account getAccount(long accountId) {
		Account account = accountMap.get(accountId);
		if (account == null) {
			account = accountRepo.findOne(accountId);
			accountMap.put(accountId, account);
		}

		return account;
	}


	public Page<Account> getAllAccount(int page) {
		return accountRepo.findAll(new PageRequest(page, 5, new Sort(Sort.Direction.DESC, "id")));
	}
}
