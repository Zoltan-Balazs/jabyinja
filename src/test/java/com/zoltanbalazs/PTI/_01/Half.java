package com.zoltanbalazs.PTI._01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Half {
    public static void main(String[] args) throws IOException {
        InputStreamReader isReader = new InputStreamReader(System.in);
        BufferedReader bufReader = new BufferedReader(isReader);

        String inputStr = bufReader.readLine();
        int a = Integer.parseInt(inputStr.split(" ")[0]);
        int b = Integer.parseInt(inputStr.split(" ")[1]);

        // int a = Integer.parseInt(System.console().readLine("a: "));
        // int b = Integer.parseInt(System.console().readLine("b: "));

        for (int i = a; i <= b; ++i) {
            System.out.println(i / 2.0);
        }
    }
}