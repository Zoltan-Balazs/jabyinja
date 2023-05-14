package com.zoltanbalazs.PTI._03;

public class IterletterMain {
    public static void main(String[] args) {
        String original = "Hello world!";
        com.zoltanbalazs.PTI._03.stringutils.IterLetter str = new com.zoltanbalazs.PTI._03.stringutils.IterLetter(
                original);
        System.out.println(str.hasNext());

        for (int i = 0; i < original.length(); ++i) {
            str.printNext();
        }

        System.out.println(str.hasNext());
        str.printNext();
        str.restart();

        for (int i = 0; i < 5; ++i) {
            str.printNext();
        }
    }
}
