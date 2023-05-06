package com.zoltanbalazs.PTI._04._02;

import com.zoltanbalazs.PTI._04._02.utils.DoubleVector;

public class Main {
    public static void main(String[] args) {
        double[] arr = new double[] { 1.0, 2.0, 3.0, 5.0 };
        DoubleVector vec = new DoubleVector(arr);

        double times = 5.0;
        System.out.println(vec + " + " + vec + " = " + vec.add(arr));
        System.out.println(vec + " - " + vec + " = " + vec.subtract(arr));
        System.out.println(vec + " * " + vec + " = " + vec.vectorMultiply(arr));
        System.out.println(vec + " * " + times + " = " + vec.scalarMultiply(times));
    }
}
