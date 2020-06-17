package com.sn.thread.countdown;

import java.util.Random;
import java.util.stream.IntStream;

public class CountDownTest {
    public static void main(String[] args) {
        Random random = new Random(System.currentTimeMillis());
        CountDown countDown = new CountDown(5);
        System.out.println("first step......");
        IntStream.rangeClosed(1, 5).forEach(i -> {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " is working");
                try {
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDown.countDown();
            }, String.valueOf(i)).start();
        });
        countDown.await();
        System.out.println("second step......");
    }
}
