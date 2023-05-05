package com.zoltanbalazs.PTI._01;

public class TwoNum {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Please enter exactly 2 arguments!");
            System.exit(1);
        }
        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);
        System.out.println(a + " + " + b + " = " + (a + b));
        System.out.println(a + " - " + b + " = " + (a - b));
        System.out.println(a + " * " + b + " = " + (a * b));
        if (b == 0) {
            System.out.println("Second argument is 0, division is not possible!");
        } else {
            System.out.println(a + " / " + b + " = " + ((double) a / b));
            System.out.println(a + " % " + b + " = " + (a % b));
        }
    }
}