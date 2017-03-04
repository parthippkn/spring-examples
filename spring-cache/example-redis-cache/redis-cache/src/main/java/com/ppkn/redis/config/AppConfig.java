package com.ppkn.redis.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching
@ComponentScan("com.ppkn.redis")
public class AppConfig {

  private final String hostName = "127.0.0.1";
  private final int port = 7070;
  private final int MAX_POOL_SIZE = 50;

  @Bean
  public RedisTemplate<String, String> redisTemplate() {

    RedisTemplate<String, String> template = new RedisTemplate<>();
    template.setConnectionFactory(connectionFactory());
    return template;
  }

  @Bean
  public JedisConnectionFactory connectionFactory() {

    JedisPoolConfig poolConfig = new JedisPoolConfig();
    poolConfig.setMaxTotal(MAX_POOL_SIZE);

    JedisConnectionFactory factory = new JedisConnectionFactory(poolConfig);
    factory.setHostName(hostName);
    factory.setPort(port);
    return factory;
  }

  @Bean
  public RedisCacheManager cacheManager() {

    return new RedisCacheManager(redisTemplate());
  }
}

