package com.sn.thread.compare_and_set_lock;


import java.util.stream.IntStream;

public class LockTest {
    private static final CompareAndSetLock lock = new CompareAndSetLock();

    public static void main(String[] args) {
        IntStream.range(0, 10).forEach(i -> {
            new Thread(LockTest::doSomething).start();
        });
    }

    public static void doSomething() {
        try {
            lock.tryLock();
            System.out.println(Thread.currentThread().getName() + " get the lock ");
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
