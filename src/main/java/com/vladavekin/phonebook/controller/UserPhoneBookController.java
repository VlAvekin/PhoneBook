package com.vladavekin.phonebook.controller;

import com.vladavekin.phonebook.domain.PhoneBookData;
import com.vladavekin.phonebook.domain.User;
import com.vladavekin.phonebook.repos.PhoneBookDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Set;

@Controller
public class UserPhoneBookController {

    @Autowired
    PhoneBookDataRepo pbdRepo;

    @GetMapping("/user-phone-book/{user}")
    public String getPhoneBook(@RequestParam(required = false, defaultValue = "") String search,
                               @AuthenticationPrincipal User currentUser,
                               @PathVariable User user,
                               @RequestParam(required = false) PhoneBookData phoneBook,
                               Model model) {

        Set<PhoneBookData> phoneBooks = user.getPhoneBookData();
        model.addAttribute("phoneBooks", phoneBooks);

        if (phoneBook != null){
            model.addAttribute("phoneBook", phoneBook);
        }

        model.addAttribute("isCurrentUser", currentUser.equals(user));

        return "/userPhoneBook";
    }

    @PostMapping("/user-phone-book")
    public String add(
            @AuthenticationPrincipal User user,
            @Valid PhoneBookData pdb,
            BindingResult bindingResult,
            Model model) {

        pdb.setAuthor(user);
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            model.addAttribute("phoneBook", pdb);
        } else {
            pbdRepo.save(pdb);
        }

        return "redirect:/user-phone-book/" + user.getId();
    }

    @PostMapping("/user-phone-book/{user}")
    private String edit(@AuthenticationPrincipal User currentUser,
                        @PathVariable Long user,
                        @RequestParam("id") PhoneBookData phoneBook,
                        @Valid PhoneBookData pdb,
                        BindingResult bindingResult,
                        Model model) {

        model.addAttribute("isCurrentUser", currentUser.equals(user));

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            model.addAttribute("phoneBook", pdb);

            return "redirect:/user-phone-book/" + user;
        }

        if(phoneBook.getAuthor().equals(currentUser)){

            phoneBook.setAddress(pdb.getAddress());
            phoneBook.setEmail(pdb.getEmail());
            phoneBook.setFirstName(pdb.getFirstName());
            phoneBook.setHomePhone(pdb.getHomePhone());
            phoneBook.setLastName(pdb.getLastName());
            phoneBook.setMobilePhone(pdb.getMobilePhone());
            phoneBook.setPatronymic(pdb.getPatronymic());

            pbdRepo.save(phoneBook);
        }

        return "redirect:/user-phone-book/" + user;
    }

    @PostMapping("/user-phone-book/{user}/{phoneBookData}")
    public String delete(@PathVariable PhoneBookData phoneBookData,
                         @PathVariable Long user) {

        pbdRepo.delete(phoneBookData);

        return "redirect:/user-phone-book/" + user;
    }

}