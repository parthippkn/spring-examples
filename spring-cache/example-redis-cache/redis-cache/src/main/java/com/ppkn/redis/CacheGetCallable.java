package com.ppkn.redis;

import java.util.concurrent.Callable;

import com.ppkn.redis.service.CacheService;

public class CacheGetCallable implements Callable<String> {

  private CacheService cacheService = null;
  private String cacheKey = null;
  
  public CacheGetCallable(CacheService cacheService, String cacheKey) {
    
    this.cacheService = cacheService;
    this.cacheKey = cacheKey;
    
  }
  @Override
  public String call() throws Exception {
    
    String returnValue = "Return value [ " + Thread.currentThread().getName() + " ] : " + cacheService.getCache(cacheKey, null);
    return returnValue;
  }
  
}