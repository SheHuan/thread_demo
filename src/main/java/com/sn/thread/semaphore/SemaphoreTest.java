package com.sn.thread.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreTest {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " in");
                    semaphore.acquire();
//                    semaphore.tryAcquire(1,TimeUnit.SECONDS);
                    System.out.println(Thread.currentThread().getName() + "  get the semaphore");
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                    System.out.println(Thread.currentThread().getName() + " out");
                }
            }).start();
        }
    }
}
