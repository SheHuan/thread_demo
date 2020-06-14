package com.sn.thread.future;

/**
 * Future 代表未来的一个数据凭据，通过它异步获取结果
 * FutureTask 具体要执行的任务
 * FutureService 桥接Future和FutureTask
 */
public class Test {
    public static void main(String[] args) {
        FutureService futureService = new FutureService();
//        Future<String> future = futureService.submit(() -> {
//            try {
//                Thread.sleep(10000L);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return "finish";
//        });
//        System.out.println(future.get());

        futureService.submit(() -> {
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "finish";
        }, Test::getResult);

        System.out.println("---------------------------");
        System.out.println("do other thing");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("---------------------------");

    }

    private static void getResult(String result) {
        System.out.println(result);
    }
}
