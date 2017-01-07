package com.ppkn.core.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
// ignore if property file not found
@PropertySource(value = "classpath:application-${profile:native}.properties", ignoreResourceNotFound = true)
public class AppConfigReader {

    private final Logger logger = LoggerFactory.getLogger(AppConfigReader.class);

    @Autowired
    private Environment environment;

    public String getApplicationCryptoKey() {
	return getProperty("application.crypto.key");
    }

    public String getApplicationRoot() {
	return getProperty("application.root");
    }

    public String getLogRoot() {
	return getProperty("application.log.root");
    }

    public String getLogLevel() {
	return getProperty("application.log.level");
    }

    public String getDBDriverClassName() {
	return getProperty("db.driver");
    }

    public String getDBUrl() {
	return getProperty("db.url");
    }

    public String getDBUsername() {
	return getProperty("db.username");
    }

    public String getDBPassword() {
	return getProperty("db.password");
    }

    public String getHibernateDialect() {
	return getProperty("hibernate.dialect");
    }

    public String getHibernateFormatSql() {
	return getProperty("hibernate.format_sql");
    }

    public String getHibernateShowSql() {
	return getProperty("hibernate.show_sql");
    }

    private String getProperty(String propName) {
	return environment.getProperty(propName);
    }

    @PostConstruct
    public void print() {
	logger.debug("In post-construct method..." + this.getLogRoot());
    }

}
