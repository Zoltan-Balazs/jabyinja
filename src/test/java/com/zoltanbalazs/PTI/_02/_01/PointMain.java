package com.zoltanbalazs.PTI._02._01;

public class PointMain {
    public static void main(String[] args) {
        Point p1 = new Point();
        p1.x = 5;
        p1.y = 0;

        System.out.println("p1: (" + p1.x + ", " + p1.y + ")");

        p1.move(1, 0);
        System.out.println("p1: (" + p1.x + ", " + p1.y + ")");

        p1.mirror(0, 0);
        System.out.println("p1: (" + p1.x + ", " + p1.y + ")");

        Point p2 = new Point();
        p2.x = 0;
        p2.y = 0;

        System.out.println(p1.distance(p2));
    }
}
