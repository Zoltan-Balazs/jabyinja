package com.zoltanbalazs.PTI._02._05;

public class LineMain {
    public static void main(String[] args) {
        Line l1 = new Line();
        Line l2 = new Line();
        Line l3 = new Line();

        l1.a = 4;
        l1.b = 1;
        l1.c = 10;

        l2.a = -4;
        l2.b = -1;
        l2.c = 2;

        l3.a = -0.25;
        l3.b = 1;
        l3.c = 0.25;

        Point p1 = new Point();
        p1.x = 7;
        p1.y = 2;

        System.out.println(l3.contains(p1));
        System.out.println(l1.isParallelWith(l2));
        System.out.println(l1.isOrthogonalTo(l3));
    }
}
