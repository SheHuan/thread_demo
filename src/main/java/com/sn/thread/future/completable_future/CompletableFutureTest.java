package com.sn.thread.future.completable_future;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CompletableFutureTest {
    public static void main(String[] args) throws InterruptedException {
        test3();
    }

    private static void test() throws InterruptedException {
        CompletableFuture.runAsync(() -> {
            try {
                System.out.println("start");
                TimeUnit.SECONDS.sleep(2);
                System.out.println("end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).whenComplete((v, t) -> {
            System.out.println("done");
        });
        Thread.currentThread().join();
    }

    private static void test2() throws InterruptedException {
        List<Callable<Integer>> tasks = IntStream.range(0, 10).boxed().map(i -> (Callable<Integer>) () -> get()).collect(Collectors.toList());
        ExecutorService service = Executors.newCachedThreadPool();
        service.invokeAll(tasks).stream().map(future -> {
            try {
                return future.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).parallel().forEach(CompletableFutureTest::show);
    }

    private static int get() {
        int value = ThreadLocalRandom.current().nextInt(10);
        try {
            System.out.println(Thread.currentThread().getName() + " will sleep " + value);
            TimeUnit.SECONDS.sleep(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " sleep finish");
        return value;
    }

    private static void show(int value) {
        System.out.println(Thread.currentThread().getName() + " show value " + value);
    }

    private static void test3() throws InterruptedException {
        IntStream.range(0, 10).boxed().forEach(i ->
                CompletableFuture.supplyAsync(CompletableFutureTest::get)
                        .thenAccept(CompletableFutureTest::show)
                        .whenComplete((v, t) -> System.out.println(i + " is done")));
        Thread.currentThread().join();

    }
}
