package com.example.jobprocessor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.boot.SpringApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAsync 
@EnableCaching
public class JobprocessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobprocessorApplication.class, args);
	}

}
