package com.zoltanbalazs.PTI._03.stringutils;

public class IterLetter {
    private String str;
    private int idx;

    public IterLetter(String other) {
        if (other == null) {
            throw new IllegalArgumentException("Reference is null!");
        }
        this.str = other;
        this.idx = 0;
    }

    public void printNext() {
        if (this.hasNext()) {
            System.out.println(str.charAt(this.idx));
            this.idx++;
        }
    }

    public void restart() {
        this.idx = 0;
    }

    public boolean hasNext() {
        return this.idx < this.str.length();
    }
}