package com.urise.webapp;

public class MainString {

    public static void main(String[] args) {
        String[] strArray = new String[]{"1", "2", "3", "4", "5"};
        StringBuilder builder = new StringBuilder();

        for (String str : strArray) {
            builder.append(str).append(",");
        }
        System.out.println(builder.deleteCharAt(9).append("!"));
        String str = "abc";
        String str1 = "a";
        String str2 = (str1 + "bc").intern();
        System.out.println(str == str2);
    }
}