package com.ppkn.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

	private final Logger logger = LoggerFactory.getLogger(ServiceConfig.class);

	// configure entity manager

	// configure datasource

	// configure message source

	// configure config.yaml
	public ServiceConfig() {
		System.out.println("In ServiceConfig..");
	}
}
