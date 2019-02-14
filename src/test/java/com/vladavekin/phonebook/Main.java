package com.vladavekin.phonebook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws Exception {

        String[] ss = { "Avekin Vlad +380999999999", // 11
                        "Appel Tima +380666666666", // 10
                        "Ofise Bob +380955555555"}; // 9

        String a = "T";

        for (String s : ss) {
            if (similarity(s, a)){
                System.out.println(s);
            }
        }
    }

    public static boolean similarity(String compared, String comparable) {

        if (comparable.charAt(0) == '+')
            comparable = "(\\+)" + comparable.substring(1);

        Pattern pattern = Pattern.compile(comparable + "(\\w*)");
        Matcher matcher = pattern.matcher(compared);
        while(matcher.find())
            return true;

        return false;
    }
}
