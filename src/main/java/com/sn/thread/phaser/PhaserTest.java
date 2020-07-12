package com.sn.thread.phaser;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class PhaserTest {
    private static final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        Phaser phaser = new Phaser();
        IntStream.rangeClosed(1, 5).boxed().map(i -> phaser).forEach(i -> new Task(i).start());

        phaser.register();
        phaser.arriveAndAwaitAdvance();

        System.out.println("All worker finished");
    }

    private static class Task extends Thread {
        private Phaser phaser;

        public Task(Phaser phaser) {
            this.phaser = phaser;
            this.phaser.register();
        }

        @Override
        public void run() {
            System.out.println("The worker [" + getName() + "] is working");
            try {
                TimeUnit.SECONDS.sleep(random.nextInt(5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            phaser.arriveAndAwaitAdvance();
        }
    }
}
