package com.example.newsservice;

import com.example.newsservice.model.RuleSet;
import com.example.newsservice.service.Helper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class NewsServiceApplication {

	public static void main(String[] args) {
		RuleSet rules=Helper.readFile();
		//SpringApplication.run(NewsServiceApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
