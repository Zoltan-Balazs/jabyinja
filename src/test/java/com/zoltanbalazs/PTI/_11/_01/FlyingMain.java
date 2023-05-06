package com.zoltanbalazs.PTI._11._01;

interface Flying {
    // ....hogy repül?
    public void fly();
}

class Bird implements Flying {
    public String toString() {
        return "It's a bird! ";
    }

    public void fly() {
        System.out.println("Flying with wings!");
    }
}

class Plane implements Flying {
    public String toString() {
        return "It's a plane! ";
    }

    public void fly() {
        System.out.println("Flying with turbines!");
    }
}

class Superman implements Flying {
    public String toString() {
        return "It's Superman! ";
    }

    public void fly() {
        System.out.println("Flying with superpower!");
    }
}

public class FlyingMain {
    public static void main(String[] args) {
        Flying f1 = new Bird();
        Flying f2 = new Plane();
        Flying f3 = new Superman();

        System.out.println("" + f1 + f2 + f3);
    }
}