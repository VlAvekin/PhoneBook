package com.vladavekin.phonebook.repos;

import com.vladavekin.phonebook.domain.PhoneBookData;
import com.vladavekin.phonebook.domain.User;

import java.io.IOException;
import java.util.Set;

public interface PhoneBookDataRepo {
    Set<PhoneBookData> findBySearchByUser(String search, User user) throws IOException;

    PhoneBookData save(PhoneBookData phoneBook) throws IOException;

    Iterable<PhoneBookData> findBySearchAll(String search) throws IOException;

    Iterable<PhoneBookData> findAll() throws IOException;

    void delete(PhoneBookData phoneBookData) throws IOException;
}