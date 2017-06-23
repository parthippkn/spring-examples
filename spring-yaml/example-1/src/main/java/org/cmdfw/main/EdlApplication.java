/**
 * Copyright 2017 Chinmaya Mission DFW all rights reserved
 *
 */
package org.cmdfw.main;

import org.cmdfw.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author parthi
 */

@SpringBootApplication
public class EdlApplication {

	public static void main(String[] args) {

		SpringApplication.run(AppConfig.class, args);
	}
}
