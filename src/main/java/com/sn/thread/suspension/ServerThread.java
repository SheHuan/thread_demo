package com.sn.thread.suspension;

import java.util.Random;

public class ServerThread extends Thread {
    private final RequestQueue queue;

    private final Random random;

    private volatile boolean close = false;

    public ServerThread(RequestQueue queue) {
        this.queue = queue;
        random = new Random(System.currentTimeMillis());
    }

    @Override
    public void run() {
        while (!close) {
            Request request = queue.getRequest();
            if (request == null) {
                System.out.println("Received the empty request");
                continue;
            }
            System.out.println("Server -> " + request.getValue());
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void close() {
        this.close = true;
        // queue.getRequest()如果正在wait会被打断，如果正在sleep也会被打断
        this.interrupt();
    }
}
