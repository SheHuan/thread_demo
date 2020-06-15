package com.sn.thread;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class CaptureService {
    static final LinkedList<Control> CONTROLS = new LinkedList<>();

    public static void main(String[] args) {
        List<Thread> worker = new ArrayList<>();
        Stream.of("M1", "M2", "M3", "M4", "M5", "M6", "M7", "M8", "M9", "M10")
                .map(CaptureService::createCaptureThread)
                .forEach(t -> {
                    t.start();
                    worker.add(t);
                });
        worker.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Optional.of("All of the capture finished").ifPresent(System.out::println);
    }

    public static Thread createCaptureThread(String name) {
        return new Thread(() -> {
            synchronized (CONTROLS) {
                while (CONTROLS.size() >= 5) {
                    try {
                        CONTROLS.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Optional.of("The worker " + Thread.currentThread().getName() + " begin capture data").ifPresent(System.out::println);
                CONTROLS.addLast(new Control());
            }

            Optional.of("The worker " + Thread.currentThread().getName() + " is working").ifPresent(System.out::println);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (CONTROLS) {
                Optional.of("The worker " + Thread.currentThread().getName() + " finish capture data").ifPresent(System.out::println);
                CONTROLS.removeFirst();
                CONTROLS.notifyAll();
            }
        }, name);
    }

    public static class Control {

    }
}