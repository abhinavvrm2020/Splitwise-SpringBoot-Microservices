package com.example.MainProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.MainProject"})
public class MainProjectApplication {
	public static void main(String[] args) {
		SpringApplication.run(MainProjectApplication.class, args);
	}
}