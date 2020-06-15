package com.sn.thread.lock2;

public class ReaderWorker extends Thread {
    private final SharedData data;

    public ReaderWorker(SharedData data) {
        this.data = data;
    }

    @Override
    public void run() {
        try {
            while (true) {
                char[] buffer = data.read();
                System.out.println(Thread.currentThread().getName() + " reads " + String.valueOf(buffer));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
