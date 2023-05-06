package com.zoltanbalazs.PTI._02._01;

public class Point {
    double x, y;

    void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    void mirror(double cx, double cy) {
        this.x = 2 * cx - this.x;
        this.y = 2 * cy - this.y;
    }

    void mirror(Point p) {
        this.x = 2 * p.x - this.x;
        this.y = 2 * p.y - this.y;
    }

    double distance(Point p) {
        double dx = this.x - p.x;
        double dy = this.y - p.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
