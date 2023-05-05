package com.zoltanbalazs.PTI._01;

public class Half {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Please enter exactly 2 arguments!");
            System.exit(1);
        }
        int a = Integer.parseInt(System.console().readLine("a: "));
        int b = Integer.parseInt(System.console().readLine("b: "));
        for (int i = a; i <= b; ++i) {
            System.out.println(i / 2.0);
        }
    }
}