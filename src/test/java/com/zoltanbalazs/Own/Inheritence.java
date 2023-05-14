package com.zoltanbalazs.Own;

class Cheese {
}

class Gorgonzola extends Cheese {
}

class NagyonSajtosGorgonzola extends Gorgonzola {
    Gorgonzola gz;

    public NagyonSajtosGorgonzola() {
        this.gz = null;
    }

    public NagyonSajtosGorgonzola(Gorgonzola gz) {
        this.gz = gz;
    }

    public void check() {
        if (gz == null) {
            System.out.println("This is NOT a Gorgonzola");
        } else {
            System.out.println("This is a Gorgonzola");
        }
    }
}

public class Inheritence {
    public static void main(String[] args) {
        new NagyonSajtosGorgonzola().check();
        new NagyonSajtosGorgonzola(new NagyonSajtosGorgonzola()).check();
    }
}