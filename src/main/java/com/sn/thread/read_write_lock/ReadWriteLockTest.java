package com.sn.thread.read_write_lock;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {
    private static final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
    private static final Lock readLock = readWriteLock.readLock();
    private static final Lock writeLock = readWriteLock.writeLock();

    private static final ArrayList<Long> list = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        new Thread(ReadWriteLockTest::write).start();
        TimeUnit.SECONDS.sleep(1);
        new Thread(ReadWriteLockTest::read).start();
    }

    private static void write() {
        try {
            writeLock.lock();
            TimeUnit.SECONDS.sleep(3);
            list.add(System.currentTimeMillis());
            System.out.println("write end");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }

    private static void read() {
        try {
            readLock.lock();
            TimeUnit.SECONDS.sleep(3);
            list.forEach(System.out::println);
            System.out.println("read end");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
        }
    }
}
