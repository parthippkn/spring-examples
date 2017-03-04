package com.ppkn.redis.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

  @Cacheable(value = "serviceCache", key = "#key")
  public String getCache(String key, String value) {

    System.out.println("inside putCache..");
    return "Cached key: " + key + " ; value : " + value;
  }

  @CacheEvict(value = "serviceCache", key = "#key")
  public void deleteCache(String key) {
    System.out.println("inside deleteCache..");
  }
}