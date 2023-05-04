package com.zoltanbalazs;

public class Ownclass {

    public static void main(String[] args) {
        Test t = new Test(94, "Heh");
        System.out.println(t);
        t.SetA(123123);
        System.out.println(t);
        t.SetA(123);
        System.out.println(t);
        String test = t.toString();
        System.out.println(test);

        t.SetB("Hello");
        System.out.println(t);
        System.out.println(test);

        Test t2 = new Test(123);
        System.out.println(t);
        System.out.println(t2);
        t2.SetA(12333);
        System.out.println(t2);

        Test t3 = new Test("Hello World");
        System.out.println(t3);
        System.out.println(t);

        double v = Test.GetC();
        System.out.println(v);
    }
}
