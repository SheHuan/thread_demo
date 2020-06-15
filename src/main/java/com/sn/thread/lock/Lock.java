package com.sn.thread.lock;

import java.util.Collection;
import java.util.concurrent.TimeoutException;

public interface Lock {
    void  lock() throws InterruptedException;

    void  lock(long mills) throws InterruptedException, TimeoutException;

    void unlock();

    Collection<Thread> getBlockedThread();

    int getBlockedSize();
}
