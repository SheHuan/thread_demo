package com.sn.thread.condition;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest {
    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();
    private static int data = 0;
    private static volatile boolean noUse = true;

    public static void main(String[] args) {
        new Thread(()->{
            for (;;){
                buildData();
            }
        }).start();

        new Thread(()->{
            for (;;){
                useData();
            }
        }).start();
    }

    public static void buildData() {
        try {
            lock.lock();
            while (noUse) {
                condition.await();
            }
            TimeUnit.SECONDS.sleep(2);
            data++;
            System.out.println("p->" + data);
            noUse = true;
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void useData(){
        try {
            lock.lock();
            while (!noUse) {
                condition.await();
            }
            TimeUnit.SECONDS.sleep(2);
            System.out.println("c->" + data);
            noUse = false;
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
