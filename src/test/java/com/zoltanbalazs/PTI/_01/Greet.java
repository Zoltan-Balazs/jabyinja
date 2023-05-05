package com.zoltanbalazs.PTI._01;

public class Greet {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please give at least one argument!");
            System.exit(1);
        }
        System.out.println("Hello " + args[0] + "!");
    }
}