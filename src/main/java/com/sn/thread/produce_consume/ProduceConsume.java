package com.sn.thread.produce_consume;

public class ProduceConsume {
    private final Object LOCK = new Object();
    private volatile boolean isProduced = false;
    private int i = 0;

    public void produce() {
        synchronized (LOCK) {
            if (isProduced) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("xxxxx");
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
                System.out.println("produce--->" + i);
                LOCK.notify();
                isProduced = true;
            }
        }

    }

    public void consume() {
        synchronized (LOCK) {
            if (isProduced) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("consume--->" + i);
                LOCK.notify();
                isProduced = false;
            } else {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
