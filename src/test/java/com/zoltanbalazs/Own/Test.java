package com.zoltanbalazs.Own;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public int a;
    public String b;
    public static double c = 5.3;
    public static List<Integer> d = new ArrayList<>(5);

    public Test(int a) {
        this.a = a;
    }

    public Test(String b) {
        this.b = b;
    }

    public Test(int b, String a) {
        this.a = b;
        this.b = a;
        d = new ArrayList<>(5);
        for (int i = 0; i < 5; ++i) {
            d.add(i, i * i);
        }
    }

    public void SetA(int val) {
        a = val;
    }

    public static double GetC() {
        return c;
    }

    public void SetB(String t) {
        b = t;
    }

    public int GetSize() {
        return d.size();
    }

    @Override
    public String toString() {
        return String.valueOf(a) + " " + b + " " + String.valueOf(c);
    }
}
