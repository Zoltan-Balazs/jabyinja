package com.zoltanbalazs.PTI._11._02;

public class PrintablePoint extends Point implements Printable {
    public PrintablePoint(int x, int y) {
        super(x, y);
    }

    public void print() {
        System.out.println("(" + getX() + ", " + getY() + ")");
    }
}
