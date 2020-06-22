package com.sn.thread.active_object;

public class SchedulerThread extends Thread {
    private final ActivationQueue activationQueue;

    public SchedulerThread(ActivationQueue activationQueue) {
        this.activationQueue = activationQueue;
    }

    public void invoke(MethodRequest request) {
        activationQueue.put(request);
    }

    @Override
    public void run() {
        while (true) {
            activationQueue.take().execute();
        }
    }
}
