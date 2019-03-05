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
@WithUserDetails("tester")
@TestPropertySource("/application-DB-test.properties")
@Sql(value = {"/create-user-before.sql", "/create-phone-book-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-phone-book-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserPhoneBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void pageTest() throws Exception {

        this.mockMvc.perform(get("/user-phone-book/2"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/nav/div[2]").string("tester"));
    }

    @Test
    public void getListUserPhoneBookControllerTest() throws Exception {
        this.mockMvc.perform(get("/user-phone-book/2"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div/table/tbody/tr").nodeCount(2));
    }

    @Test
    public void searchLastNameTest() throws Exception {

        final String lastName = "Fiveth";

        this.mockMvc.perform(get("/user-phone-book/2").param("search", lastName))
                .andDo(print())
                .andExpect(xpath("/html/body/div/table/tbody/tr").nodeCount(1))
                .andExpect(xpath("/html/body/div/table/tbody/tr[@data_id=5]").exists());
    }

    @Test
    public void editInShapePhoneBookControllerTest() throws Exception {

        final String phoneBookId = "6";

        final String lastName = "Sixth";
        final String firstName = "Sixs";
        final String patronymic = "Sixth";
        final String mobilePhone = "+380931234567";
        final String homePhone = "1234";
        final String address = "1234567";
        final String email = "test@test.com";

        MockHttpServletRequestBuilder get = get("/user-phone-book/2")
                .param("phoneBook", phoneBookId);

        this.mockMvc.perform(get)
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//input[@id='lastName']/@value").string(lastName))
                .andExpect(xpath("//input[@id='firstName']/@value").string(firstName))
                .andExpect(xpath("//input[@id='patronymic']/@value").string(patronymic))
                .andExpect(xpath("//input[@id='phonefield']/@value").string(mobilePhone))
                .andExpect(xpath("//input[@id='homePhone']/@value").string(homePhone))
                .andExpect(xpath("//input[@id='address']/@value").string(address))
                .andExpect(xpath("//input[@id='inputEmail4']/@value").string(email));
    }



    @Test
    public void editPhoneBookBindingResultTest() throws Exception {

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

    @Test
    public void deletePhoneBookControllerTest() throws Exception {

        final String userId = "2";
        final String phoneBookId = "5";

        MockHttpServletRequestBuilder post = post("/user-phone-book/" + userId + "/" + phoneBookId)
                .with(csrf());
        this.mockMvc.perform(post)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(authenticated());

        this.mockMvc.perform(get("/user-phone-book/2"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div/table/tbody/tr").nodeCount(1));

    }
}