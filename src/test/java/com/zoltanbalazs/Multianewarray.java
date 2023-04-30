package com.zoltanbalazs;

class Multianewarray {
    public static void main(String[] args) {
        int[][][] a = new int[1][2][];

        a[0][0] = new int[] { 1, 3, 2 };
        a[0][1] = new int[] { 6, 5, 4, 8, 9 };

        for (int i = 0; i < a.length; ++i) {
            for (int j = 0; j < a[i].length; ++j) {
                for (int k = 0; k < a[i][j].length; ++k) {
                    System.out.println(a[i][j][k]);
                }
            }
        }
    }
}
