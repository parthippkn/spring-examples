/*
 * Copyright (c) 2017. Chinmaya Mission DFW. All rights reserved.
 *
 */

package org.cmdfw.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Parthipan on 6/7/2017.
 */
@Controller
public class HomeController {

    public static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping("/")
    public String rootMapping() {
        logger.debug("In rootmapping..");
        return "/index.html";
    }
}
