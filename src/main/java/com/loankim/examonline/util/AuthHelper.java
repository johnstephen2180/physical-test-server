package com.loankim.examonline.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.loankim.examonline.om.Account;

/**
 * https://github.com/auth0/java-jwt
 * 
 * 
 * @author LamHa
 */
public class AuthHelper {
	private static final String ISSUER = "auth0";
	private static final String SIGNING_KEY = "easyphysics@^($%*$%";
	// expire trong 10 ngày
	private static final int TTL_MILI = 864000000;


	public static String createSignToken(Account account) {
		String token;
		try {
			token = JWT.create().withIssuer(ISSUER).withExpiresAt(new Date(System.currentTimeMillis() + TTL_MILI))
					.withClaim("id", String.valueOf(account.getId()))
					.withClaim("ROLE_ARRAY", String.valueOf(account.getRoleString())).withClaim("ttl", TTL_MILI)
					.sign(Algorithm.HMAC256(SIGNING_KEY));
		} catch (Exception e) {
			System.out.println("createJsonWebToken fail!");
			throw new RuntimeException(e);
		}
		return token;
	}


	public static DecodedJWT verifyToken(String token) throws IllegalArgumentException, UnsupportedEncodingException {
		// cho phép trễ 1mili, giả sử set expire là 10mili thì 11mili mới expire
		return JWT.require(Algorithm.HMAC256(SIGNING_KEY)).withIssuer(ISSUER).acceptLeeway(1).build().verify(token);
	}


	public static long getUserId(String token) {
		Claim claim = JWT.decode(token).getClaim("id");
		return Long.parseLong(claim.asString());
	}


	public static Account getUser(String token) {
		Account user = new Account();
		JWT decode = JWT.decode(token);
		user.setId(Long.parseLong(decode.getClaim("id").asString()));
		return user;
	}


	public static JWT getUserDecoder(String token) {
		return JWT.decode(token);
	}

}
