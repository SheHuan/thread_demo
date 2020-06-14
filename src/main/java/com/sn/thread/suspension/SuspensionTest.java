package com.sn.thread.suspension;

public class SuspensionTest {
    public static void main(String[] args) {
        RequestQueue queue = new RequestQueue();

        new ClientThread(queue, "hello").start();
        ServerThread serverThread = new ServerThread(queue);
        serverThread.start();

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        serverThread.close();
    }
}
