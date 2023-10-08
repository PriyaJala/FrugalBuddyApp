package com.springboot.FrugalBuddyApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude= SecurityAutoConfiguration.class)
@ComponentScan(basePackages = "com.springboot.FrugalBuddyApplication.Service")

public class FrugalBuddyApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrugalBuddyApplication.class, args);
	}

}
