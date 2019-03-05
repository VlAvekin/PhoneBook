package com.vladavekin.phonebook.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("test")
@TestPropertySource("/application-DB-test.properties")
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getProfile() throws Exception {
        final String fullName = "TestUser";
        final String login = "test";

        this.mockMvc.perform(get("/profile"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(xpath("//b[@id='username']").string(login))
                .andExpect(xpath("//input[@id='fullName']/@value").string(fullName));
    }

    @Test
    public void updateProfile() throws Exception {
        final String login = "test";
        final String fullName = "Test Test";
        final String password1 = "12345";
        final String password2 = "12345";

        MockHttpServletRequestBuilder post = post("/profile")
                .param("fullName", fullName)
                .param("password", password1)
                .param("password2", password2)
                .with(csrf());
        this.mockMvc.perform(post)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(xpath("//b[@id='username']").string(login));

    }

    @Test
    public void profileBindingResultTest() throws Exception {

        final String login = "test";
        final String fullName = "Full name minimum 5 char";
        final String password1 = "Password minimum 5 char";
        final String password2 = "Password minimum 5 char";

        MockHttpServletRequestBuilder post = post("/profile")
                .param("fullName", " ")
                .param("password", " ")
                .param("password2", " ")
                .with(csrf());

        this.mockMvc.perform(post)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(xpath("//b[@id='username']").string(login))
                .andExpect(xpath("//div[@id='fullName']").string(fullName))
                .andExpect(xpath("//div[@id='password1']").string(password1))
                .andExpect(xpath("//div[@id='password2']").string(password2));
    }

    @Test
    public void profilePasswordEqualsTest() throws Exception {

        final String password1 = "Passwords are different!";

        MockHttpServletRequestBuilder post = post("/profile")
                .param("fullName", " ")
                .param("password", "12345")
                .param("password2", " ")
                .with(csrf());

        this.mockMvc.perform(post)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='password1']").string(password1));
    }
}