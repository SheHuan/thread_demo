package com.sn.thread.lock2;

public class SharedData {
    private final char[] buffer;

    private final ReadWriteLock lock = new ReadWriteLock();

    public SharedData(int size) {
        buffer = new char[size];
        for (int i = 0; i < size; i++) {
            buffer[i] = '*';
        }
    }

    public char[] read() throws InterruptedException {
        try {
            lock.readLock();
            return doRead();
        } finally {
            lock.readUnLock();
        }
    }

    public void write(char c) throws InterruptedException {
        try {
            lock.writeLock();
            doWrite(c);
        } finally {
            lock.writeUnLock();
        }
    }

    private void doWrite(char c) {
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = c;
            slowly(10);
        }
    }

    private char[] doRead() {
        char[] newBuffer = new char[buffer.length];
        for (int i = 0; i < buffer.length; i++) {
            newBuffer[i] = buffer[i];
        }
        slowly(50);
        return newBuffer;
    }

    private void slowly(int mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
