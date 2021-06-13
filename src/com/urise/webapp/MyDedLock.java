package com.urise.webapp;

public class MyDedLock {
    public static final MyDedLock LOCK_ONE = new MyDedLock();
    public static final MyDedLock LOCK_TWO = new MyDedLock();
    private static int count = 0;

    public static void main(String[] args) {
        new Thread(LOCK_ONE::decrement).start();
        new Thread(LOCK_TWO::increment).start();
    }

    public synchronized void increment() {
        for (int i = 0; i < 100000; i++) {
            count++;
        }
        System.out.println("I waiting for the LOCK-ONE stream...");
        synchronized (LOCK_ONE) {
            System.out.println("Заснул");
        }
    }

    public synchronized void decrement() {
        for (int i = 100000; i > 0; i--) {
            count--;
        }
        System.out.println("Im waiting for the LOCK_TWO  stream...");
        synchronized (LOCK_TWO) {
            System.out.println("Заснул");
        }
    }
}