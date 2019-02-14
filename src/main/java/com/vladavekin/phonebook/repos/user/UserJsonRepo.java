package com.vladavekin.phonebook.repos.user;

import com.google.gson.Gson;
import com.vladavekin.phonebook.domain.User;
import com.vladavekin.phonebook.repos.UserRepo;
import com.vladavekin.phonebook.service.FileJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserJsonRepo implements UserRepo {

    @Value("${json.db.path}")
    private String path;

    private Gson gson = new Gson();

    @Autowired
    private FileJson fileJson;


    public User findByUsername(String username) {

        if (username == null)
            return null;

        fileJson.setPath(path);

        List<String> jsons;
        User user = null;

        final File file = new File(path);
        if (file.exists() && file.isFile()) {
            try {
                jsons = fileJson.read();

                if (jsons != null) {
                    for (String json : jsons) {

                        if (json == null) {
                            return new User();
                        }

                        user = gson.fromJson(json, User.class);
                        if (user != null &&
                                user.getUsername() != null &&
                                user.getUsername().equals(username)) {
                            return user;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Optional<User> findById(Long id) throws IOException {

        if (id == null)
            return null;

        fileJson.setPath(path);

        List<String> jsons;
        User user = null;

        final File file = new File(path);
        if (file.exists() && file.isFile()) {
            jsons = fileJson.read();
            if (jsons != null) {
                // TODO -> O(n) => O(log[n]) (sorting phone book)
                for (String json : jsons) {

                    if (json == null) {
                        return null;
                    }

                    user = gson.fromJson(json, User.class);
                    if (user != null &&
                        user.getId() != null &&
                        user.getId() == id) {
                        return Optional.of(user);
                    }
                }
            }
        }
        return null;
    }

    public User save(User user) throws IOException {

        // not null
        if (user == null)
            return null;

        fileJson.setPath(path);

        final File file = new File(path);
        List<String> jsons = new LinkedList<>();

        if (file.exists() && file.isFile()) {

            // user exist, update
            User update = findByUsername(user.getUsername());
            if (update != null) {
                user.setId(update.getId());
                jsons = fileJson.update(user);
                fileJson.write(jsons);
                return user;
            }


            // auto increment id
            List<String> auto = fileJson.read();
            User previous = gson.fromJson(auto.get(auto.size() - 1), User.class);
            user.setId(previous.getId() + 1);

            jsons = new LinkedList<>();

            jsons = fileJson.read();
        } else {
            user.setId(1L);
        }

        jsons.add(gson.toJson(user));

        fileJson.write(jsons);
        return user;
    }

}