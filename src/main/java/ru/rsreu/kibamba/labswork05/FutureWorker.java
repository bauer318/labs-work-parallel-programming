package ru.rsreu.kibamba.labswork05;

import ru.rsreu.kibamba.labswork03.RectangleMethodIntegralCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;

public class FutureWorker {
    private static final double A = 0;
    private static final double B = 1;
    private static final int TASK_WAITING_TIME = 5;
    private static final double ERROR_RATE = 1E-8;
    private AtomicInteger progress = new AtomicInteger(0);
    private Semaphore semaphore;
    private  CountDownLatch countDownLatch;
    private ReentrantLock locker;
    private List<Long> taskTimeList = new ArrayList<>();
    private void intSemaphore(int threadNum){
        semaphore = new Semaphore(threadNum);
    }
    private void intCountDownLatch(int threadNum){
        countDownLatch = new CountDownLatch(threadNum);
    }
    private void initLocker(){
        locker = new ReentrantLock();
    }
    public double doTask(int threadNum, BiConsumer<Integer, Integer> progressCallback){
        long startTime = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        double a = A;
        double b = B;
        double h = (B-A)/threadNum;
        List<Future<Double>> futureList = new ArrayList<>();
        progressCallback.accept(progress.get(), threadNum);
        intSemaphore(threadNum);
        intCountDownLatch(threadNum);
        initLocker();
        for (int i=0; i<threadNum;i++){
            double currentA = a;
            double currentB = b;
            Future<Double> future = executorService.submit(()->solveIntegral(currentA,currentB, progressCallback,threadNum));
            futureList.add(future);
            a = b;
            b+=h;
        }
        List<Long> taskDurationList = new ArrayList<>();
        try{
            countDownLatch.await();
            long finalTaskTime = System.currentTimeMillis();
            taskTimeList.forEach(time -> taskDurationList.add(finalTaskTime-time));
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }
        taskDurationList.forEach(duration -> System.out.println("Task's time :"+duration + " ms"));
        AtomicReference<Double> result = new AtomicReference<>((double)0);
        futureList.forEach(future ->{
            try{
                double value = future.get(TASK_WAITING_TIME, TimeUnit.MINUTES);
                double prev = result.get();
                result.set(prev+value);
            }catch (InterruptedException ie) {
                ie.printStackTrace();
            }catch(ExecutionException ex) {
                throw new RuntimeException();
            }catch(TimeoutException timeOutException){
                timeOutException.printStackTrace();
            }catch (CancellationException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
        return result.get();
    }
    private double solveIntegral(double a, double b,BiConsumer<Integer, Integer> progressCallback, int threadNum) throws InterruptedException {
        double result = 0;
        semaphore.acquire();
        try {
            RectangleMethodIntegralCalculator rectangleMethodIntegralCalculator = new RectangleMethodIntegralCalculator(ERROR_RATE);
            result = rectangleMethodIntegralCalculator.calculateIntegral(a,b,x->{
                return Math.sin(x) * x;
            });
        }finally {
            semaphore.release();
            countDownLatch.countDown();
            taskTimeList.add(System.currentTimeMillis());
            if(locker.tryLock()){
                progress.set(progress.get()+1);
                progressCallback.accept(progress.get(), threadNum);
                locker.unlock();
            }
        }
        return result;
    }
}
