package com.zoltanbalazs;

public class Test {
    public int a;
    public String b;

    public Test(int a) {
        this.a = a;
    }

    public Test(String b) {
        this.b = b;
    }

    public Test(int b, String a) {
        this.a = b;
        this.b = a;
    }

    public void SetA(int val) {
        a = val;
    }

    public void SetB(String t) {
        b = t;
    }

    @Override
    public String toString() {
        return String.valueOf(a) + " " + b;
    }
}
