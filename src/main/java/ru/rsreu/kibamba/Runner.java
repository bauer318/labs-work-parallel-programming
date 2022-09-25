package ru.rsreu.kibamba;

import ru.rsreu.kibamba.labs01.CircleSurfaceCalculator;
import ru.rsreu.kibamba.labswork02.RectangleMethodIntegralCalculator;

import java.io.FileNotFoundException;

public class Runner {
    private static final double ERROR_RATE = 1E-2;
    private static final double A = 0.0;
    private static final double B = 1.0;

    public static void main(String[] args) {
        /*CircleSurfaceCalculator circleSurfaceCalculator = new CircleSurfaceCalculator();
        System.out.println(circleSurfaceCalculator.calculateCircleSurface(25));*/
        long startTime = System.currentTimeMillis();
        RectangleMethodIntegralCalculator rectangleMethodIntegralCalculator = new RectangleMethodIntegralCalculator(ERROR_RATE);
        System.out.println(rectangleMethodIntegralCalculator.calculateIntegral(A, B, x -> {
            return Math.sin(x) * x;
        }));
        System.out.println("Time : " + (System.currentTimeMillis() - startTime) + " ms");
    }
}
