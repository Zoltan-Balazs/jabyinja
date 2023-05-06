package com.zoltanbalazs.PTI._05._03;

public class Main {
    public static void main(String[] args) {
        WildAnimal a = WildAnimal.ELEFANT;
        System.out.println(a.name());
        System.out.println(a.ordinal());
        System.out.println(a);

        WildAnimal[] animal_arr = WildAnimal.values();
        System.out.println(WildAnimal.listAllAnimals());
    }
}
