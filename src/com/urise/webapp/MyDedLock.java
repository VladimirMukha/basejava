package com.urise.webapp;

public class MyDedLock {

    public static void main(String[] args) {
        String lockOne = "lock2";
        String lokTwo = "lock1";
        new Thread(() -> detLock(lockOne, lokTwo)).start();
        new Thread(() -> detLock(lokTwo, lockOne)).start();
    }

    public static void detLock(String lockOne, String lockTwo) {
        synchronized (lockOne) {
            System.out.println(Thread.currentThread().getName() + " Thread captured " + lockOne
                    + " object wait " + lockTwo);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockTwo) {
                System.out.println(Thread.currentThread().getName() + " Thread captured " + lockTwo
                        + " object wait " + lockOne);
            }
        }
    }
}