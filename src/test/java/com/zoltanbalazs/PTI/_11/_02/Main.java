package com.zoltanbalazs.PTI._11._02;

public class Main {
    public static void main(String[] args) {
        PrintableAndReverseablePoint prp = new PrintableAndReverseablePoint(5, 6);
        prp.setX(20);
        prp.setY(50);
        prp.print();
        prp.reverse();
        prp.print();
        foo(prp);
    }

    public static void foo(Printable p) {
        p.print();
    }
}
