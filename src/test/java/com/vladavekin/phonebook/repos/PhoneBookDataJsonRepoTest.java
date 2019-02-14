package com.vladavekin.phonebook.repos;

import com.vladavekin.phonebook.domain.PhoneBookData;
import com.vladavekin.phonebook.domain.User;
import com.vladavekin.phonebook.repos.phoneBookData.PhoneBookDataJsonRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PhoneBookDataJsonRepoTest {

    @Autowired
    private PhoneBookDataJsonRepo phoneBookDataJsonRepo;


    // read
    @Test
    public void findByAll() throws IOException {

        final Set<PhoneBookData> pbd = phoneBookDataJsonRepo.findAll();

        for (PhoneBookData phoneBookData : pbd) {
            System.out.println(phoneBookData);
        }

        assertNotNull(pbd);

    }

    @Test
    public void findBySearchAll() throws IOException { // lastName, firstName, mobilePhone

        User user = new User();
        user.setId(6L);
        user.setUsername("six");
        user.setFullName("User");
        user.setActive(true);

        PhoneBookData expected = new PhoneBookData();
        expected.setId(1L);
        expected.setLastName("Six");
        expected.setFirstName("FirstName");
        expected.setPatronymic("Patronymic");
        expected.setMobilePhone("+380999999999");
        expected.setAuthor(user);

        String search = "Six";
        final Set<PhoneBookData> pbd = phoneBookDataJsonRepo.findBySearchAll(search);

        PhoneBookData actual = new PhoneBookData();

        for (PhoneBookData phoneBookData : pbd) {
            actual = phoneBookData;
        }

        assertEquals(expected, actual);
    }


    @Test
    public void findBySearchByUser() throws IOException {

        User user = new User();
        user.setId(4L);
        user.setUsername("four");
        user.setFullName("User");
        user.setActive(true);

        String search = "One";

        final Set<PhoneBookData> pbd = phoneBookDataJsonRepo.findBySearchByUser(search, user);

        PhoneBookData actual = new PhoneBookData();

        for (PhoneBookData phoneBookData : pbd) {
            actual = phoneBookData;
        }
        System.out.println(actual);

    }

    // write
    @Test
    public void save() throws IOException {
        User user = new User();
        user.setId(2L);
        user.setUsername("qwerty");
        user.setFullName("qwerty");
        user.setPassword("$2a$08$kNcUMbXfLHx0B9ECf/pERu8UnCdbKVB9Rto6Ten3IRXFzk6jmQ9sO");
        user.setActive(true);

        PhoneBookData phoneBookData = new PhoneBookData();
        phoneBookData.setLastName("LastName");
        phoneBookData.setFirstName("FirstName");
        phoneBookData.setPatronymic("Patronymic");
        phoneBookData.setMobilePhone("+380999999999");
        phoneBookData.setAuthor(user);

        phoneBookDataJsonRepo.save(phoneBookData);

    }

    @Test
    public void delete() throws IOException {
        User user = new User();
        user.setId(3L);
        user.setUsername("three");
        user.setFullName("User");
        user.setPassword("12345");
        user.setPassword2("12345");
        user.setActive(true);

        PhoneBookData phoneBookData = new PhoneBookData();
        phoneBookData.setId(1L);
        phoneBookData.setLastName("LastName");
        phoneBookData.setFirstName("FirstName");
        phoneBookData.setPatronymic("Patronymic");
        phoneBookData.setMobilePhone("+380999999999");
        phoneBookData.setAuthor(user);

        phoneBookDataJsonRepo.delete(phoneBookData);
    }
}