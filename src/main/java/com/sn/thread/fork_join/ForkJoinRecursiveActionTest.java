package com.sn.thread.fork_join;

import java.util.Optional;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class ForkJoinRecursiveActionTest {
    private static final int MAX_THRESHOLD = 3;
    private static final AtomicInteger SUM = new AtomicInteger();

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.submit(new CalculateRecursiveAction(1, 10));
        try {
            forkJoinPool.awaitTermination(10, TimeUnit.SECONDS);
            Optional.of(SUM).ifPresent(System.out::println);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class CalculateRecursiveAction extends RecursiveAction {
        private int start;
        private int end;

        public CalculateRecursiveAction(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (end - start < MAX_THRESHOLD) {
                SUM.addAndGet(IntStream.rangeClosed(start, end).sum());
            } else {
                int middle = (start + end) / 2;
                CalculateRecursiveAction leftAction = new CalculateRecursiveAction(start, middle);
                CalculateRecursiveAction rightAction = new CalculateRecursiveAction(middle + 1, end);
                leftAction.fork();
                rightAction.fork();
            }
        }
    }
}
