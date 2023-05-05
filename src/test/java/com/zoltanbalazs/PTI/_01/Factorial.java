package com.zoltanbalazs.PTI._01;

public class Factorial {
    public static void main(String[] args) {
        int n = Integer.parseInt(System.console().readLine("n: "));
        int fact = 1;
        for (int i = 1; i <= n; ++i) {
            fact *= i;
        }
        if (1 <= fact && 0 <= n) {
            System.out.println(n + "! = " + fact);
        } else {
            System.out.println("Incorrect n value or too big factorial");
        }
    }
}