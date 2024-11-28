package com.productApp.FirstProductApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
// Enable scheduling for scheduled tasks like sending emails
@EnableScheduling

public class FirstProductAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstProductAppApplication.class, args);
	}

}
