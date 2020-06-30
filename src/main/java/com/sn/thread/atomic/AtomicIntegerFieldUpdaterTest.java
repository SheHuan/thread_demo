package com.sn.thread.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdaterTest {
    public static void main(String[] args) {
        AtomicIntegerFieldUpdater<Simple> updater = AtomicIntegerFieldUpdater.newUpdater(Simple.class, "i");
        Simple simple = new Simple();
        boolean result = updater.compareAndSet(simple, 0, 1);
        System.out.println(result);
        System.out.println(updater.get(simple));
    }

    static class Simple {
        volatile int i;
    }
}
