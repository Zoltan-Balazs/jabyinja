package com.zoltanbalazs;

public class Ownclass {

    public static void main(String[] args) {
        Test t = new Test(94);
        System.out.println(t);
        t.SetA(123123);
        System.out.println(t);
        t.SetA(123);
        System.out.println(t);
    }
}
