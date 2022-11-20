package ru.rsreu.kibamba.labswork08;

public class OwnSemaphore implements CustomSemaphore{

    private final Object locker = new Object();
    private int permits;
    public OwnSemaphore(int permits){
        this.permits = permits;
    }

    @Override
    public void acquire() throws InterruptedException {
        synchronized (locker){
            while (permits<=0){
                locker.wait();
            }
            permits--;
        }
    }
    @Override
    public boolean tryAcquire() {
        synchronized (locker){
            if(permits>0){
                permits--;
                return true;
            }
        }
        return false;
    }
    @Override
    public void release() {
        synchronized (locker){
            permits++;
            locker.notifyAll();
        }
    }
}
