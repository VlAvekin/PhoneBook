package com.vladavekin.phonebook.service.userService;

import com.vladavekin.phonebook.domain.User;
import com.vladavekin.phonebook.repos.UserRepo;
import com.vladavekin.phonebook.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    @Qualifier("userDBRepo")
    private UserRepo userRepo;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private static final String ENCODED_PASSWORD = "ENCODED PASSWORD";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.userService = Mockito.spy(this.userService);
    }

    @Test
    public void addNewUser() throws IOException {
        User user = new User();

        boolean isUserCreated = userService.addNewUser(user);

        Assert.assertTrue(isUserCreated);
        Assert.assertTrue(user.isActive());

        Mockito.verify(passwordEncoder, Mockito.times(1)).encode(user.getPassword());
        Mockito.verify(userRepo, Mockito.times(1)).save(user);
    }

    @Test
    public void addNewFailUser() throws IOException {
        final String username = "John";

        User user = new User();

        user.setUsername(username);

        Mockito.doReturn(new User())
                .when(userRepo)
                .findByUsername(username);

        boolean isUserCreated = userService.addNewUser(user);

        Assert.assertFalse(isUserCreated);

        Mockito.verify(passwordEncoder, Mockito.times(0)).encode(ArgumentMatchers.anyString());
        Mockito.verify(userRepo, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameFail() {
        User user = new User();

        User expected = (User) userService.loadUserByUsername(user.getUsername());

        Assert.assertEquals(expected, user);

        Mockito.verify(userRepo, Mockito.times(1)).findByUsername(user.getUsername());
    }

    @Test()
    public void loadUserByUsername() {
        final String username = "John";

        User user = new User();

        user.setUsername(username);

        Mockito.doReturn(new User())
                .when(userRepo)
                .findByUsername(username);

        User expected = (User) userService.loadUserByUsername(user.getUsername());

        Assert.assertEquals(expected, user);

        Mockito.verify(userRepo, Mockito.times(1)).findByUsername(user.getUsername());
    }

}