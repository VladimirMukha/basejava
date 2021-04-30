package com.urise.webapp;

import com.urise.webapp.model.SectionType;

public class SingletonTest {

    private static SingletonTest SingletonTest = new SingletonTest();

    private SingletonTest() {

    }

    public static SingletonTest getInstance() {
        return SingletonTest;
    }

    public static void main(String[] args) {
        for (SectionType section : SectionType.values()) {
            System.out.println(section.getTitle());
        }
    }
}