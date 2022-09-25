package ru.rsreu.kibamba.labswork02;

public class RectangleMethodIntegralCalculator {
    private double errorRate;

    public RectangleMethodIntegralCalculator(double errorRate) {
        this.errorRate = errorRate;
    }

    public int getSegmentNumber(double a, double b) {
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
        int segmentNumber = getSegmentNumber(a,b);
        double h = (b-a)/segmentNumber;
        for(double i = a; i<=(b-h);i+=h){
            result += h*function.f((i+i+h)/2);
        }
        return result;
    }
}
