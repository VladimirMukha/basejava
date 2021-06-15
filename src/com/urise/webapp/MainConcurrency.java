package com.urise.webapp;

import java.util.concurrent.*;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private int counter;
    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
                //   throw new IllegalStateException();
            }
        };
        thread0.start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
            }

            private void inc() {
                synchronized (this) {
//                    counter++;
                }
            }

        }).start();

        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        ExecutorService executorService = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);
        for (int y = 0; y < 45; y++) {



                for (int i = 0; i < THREADS_NUMBER; i++) {
                    Future<Integer> count = executorService.submit(() -> {
                    for (int j = 0; j < 100; j++) {
                        mainConcurrency.inc();
                    }
                    latch.countDown();
                    return 5;
                });

            }
        }
        latch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();
        System.out.println();
            System.out.println(mainConcurrency.counter);

    }
        private synchronized void inc () {
            counter++;
        }
    }
