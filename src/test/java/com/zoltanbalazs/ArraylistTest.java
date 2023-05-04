package com.zoltanbalazs;

import java.util.ArrayList;
import java.util.List;

public class ArraylistTest {
    public static void main(String[] args) {
        List<Double> a = new ArrayList<Double>();

        for (int i = 0; i < 15; ++i) {
            a.add(Math.pow(i, 2.3));
        }

        for (int i = 0; i < 15; ++i) {
            System.out.println(a.get(i));
        }
    }
}
