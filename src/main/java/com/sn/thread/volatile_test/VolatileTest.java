package com.sn.thread.volatile_test;

public class VolatileTest {
    private volatile static int INIT_VALUE = 0;
    private static final int LIMIT_VALUE = 5;

    public static void main(String[] args) {
        new Thread(() -> {
            int temp = INIT_VALUE;
            while (temp < LIMIT_VALUE) {
                if (temp != INIT_VALUE) {
                    System.out.printf("The value update to [%d]\n", INIT_VALUE);
                    temp = INIT_VALUE;
                }
            }
        }).start();

        new Thread(() -> {
            int temp = INIT_VALUE;
            while (temp < LIMIT_VALUE) {
                System.out.printf("Update the value to [%d]\n", ++temp);
                INIT_VALUE = temp;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
