package com.zoltanbalazs.Own;

import java.util.function.Function;

class Executor<T> {
    public Executor(Function<T, T> endomorphism, T target) {
        System.out.println(endomorphism.apply(target));
    }

    public Executor(T target, Function<T, T> endomorphism) {
        System.out.println(endomorphism.apply(target));
    }
}

public class Functional {
    public static void main(String[] args) {
        Executor<Integer> ex1 = new Executor<Integer>(a -> a + 1, 10);
        // var ex2 = new Executor<Integer>(10, a -> a + 2);
        // var ex3 = new Executor<Function<Integer, Integer>>(f -> a -> f.apply(a),
        // (Integer a) -> a);
        // var ex4 = new Executor<Function<Integer, Integer>>((Integer a) -> a, f -> a
        // -> f.apply(a));
    }
}