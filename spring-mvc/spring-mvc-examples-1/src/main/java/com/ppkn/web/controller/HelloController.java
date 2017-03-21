package com.ppkn.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {

  @RequestMapping("hello")
  public ModelAndView getHelloView() {
      return new ModelAndView("hello");
  }
}
