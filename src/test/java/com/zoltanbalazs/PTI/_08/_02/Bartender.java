package com.zoltanbalazs.PTI._08._02;

public class Bartender {
    public static boolean order(Beverage b, Guest g) {
        return !(b.getLegalAge() == 18 && g instanceof Minor);
    }
}
