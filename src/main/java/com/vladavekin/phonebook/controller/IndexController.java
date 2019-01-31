package com.vladavekin.phonebook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.Map;

@Controller
public class IndexController implements WebMvcConfigurer {

    @GetMapping("/")
    public String index(Map<String, Object> model) {

        return "index";
    }

}
