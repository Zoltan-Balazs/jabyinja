package com.zoltanbalazs.Own;

public class Arrayclass {
    static class Another {
        int i;
        String s;

        public Another(int i, String s) {
            this.s = s;
            this.i = i;
        }
    }

    public static void main(String[] args) {
        Another[] array = new Another[5];
        for (int i = 0; i < array.length; ++i) {
            array[i] = new Another(i, "Hello");
        }

        for (Another a : array) {
            System.out.println(a.s + " " + a.i);
        }
    }
}
