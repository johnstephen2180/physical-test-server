package com.loankim.examonline.controller;

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
}
