package com.zoltanbalazs.PTI._10._01;

class Superhero {
    public Superhero() {

    }

    public Superhero(String s) {
        System.out.println("Th eral name of this superhero is " + s);
    }
}

class Batman extends Superhero {
    public Batman(String s) {
        System.out.println("The real name of Batman is " + s);
    }
}

public class KisZH {
    public static void main(String[] args) {
        Superhero darkNight = new Batman("Bruce Wayne");
    }
}