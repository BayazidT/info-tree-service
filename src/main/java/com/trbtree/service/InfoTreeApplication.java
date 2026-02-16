package com.trbtree.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class InfoTreeApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfoTreeApplication.class, args);
	}

}
