package com.zoltanbalazs.Own;

import java.util.ArrayList;
import java.util.List;

public class Arraylist {
    public static void main(String[] args) {
        List<Double> a = new ArrayList<Double>();

        for (int i = 0; i < 15; ++i) {
            a.add(Math.pow(i, 2.3));
        }

        for (int i = 0; i < 15; ++i) {
            System.out.println(a.get(i));
        }

        System.out.println(a.size());
    }
}
