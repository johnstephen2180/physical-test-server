package com.loankim.examonline.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author LamHM
 *
 */
public class JwtTokenMalformedException extends AuthenticationException {
	private static final long serialVersionUID = 1L;

	public JwtTokenMalformedException(String msg) {
		super(msg);
	}
}
