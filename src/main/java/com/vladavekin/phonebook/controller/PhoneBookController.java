package com.vladavekin.phonebook.controller;

import com.vladavekin.phonebook.domain.PhoneBookData;
import com.vladavekin.phonebook.domain.User;
import com.vladavekin.phonebook.repos.PhoneBookDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class PhoneBookController {

    @Autowired
    PhoneBookDataRepo pbdRepo;

    @GetMapping("/phoneBook")
    public String getList(Model model) {

        phoneBookList(model);

        return "phoneBook";
    }


    @GetMapping("/phoneBook/{id}")
    private String edit(@PathVariable Long id,
                        Model model) {

        phoneBookList(model);

        return "phoneBook";
    }

    @PostMapping("/phoneBook")
    public String add(
            @AuthenticationPrincipal User user,
            @Valid PhoneBookData pdb,
            Model model) {

        pdb.setAuthor(user);

        pbdRepo.save(pdb);

        phoneBookList(model);

        return "phoneBook";
    }


    @PostMapping("/phoneBook/{id}")
    public String delete(@PathVariable Long id,
                         Model model) {

        PhoneBookData phoneBookData = new PhoneBookData();
        phoneBookData.setId(id);

        pbdRepo.delete(phoneBookData);

        phoneBookList(model);

        return "redirect:/phoneBook";
    }

    private void phoneBookList(Model model) {
        Iterable<PhoneBookData> pbdList = pbdRepo.findAll();
        model.addAttribute("phoneBooks", pbdList);
    }

}
