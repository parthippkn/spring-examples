package com.ppkn.jms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Created by Parthipan on 6/23/2017.
 */
@SpringBootApplication
public class Main {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(AppConfig.class);
        MessageSender sender = context.getBean(MessageSender.class);
        long begin = System.currentTimeMillis();
        sender.sendMessage(1000);
        System.out.println("Time Taken : " + (System.currentTimeMillis() - begin));
    }
}
