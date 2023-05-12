package com.zoltanbalazs.PTI._01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Sqrt {
    public static void main(String[] args) throws IOException {
        InputStreamReader isReader = new InputStreamReader(System.in);
        BufferedReader bufReader = new BufferedReader(isReader);

        String inputStr = bufReader.readLine();
        int iter = Integer.parseInt(inputStr);

        // int iter = Integer.parseInt(System.console().readLine("Iterációk száma: "));

        double num = 0.5;
        for (int i = 0; i < iter; ++i) {
            num = 1 / (2 + num);
        }
        num = 1 + num;

        System.out.println("√2 értéke " + iter + " db iteráció után: " + num);
    }
}