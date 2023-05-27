package com.zoltanbalazs;

public class Pair<F, S> {
    public final F first;
    public final S second;

    /***
     * Constructor for Pair class
     * 
     * @param first  First element of the pair
     * @param second Second element of the pair
     */
    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }
}
