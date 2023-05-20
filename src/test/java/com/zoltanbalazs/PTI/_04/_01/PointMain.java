package com.zoltanbalazs.PTI._04._01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.zoltanbalazs.PTI._04._01.util.Point;

public class PointMain {
    public static void main(String[] args) throws IOException {
        InputStreamReader isReader = new InputStreamReader(System.in);
        BufferedReader bufReader = new BufferedReader(isReader);

        // int num = Integer.parseInt(System.console().readLine("Number of points to
        // enter: "));
        String stdin = bufReader.readLine();

        int num = Integer.parseInt(stdin.split(" ")[0]);
        Point[] arr = new Point[num];

        for (int i = 0; i < num; ++i) {
            // double x = Double.parseDouble(System.console().readLine("Point " + (i + 1) +
            // " x: "));
            // double y = Double.parseDouble(System.console().readLine("Point " + (i + 1) +
            // " y: "));
            double x = Double.parseDouble(stdin.split(" ")[i * 2 + 1]);
            double y = Double.parseDouble(stdin.split(" ")[i * 2 + 2]);

            arr[i] = new Point(x, y);
        }

        Point center = Point.centerOfMass(arr);
        System.out.println(center);
    }
}