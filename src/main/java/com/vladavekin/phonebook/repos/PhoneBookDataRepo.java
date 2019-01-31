package com.vladavekin.phonebook.repos;

import com.vladavekin.phonebook.domain.PhoneBookData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneBookDataRepo extends JpaRepository<PhoneBookData, Long> {
}
