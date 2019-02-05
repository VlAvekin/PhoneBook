package com.vladavekin.phonebook.controller;

import com.vladavekin.phonebook.domain.PhoneBookData;
import com.vladavekin.phonebook.repos.PhoneBookDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PhoneBookController {

    @Autowired
    PhoneBookDataRepo pbdRepo;

    @GetMapping("/phoneBook")
    public String getList(@RequestParam(required = false, defaultValue = "") String search,
                          Model model) {

        Iterable<PhoneBookData> pbdList;

        if (search != null && !search.isEmpty()){
            pbdList =
                    pbdRepo.findBySearchAll(search);
        } else {
            pbdList = pbdRepo.findAll();
        }

        model.addAttribute("phoneBooks", pbdList);


        return "phoneBook";
    }

}
