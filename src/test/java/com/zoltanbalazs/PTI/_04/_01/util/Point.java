package com.zoltanbalazs.PTI._04._01.util;

public class Point {
    private double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    public void mirror(double cx, double cy) {
        this.x = 2 * cx - this.x;
        this.y = 2 * cy - this.y;
    }

    public void mirror(Point p) {
        this.x = 2 * p.x - this.x;
        this.y = 2 * p.y - this.y;
    }

    public double distance(Point p) {
        double dx = this.x - p.x;
        double dy = this.y - p.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public static Point centerOfMass(Point[] arr) {
        double sumX = 0;
        double sumY = 0;

        for (int i = 0; i < arr.length; ++i) {
            sumX += arr[i].getX();
            sumY += arr[i].getY();
        }

        return new Point(sumX / arr.length, sumY / arr.length);
    }

    @Override
    public String toString() {
        return "(" + this.getX() + ", " + this.getY() + ")";
    }
}
