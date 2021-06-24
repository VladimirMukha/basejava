package com.urise.webapp;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

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
        List<Integer> listInt = Arrays.asList(3, 2, 4, 2, 1);
        int[] integerNumber = {1, 2, 3, 3, 2, 3};
        MainConcurrency concurrency = new MainConcurrency();
        System.out.println(concurrency.oddOrEven(listInt));
        System.out.println(concurrency.minValue(integerNumber));
    }

    private int minValue(int[] values) {
        List<Integer> result = Arrays.stream(values).distinct().boxed().sorted().collect(Collectors.toList());
        return Integer.valueOf(result.stream().map(Objects::toString).collect(Collectors.joining()));
    }

    private List<Integer> oddOrEven(List<Integer> integers) {
        return integers.stream().filter(integers.stream()
                .mapToInt(Integer::intValue)
                .sum() % 2 != 0 ? n -> n % 2 == 0 : n -> n % 2 == 1)
                .collect(Collectors.toList());
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