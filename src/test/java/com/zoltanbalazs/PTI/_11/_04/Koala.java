package com.zoltanbalazs.PTI._11._04;

public class Koala extends Animal implements Herbivorous {
    public Koala(String habitat, String name) {
        super(habitat, name);
    }

    public void hangotAd() {
        System.out.println("Cuki koala hangok");
    }

    public void eszik() {
        System.out.println("A koala eukaliptusz levelet eszik :)");
    }

    public void eteltKeres() {
        System.out.println("Nincs a koalának étele :(");
    }
}
