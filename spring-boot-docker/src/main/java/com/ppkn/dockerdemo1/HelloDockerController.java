package com.ppkn.dockerdemo1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Parthipan on 7/9/2017.
 */
@RestController
public class HelloDockerController {

    private static final Logger logger = LoggerFactory.getLogger(HelloDockerController.class);

    @RequestMapping("/")
    public String getHello() {

        logger.debug("HelloDockerController method invoked..");
        return "Hello Docker Controller";
    }
}
