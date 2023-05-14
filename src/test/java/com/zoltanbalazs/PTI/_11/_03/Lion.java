package com.zoltanbalazs.PTI._11._03;

class Lion extends Animal {

    public Lion(String name, String habitat) {
        super(name, habitat);
    }

    public void animalSound() {
        System.out.println("Roar!");
    }

    public void hunt() {
        System.out.println(this.name + " hunts.");
    }
}