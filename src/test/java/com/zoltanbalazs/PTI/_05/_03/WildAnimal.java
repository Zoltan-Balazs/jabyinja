package com.zoltanbalazs.PTI._05._03;

public enum WildAnimal {
    MAJOM("banan", 8),
    ELEFANT("malna", 30),
    ZSIRAF("korte", 10),
    MOSOMEDVE("alma", 5);

    private final String food;
    private final int amount;

    WildAnimal(String food, int amount) {
        this.food = food;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return this.ordinal() + ": " + this.name() + " szeretne enni " + this.amount + " db " + this.food
                + "-t egy nap.";
    }

    public static String listAllAnimals() {
        StringBuilder bld = new StringBuilder();
        for (WildAnimal a : WildAnimal.values()) {
            bld.append(a.ordinal() + ": " + a.name() + " szeretne enni " + a.amount * 7 + " db " + a.food
                    + "-t egy heten.\n");
        }
        return bld.toString();
    }
}