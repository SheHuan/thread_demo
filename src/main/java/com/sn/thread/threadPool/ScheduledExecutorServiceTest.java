package com.sn.thread.threadPool;

import java.util.concurrent.*;

public class ScheduledExecutorServiceTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);

//        ScheduledFuture<?> future = executor.schedule(() -> {
//            System.out.println("hello");
//        }, 2, TimeUnit.SECONDS);
//        System.out.println(future.cancel(true));

//        ScheduledFuture<Integer> future = executor.schedule(() -> 2, 2, TimeUnit.SECONDS);
//        System.out.println(future.get());

//        executor.scheduleAtFixedRate(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(5);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("hello" + System.currentTimeMillis());
//        }, 2, 2, TimeUnit.SECONDS);

        executor.scheduleWithFixedDelay(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("hello" + System.currentTimeMillis());
        }, 2, 2, TimeUnit.SECONDS);
    }
}
