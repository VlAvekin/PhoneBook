package com.vladavekin.phonebook.repos.user;

import com.vladavekin.phonebook.domain.User;
import com.vladavekin.phonebook.repos.UserRepo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDBRepo extends JpaRepository<User, Long>, UserRepo {

    User findByUsername(String username);

}