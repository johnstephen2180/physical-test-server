package com.loankim.examonline.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author LamHM
 *
 */
public class JwtTokenMissingException extends AuthenticationException {
	private static final long serialVersionUID = 1L;

	public JwtTokenMissingException(String msg) {
		super(msg);
	}
}
