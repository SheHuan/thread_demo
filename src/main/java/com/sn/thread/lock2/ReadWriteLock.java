package com.sn.thread.lock2;

public class ReadWriteLock {
    // 正在读的线程个数
    private int readingReaders = 0;
    // 等待读的线程个数
    private int waitingReaders = 0;
    // 正在写的线程个数
    private int writingWrites = 0;
    // 等待写的线程个数
    private int waitingWrites = 0;
    private boolean preferWriter = true;

    public ReadWriteLock() {
        this(true);
    }

    public ReadWriteLock(boolean preferWriter) {
        this.preferWriter = preferWriter;
    }

    public synchronized void readLock() throws InterruptedException {
        waitingReaders++;
        try {
            while (writingWrites > 0 || (preferWriter && writingWrites > 0)) {
                this.wait();
            }
            readingReaders++;
        } finally {
            waitingReaders--;
        }

    }

    public synchronized void readUnLock() {
        readingReaders--;
        this.notifyAll();
    }

    public synchronized void writeLock() throws InterruptedException {
        waitingWrites++;
        try {
            while (readingReaders > 0 || writingWrites > 0) {
                this.wait();
            }
            writingWrites++;
        } finally {
            waitingWrites--;
        }
    }

    public synchronized void writeUnLock() {
        writingWrites--;
        this.notifyAll();
    }
}
