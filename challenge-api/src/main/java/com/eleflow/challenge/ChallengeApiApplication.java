package com.eleflow.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableWebFlux
@SpringBootApplication
@EnableAutoConfiguration
public class ChallengeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApiApplication.class, args);
	}

}
