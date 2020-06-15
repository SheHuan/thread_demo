package com.sn.thread.volatile_test;

public class VolatileTest2 {
    private static int INIT_VALUE = 0;
    private static final int LIMIT_VALUE = 500;

    public static void main(String[] args) {
        new Thread(() -> {
            while (INIT_VALUE < LIMIT_VALUE) {
                System.out.println("t1：" + ++INIT_VALUE);
            }
        }).start();

        new Thread(() -> {
            while (INIT_VALUE < LIMIT_VALUE) {
                System.out.println("t2：" + ++INIT_VALUE);
            }
        }).start();
    }
}
