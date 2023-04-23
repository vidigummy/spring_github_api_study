package com.example.spring_web_working_vidi_lab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class AuthController {

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String helloMan(Model model){
        return "login";
    }

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String hello(Model model){
        return "login";
    }
}