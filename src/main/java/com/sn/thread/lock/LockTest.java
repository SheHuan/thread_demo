package com.sn.thread.lock;

import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

public class LockTest {
    public static void main(String[] args) {
        BooleanLock booleanLock = new BooleanLock();
        Stream.of("T1", "T2", "T3").forEach(name -> {
            new Thread(() -> {
                try {
                    booleanLock.lock(1_000);

                    System.out.println(Thread.currentThread().getName() + " get the lock monitor");

                    System.out.println(Thread.currentThread().getName() + " is working");
                    Thread.sleep(5_000);
                    System.out.println(Thread.currentThread().getName() + " finished");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    System.out.println(e.getMessage());
                } finally {
                    booleanLock.unlock();
                }
            }, name).start();
        });
    }
}
