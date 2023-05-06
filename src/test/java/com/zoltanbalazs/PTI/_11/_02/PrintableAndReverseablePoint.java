package com.zoltanbalazs.PTI._11._02;

public class PrintableAndReverseablePoint extends Point implements Printable, Reversable {
    private int prevX;
    private int prevY;

    public PrintableAndReverseablePoint(int x, int y) {
        super(x, y);
        prevX = 0;
        prevY = 0;
    }

    @Override
    public void setX(int x) {
        prevX = getX();
        super.setX(x);
    }

    @Override
    public void setY(int y) {
        prevY = getY();
        super.setY(y);
    }

    public void reverse() {
        super.setX(prevX);
        super.setY(prevY);
    }

    public void print() {
        System.out.println("(" + getX() + ", " + getY() + ")");
    }
}
