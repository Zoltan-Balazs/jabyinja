package com.zoltanbalazs.PTI._01;

public class GCD {
    static long gcd(long a, long b) {
        if (a == 0) {
            return b;
        }
        return gcd(b % a, a);
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Adjon meg két vagy több számot argumentumot!");
            System.exit(1);
        }

        long[] nums = new long[args.length];
        for (int i = 0; i < args.length; ++i) {
            nums[i] = Long.parseLong(args[i]);
        }

        long res = 0;
        for (long num : nums) {
            res = gcd(res, num);
        }

        System.out.println("A megadott számok LNKO-ja: " + res);
    }
}