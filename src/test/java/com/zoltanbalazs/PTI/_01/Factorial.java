package com.zoltanbalazs.PTI._01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Factorial {
    public static void main(String[] args) throws IOException {
        InputStreamReader isReader = new InputStreamReader(System.in);
        BufferedReader bufReader = new BufferedReader(isReader);

        String inputStr = bufReader.readLine();
        int n = Integer.parseInt(inputStr);

        // int n = Integer.parseInt(System.console().readLine("n: "));

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