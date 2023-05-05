package com.zoltanbalazs.PTI._02._02;

public class Circle {
    private double x, y, radius;

    Circle(double x, double y, double radius) {
        this.x = x;
        this.y = y;
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
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getArea() {
        return Math.PI * this.radius * this.radius;
    }
}
