package com.loankim.examonline.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.loankim.examonline.manager.AccountManager;
import com.loankim.examonline.om.Account;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private AccountManager accountManager;


	@PostMapping(path = "/all")
	public @ResponseBody Page<Account> getAllUser(@RequestParam("page") int page) {
		return accountManager.getAllAccount(page);
	}


	@PostMapping(path = "/detail")
	public @ResponseBody Account getUserDetail(@RequestParam("id") long accountId) {
		return accountManager.getAccount(accountId);
	}
	
	@PostMapping(path = "/update")
	public @ResponseBody Account update(@RequestParam("id") long accountId, @RequestParam("roles") List<String> roles) {
		Account account = accountManager.getAccount(accountId);
		account.updateRoles(roles);
		return accountManager.save(account);
	}

}
