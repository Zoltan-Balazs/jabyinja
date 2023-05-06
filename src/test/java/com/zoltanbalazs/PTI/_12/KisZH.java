package com.zoltanbalazs.PTI._12;

class A {
    int alma() {
        return 1;
    }

    int cseresznye() {
        return alma();
    }
}

class B extends A {
    int alma() {
        return 2;
    }
}

public class KisZH {
    public static void main(String[] args) {
        A a = new B();
        System.out.println(a.cseresznye());
    }
}