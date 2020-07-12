package com.sn.thread.phaser;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class PhaserTest2 {
    private static final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        Phaser phaser = new Phaser(5);
        IntStream.rangeClosed(1, 5).forEach(i -> {
            new Athletes(i, phaser).start();
        });
    }

    private static class Athletes extends Thread {
        private Phaser phaser;
        private int num;

        public Athletes(int num, Phaser phaser) {
            this.num = num;
            this.phaser = phaser;
        }

        @Override
        public void run() {
            try {
                System.out.println(num + " start running");
                TimeUnit.SECONDS.sleep(random.nextInt(5));
                System.out.println(num + " end running");
                phaser.arriveAndAwaitAdvance();

                System.out.println(num + " start bicycle");
                TimeUnit.SECONDS.sleep(random.nextInt(5));
                System.out.println(num + " end bicycle");
                phaser.arriveAndAwaitAdvance();

                System.out.println(num + " start swimming");
                TimeUnit.SECONDS.sleep(random.nextInt(5));
                System.out.println(num + " end swimming");
                phaser.arriveAndAwaitAdvance();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
