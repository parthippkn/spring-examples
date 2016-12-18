package com.ppkn.core.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.ppkn.core.config.ServiceConfig;

public class DataLoadRunner {

	public static void main(String[] args) {

		new AnnotationConfigApplicationContext(ServiceConfig.class);
	}
}
