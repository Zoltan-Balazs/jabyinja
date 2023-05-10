package com.zoltanbalazs.PTI._11._04;

public class Eagle extends Animal implements Flying, Predator {
    public Eagle(String habitat, String name) {
        super(habitat, name);
    }

    public void hangotAd() {
        System.out.println("Mérges sas hangok");
    }

    public void repul() {
        System.out.println("A sas éppen repül");
    }

    public void eszik() {
        System.out.println("A sas eszik :(");
    }

    public void vadaszik() {
        System.out.println("A sas éppen vadászik!");
    }
}
