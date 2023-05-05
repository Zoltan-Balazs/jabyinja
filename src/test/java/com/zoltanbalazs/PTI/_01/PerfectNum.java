package com.zoltanbalazs.PTI._01;

public class PerfectNum {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Adjon meg pontosan egy számot argumentumként!");
            System.exit(1);
        }

        int num = Integer.parseInt(args[0]);
        int divisiorSum = 0;

        for (int i = 1; i < num; ++i) {
            if (num % i == 0) {
                divisiorSum += i;
            }
        }

        if (divisiorSum == num) {
            System.out.println("A(z) " + num + " tökéletes szám!");
        } else {
            System.out.println("A(z) " + num + " nem tökéletes szám!");
        }
    }
}