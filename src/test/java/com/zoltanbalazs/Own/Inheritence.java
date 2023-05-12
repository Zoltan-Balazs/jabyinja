package com.zoltanbalazs.Own;

class Cheese {
}

class Gorgonzola extends Cheese {
}

class NagyonSajtosGorgonzola extends Gorgonzola {
    public NagyonSajtosGorgonzola() {
        System.out.println("This is NOT a Gorgonzola");
    }

    public NagyonSajtosGorgonzola(Gorgonzola gz) {
        System.out.println("This is a Gorgonzola");
    }
}

public class Inheritence {
    public static void main(String[] args) {
        new NagyonSajtosGorgonzola(new NagyonSajtosGorgonzola(new NagyonSajtosGorgonzola()));
    }
}