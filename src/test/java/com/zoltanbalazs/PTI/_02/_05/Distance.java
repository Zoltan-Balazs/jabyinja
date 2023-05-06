package com.zoltanbalazs.PTI._02._05;

public class Distance {
    public static void main(String[] args) {
        double distance = 0.0;

        if (args.length > 2) {
            for (int i = 0; i < args.length / 2 - 1; ++i) {
                int x1 = Integer.parseInt(args[2 * i]);
                int y1 = Integer.parseInt(args[2 * i + 1]);

                int x2 = Integer.parseInt(args[2 * (i + 1)]);
                int y2 = Integer.parseInt(args[2 * (i + 1) + 1]);

                int dx = x2 - x1;
                int dy = y2 - y1;

                distance += Math.sqrt(dx * dx + dy * dy);
            }
        }

        System.out.println(distance);
    }
}
