package com.ppkn.core.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan("com.ppkn.core")
public class TestConfig {

    @Bean
    public DataSource dataSource() {

	HikariConfig configuration = new HikariConfig();
	configuration.setDriverClassName("org.hsqldb.jdbcDriver");
	// configuration.setDataSourceJNDI(jndiDataSource);
	configuration.setJdbcUrl("jdbc:hsqldb:mem:ppknJunit");
	configuration.setUsername("sa");
	configuration.setPassword("");
	return new HikariDataSource(configuration);
    }
}
