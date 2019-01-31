package com.vladavekin.phonebook.repos;

import com.vladavekin.phonebook.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

}
