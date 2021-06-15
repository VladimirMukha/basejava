package com.urise.webapp;

public class MyDedLock {
    private static final String NAME_ONE = "Вася";
    private static final String NAME_TWO = "Петя";

    public static void main(String[] args) {
        new Thread(MyDedLock::detLock).start();
        new Thread(MyDedLock::detLock).start();
    }

    public static void detLock() {
        synchronized (NAME_ONE) {
            toWait();
            synchronized (NAME_TWO) {
            }
        }
        synchronized (NAME_TWO) {
            toWait();
            synchronized (NAME_ONE) {
            }
        }
    }

    public  static  void toWait() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Ждемс...");
    }
}