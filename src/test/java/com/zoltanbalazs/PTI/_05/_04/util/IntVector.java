package com.zoltanbalazs.PTI._05._04.util;

public class IntVector {
    int[] numbers;

    public IntVector(int[] numbers) {
        this.numbers = numbers;
    }

    public void add(int n) {
        for (int i = 0; i < numbers.length - 1; i++)
            numbers[i] += n;
    }

    public String toString() {
        StringBuilder bld = new StringBuilder();

        bld.append("[");
        for (int i = 0; i < numbers.length; ++i) {
            bld.append(numbers[i] + " ");
        }
        bld.deleteCharAt(bld.length() - 1);
        bld.append("]");

        return bld.toString();
    }
}