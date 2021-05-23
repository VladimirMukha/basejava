package com.urise.webapp;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileTest {
    static String count = "";

    public static void main(String[] args) {
        Path parent = Paths.get("d:/test/basejava/src");

        foundDeepFiles(new File(parent.toString()));
    }

    private static void foundDeepFiles(File dir) {
        File[] filesList = dir.listFiles();

        for (File file : filesList) {
            if (file.isDirectory()) {
                System.out.println(count + "dir:" + file.getName());
                count += "-";
                foundDeepFiles(file);
            } else if (file.isFile()) {
                count = "";
                System.out.println("file:" + file.getName());
            }
        }
    }
}