package com.vladavekin.phonebook.controller;

import com.vladavekin.phonebook.domain.PhoneBookData;
import com.vladavekin.phonebook.domain.User;
import com.vladavekin.phonebook.repos.PhoneBookDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

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
                    pbdRepo.findBySearch(search);
        } else {
            pbdList = pbdRepo.findAll();
        }

        model.addAttribute("phoneBooks", pbdList);

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
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            model.addAttribute("phoneBook", pdb);
        } else {
            pdb.setAuthor(user);
            pbdRepo.save(pdb);
        }

        phoneBookList(model);

        return "phoneBook";
    }

    @PostMapping("/phoneBook/{id}")
    public String delete(@PathVariable PhoneBookData id,
                         Model model) {

        pbdRepo.delete(id);

        phoneBookList(model);

        return "redirect:/phoneBook";
    }

    private void phoneBookList(Model model) {
        Iterable<PhoneBookData> pbdList = pbdRepo.findAll();
        model.addAttribute("phoneBooks", pbdList);
    }

}
