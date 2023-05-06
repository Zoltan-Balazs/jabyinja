package com.zoltanbalazs.PTI._11._03;

class AnimalMain {
    public static void main(String[] args) {
        // Az Animal osztály absztrakt, nem példányosítható!
        // Animal genericAnimal = new Animal("some animal", "forest");
        Animal lion = new Lion("Leo the Lion", "savannah");
        System.out.println(lion);
        lion.animalSound();
    }
}