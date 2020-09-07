package com.rvprogramming.springbootmapsdemo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootMapsDemoApplication implements ApplicationRunner {
	private static final Logger LOGGER = LogManager.getLogger(SpringBootMapsDemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMapsDemoApplication.class, args);
	}
	
	@Override
	public void run(ApplicationArguments applicationArguments) throws Exception {
		LOGGER.trace("Starting application, in run, applicationArguments: '" + String.valueOf(applicationArguments) + "'...");
		
	}

}
