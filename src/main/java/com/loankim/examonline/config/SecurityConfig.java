package com.loankim.examonline.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import com.loankim.examonline.security.JwtAuthenticationEntryPoint;
import com.loankim.examonline.security.JwtAuthenticationProvider;
import com.loankim.examonline.security.JwtAuthenticationSuccessHandler;
import com.loankim.examonline.security.JwtAuthenticationTokenFilter;

/**
 * @author LamHa
 *         https://gitlab.com/palmapps/jwt-spring-security-demo/blob/master/src/main/java/nl/palmapps/myawesomeproject/security/config/WebSecurityConfig.java
 * 
 *         https://www.linkedin.com/pulse/json-web-token-jwt-spring-security-real-world-example-boris-trivic/
 *
 */
@EnableWebSecurity
// Enable this annotation for @PreAuthorize in controller is active
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	// Handle exception
	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Autowired
	private JwtAuthenticationProvider authenticationProvider;


	@Bean
	@Override
	public AuthenticationManager authenticationManager() throws Exception {
		return new ProviderManager(Arrays.asList(authenticationProvider));
	}


	public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
		JwtAuthenticationTokenFilter authenticationTokenFilter = new JwtAuthenticationTokenFilter("/admin/**");
		authenticationTokenFilter
				.setRequiresAuthenticationRequestMatcher(new OrRequestMatcher(new AntPathRequestMatcher("/user/**"),
						new AntPathRequestMatcher("/admin/**"), new AntPathRequestMatcher("/exam/**")));
		authenticationTokenFilter.setAuthenticationManager(authenticationManager());
		authenticationTokenFilter.setAuthenticationSuccessHandler(new JwtAuthenticationSuccessHandler());
		return authenticationTokenFilter;
	}


	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// we don't need CSRF because our token is invulnerable
		httpSecurity.csrf().disable();
		httpSecurity.authorizeRequests().antMatchers("/images/**").permitAll();
		httpSecurity.authorizeRequests().antMatchers("/token/**").permitAll();
		// don't create session
		httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				// All urls must be authenticated (filter for token always fires
				// (/**)
				.authorizeRequests().anyRequest().authenticated();

		httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

		// Call our errorHandler if authentication/authorisation fails
		httpSecurity.exceptionHandling().authenticationEntryPoint(unauthorizedHandler);

		// disable page caching
		httpSecurity.headers().cacheControl();
	}

}
