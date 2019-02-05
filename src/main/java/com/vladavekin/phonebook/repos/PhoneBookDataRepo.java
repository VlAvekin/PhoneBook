package com.vladavekin.phonebook.repos;

import com.vladavekin.phonebook.domain.PhoneBookData;
import com.vladavekin.phonebook.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;

public interface PhoneBookDataRepo extends JpaRepository<PhoneBookData, Long> {

    @Query("from PhoneBookData pbd " +
            "where concat(pbd.lastName, ' ', pbd.firstName, ' ', pbd.mobilePhone) " +
            "like concat('%', :search, '%') ")
    List<PhoneBookData> findBySearchAll(@Param("search") String search);

    @Query("from PhoneBookData pbd " +
            "where pbd.author=:user " +
            "and " +
            "concat(pbd.lastName, ' ', pbd.firstName, ' ',  pbd.mobilePhone) " +
            "like concat('%', :search, '%')")
    Set<PhoneBookData> findBySearchByUser(@Param("search") String search,
                                          @PathVariable User user);
}
