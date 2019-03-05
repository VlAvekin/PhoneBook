package com.vladavekin.phonebook.repos.phoneBookData;

import com.google.gson.Gson;
import com.vladavekin.phonebook.domain.PhoneBookData;
import com.vladavekin.phonebook.domain.User;
import com.vladavekin.phonebook.repos.PhoneBookDataRepo;
import com.vladavekin.phonebook.repos.user.UserJsonRepo;
import com.vladavekin.phonebook.service.FileJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class PhoneBookDataJsonRepo implements PhoneBookDataRepo {

    @Value("${json.db.path}")
    private String path;

    private Gson gson = new Gson();

    @Autowired
    private FileJson fileJson;

    @Autowired
    private UserJsonRepo userJsonRepo;

    public Set<PhoneBookData> findAll() throws IOException {

        fileJson.setPath(path);

        Set<PhoneBookData> pbdList = new HashSet<>();
        List<String> jsons;

        final File file = new File(path);
        if (file.exists() && file.isFile()) {
            jsons = fileJson.read();

            for (String json: jsons) {

                final User previous = gson.fromJson(json, User.class);

                User actual = new User();
                actual.setId(previous.getId());
                actual.setUsername(previous.getUsername());
                actual.setFullName(previous.getFullName());
                actual.setActive(previous.isActive());

                Set<PhoneBookData> pbdSet = previous.getPhoneBookData();
                if (pbdSet != null){
                    for (PhoneBookData phoneBookData : pbdSet) {
                        phoneBookData.setAuthor(actual);
                        pbdList.add(phoneBookData);
                    }
                }
            }
            return pbdList;
        }
        return null;
    }

    // lastName, firstName, mobilePhone
    public Set<PhoneBookData> findBySearchAll(final String search) throws IOException {

        Set<PhoneBookData> pbdList = findAll();
        Set<PhoneBookData> actualList = search(pbdList, search, null);

        return actualList;
    }

    public Set<PhoneBookData> findBySearchByUser(final String search,
                                                User user) throws IOException {

        Set<PhoneBookData> pbdList;
        if (user.getPhoneBookData() != null){
            pbdList = user.getPhoneBookData();
        } else {
            pbdList = userJsonRepo
                    .findById(user.getId())
                    .get()
                    .getPhoneBookData();
        }

        User actual = new User();
        actual.setId(user.getId());
        actual.setUsername(user.getUsername());
        actual.setFullName(user.getFullName());
        actual.setActive(user.isActive());

        Set<PhoneBookData> actualList = search(pbdList, search, actual);

        return actualList;
    }

    private Set<PhoneBookData> search(final Set<PhoneBookData> list,
                                      final String search,
                                      final User user) {

        if (list == null) {
            return new HashSet<>();
        }

        Set<PhoneBookData> actualList = new HashSet<>();

        String str = "";

        for (PhoneBookData phoneBookData: list) {

            str = phoneBookData.getLastName() + " " +
                    phoneBookData.getFirstName() + " " +
                    phoneBookData.getMobilePhone();

            if (similarity(str, search)){

                if (user != null){
                    phoneBookData.setAuthor(user);
                }

                actualList.add(phoneBookData);
            }
        }

        return actualList;
    }

    private boolean similarity(String compared, String comparable) {

        if (comparable.charAt(0) == '+')
            comparable = "(\\+)" + comparable.substring(1);

        Pattern pattern = Pattern.compile(comparable + "(\\w*)");
        Matcher matcher = pattern.matcher(compared);
        while(matcher.find())
            return true;

        return false;
    }

    public PhoneBookData save(PhoneBookData phoneBookData) throws IOException {

        if (phoneBookData == null){
            return null;
        }

        final User user = phoneBookData.getAuthor();
        phoneBookData.setAuthor(null);

        User actualUser = userJsonRepo
                .findById(user.getId())
                .get();

        actualUser.setPassword2(null);

        Set<PhoneBookData> list;

        long id = 1L;

        if (actualUser.getPhoneBookData() != null){
            list = actualUser.getPhoneBookData();
            for (Iterator<PhoneBookData> it = list.iterator(); it.hasNext(); ) {
                PhoneBookData buf = it.next();

                // update
                if (buf.getId() == phoneBookData.getId()){
                    list.remove(buf);
                    list.add(phoneBookData);
                    break;
                }

                // auto increment id
                if (phoneBookData.getId() == null) {
                    if (buf.getId() >= id) {
                        id = buf.getId() + 1;
                    }
                }

            }
            phoneBookData.setId(id);
        } else {
            list = new HashSet<>();
            phoneBookData.setId(id);
        }

        list.add(phoneBookData);

        actualUser.setPhoneBookData(list);

        userJsonRepo.save(actualUser);

        return phoneBookData;
    }

    public void delete(PhoneBookData phoneBookData) throws IOException {

        final User user = phoneBookData.getAuthor();
        phoneBookData.setAuthor(null);

        System.out.println(user);

        User actual = userJsonRepo
                .findById(user.getId())
                .get();

        Set<PhoneBookData> list;

        if (actual.getPhoneBookData() != null){
            list = actual.getPhoneBookData();

            for (Iterator<PhoneBookData> it = list.iterator(); it.hasNext(); ) {
                PhoneBookData buf = it.next();
                if (buf.getId() == phoneBookData.getId()){
                    list.remove(buf);
                    break;
                }
            }

            actual.setPhoneBookData(list);
            userJsonRepo.save(actual);
        }
    }

    public void setPath(String path) {
        this.path = path;
    }
}
