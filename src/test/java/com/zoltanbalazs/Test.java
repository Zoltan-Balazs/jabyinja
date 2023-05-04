package com.zoltanbalazs;

public class Test {
    public int a;

    public Test(int b) {
        this.a = b;
    }

    public void SetA(int val) {
        a = val;
    }

    @Override
    public String toString() {
        return String.valueOf(a);
    }
}
