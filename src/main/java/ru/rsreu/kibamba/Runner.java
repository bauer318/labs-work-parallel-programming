package ru.rsreu.kibamba;

import ru.rsreu.kibamba.labswork03.RectangleMethodIntegralCalculator;

public class Runner {
    private static final double ERROR_RATE = 1E-8;
    private static final double A = 0.0;
    private static final double B = 1.0;

    public static void main(String[] args) {
        Thread integralThread = new Thread(()->{
                long startTime = System.currentTimeMillis();
                RectangleMethodIntegralCalculator rectangleMethodIntegralCalculator = new RectangleMethodIntegralCalculator(ERROR_RATE);
                double result = rectangleMethodIntegralCalculator.calculateIntegral(A,B,x->{
                    return Math.sin(x) * x;
                });
                System.out.println("Result :"+result);
                System.out.println("Time : " + (System.currentTimeMillis() - startTime) + " ms");
        });
        integralThread.start();
    }
}
