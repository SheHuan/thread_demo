package com.sn.thread.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

public class ExchangerTest {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start");
            try {
                String result = exchanger.exchange("I am t1");
                System.out.println(Thread.currentThread().getName() + " receive value [" + result + "]");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start");
            try {
                String result = exchanger.exchange("I am t2");
                System.out.println(Thread.currentThread().getName() + " receive value [" + result + "]");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }
}
