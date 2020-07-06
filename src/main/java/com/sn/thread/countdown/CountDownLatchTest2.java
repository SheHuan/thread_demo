package com.sn.thread.countdown;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest2 {
    public static void main(String[] args) {
        CountDownLatch countDown = new CountDownLatch(1);
        new Thread(() -> {
            try {
                countDown.await();
                System.out.println("a start");
                Thread.sleep(1000);
                System.out.println("a end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                System.out.println("b start");
                Thread.sleep(3000);
                System.out.println("b end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                countDown.countDown();
            }
        }).start();
    }
}
