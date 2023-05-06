package com.zoltanbalazs.PTI._05._01;

public class KisZH {
    public static void main(String[] args) {
        int a = 2, b = 3;
        swap(a, b);
        System.out.println(a + ", " + b);
    }

    public static void swap(int a, int b) {
        int tmp = a;
        a = b;
        b = tmp;
    }
}