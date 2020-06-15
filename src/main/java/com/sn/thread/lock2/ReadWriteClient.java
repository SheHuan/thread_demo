package com.sn.thread.lock2;

public class ReadWriteClient {
    public static void main(String[] args) {
        SharedData data = new SharedData(10);
        new ReaderWorker(data).start();
        new ReaderWorker(data).start();
        new ReaderWorker(data).start();
        new ReaderWorker(data).start();
        new ReaderWorker(data).start();

        new WriterWorker(data, "hello").start();
        new WriterWorker(data, "world").start();
    }
}
