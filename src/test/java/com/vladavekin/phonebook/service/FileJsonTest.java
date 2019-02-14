package com.vladavekin.phonebook.service;

import com.google.gson.Gson;
import com.vladavekin.phonebook.domain.User;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class FileJsonTest {

    private Gson gson = new Gson();

    @Test
    public void update() throws IOException {

        FileJson fileJson = new FileJson();

        fileJson.setPath("E:/IntellijProdjekt/Spring/PhoneBook/src/test/java/jsonDB/JsonDB.json");

        List<String> jsons = fileJson.read();

        for (String json: jsons) {
            User user = gson.fromJson(json, User.class);
            System.out.println(user);
        }

        System.out.println();

        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setUsername("one");
        expectedUser.setFullName("User");
        expectedUser.setPassword("123456");
        expectedUser.setPassword2("123456");
        expectedUser.setActive(true);

        jsons = fileJson.update(expectedUser);

        for (String json: jsons) {
            User user = gson.fromJson(json, User.class);
            System.out.println(user);
        }

    }

    @Test
    public void readJson() throws IOException {

        FileJson fileJson = new FileJson();

        fileJson.setPath("E:/IntellijProdjekt/Spring/PhoneBook/src/test/java/jsonDB/JsonDB.json");

        List<String> jsons = fileJson.read();

        for (String json: jsons) {
            User user = gson.fromJson(json, User.class);
            System.out.println(user);
        }

    }

    @Test
    public void read() throws IOException {

        FileJson fileJson = new FileJson();

        fileJson.setPath("E:/IntellijProdjekt/Spring/PhoneBook/src/test/java/jsonDB/JsonDB.json");

        List<String> jsons = fileJson.read();

        for (String json: jsons) {
            System.out.println(json);
        }

    }

    @Test
    public void write() {

    }
}