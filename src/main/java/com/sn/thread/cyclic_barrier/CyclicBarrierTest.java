package com.sn.thread.cyclic_barrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierTest {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {
            System.out.println("all finished");
        });

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("t1 finished");
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(4);
                System.out.println("t2 finished");
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
