package com.zoltanbalazs.Own;

import java.util.function.Function;

class Executor<T> {
    Function<T, T> endomorphism;
    T target;

    public Executor(Function<T, T> endomorphism, T target) {
        this.endomorphism = endomorphism;
        this.target = target;
    }

    public Executor(T target, Function<T, T> endomorphism) {
        this.endomorphism = endomorphism;
        this.target = target;
    }

    public void execute() {
        System.out.println(endomorphism.apply(target));
    }
}

public class Functional {
    public static void main(String[] args) {
        new Executor<Integer>(a -> a + 1, 10).execute();
        // new Executor<Integer>(10, a -> a + 2);
        // var ex3 = new Executor<Function<Integer, Integer>>(f -> a -> f.apply(a),
        // (Integer a) -> a);
        // var ex4 = new Executor<Function<Integer, Integer>>((Integer a) -> a, f -> a
        // -> f.apply(a));
    }
}