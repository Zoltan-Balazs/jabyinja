package com.zoltanbalazs.PTI._01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SquareRoot {
    public static void main(String[] args) throws IOException {
        InputStreamReader isReader = new InputStreamReader(System.in);
        BufferedReader bufReader = new BufferedReader(isReader);

        String inputStr = bufReader.readLine();
        double s = Double.parseDouble(inputStr.split(" ")[0]);
        double e = Double.parseDouble(inputStr.split(" ")[1]);

        // double s = Double.parseDouble(System.console().readLine("S értéke: "));
        // double e = Double.parseDouble(System.console().readLine("epsilon értéke: "));

        double x = s / 2;
        double xi = 0.5 * (x + s / x);
        while (Math.abs(xi - x) > e) {
            x = xi;
            xi = 0.5 * (x + s / x);
        }

        System.out.println(s + " négyzetgyöke a babilóniai módszerrel: " + xi);
    }
}