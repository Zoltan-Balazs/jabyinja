package com.zoltanbalazs.PTI._01;

public class SquareRoot {
    public static void main(String[] args) {
        double s = Double.parseDouble(System.console().readLine("S értéke: "));
        double e = Double.parseDouble(System.console().readLine("epsilon értéke: "));

        double x = s / 2;
        double xi = 0.5 * (x + s / x);
        while (Math.abs(xi - x) > e) {
            x = xi;
            xi = 0.5 * (x + s / x);
        }

        System.out.println(s + " négyzetgyöke a babilóniai módszerrel: " + xi);
    }
}