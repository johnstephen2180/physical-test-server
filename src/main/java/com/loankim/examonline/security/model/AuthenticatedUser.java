package com.loankim.examonline.security.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author LamHa
 *
 */
public class AuthenticatedUser implements UserDetails {
	private static final long serialVersionUID = 1L;
	private final Long userId;
	private final String token;
	private final String privateKey;
	private final Collection<? extends GrantedAuthority> authorities;


	public AuthenticatedUser(Long userId, String token, String privateKey,
			Collection<? extends GrantedAuthority> authorities) {
		this.userId = userId;
		this.token = token;
		this.authorities = authorities;
		this.privateKey = privateKey;
	}


	@JsonIgnore
	public Long getUserId() {
		return userId;
	}


	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}


	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}


	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}


	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return true;
	}


	public String getToken() {
		return token;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}


	@Override
	public String getPassword() {
		return null;
	}


	public String getPrivateKey() {
		return privateKey;
	}


	@Override
	public String getUsername() {
		return null;
	}


	@Override
	public String toString() {
		return "{userId:" + userId + ", privateKey:" + privateKey + "}";
	}

}
