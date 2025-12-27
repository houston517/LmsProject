package org.example.lmsproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LmsProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(LmsProjectApplication.class, args);
	}

}
