package com.zoltanbalazs.PTI._02._02;

public class CircleMain {
    public static void main(String[] args) {
        Circle c = new Circle(0, 0, 1);
        System.out.println(c.getArea());

        c.setX(5);
        c.setY(2);
        c.setRadius(10);
        System.out.println(c.getArea());
    }
}
