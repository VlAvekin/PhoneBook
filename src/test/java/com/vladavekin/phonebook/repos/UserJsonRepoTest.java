package com.vladavekin.phonebook.repos;

import com.vladavekin.phonebook.domain.PhoneBookData;
import com.vladavekin.phonebook.domain.User;
import com.vladavekin.phonebook.repos.user.UserJsonRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserJsonRepoTest {

    @Autowired
    private UserJsonRepo userJsonRepo;


    private User expectedUser;

    @Before
    public void before() {
        expectedUser = new User();
        expectedUser.setId(9L);
        expectedUser.setUsername("nine");
        expectedUser.setFullName("User Users");
        expectedUser.setPassword("12345");
        expectedUser.setPassword2("12345");
        expectedUser.setActive(true);
    }

    @Test
    public void findByUsername() throws IOException {
        User actualUser = userJsonRepo.findByUsername("nine");
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void findByUsernameNotExist() throws IOException {
        User actualUser = userJsonRepo.findByUsername("exist");
        assertNull(actualUser);
    }

    @Test
    public void findByUsernameNull() throws IOException {
        User actualUser = userJsonRepo.findByUsername(null);
        assertNull(actualUser);
    }

    @Test
    public void findById() throws IOException {
        User actualUser = userJsonRepo.findById(9L).get();
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void findByIdNull() throws IOException {
        User actualUser = userJsonRepo.findById(null).get();
        assertNull(actualUser);
    }

    @Test
    public void findByIdNotExist() throws IOException {
        User actualUser = userJsonRepo.findById(13l).get();
        assertNull(actualUser);
    }

    @Test
    public void saveIdExist() throws IOException {
        expectedUser = new User();
        expectedUser.setId(9L);
        expectedUser.setUsername("nine");
        expectedUser.setFullName("User 9");
        expectedUser.setPassword("12345");
        expectedUser.setPassword2("12345");
        expectedUser.setActive(false);

        User actualUser = userJsonRepo.save(expectedUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void save() throws IOException {

        expectedUser = new User();
        expectedUser.setUsername("two");
        expectedUser.setFullName("User Users");
        expectedUser.setPassword("123456");
        expectedUser.setPassword2("123456");
        expectedUser.setActive(true);

        PhoneBookData phoneBookData = new PhoneBookData();

        phoneBookData.setId(1L);
        phoneBookData.setLastName("LastFame");
        phoneBookData.setFirstName("FirstName");
        phoneBookData.setPatronymic("Patronymic");
        phoneBookData.setMobilePhone("+380999999999");

        Set<PhoneBookData> set = new HashSet<>();
        set.add(phoneBookData);

        expectedUser.setPhoneBookData(set);

        User actualUser = userJsonRepo.save(expectedUser);
        assertEquals(expectedUser, actualUser);
    }


    @Test
    public void update() throws IOException {

        expectedUser = new User();
        expectedUser.setId(2L);
        expectedUser.setUsername("two");
        expectedUser.setFullName("User Users");
        expectedUser.setPassword("123456");
        expectedUser.setPassword2("123456");
        expectedUser.setActive(true);

        PhoneBookData phoneBookData = new PhoneBookData();

        phoneBookData.setId(1L);
        phoneBookData.setLastName("LastFame");
        phoneBookData.setFirstName("FirstName");
        phoneBookData.setPatronymic("Patronymic");
        phoneBookData.setMobilePhone("+380999999999");

        Set<PhoneBookData> set = new HashSet<>();
        set.add(phoneBookData);

        expectedUser.setPhoneBookData(set);

        User actualUser = userJsonRepo.save(expectedUser);
        assertEquals(expectedUser, actualUser);
    }

}