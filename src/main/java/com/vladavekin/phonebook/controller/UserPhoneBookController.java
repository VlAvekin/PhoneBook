package com.vladavekin.phonebook.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class UserPhoneBookController {

    @GetMapping("/user-phone-book/{id}")
    public String getPhoneBook(@RequestParam Long id) {

        return "/userPhoneBook";
    }

}
