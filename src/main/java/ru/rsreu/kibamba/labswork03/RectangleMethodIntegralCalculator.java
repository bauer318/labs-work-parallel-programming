package ru.rsreu.kibamba.labswork03;

import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

public class RectangleMethodIntegralCalculator {
    private double errorRate;
    public static double tempPercentWorkDone = 0.0;
    private CountDownLatch countDownLatch;
    private Consumer<Integer> progressCallback = progress -> {
    };

    public RectangleMethodIntegralCalculator(double errorRate) {
        this.errorRate = errorRate;
    }

    public int getN(double a, double b) {
        int result = 1;
        while ((b - a) / result > errorRate) {
            result++;
        }
        return result;
    }
    public double calculateIntegral(double a, double b, Function function){
        if(a>b){
            return 0;
        }
        double result = 0.0;
        int n = getN(a,b);
        double h = (b-a)/n;
        double percentWorkDone;
        int it = 0;
        for(double i = a; i<=(b-h);i+=h){
            result += h*function.f((i+i+h)/2);
            percentWorkDone = Math.round( (((double) it) / n) * 100 );
            if(percentWorkDone-tempPercentWorkDone>=10){
                tempPercentWorkDone = percentWorkDone;
                progressCallback.accept((int)percentWorkDone);
            }
            it++;
        }
        return result;
    }
}
