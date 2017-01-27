package com.ppkn.core.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ppkn.core.crypto.ReversibleEncryptor;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import liquibase.integration.spring.SpringLiquibase;

@Configuration
@ComponentScan("com.ppkn.core")
@EnableJpaRepositories(basePackages = { "com.ppkn.core" })
@EnableTransactionManagement
public class ServiceConfig {

	private static final Logger logger = LoggerFactory.getLogger(ServiceConfig.class);
	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";

	@Autowired
	private AppConfigReader appConfigReader;

	@Autowired
	private ReversibleEncryptor reversibleEncryptor;

	// configure datasource
	@Bean
	public DataSource dataSource() {

		HikariConfig configuration = new HikariConfig();
		configuration.setDriverClassName(appConfigReader.getDBDriverClassName());
		// configuration.setDataSourceJNDI(jndiDataSource);
		configuration.setJdbcUrl(appConfigReader.getDBUrl());
		configuration.setUsername(appConfigReader.getDBUsername());
		configuration.setPassword(reversibleEncryptor.decrypt(appConfigReader.getDBPassword()));
		return new HikariDataSource(configuration);
	}

	private Properties jpaProperties() {

		Properties jpaProperties = new Properties();

		jpaProperties.put(PROPERTY_NAME_HIBERNATE_DIALECT, appConfigReader.getHibernateDialect());
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, appConfigReader.getHibernateShowSql());
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, appConfigReader.getHibernateFormatSql());

		return jpaProperties;
	}

	// configure entity manager
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

		logger.info("//======== Creating entityManagerFactory ");
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource());
		entityManagerFactory.setJpaProperties(jpaProperties());
		entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactory.setPackagesToScan("com.ppkn.core.domain");
		return entityManagerFactory;
	}

	// configure JPA
	@Bean
	public PlatformTransactionManager transactionManager() {

		logger.info("//======== Creating transactionManager ");
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}

	// configure message source
	@Bean
	public MessageSource messageSource() {

		logger.info("//======== Creating message source ");
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("messages");
		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}

	// configure property place holder
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {

		logger.info("//======== Creating property place holdeer.. ");
		PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
		pspc.setIgnoreUnresolvablePlaceholders(true);
		pspc.setIgnoreResourceNotFound(true);
		pspc.setLocation(new ClassPathResource("application-native.properties"));
		return pspc;
	}

	@Bean
	SpringLiquibase springLiquibase(DataSource dataSource, Environment env) {
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setDataSource(dataSource);
		liquibase.setChangeLog("classpath:db.changelog.xml");
		return liquibase;
	}
}
