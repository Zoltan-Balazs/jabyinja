package com.zoltanbalazs.PTI._11._04;

public abstract class Animal {
    private String habitat;
    private String name;

    public Animal(String habitat, String name) {
        this.habitat = habitat;
        this.name = name;
    }

    public void miANeve() {
        System.out.println("Név: " + this.name);
    }

    public void holEl() {
        System.out.println("Élőhely: " + this.habitat);
    }

    public abstract void hangotAd();
}
