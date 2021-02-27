package com.project.bookstore.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainController {

  @RequestMapping("/")
  public String home(){
    return hello();
  }

  @RequestMapping(value = "/hello")
  public String hello(){
    return "Hello. This is 4413 project.\nProject members are:\n1. Rushil\n2. Aman\n3. Rajanbir";
  }

//  @GetMapping(value = "/error")
//  public ModelAndView error(){
//    return new ModelAndView("error404");
//  }

}
