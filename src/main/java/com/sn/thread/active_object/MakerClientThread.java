package com.sn.thread.active_object;

public class MakerClientThread extends Thread {
    private final ActiveObject activeObject;

    private final char fillChar;

    public MakerClientThread(String name, ActiveObject activeObject) {
        super(name);
        this.activeObject = activeObject;
        this.fillChar = name.charAt(0);
    }

    @Override
    public void run() {
        for (int i = 0; true; i++) {
            Result result = activeObject.makeString(i + 1, fillChar);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String value = (String) result.getResultValue();
            System.out.println(Thread.currentThread().getName() + ":" + value);
        }
    }
}
