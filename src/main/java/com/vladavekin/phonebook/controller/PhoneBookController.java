package com.vladavekin.phonebook.controller;

import com.vladavekin.phonebook.domain.PhoneBookData;
import com.vladavekin.phonebook.domain.User;
import com.vladavekin.phonebook.repos.PhoneBookDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;


@Controller
public class PhoneBookController {

    @Value("${phone.book.repo.class}")
    private String namePBD;

    private PhoneBookDataRepo pbdRepo;

    @Autowired
    public void setPbdRepo(ApplicationContext context){
        pbdRepo = (PhoneBookDataRepo) context.getBean(namePBD);
    }


    @GetMapping("/phoneBook")
    public String getList(@RequestParam(required = false, defaultValue = "") String search,
                          Model model) throws IOException {

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

    @PostMapping("/phoneBook")
    public String add(
            @AuthenticationPrincipal User user,
            @Valid PhoneBookData pdb,
            BindingResult bindingResult,
            Model model) throws IOException {

        pdb.setAuthor(user);
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);

            model.addAttribute("phoneBook", pdb);

        } else {

            pbdRepo.save(pdb);
        }

        Iterable<PhoneBookData> pbdList = pbdRepo.findAll();
        model.addAttribute("phoneBooks", pbdList);

        return "phoneBook";
    }

}
