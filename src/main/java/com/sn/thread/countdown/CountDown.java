package com.sn.thread.countdown;

public class CountDown {
    private final int total;

    private int counter = 0;

    public CountDown(int total) {
        this.total = total;
    }

    public void countDown() {
        synchronized (this) {
            this.counter++;
            this.notifyAll();
        }
    }

    public void await() {
        synchronized (this) {
            while (this.counter != total) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
