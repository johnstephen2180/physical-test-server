package com.loankim.examonline;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class ExamOnlineApplication {
	public static void main(String[] args) throws InterruptedException {
		System.setProperty("log4j.configurationFile", "config/log4j2.xml");
		PropertyConfigurator.configure("config/log4j.properties");
		SpringApplication.run(ExamOnlineApplication.class, args);
	}


	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("your origin or * for allow all");
			}
		};
	}
}
