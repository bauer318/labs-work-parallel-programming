package ru.rsreu.kibamba.labswork08;

public interface CustomSemaphore {
    void acquire() throws InterruptedException;
    boolean tryAcquire();
    void release();
}
