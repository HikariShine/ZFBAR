package com.example.myprojects.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Tests {
	
	public static String staticName;
	
	@Value("${spring.application.name}")
	private void setName(String name) {
		staticName = name;
	}
	
	public static String getName() {
		return staticName;
	}
}
