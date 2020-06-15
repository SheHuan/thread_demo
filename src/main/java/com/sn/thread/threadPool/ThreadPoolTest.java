package com.sn.thread.threadPool;

import java.util.stream.IntStream;

public class ThreadPoolTest {
    public static void main(String[] args) {
        SimpleThreadPool pool = new SimpleThreadPool();
        IntStream.range(0, 50).forEach(i -> {
            pool.submit(() -> {
                System.out.println("The runnable " + i + " is serviced by " + Thread.currentThread().getName());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("The runnable " + i + " is serviced by " + Thread.currentThread().getName() + " finished");
            });
        });
        pool.shutdown();
        pool.submit(() -> {
            System.out.println("xxx");
        });
    }
}
