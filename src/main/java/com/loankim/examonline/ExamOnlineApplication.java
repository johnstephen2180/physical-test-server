package com.loankim.examonline;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExamOnlineApplication {
	public static void main(String[] args) throws InterruptedException {
		System.setProperty("log4j.configurationFile", "config/log4j2.xml");
		PropertyConfigurator.configure("config/log4j.properties");
		SpringApplication.run(ExamOnlineApplication.class, args);
	}
}
