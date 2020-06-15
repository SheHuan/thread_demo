package com.sn.thread;

import com.sn.thread.produce_consume.ProduceConsume2;

public class Test {
    public static void main(String[] args) {
//        Runtime.getRuntime().addShutdownHook(new Thread(()->{
//            System.out.println("exit.....");
//        }));
        test3();
    }

    public static void test1() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Thread thread2 = new Thread() {
                    @Override
                    public void run() {

                        System.out.println("thread2");
                        try {
                            sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("thread22");
                    }
                };
                thread2.start();
                try {
                    thread2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                System.out.println("thread");
            }
        };

        thread.start();
    }

    public static void test2() {
        ProduceConsume2 pc = new ProduceConsume2();
        try {
            new Thread(() -> {
                while (true) {
                    pc.produce();
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }


        new Thread(() -> {
            while (true) {
                pc.consume();
            }
        }).start();
    }

    public static void test3() {
        Thread t = new Thread(() -> {
            while (true) {
                System.out.println("xxxxxxxxxxx");
                try {
                    Thread.sleep(1000);
                    int s = 1/0;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.setUncaughtExceptionHandler((thread, e) -> {
            System.out.println(e);
        });
        t.start();
    }
}
