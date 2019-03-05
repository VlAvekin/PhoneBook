package com.vladavekin.phonebook.service.userService;

import com.vladavekin.phonebook.domain.User;
import com.vladavekin.phonebook.repos.UserRepo;
import com.vladavekin.phonebook.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceUpdateTest {

    @Autowired
    private UserService userService;

    @MockBean
    @Qualifier("userDBRepo")
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    private static final String ENCODED_PASSWORD = "ENCODED PASSWORD";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.userService = Mockito.spy(this.userService);
    }

    @Test
    public void updateProfile() throws IOException {
        final String fullName = "Tester";
        final String password = ENCODED_PASSWORD;

        User user = new User();

        boolean isUserUpdate = userService.updateProfile(user, password, fullName);

        Assert.assertTrue(isUserUpdate);

        Assert.assertNotNull(user.getFullName());
        Assert.assertNotNull(user.getPassword());

        Mockito.verify(userRepo, Mockito.times(1)).save(user);
    }

    @Test
    public void updateFailProfile() throws IOException {
        User user = new User();

        boolean isUserUpdate = userService.updateProfile(user, null, null);

        Assert.assertFalse(isUserUpdate);

        Assert.assertNull(user.getFullName());
        Assert.assertNull(user.getPassword());

        Mockito.verify(passwordEncoder, Mockito.times(0)).encode(ArgumentMatchers.anyString());
        Mockito.verify(userRepo, Mockito.times(0)).save(user);
    }
}