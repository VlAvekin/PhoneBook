package com.vladavekin.phonebook.controller;

import com.vladavekin.phonebook.domain.User;
import com.vladavekin.phonebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String getProfile(
                             @AuthenticationPrincipal User user,
                             Model model) {

        model.addAttribute("username", user.getUsername());
        model.addAttribute("fullName", user.getFullName());

        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@AuthenticationPrincipal User currentUser,
                                @Valid User user,
                                BindingResult bindingResult,
                                Model model) throws IOException {

        if (user.getPassword() != null && !user.getPassword().equals(user.getPassword2())) {
            model.addAttribute("passwordError", "Passwords are different!");

            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("fullName", currentUser.getFullName());
            return "profile";
        }

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);

            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("fullName", currentUser.getFullName());
            return "profile";
        }

        userService.updateProfile(currentUser, user.getPassword(), user.getFullName());

        return "redirect:/";
    }
}
