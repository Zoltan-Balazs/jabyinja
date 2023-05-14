package com.zoltanbalazs.Own;

class Parent {
}

class Child extends Parent {
    Parent p;

    public Child() {
        this.p = null;
    }

    public Child(Parent p) {
        this.p = p;
    }

    public void check() {
        if (p == null) {
            System.out.println("This is a Child");
        } else {
            System.out.println("This is a Parent");
        }
    }
}

public class Inheritence {
    public static void main(String[] args) {
        new Child().check();
        new Child(new Child()).check();
    }
}