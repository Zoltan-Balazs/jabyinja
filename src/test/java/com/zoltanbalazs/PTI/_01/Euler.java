package com.zoltanbalazs.PTI._01;

public class Euler {
    public static void main(String[] args) {
        int iter = Integer.parseInt(System.console().readLine("Iterációk száma: "));

        double evenNum = 2 * Math.floor(iter / 3.0);

        double num;
        if (iter % 3 == 0) {
            num = 1 / evenNum;
            evenNum -= 2.0;
        } else {
            num = 1;
        }

        for (int i = iter - 1; 0 <= i; --i) {
            if (i % 3 == 0) {
                num = 1 / (evenNum + num);
                evenNum -= 2.0;
            } else {
                num = 1 / (1 + num);
            }
        }

        num = 1 + num;

        System.out.println("e értéke " + iter + " db iteráció után: " + num);
    }
}