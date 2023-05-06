package com.zoltanbalazs.PTI._09._04;

import java.util.Map.Entry;

public class Main {
    public static void main(String[] args) {
        MultiSet a = new MultiSet();
        a.put("Teszt");
        a.put("Teszt");
        a.put("Teszt");
        a.put("Teszt");
        a.put("Körte");
        a.put("Alma");

        MultiSet b = new MultiSet();
        b.put("Teszt");
        b.put("Teszt");
        b.put("Alma");
        b.put("Körte");
        b.put("Körte");

        MultiSet c = new MultiSet();
        c = a.intersect(b);

        for (Entry<String, Integer> e : c.getData().entrySet()) {
            System.out.println(e.getKey() + ", " + e.getValue());
        }
    }
}
