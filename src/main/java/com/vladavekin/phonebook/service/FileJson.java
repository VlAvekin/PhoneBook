package com.vladavekin.phonebook.service;

import com.google.gson.Gson;
import com.vladavekin.phonebook.domain.User;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

@Service
public class FileJson {

    private String path;

    private Gson gson = new Gson();

    public List<String> update(final User user) throws IOException{

        List<String> jsons = read();
        User expectedUser;
        // TODO -> O(n) => O(log[n]) (sorting phone book)
        for (int i = 0; i < jsons.size(); i++) {

            expectedUser = gson.fromJson(jsons.get(i), User.class);
            if (user.getId() == expectedUser.getId()) {
                jsons.set(i, gson.toJson(user));
            }
        }
        return jsons;
    }

    public List<String> read() throws IOException {

        List<String> jsons = new LinkedList<>();

        Reader reader = new FileReader(path);
        Scanner scan = new Scanner(reader);

        String line;

        while (scan.hasNextLine()){

            line = scan.nextLine();

            if (!line.equals("[") &&
                    !line.equals("]") &&
                    !line.equals("") &&
                    !line.equals(" ") &&
                    line != null) {

                //line = line.replace(" ", "");

                if (line.charAt(line.length() - 1) == ','){
                    line = line.substring(0, line.length() - 1);
                }

                jsons.add(line);
            }
        }
        reader.close();

        return jsons;
    }

    public void write(List<String> jsons) throws IOException {

        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        boolean one = true;
        for (String json: jsons) {
            if (one) {
                sb.append(json + "");
                one = false;
            } else {
                sb.append(",\n" + json);
            }
        }
        sb.append("\n]");

        Writer writer = new FileWriter(path);
        writer.write(sb.toString());
        writer.close();
    }

    public void setPath(String path) {
        this.path = path;
    }
}