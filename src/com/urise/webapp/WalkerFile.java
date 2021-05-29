package com.urise.webapp;

import com.urise.webapp.exception.StorageException;

import java.io.File;

public class WalkerFile {
    public static void main(String[] args) {
        File dir = new File("D:\\test\\basejava\\src");

        foundDeepFiles(dir, "");
    }

    private static void foundDeepFiles(File dir, String space) {
        File[] filesList = dir.listFiles();

        if (filesList == null) {
            throw new StorageException("file error", null, null);
        }

        for (File file : filesList) {
            if (file.isDirectory()) {
                System.out.println(space + "D:" + file.getName());
                foundDeepFiles(file, space + "  ");
            } else if (file.isFile()) {
                System.out.println(space + "F:" + file.getName());
            }
        }
    }
}