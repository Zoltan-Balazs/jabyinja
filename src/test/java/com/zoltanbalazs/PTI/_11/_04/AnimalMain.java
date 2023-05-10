package com.zoltanbalazs.PTI._11._04;

public class AnimalMain {
    public static void main(String[] args) {
        Koala k = new Koala("Australia", "Nom Nom");
        k.miANeve();
        k.holEl();
        k.eszik();
        k.hangotAd();
        k.eteltKeres();

        System.out.println();

        Eagle e = new Eagle("Air", "Bald Eagle");
        e.miANeve();
        e.holEl();
        e.repul();
        e.eszik();
        e.hangotAd();
        e.vadaszik();
    }
}
