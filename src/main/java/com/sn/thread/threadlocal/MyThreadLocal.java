package com.sn.thread.threadlocal;


import java.util.HashMap;
import java.util.Map;

public class MyThreadLocal<T> {
    private final Map<Thread, T> storage = new HashMap<>();

    public void set(T value) {
        synchronized (this) {
            Thread key = Thread.currentThread();
            storage.put(key, value);
        }
    }

    public T get() {
        synchronized (this) {
            Thread key = Thread.currentThread();
            T value = storage.get(key);
            if (value == null) {
                value = initialValue();
            }
            return value;
        }
    }

    public T initialValue() {
        return null;
    }

}
