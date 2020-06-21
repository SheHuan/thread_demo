package com.sn.thread.worker;

import java.util.Random;

public class TransportThread extends Thread {
    private static final Random random = new Random(System.currentTimeMillis());

    private final Channel channel;

    public TransportThread(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }

    @Override
    public void run() {
            for (int i = 0; true; i++) {
                Request request = new Request(getName(), i);
                this.channel.put(request);
                try {
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }
}
