package com.sn.thread.atomic;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceTest {
    public static void main(String[] args) {
        Simple simple = new Simple("xxx");
        AtomicReference<Simple> simpleAtomicReference = new AtomicReference<>(simple);
        System.out.println(simpleAtomicReference.get());
        Boolean result = simpleAtomicReference.compareAndSet(simple, new Simple("yyy"));
        System.out.println(result);
    }

    static class Simple {
        private String name;

        public Simple(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Simple{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
