package com.evils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PubgAnalysisApplication {

	public static void main(String[] args) {
		SpringApplication.run(PubgAnalysisApplication.class, args);
	}
}
