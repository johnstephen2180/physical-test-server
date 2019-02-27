package com.loankim.examonline.service;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loankim.examonline.dao.AccountRepository;
import com.loankim.examonline.om.Account;
import com.loankim.examonline.om.Role;

@Service
public class AccountService {
	@Autowired
	private AccountRepository accountRepo;


	public void save(Account account) {
		HashSet<Role> roles = new HashSet<>();
		// roles.add(new Role(0, "ROLE_ADMIN", "Giao Vien"));
		roles.add(new Role(1, "ROLE_USER", "Hoc Sinh"));
		// roles.add(new Role(2, "ROLE_GUEST", "Khach"));
		account.setRoles(roles);
		accountRepo.save(account);
	}


	public Account findByUsername(String username) {
		return accountRepo.findByUsername(username);
	}


	public Account findByClientId(long clientId) {
		return accountRepo.findByClientId(clientId);
	}


	public Account login(long clientId) {
		return findByClientId(clientId);
	}

}
