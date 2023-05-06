package com.zoltanbalazs.PTI._11._03;

abstract class Animal {

    protected String name;
    private String habitat;

    public Animal(String name, String habitat) {
        this.name = name;
        this.habitat = habitat;
    }

    public String toString() {
        return this.name + " lives in the " + habitat;
    }

    // Nem tudjuk milyen hangot ad...
    public abstract void animalSound();

}