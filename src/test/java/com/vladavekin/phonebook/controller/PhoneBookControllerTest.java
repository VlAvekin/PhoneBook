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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("test")
@TestPropertySource("/application-DB-test.properties")
@Sql(value = {"/create-user-before.sql", "/create-phone-book-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-phone-book-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PhoneBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void pageTest() throws Exception {

        this.mockMvc.perform(get("/phoneBook"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/nav/div[2]").string("test"));
    }

    @Test
    public void getListPhoneBookTest() throws Exception {

        this.mockMvc.perform(get("/phoneBook"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div/table/tbody/tr").nodeCount(6));
    }

    @Test
    public void searchLastNameTest() throws Exception {

        final String lastName = "First";

        this.mockMvc.perform(get("/phoneBook").param("search", lastName))
                .andDo(print())
                .andExpect(xpath("/html/body/div/table/tbody/tr").nodeCount(1))
                .andExpect(xpath("/html/body/div/table/tbody/tr[@data_id=1]").exists())
                .andExpect(xpath("/html/body/div/table/tbody/tr[@data_id=1]/td[1]").string(lastName));
    }

    @Test
    public void searchFirstNameTest() throws Exception {

        final String firstName = "Three";

        this.mockMvc.perform(get("/phoneBook").param("search", firstName))
                .andDo(print())
                .andExpect(xpath("/html/body/div/table/tbody/tr").nodeCount(1))
                .andExpect(xpath("/html/body/div/table/tbody/tr[@data_id=3]").exists())
                .andExpect(xpath("/html/body/div/table/tbody/tr[@data_id=3]/td[2]").string(firstName));
    }

    @Test
    public void searchMobilePhoneTest() throws Exception {

        final String mobilePhone = "+380661234567";

        this.mockMvc.perform(get("/phoneBook").param("search", mobilePhone))
                .andDo(print())
                .andExpect(xpath("/html/body/div/table/tbody/tr").nodeCount(1))
                .andExpect(xpath("/html/body/div/table/tbody/tr[@data_id=2]").exists())
                .andExpect(xpath("/html/body/div/table/tbody/tr[@data_id=2]/td[4]").string(mobilePhone));
    }

    @Test
    public void addPhoneBookTest() throws Exception {

        final String lastName = "Tens";
        final String firstName = "Tenth";
        final String patronymic = "Tenth";
        final String mobilePhone = "+380501234567";

        MockHttpServletRequestBuilder post = post("/phoneBook")
                .param("lastName", lastName)
                .param("firstName", firstName)
                .param("patronymic", patronymic)
                .param("mobilePhone", mobilePhone)
                .with(csrf());

        this.mockMvc.perform(post)
                .andDo(print())
                .andExpect(xpath("/html/body/div/table/tbody/tr").nodeCount(7))
                .andExpect(xpath("/html/body/div/table/tbody/tr[@data_id=10]").exists())
                .andExpect(xpath("/html/body/div/table/tbody/tr[@data_id=10]/td[1]").string(lastName))
                .andExpect(xpath("/html/body/div/table/tbody/tr[@data_id=10]/td[2]").string(firstName))
                .andExpect(xpath("/html/body/div/table/tbody/tr[@data_id=10]/td[3]").string(patronymic))
                .andExpect(xpath("/html/body/div/table/tbody/tr[@data_id=10]/td[4]").string(mobilePhone));
    }

    @Test
    public void addPhoneBookBindingResultTest() throws Exception {

        final String lastName = "Last name minimum 4 char";
        final String firstName = "First name minimum 4 char";
        final String patronymic = "Patronymic minimum 4 char";
        final String mobilePhone = "Mobile phone length 13 char format +380661234567";
        final String email = "Email is not correct";

        MockHttpServletRequestBuilder post = post("/phoneBook")
                .param("lastName", " ")
                .param("firstName", " ")
                .param("patronymic", " ")
                .param("mobilePhone", " ")
                .param("homePhone", " ")
                .param("address", " ")
                .param("email", " ")
                .with(csrf());

        this.mockMvc.perform(post)
                .andDo(print())
                .andExpect(xpath("//div[@id='lastName']").string(lastName))
                .andExpect(xpath("//div[@id='firstName']").string(firstName))
                .andExpect(xpath("//div[@id='patronymic']").string(patronymic))
                .andExpect(xpath("//div[@id='mobilePhone']").string(mobilePhone))
                .andExpect(xpath("//div[@id='email']").string(email));
    }
}