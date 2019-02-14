package com.vladavekin.phonebook.repos;

import com.vladavekin.phonebook.domain.User;

import java.io.IOException;
import java.util.Optional;

public interface UserRepo {

    User findByUsername(String username);

    User save(User user) throws IOException;

    Optional<User> findById(Long id) throws IOException;

}
