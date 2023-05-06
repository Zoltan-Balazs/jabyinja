package com.zoltanbalazs.PTI._02._03;

import com.zoltanbalazs.PTI._02._03.utils.Point;

public class Circle {
    private double x, y, radius;

    public Circle(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        if (radius <= 0) {
            throw new IllegalArgumentException("Non-positive radius!");
        }
        this.radius = radius;
    }

    public Point getCenter() {
        return new Point(this.x, this.y);
    }

    public void enlarge(double f) {
        this.radius *= f;
    }

    public void setRadius(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Non-positive radius!");
        }
        this.radius = radius;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
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
