package com.sn.thread.worker;

public class WorkerTest {
    public static void main(String[] args) {
        Channel channel = new Channel(5);
        channel.startWorker();

        new TransportThread("A", channel).start();
        new TransportThread("B", channel).start();
        new TransportThread("C", channel).start();

    }
}
