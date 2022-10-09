package ru.rsreu.kibamba.labswork03;

public class RectangleMethodIntegralCalculator {
    private double errorRate;

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

    public double calculateIntegral(double a, double b, Function function) throws InterruptedException {
        if (a > b) {
            return 0;
        }
        double result = 0.0;
        int n = getN(a, b);
        double h = (b - a) / n;
        int it = 0;
        for (double i = a; i <= (b - h); i += h) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException(Thread.currentThread().getName() + " been interrupted");
            }
            result += h * function.f((i + i + h) / 2);
        }
        return result;
    }
}
