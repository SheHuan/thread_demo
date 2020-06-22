package com.sn.thread.active_object;

public final class ActiveObjectFactory {
    private ActiveObjectFactory() {
    }

    public static ActiveObject createActiveObject() {
        Servant servant = new Servant();
        ActivationQueue queue = new ActivationQueue();
        SchedulerThread schedulerThread = new SchedulerThread(queue);
        ActiveObjectProxy proxy = new ActiveObjectProxy(schedulerThread, servant);
        schedulerThread.start();
        return proxy;
    }
}
