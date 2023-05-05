package com.zoltanbalazs.PTI._01;

public class Odd {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Adjon meg pontosan egy számot a parancssorban!");
            System.exit(1); // Nem jelezzük hibakóddal hogy hibásan futott le a program, ha a lefutási kódot
                            // megnézzük, akkor 0 (helyes lefutás) lett volna az eredmény.
        } else {
            int num = Integer.parseInt(args[0]);
            // String answer = (num % 2 == 1) ? "páratlan" : "páros"; Negatív num esetén
            // -1-et ad a Java modulusra.
            String answer = (num % 2 == 0) ? "páros" : "páratlan"; // Inkább vizsgáljuk hogy 0-val egyenlő-e, ha igen
                                                                   // akkor biztosan páros.
            System.out.println(answer);
        }
    }
}