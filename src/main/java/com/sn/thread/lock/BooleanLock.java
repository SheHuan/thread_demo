package com.sn.thread.lock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeoutException;

public class BooleanLock implements Lock {
    // true：锁已经被占用；false：锁空闲
    private boolean initValue;

    private Collection<Thread> blockedThreadCollection = new ArrayList<>();

    private Thread currentThread;

    public BooleanLock() {
        this.initValue = false;
    }

    @Override
    public synchronized void lock() throws InterruptedException {
        while (initValue) {
            blockedThreadCollection.add(Thread.currentThread());
            this.wait();
        }
        blockedThreadCollection.remove(Thread.currentThread());
        this.initValue = true;
        currentThread = Thread.currentThread();
    }

    @Override
    public synchronized void lock(long mills) throws InterruptedException, TimeoutException {
        if (mills <= 0) {
            lock();
            return;
        }
        long remainTime = mills;
        long endTime = System.currentTimeMillis() + mills;
        while (initValue) {
            if (remainTime <= 0) {
                throw new TimeoutException(Thread.currentThread() + " time out");
            }
            blockedThreadCollection.add(Thread.currentThread());
            this.wait(mills);
            remainTime = endTime - System.currentTimeMillis();
        }
        blockedThreadCollection.remove(Thread.currentThread());
        this.initValue = true;
        currentThread = Thread.currentThread();
    }

    @Override
    public synchronized void unlock() {
        if (Thread.currentThread() == currentThread) {
            this.initValue = false;
            System.out.println(Thread.currentThread().getName() + " release the lock monitor");
            this.notifyAll();
        }
    }

    @Override
    public Collection<Thread> getBlockedThread() {
        return Collections.unmodifiableCollection(blockedThreadCollection);
    }

    @Override
    public int getBlockedSize() {
        return blockedThreadCollection.size();
    }
}
