package com.zoltanbalazs.PTI._08._02;

public class CoffeeShop {
    public static void main(String[] args) {
        Beverage beer = new Beverage("Beer", 18);
        Beverage orangeJuice = new Beverage("Orange Juice", 0);

        Adult a = new Adult("Teszt Péter 1", 21);
        Minor m = new Minor("Teszt Péter 2", 17);

        System.out.println(Bartender.order(beer, a));
        System.out.println(Bartender.order(orangeJuice, a));
        System.out.println(Bartender.order(beer, m));
        System.out.println(Bartender.order(orangeJuice, m));
    }
}
