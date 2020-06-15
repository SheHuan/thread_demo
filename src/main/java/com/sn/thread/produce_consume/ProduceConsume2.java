package com.sn.thread.produce_consume;

public class ProduceConsume2 {
    private final Object LOCK = new Object();
    private volatile boolean isProduced = false;
    private int i = 0;

    public void produce() {
        synchronized (LOCK) {
            while (isProduced) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            i++;
            System.out.println("produce--->" + i);
            LOCK.notifyAll();
            isProduced = true;

        }

    }

    public void consume() {
        synchronized (LOCK) {
            while (!isProduced) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("consume--->" + i);
            LOCK.notifyAll();
            isProduced = false;
        }
    }
}
