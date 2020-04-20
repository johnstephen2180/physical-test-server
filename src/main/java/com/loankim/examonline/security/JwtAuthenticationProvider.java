package com.loankim.examonline.security;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.loankim.examonline.security.model.AuthenticatedUser;
import com.loankim.examonline.security.model.JwtAuthenticationToken;
import com.loankim.examonline.util.AuthHelper;
import com.loankim.examonline.util.Security;

/**
 * @author LamHM
 *
 */
@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	@Override
	public boolean supports(Class<?> authentication) {
		return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
	}


	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
	}


	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {

		JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
		String token = jwtAuthenticationToken.getToken();
		JWT userDecoder = AuthHelper.getUserDecoder(token);
		long userId = Long.parseLong(userDecoder.getClaim("id").asString());
		List<GrantedAuthority> authorityList = AuthorityUtils
				.commaSeparatedStringToAuthorityList(userDecoder.getClaim("ROLE_ARRAY").asString());

		return new AuthenticatedUser(userId, token, Security.genPrivateKey(token, userId), authorityList);
		// return new AuthenticatedUser(user.getId(), token,
		// Security.genPrivateKey(token, user.getId()),
		// Collections.<GrantedAuthority>emptyList());
	}

}
