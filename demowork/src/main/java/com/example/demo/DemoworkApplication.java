package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.demo.*"})
public class DemoworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoworkApplication.class, args);
	}
}
