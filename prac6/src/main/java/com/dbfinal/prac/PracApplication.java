package com.dbfinal.prac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.dbfinal.prac.repositories")
public class PracApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracApplication.class, args);
	}

}
