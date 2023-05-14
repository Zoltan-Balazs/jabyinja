package com.zoltanbalazs.PTI._11._03;

class AnimalMain {
    public static void main(String[] args) {
        Animal lion = new Lion("Leo the Lion", "savannah");
        System.out.println(lion);
        lion.animalSound();
    }
}