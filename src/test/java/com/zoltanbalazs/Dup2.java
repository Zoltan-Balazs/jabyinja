package com.zoltanbalazs;

public class Dup2 {
    public static long dup2(long a) {
        return a = a + 1;
    }

    public static void main(String[] args) {
        long b = 5;
        long a = dup2(b);
        System.out.println(a);
    }
}
