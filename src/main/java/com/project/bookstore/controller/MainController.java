package com.project.bookstore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainController {

  Logger log = LoggerFactory.getLogger(MainController.class);

  @RequestMapping("/")
  public String home(){
    return hello();
  }

  @RequestMapping(value = "/hello")
  public String hello(){
    return "Hello. This is 4413 project.\nProject members are:\n1. Rushil\n2. Aman\n3. Rajanbir";
  }

  @GetMapping(value = "/error")
  public ModelAndView error(){
    return new ModelAndView("error404");
  }

  @Scheduled(cron = "0 */30 * * * *")
  public void dummyWorkerJob(){
    log.error("cron job ran");
  }

}
