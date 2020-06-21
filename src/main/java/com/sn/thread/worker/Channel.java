package com.sn.thread.worker;

public class Channel {
    private static final int MAX_REQUEST = 100;

    private final Request[] requestQueue;

    private int head;

    private int tail;

    private int count;

    private final WorkerThread[] workerPool;

    public Channel(int workers) {
        requestQueue = new Request[MAX_REQUEST];
        head = 0;
        tail = 0;
        count = 0;
        workerPool = new WorkerThread[workers];

        init();
    }

    private void init() {
        for (int i = 0; i < workerPool.length; i++) {
            workerPool[i] = new WorkerThread("worker-" + i, this);
        }
    }

    public void startWorker() {
        for (WorkerThread workerThread : workerPool) {
            workerThread.start();
        }
    }

    public synchronized void put(Request request) {
        while (count >= requestQueue.length) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        requestQueue[tail] = request;
        tail = (tail + 1) % requestQueue.length;
        count++;
        notifyAll();
    }

    public synchronized Request take() {
        while (count <= 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Request request = requestQueue[head];
        head = (head + 1) % requestQueue.length;
        count--;
        notifyAll();

        return request;
    }
}
