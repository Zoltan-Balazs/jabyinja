package com.zoltanbalazs.PTI._10._04;

public class Swap {
    public static void main(String[] args) {
        Integer[] a = { 10, 20, 30, 40, 50 };

        for (int i = 0; i < a.length; ++i) {
            System.out.print(a[i] + " ");
        }
        System.out.println();

        a = swap(a, 0, 1);

        for (int i = 0; i < a.length; ++i) {
            System.out.print(a[i] + " ");
        }
        System.out.println();

        String[] b = { "Hello", "World" };

        for (int i = 0; i < b.length; ++i) {
            System.out.print(b[i] + " ");
        }
        System.out.println();

        b = swap(b, 0, 1);

        for (int i = 0; i < b.length; ++i) {
            System.out.print(b[i] + " ");
        }
        System.out.println();
    }

    public static <T> T[] swap(T[] elem, int i, int j) {
        if (elem.length < i || elem.length < j) {
            System.out.println("Lololo ilyen nem lehet");
        }

        T tmp = elem[i];
        elem[i] = elem[j];
        elem[j] = tmp;

        return elem;
    }
}
