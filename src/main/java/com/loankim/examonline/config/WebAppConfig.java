package com.loankim.examonline.config;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.loankim.examonline.security.CultureFilter;

/**
 * @author LamHM
 *
 */
@Configuration()
public class WebAppConfig extends WebMvcConfigurerAdapter {

	@Bean
	public Filter cultureFilter() {
		return new CultureFilter();
	}


	@Override
	public void addCorsMappings(CorsRegistry registry) {
		System.out.println("alpha");
		registry.addMapping("/**");
	}


	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**").addResourceLocations("file:images/");
	}

}
