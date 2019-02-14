package com.vladavekin.phonebook.controller;

import com.vladavekin.phonebook.domain.PhoneBookData;
import com.vladavekin.phonebook.domain.User;
import com.vladavekin.phonebook.repos.PhoneBookDataRepo;
import com.vladavekin.phonebook.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Controller
public class UserPhoneBookController {

    @Value("${phone.book.repo.class}")
    private String namePBD;

    @Value("${user.repo.class}")
    private String nameUser;

    private PhoneBookDataRepo pbdRepo;

    private UserRepo userRepo;

    @Autowired
    public void setUserRepo(ApplicationContext context) {
        this.userRepo = (UserRepo) context.getBean(nameUser);
    }

    @Autowired
    public void setPbdRepo(ApplicationContext context){
        pbdRepo = (PhoneBookDataRepo) context.getBean(namePBD);
    }


    @GetMapping("/user-phone-book/{userId}")
    public String getPhoneBook(@RequestParam(required = false, defaultValue = "") String search,
                               @AuthenticationPrincipal User currentUser,
                               @PathVariable Long userId,
                               @RequestParam(required = false) Long phoneBook,
                               Model model) throws IOException {

        User user = userRepo
                .findById(userId)
                .get();

        Set<PhoneBookData> phoneBooks;

        if (search != null && !search.isEmpty()){
            phoneBooks = pbdRepo.findBySearchByUser(search, user);
        } else {
            phoneBooks = pbdRepo.findBySearchByUser(" ", user);
        }

        model.addAttribute("phoneBooks", phoneBooks);

        if (phoneBook != null){
            PhoneBookData pbd = getPhoneBookData(phoneBook, userId);
            model.addAttribute("phoneBook", pbd);
        }

        model.addAttribute("isCurrentUser", currentUser.equals(user));

        return "/userPhoneBook";
    }

    @PostMapping("/user-phone-book/{user}")
    public String edit(@AuthenticationPrincipal User currentUser,
                        @PathVariable Long user,
                        @RequestParam("id") Long phoneBookId,
                        @Valid PhoneBookData pdb,
                        BindingResult bindingResult,
                        Model model) throws IOException {

        model.addAttribute("isCurrentUser", currentUser.equals(user));

        PhoneBookData phoneBook = getPhoneBookData(phoneBookId, user);

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

    @PostMapping("/user-phone-book/{userId}/{pbdId}")
    public String delete(@PathVariable Long pbdId,
                         @PathVariable Long userId) throws IOException {

        PhoneBookData phoneBookData = getPhoneBookData(pbdId, userId);

        pbdRepo.delete(phoneBookData);

        return "redirect:/user-phone-book/" + userId;
    }

    private PhoneBookData getPhoneBookData(@PathVariable Long pbdId,
                                           @PathVariable Long userId) throws IOException {
        PhoneBookData phoneBookData = null;

        User user = userRepo.findById(userId).get();
        Set<PhoneBookData> list = user.getPhoneBookData();

        for (PhoneBookData pdb: list) {
            if (pdb.getId() == pbdId){
                phoneBookData = pdb;
                break;
            }
        }
        User actual = getUser(user);
        phoneBookData.setAuthor(actual);
        return phoneBookData;
    }

    private User getUser(User user) {
        User actual = new User();
        actual.setId(user.getId());
        actual.setUsername(user.getUsername());
        actual.setFullName(user.getFullName());
        actual.setActive(user.isActive());
        return actual;
    }

}