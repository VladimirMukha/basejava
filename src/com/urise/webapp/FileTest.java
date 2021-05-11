package com.urise.webapp;

import java.io.File;
import java.util.Objects;

public class FileTest {
    public static void main(String[] args) {
        FileTest test = new FileTest();
        test.recursionMethod(new File("D:\\test\\basejava\\src\\"));
    }

    public void recursionMethod(File file) {
        File[] arrayFile = file.listFiles();
        for (File entry : Objects.requireNonNull(arrayFile)) {
            if (entry.isDirectory()) {
                recursionMethod(entry);
                continue;
            }
            System.out.println(entry.getName());
        }
    }
}