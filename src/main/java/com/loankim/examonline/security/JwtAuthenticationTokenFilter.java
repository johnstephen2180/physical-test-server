package com.loankim.examonline.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.loankim.examonline.security.model.JwtAuthenticationToken;
import com.loankim.examonline.util.AuthHelper;

/**
 * @author LamHa
 *
 *         http://stackoverflow.com/questions/13994507/how-do-you-send-a-custom-header-in-a-cross-domain-cors-xmlhttprequest
 *         https://www.devglan.com/spring-security/jwt-role-based-authorization
 */
public class JwtAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {
	private static final String HEADER_PREFIX = "Bearer ";


	public JwtAuthenticationTokenFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
		
	}


	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		// String header = request.getHeader("authorization");
		// String header2 = request.getHeader("headers");
		// String title = request.getParameter("title");

		// if (header != null)
		// header = HEADER_PREFIX + header;
		//
		// if (header == null || !header.startsWith("Bearer "))
		// throw new JwtTokenMissingException("No JWT token found in request
		// headers");
		// String authToken = header.replace(HEADER_PREFIX, "");
		String authToken = request.getParameter("token");

		try {

			AuthHelper.verifyToken(authToken);
			return getAuthenticationManager().authenticate(new JwtAuthenticationToken(authToken));
		} catch (Exception e) {
			System.out.println("attemptAuthentication fail!");
			// throw new ServletException(e.getMessage());
			throw new AccountExpiredException("Token has expired", e);
		}
	}


	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);
		// As this authentication is in HTTP header, after success we need to
		// continue the request normally
		// and return the response as if the resource was not secured at all
		chain.doFilter(request, response);
	}

}
