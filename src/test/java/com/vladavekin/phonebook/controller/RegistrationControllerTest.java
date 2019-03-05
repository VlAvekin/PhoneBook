package com.vladavekin.phonebook.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-DB-test.properties")
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void registration() throws Exception {

        // TODO

        MockHttpServletRequestBuilder post = post("/registration")
                .param("fullName", "QWERTY")
                .param("username", "qwerty")
                .param("password", "12345")
                .param("password2", "12345")
                .with(csrf());

        this.mockMvc.perform(post)
                .andDo(print());

    }

    @Test
    public void registrationBindingResultTest() throws Exception {

        final String fullName = "Full name minimum 5 char";
        final String login = "User name minimum 3 char";
        final String password1 = "Password minimum 5 char";
        final String password2 = "Password minimum 5 char";

        MockHttpServletRequestBuilder post = post("/registration")
                .param("fullName", " ")
                .param("username", " ")
                .param("password", " ")
                .param("password2", " ")
                .with(csrf());

        this.mockMvc.perform(post)
                .andDo(print())
                .andExpect(xpath("//div[@id='fullName']").string(fullName))
                .andExpect(xpath("//div[@id='username']").string(login))
                .andExpect(xpath("//div[@id='password1']").string(password1))
                .andExpect(xpath("//div[@id='password2']").string(password2));
    }

    @Test
    public void registrationPasswordEqualsTest() throws Exception {

        final String password1 = "Passwords are different!";

        MockHttpServletRequestBuilder post = post("/registration")
                .param("fullName", " ")
                .param("username", " ")
                .param("password", "12345")
                .param("password2", " ")
                .with(csrf());

        this.mockMvc.perform(post)
                .andDo(print())
                .andExpect(xpath("//div[@id='password1']").string(password1));
    }
}