package com.sn.thread.active_object;

import java.util.LinkedList;

public class ActivationQueue {
    private static final int MAX_METHOD_REQUEST_QUEUE_SIZE = 100;

    private final LinkedList<MethodRequest> methodQueue = new LinkedList<>();

    public ActivationQueue() {
    }

    public synchronized void put(MethodRequest request) {
        while (methodQueue.size() >= MAX_METHOD_REQUEST_QUEUE_SIZE) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        methodQueue.addLast(request);
        notifyAll();
    }

    public synchronized MethodRequest take() {
        while (methodQueue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        MethodRequest request = methodQueue.removeFirst();
        notifyAll();
        return request;
    }
}
