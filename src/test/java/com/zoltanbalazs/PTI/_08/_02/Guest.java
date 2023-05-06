package com.zoltanbalazs.PTI._08._02;

public class Guest {
    protected String name;
    protected int age;

    public Guest(String name, int age) {
        if (name.isEmpty() || age < 0 || 130 < age) {
            throw new IllegalArgumentException("Invalid arguments in Guest (Minor / Adult) constructor!");
        } else if (age >= 18 && this instanceof Minor) {
            throw new IllegalArgumentException("Minor can't be 18 or over!");
        } else if (age < 18 && this instanceof Adult) {
            throw new IllegalArgumentException("Adult can't be under 18!");
        }

        this.name = name;
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }
}
