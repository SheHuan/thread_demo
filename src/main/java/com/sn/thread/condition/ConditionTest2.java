package com.sn.thread.condition;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class ConditionTest2 {
    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition PRODUCE_CONDITION = lock.newCondition();
    private static final Condition CONSUME_CONDITION = lock.newCondition();
    private static final LinkedList<Long> POOL = new LinkedList<>();
    private static final int MAX_SIZE = 100;

    public static void main(String[] args) {
        IntStream.range(0, 6).forEach(ConditionTest2::beginProduce);
        IntStream.range(0, 13).forEach(ConditionTest2::beginConsume);
    }

    private static void beginProduce(int i) {
        new Thread(() -> {
            for (; ; ) {
                produce();
                sleep(2);
            }
        }, "P" + i).start();
    }

    private static void beginConsume(int i) {
        new Thread(() -> {
            for (; ; ) {
                consume();
                sleep(3);
            }
        }, "C" + i).start();
    }

    public static void produce() {
        try {
            lock.lock();
            while (POOL.size() >= MAX_SIZE) {
                PRODUCE_CONDITION.await();
            }
            long value = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + "-P->" + value);
            POOL.addLast(value);
            CONSUME_CONDITION.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void consume() {
        try {
            lock.lock();
            while (POOL.isEmpty()) {
                CONSUME_CONDITION.await();
            }
            long value = POOL.removeFirst();
            System.out.println(Thread.currentThread().getName() + "-C->" + value);
            PRODUCE_CONDITION.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private static void sleep(long timeMills) {
        try {
            TimeUnit.SECONDS.sleep(timeMills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
