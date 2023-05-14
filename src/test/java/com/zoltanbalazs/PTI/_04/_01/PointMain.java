package com.zoltanbalazs.PTI._04._01;

import com.zoltanbalazs.PTI._04._01.util.Point;

public class PointMain {
    public static void main(String[] args) {
        int num = Integer.parseInt(System.console().readLine("Number of points to enter: "));
        Point[] arr = new Point[num];

        for (int i = 0; i < num; ++i) {
            double x = Double.parseDouble(System.console().readLine("Point " + (i + 1) + " x: "));
            double y = Double.parseDouble(System.console().readLine("Point " + (i + 1) + " y: "));

            arr[i] = new Point(x, y);
        }

        Point center = Point.centerOfMass(arr);
        System.out.println(center);
    }
}