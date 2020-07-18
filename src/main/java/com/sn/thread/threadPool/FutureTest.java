package com.sn.thread.threadPool;


import java.util.concurrent.*;

public class FutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        test3();
    }

    public static void test() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Integer> future = executorService.submit(() -> {
            TimeUnit.SECONDS.sleep(2);
            return 10;
        });

        System.out.println("------------------------------");
        System.out.println(future.get());
    }

    public static void test2() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                Thread t = new Thread(runnable);
                t.setDaemon(true);
                return t;
            }
        });
        Future<Integer> future = executorService.submit(() -> {
            System.out.println("start");
            TimeUnit.SECONDS.sleep(10);
            System.out.println("end");
            return 10;
        });
        TimeUnit.SECONDS.sleep(2);
        System.out.println(future.cancel(true));
    }

    public static void test3() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Integer> future = executorService.submit(() -> {
            System.out.println("start");
            while (!Thread.interrupted()) {

            }
            System.out.println("end");
            return 10;
        });
        TimeUnit.SECONDS.sleep(2);
        System.out.println(future.cancel(true));
    }
}
