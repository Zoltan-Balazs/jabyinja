package com.zoltanbalazs.PTI._01;

public class Sqrt {
    public static void main(String[] args) {
        int iter = Integer.parseInt(System.console().readLine("Iterációk száma: "));

        double num = 0.5;
        for (int i = 0; i < iter; ++i) {
            num = 1 / (2 + num);
        }
        num = 1 + num;

        System.out.println("√2 értéke " + iter + " db iteráció után: " + num);
    }
}