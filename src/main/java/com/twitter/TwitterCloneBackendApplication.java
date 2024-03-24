package com.twitter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication

@OpenAPIDefinition(info = @Info(title = "Twitter Api's",description = "Twitter"))
public class TwitterCloneBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwitterCloneBackendApplication.class, args);
	}

}
