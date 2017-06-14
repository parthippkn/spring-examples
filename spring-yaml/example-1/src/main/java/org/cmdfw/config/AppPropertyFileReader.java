/*
 * Copyright (c) 2017. Chinmaya Mission DFW. All rights reserved.
 *
 */

package org.cmdfw.config;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Parthipan on 6/11/2017.
 */
@Configuration
@ConfigurationProperties(prefix = "application")
public class AppPropertyFileReader {

    private String name;
    private DBConfig database;
    private NeoncrmConfig neoncrm;
    private HibernateConfig hibernate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DBConfig getDatabase() {
        return database;
    }

    public void setDatabase(DBConfig database) {
        this.database = database;
    }

    public NeoncrmConfig getNeoncrm() {
        return neoncrm;
    }

    public void setNeoncrm(NeoncrmConfig neoncrm) {
        this.neoncrm = neoncrm;
    }

    public HibernateConfig getHibernate() {
        return hibernate;
    }

    public void setHibernate(HibernateConfig hibernate) {
        this.hibernate = hibernate;
    }

    public static class DBConfig {

        private String driver;
        private String url;
        private String username;
        private String password;

        public String getDriver() {
            return driver;
        }

        public void setDriver(String driver) {
            this.driver = driver;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }

    public static class NeoncrmConfig {

        private String authurl;
        private String apikey;
        private List<Endpoints> endpoints = new ArrayList<>();

        public String getAuthurl() {
            return authurl;
        }

        public void setAuthurl(String authurl) {
            this.authurl = authurl;
        }

        public String getApikey() {
            return apikey;
        }

        public void setApikey(String apikey) {
            this.apikey = apikey;
        }

        public List<Endpoints> getEndpoints() {
            return endpoints;
        }

        public void setEndpoints(List<Endpoints> endpoints) {
            this.endpoints = endpoints;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }

        public static class Endpoints {

            private String schema;
            private String url;

            public String getSchema() {
                return schema;
            }

            public void setSchema(String schema) {
                this.schema = schema;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            @Override
            public String toString() {
                return ToStringBuilder.reflectionToString(this);
            }
        }
    }

    public static class HibernateConfig {

        private String dialect;
        private String formatSql;
        private String showSql;

        public String getDialect() {
            return dialect;
        }

        public void setDialect(String dialect) {
            this.dialect = dialect;
        }

        public String getFormatSql() {
            return formatSql;
        }

        public void setFormatSql(String formatSql) {
            this.formatSql = formatSql;
        }

        public String getShowSql() {
            return showSql;
        }

        public void setShowSql(String showSql) {
            this.showSql = showSql;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
