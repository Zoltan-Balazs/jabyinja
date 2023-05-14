package com.zoltanbalazs.PTI._09._02;

import java.util.ArrayList;
import java.util.List;

public class RemoveDiffer {
    public static void main(String[] args) {
        List<String> res = removeStrDifferBeginningAndEnding(new ArrayList<>(List.of("alma", "körte", "", "teszt")));
        for (String i : res) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static List<String> getStrSameBeginningAndEnding(List<String> in) {
        List<String> arrList = new ArrayList<>();
        for (String i : in) {
            if (!i.isEmpty() && i.charAt(0) == i.charAt(i.length() - 1)) {
                arrList.add(i);
            }
        }
        return arrList;
    }

    public static List<String> removeStrDifferBeginningAndEnding(List<String> in) {
        in.removeIf(n -> n.isEmpty() || n.charAt(0) != n.charAt(n.length() - 1));
        return in;
    }
}
