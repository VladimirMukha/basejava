package com.urise.webapp;

import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private int counter;
    private static final Object LOCK = new Object();
    private static final Lock LOCKS = new ReentrantLock();
    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);
    ThreadLocal<SimpleDateFormat> formatThreadLocal = new ThreadLocal<>();


    public static void main(String[] args) throws InterruptedException {
        System.out.println(ThreadLocal.withInitial(() -> new SimpleDateFormat("dd.MM.yyyy")));
        final MainConcurrency mainConcurrency = new MainConcurrency();
        ExecutorService executorService = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            executorService.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
                latch.countDown();
            });
        }
        latch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();
        System.out.println();
        System.out.println(mainConcurrency.counter);
        System.out.println(ATOMIC_INTEGER);
    }

    private void inc() {
        //   LOCKS.lock();
        //  try {
//            counter++;
        ATOMIC_INTEGER.incrementAndGet();
        //  } finally {
        //     LOCKS.unlock();
        // }
    }
}