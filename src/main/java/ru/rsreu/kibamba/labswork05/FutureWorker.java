package ru.rsreu.kibamba.labswork05;

import ru.rsreu.kibamba.labswork03.RectangleMethodIntegralCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

public class FutureWorker {
    private static final double A = 0;
    private static final double B = 1;
    private static final int TASK_WAITING_TIME = 5;
    private static final double ERROR_RATE = 1E-6;

    public double doTask(int threadNum){
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        double a = A;
        double b = B;
        double h = (B-A)/threadNum;
        List<Future<Double>> futureList = new ArrayList<>();
        for (int i=0; i<threadNum;i++){
            double currentA = a;
            double currentB = b;
            Future<Double> future = executorService.submit(()->solveIntegral(currentA,currentB));
            futureList.add(future);
            a = b;
            b+=h;
        }
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
    private double solveIntegral(double a, double b){
        RectangleMethodIntegralCalculator rectangleMethodIntegralCalculator = new RectangleMethodIntegralCalculator(ERROR_RATE);
        double result = rectangleMethodIntegralCalculator.calculateIntegral(a,b,x->{
            return Math.sin(x) * x;
        });
        return result;
    }
}
