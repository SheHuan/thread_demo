package com.sn.thread.threadPool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SimpleThreadPool extends Thread {
    private static final int DEFAULT_MIN_SIZE = 3;

    private static final int DEFAULT_ACTIVE_SIZE = 6;

    private static final int DEFAULT_MAX_SIZE = 10;

    private static final int DEFAULT_TASK_QUEUE_SIZE = 100;

    private static final DiscardPolicy DEFAULT_DISCARD_POLICY = () -> {
        throw new DiscardException("discard this task");
    };

    private int size;

    private int min;

    private int active;

    private int max;

    private final int queueSize;

    private static int seq = 0;

    private static final String THREAD_PREFIX = "simple_thread_pool-";

    private static final ThreadGroup GROUP = new ThreadGroup("pool_group");

    private static final LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();

    private static final List<WorkerTask> THREAD_QUEUE = new ArrayList<>();

    private final DiscardPolicy discardPolicy;

    private volatile boolean destroy = false;

    public SimpleThreadPool() {
        this(DEFAULT_MIN_SIZE, DEFAULT_ACTIVE_SIZE, DEFAULT_MAX_SIZE, DEFAULT_TASK_QUEUE_SIZE, DEFAULT_DISCARD_POLICY);
    }

    public SimpleThreadPool(int min, int active, int max, int queueSize, DiscardPolicy discardPolicy) {
        this.min = min;
        this.active = active;
        this.max = max;
        this.queueSize = queueSize;
        this.discardPolicy = discardPolicy;
        init();
    }

    @Override
    public void run() {
        while (!destroy) {
            System.out.printf("Pool#Min:%d,Active:%d,Max:%d,Current:%d,QueueSize:%d\n", this.min, this.active, this.max, this.size, TASK_QUEUE.size());
            try {
                Thread.sleep(5_000);
                if (TASK_QUEUE.size() > active && size < active) {
                    for (int i = size; i < active; i++) {
                        createWorkerTask();
                    }
                    System.out.println("The thread pool incremented");
                    size = active;
                } else if (TASK_QUEUE.size() > max && size < max) {
                    for (int i = size; i < max; i++) {
                        createWorkerTask();
                    }
                    System.out.println("The thread pool incremented");
                    size = max;
                }

                synchronized (THREAD_QUEUE) {
                    if (TASK_QUEUE.isEmpty() && size > active) {
                        for (Iterator<WorkerTask> it = THREAD_QUEUE.iterator(); it.hasNext(); ) {
                            if (size - active <= 0) {
                                break;
                            }
                            WorkerTask workerTask = it.next();
                            if (workerTask.getTaskState() != TaskState.BLOCKED) {
                                continue;
                            }
                            workerTask.close();
                            workerTask.interrupt();
                            it.remove();
                            size--;
                            System.out.println(workerTask.getName() + " closed");
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void init() {
        for (int i = 0; i < min; i++) {
            createWorkerTask();
        }
        size = min;
        this.start();
    }

    private void createWorkerTask() {
        WorkerTask workerTask = new WorkerTask(GROUP, THREAD_PREFIX + (seq++));
        workerTask.start();
        THREAD_QUEUE.add(workerTask);
    }

    public void submit(Runnable runnable) {
        if (destroy) {
            throw new IllegalStateException("The thread pool already disposed");
        }
        synchronized (TASK_QUEUE) {
            if (TASK_QUEUE.size() > queueSize) {
                discardPolicy.discard();
            }
            TASK_QUEUE.addLast(runnable);
            TASK_QUEUE.notifyAll();
        }
    }

    public void shutdown() {
        while (!TASK_QUEUE.isEmpty()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        synchronized (THREAD_QUEUE) {
            int threadQueueSize = THREAD_QUEUE.size();

            while (threadQueueSize > 0) {
                for (WorkerTask workerTask : THREAD_QUEUE) {
                    if (workerTask.getTaskState() == TaskState.BLOCKED) {
                        workerTask.close();
                        workerTask.interrupt();
                        threadQueueSize--;
                    } else {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        this.destroy = true;
        System.out.println("The thread pool destroy");
    }

    public int getSize() {
        return size;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public boolean isDestroy() {
        return destroy;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }

    private enum TaskState {
        FREE, RUNNING, BLOCKED, DEAD
    }

    private static class WorkerTask extends Thread {
        private volatile TaskState taskState = TaskState.FREE;

        public WorkerTask(ThreadGroup group, String name) {
            super(group, name);
        }

        public TaskState getTaskState() {
            return taskState;
        }

        public void close() {
            taskState = TaskState.DEAD;
        }

        @Override
        public void run() {
            OUTER:
            while (taskState != TaskState.DEAD) {
                Runnable runnable;
                synchronized (TASK_QUEUE) {
                    while (TASK_QUEUE.isEmpty()) {
                        try {
                            taskState = TaskState.BLOCKED;
                            TASK_QUEUE.wait();
                        } catch (InterruptedException e) {
                            break OUTER;
                        }
                    }
                    runnable = TASK_QUEUE.removeFirst();
                }
                if (runnable != null) {
                    taskState = TaskState.RUNNING;
                    runnable.run();
                    taskState = TaskState.FREE;
                }
            }
        }
    }

    public static class DiscardException extends RuntimeException {
        public DiscardException(String message) {
            super(message);
        }
    }

    public interface DiscardPolicy {
        void discard() throws DiscardException;
    }
}
