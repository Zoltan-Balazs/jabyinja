package com.zoltanbalazs.PTI._01;

public class Half {
    public static void main(String[] args) {
        int a = Integer.parseInt(System.console().readLine("a: "));
        int b = Integer.parseInt(System.console().readLine("b: "));
        for (int i = a; i <= b; ++i) {
            System.out.println(i / 2.0);
        }
    }
}