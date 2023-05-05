package com.zoltanbalazs.PTI._01;

public class PerfectNumRange {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Adjon meg pontosan egy számot argumentumként!");
            System.exit(1);
        }

        int range = Integer.parseInt(args[0]);
        int noOfPerfectNums = 0;

        for (int i = 1; i <= range; ++i) {
            int divisiorSum = 0;

            for (int j = 1; j < i; ++j) {
                if (i % j == 0) {
                    divisiorSum += j;
                }
            }

            if (divisiorSum == i) {
                ++noOfPerfectNums;
            }
        }

        if (noOfPerfectNums != 0) {
            System.out.println(
                    "A megadott tartományban (1-től " + range + "-ig) " + noOfPerfectNums + " db tökéletes szám van.");
        } else {
            System.out.println("A megadott tartományban nincsen tökéletes szám!");
        }
    }
}