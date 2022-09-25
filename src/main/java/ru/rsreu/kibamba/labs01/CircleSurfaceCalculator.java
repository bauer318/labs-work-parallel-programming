package ru.rsreu.kibamba.labs01;

public class CircleSurfaceCalculator {

    public double calculateCircleSurface(double radius) {
        if(radius<0){
            throw new IllegalArgumentException("Отрицательное значение радиуса");
        }
        return Math.pow(radius, 2) * Math.PI;
    }

}
