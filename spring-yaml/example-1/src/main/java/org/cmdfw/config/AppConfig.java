/*
 * Copyright (c) 2017. Chinmaya Mission DFW. All rights reserved.
 *
 */

package org.cmdfw.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

/**
 * Created by Parthipan on 6/6/2017.
 */
@Configuration
@ComponentScan("org.cmdfw")

public class AppConfig {

    @Autowired
    private AppPropertyFileReader appPropertyFileReader;

    @Bean
    public DataSource dataSource() {

        System.out.println("appPropertyFileReader : " + appPropertyFileReader.toString());
        return null;
    }

}

