package ru.rsreu.kibamba.labswork04;

import java.util.HashMap;
import java.util.Map;

public class TaskWorker {
    private static final long PATIENCE = 1000 * 60 * 60;
    private final Map<Integer, Thread> threadMap = new HashMap<>();
    private int threadIdCounter = 0;

    private boolean isThreadNotExist(Thread thread) {
        return !thread.isAlive() || thread == null || thread.isInterrupted();
    }

    public int start(Runnable task) {
        Thread t = new Thread(task);
        int currentThreadId = ++threadIdCounter;
        t.start();
        threadMap.put(currentThreadId, t);
        System.out.println("Task <" + currentThreadId + "> started ");
        return currentThreadId;
    }

    public void stop(int localId) {
        Thread t = threadMap.get(localId);
        if (isThreadNotExist(t)) {
            System.out.println("Task <" + localId + "> doesn't exit");
            return;
        }
        t.interrupt();
        System.out.println("Task <" + localId + "> has been interrupted");
    }

    public void await(int localId) throws InterruptedException {
        Thread t = threadMap.get(localId);
        if (isThreadNotExist(t)) {
            System.out.println("Task <" + localId + "> doesn't exit");
            return;
        }
        System.out.println("Waiting for task <" + localId + "> to finish.");
        t.join(PATIENCE);
        if (t.isAlive()) {
            System.out.println("Tired of waiting");
            t.interrupt();
            t.join();
        }

    }

    public void exit() {
        threadMap.forEach((threadID, t) -> {
            if (t.isAlive()) {
                t.interrupt();
                System.out.println("Task <" + threadID + "> has been interrupted");
            }
        });
    }


}
