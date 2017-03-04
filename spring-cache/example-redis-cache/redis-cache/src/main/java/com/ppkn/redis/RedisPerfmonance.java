package com.ppkn.redis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.ppkn.redis.config.AppConfig;
import com.ppkn.redis.service.CacheService;

public class RedisPerfmonance {

  private ApplicationContext ctx = null;
  
  public RedisPerfmonance() {
    
    ctx = new AnnotationConfigApplicationContext(AppConfig.class);
  }
  
  public void simpleTest() {
    
    CacheService service = ctx.getBean(CacheService.class);
    service.getCache("hello20", "value1");
    service.deleteCache("hello");
    service.getCache("hello30", "value1");
  }
  
  public void threadTest() {
    
    long begin = System.currentTimeMillis();
    
    CacheService service = ctx.getBean(CacheService.class);
    String key = "hello" + new Date().getTime();
    service.getCache(key, "value1");
    
    ExecutorService executorService = Executors.newFixedThreadPool(20);
    List<Future<String>> featuresList = new ArrayList<>();
    
    for(int i=0; i<10000; i++) {
      featuresList.add( executorService.submit(new CacheGetCallable(service, key) ) );
    }
    
    executorService.shutdown();
    
    try {
      for(int i=0; i<featuresList.size(); i++) {
        System.out.println("threadFuture ["+i+ "] : " + featuresList.get(i).get().toString());
      }
      
    } catch (InterruptedException | ExecutionException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
   
    System.out.println("\n Total time taken (msec) : " + (System.currentTimeMillis() - begin));
  }
  
  public static void main(String[] args) {

    System.out.println("Hello");
    int nThreads = Runtime.getRuntime().availableProcessors();
    System.out.println("availableProcessors : " + nThreads);
    new RedisPerfmonance().threadTest();
    
  }
}






