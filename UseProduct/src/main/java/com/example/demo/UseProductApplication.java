package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class UseProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(UseProductApplication.class, args);
	}
	@Bean
	public RestTemplate resttemplate () {
		return new RestTemplate();
	}

}
