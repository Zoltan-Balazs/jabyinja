package com.zoltanbalazs.PTI._08._02;

public class Beverage {
    private String name;
    private int legalAge;

    public Beverage(String name, int legalAge) {
        if (name.isEmpty() || legalAge < 0 || 130 < legalAge) {
            throw new IllegalArgumentException("Invalid arguments in Beverage constructor!");
        }

        this.name = name;
        this.legalAge = legalAge;
    }

    public String getName() {
        return this.name;
    }

    public int getLegalAge() {
        return this.legalAge;
    }
}
