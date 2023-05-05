package com.zoltanbalazs.Own;

public class Instanceof {
    public static void main(String[] args) {
        Integer a = 5;

        if (a instanceof Integer) {
            System.out.println("a is of type `Integer`");
        } else {
            System.out.println("a isn't of type `Integer`");
        }

        if (a instanceof Number) {
            System.out.println("a is of type `Number`");
        } else {
            System.out.println("a isn't of type `Number`");
        }

        Number b = 5.0;
        if (b instanceof Integer) {
            System.out.println("b is of type `Integer`");
        } else if (b instanceof Float) {
            System.out.println("b is of type `Float`");
        } else if (b instanceof Double) {
            System.out.println("b is of type `Double`");
        } else {
            System.out.println("b isn't of type `Integer`, `Float` or `Double`");
        }

        Number c = 5l;
        if (c instanceof Integer) {
            System.out.println("c is of type `Integer`");
        } else if (c instanceof Float) {
            System.out.println("c is of type `Float`");
        } else if (c instanceof Double) {
            System.out.println("c is of type `Double`");
        } else {
            System.out.println("c isn't of type `Integer`, `Float` or `Double`");

            if (c instanceof Comparable) {
                System.out.println("However c is `Comparable`");
            } else {
                System.out.println("And c isn't even `Comparable`");
            }
        }
    }
}
