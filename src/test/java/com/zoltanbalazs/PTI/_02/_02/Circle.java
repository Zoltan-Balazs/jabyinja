package com.zoltanbalazs.PTI._02._02;

public class Circle {
    private double radius;

    Circle(double x, double y, double radius) {
        if (radius < 0) {
            throw new IllegalArgumentException("Non-positive radius!");
        } else {
            this.radius = radius;
        }
    }

    void enlarge(double f) {
        this.radius *= f;
    }

    public void setRadius(double radius) {
        if (radius < 0) {
            throw new IllegalArgumentException("Non-positive radius!");
        } else {
            this.radius = radius;
        }
    }

    public void setX(double x) {
    }

    public void setY(double y) {
    }

    public double getArea() {
        return Math.PI * this.radius * this.radius;
    }
}
