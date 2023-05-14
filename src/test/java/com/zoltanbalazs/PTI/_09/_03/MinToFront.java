package com.zoltanbalazs.PTI._09._03;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinToFront {
    public static void main(String[] args) {
        List<Integer> res = minToFront(new ArrayList<>(List.of(25, 3, 2, 6, 7, 1, 1, 1)));
        for (int i : res) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static List<Integer> minToFront(List<Integer> in) {
        if (in.isEmpty()) {
            throw new IllegalArgumentException("List is empty!");
        }

        int min = Collections.min(in);
        int count = Collections.frequency(in, min);

        in.removeIf(n -> n == min);
        for (int i = 0; i < count; ++i) {
            in.add(0, min);
        }

        return in;
    }
}
