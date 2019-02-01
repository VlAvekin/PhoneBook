package com.vladavekin.phonebook.repos;

import com.vladavekin.phonebook.domain.PhoneBookData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhoneBookDataRepo extends JpaRepository<PhoneBookData, Long> {

    @Query("from PhoneBookData pbd " +
            "where concat(pbd.lastName, ' ', pbd.firstName, ' ', pbd.mobilePhone) " +
            "like concat('%', :search, '%') ")
    List<PhoneBookData> findBySearch(@Param("search") String search);
}
