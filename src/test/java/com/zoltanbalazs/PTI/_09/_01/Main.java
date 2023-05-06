package com.zoltanbalazs.PTI._09._01;

import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> res = divisiors(123);
        for (int i : res) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static List<Integer> divisiors(int num) {
        List<Integer> linkList = new LinkedList<>();
        for (int i = 1; i <= num; ++i) {
            if (num % i == 0) {
                linkList.add(i);
            }
        }
        return linkList;
    }
}
