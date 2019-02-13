package com.loankim.examonline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.loankim.examonline.om.Account;
import com.loankim.examonline.service.AccountService;
import com.loankim.examonline.service.AutoIncrementService;
import com.loankim.examonline.util.AuthHelper;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.json.JsonObject;

//https://stackoverflow.com/questions/50803727/spring-with-jwt-auth-get-current-user
@RestController
public class LoginController {
	@Autowired
	private AccountService accountService;
	@Autowired
	private AutoIncrementService autoIncrService;
	private static final String FB_PROVIDER = "fb";


	@PostMapping(value = "/token/get", produces = "application/json; charset=UTF-8")
	public Account loginByFbClientId(@RequestParam("fbToken") String fbToken) {
		FacebookClient facebookClient = new DefaultFacebookClient(fbToken, Version.VERSION_2_6);
		JsonObject user = facebookClient.fetchObject("me", JsonObject.class,
				Parameter.with("fields", "name,id,email,birthday"));

		long clientId = user.getLong("id");
		Account account = accountService.login(clientId);
		if (account == null) {
			JsonObject picture = facebookClient.fetchObject("/me/picture", JsonObject.class,
					Parameter.with("type", "large"), Parameter.with("redirect", "false"));

			JsonObject data = picture.getJsonObject("data");
			account = new Account();
			account.setId(autoIncrService.gendAccountId());
			account.setClientId(clientId);
			account.setActive(false);
			account.setFullName(user.getString("name"));
			account.setCreateTime(System.currentTimeMillis());
			account.setAvatar(data.getString("url"));
			account.setProvider(FB_PROVIDER);
			accountService.save(account);
		}
		
		String signToken = AuthHelper.createSignToken(account);
		account.setToken(signToken);
		return account;
	}
	
}
