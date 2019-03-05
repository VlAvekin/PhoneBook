package com.vladavekin.phonebook.service;

import com.vladavekin.phonebook.domain.User;
import com.vladavekin.phonebook.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Service
public class UserService implements UserDetailsService {

    @Value("${user.repo.class}")
    private String nameUser;

    private UserRepo userRepo;

    @Autowired
    private void setUserRepo(ApplicationContext context) {
        this.userRepo = (UserRepo) context.getBean(nameUser);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = userRepo.findByUsername(username);

        if (user == null){
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public boolean updateProfile(User user, String password, String fullName) throws IOException {

        //user = userRepo.findById(user.getId()).get();

        if (StringUtils.isEmpty(password) && StringUtils.isEmpty(fullName)) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(password));
        user.setFullName(fullName);

        userRepo.save(user);
        return true;
    }


    public boolean addNewUser(User user) throws IOException {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null){
            return false;
        }

        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);

        return true;
    }
}