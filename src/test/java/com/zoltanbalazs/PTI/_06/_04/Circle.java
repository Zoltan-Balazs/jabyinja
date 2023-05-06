package com.zoltanbalazs.PTI._06._04;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.zoltanbalazs.PTI._06._04.utils.Point;

public class Circle {
    private double x, y, radius;

    public Circle(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        if (radius <= 0) {
            throw new IllegalArgumentException("Non-positive radius!");
        }
        this.radius = radius;
    }

    public Point getCenter() {
        return new Point(this.x, this.y);
    }

    public void enlarge(double f) {
        this.radius *= f;
    }

    public void setRadius(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Non-positive radius!");
        }
        this.radius = radius;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getArea() {
        return Math.PI * this.radius * this.radius;
    }

    @Override
    public String toString() {
        return "Circle:\n\t(x, y) = (" + this.x + ", " + this.y + ")\n\tRadius = " + this.radius + "\n\tArea = "
                + getArea();
    }

    public void saveToFile(String filename) throws IOException {
        File outFile = new File(filename);
        try (BufferedWriter out = new BufferedWriter(new FileWriter(outFile))) {
            out.write(toString() + "\n");
        }
    }

    public static Circle readFromFile(String filename) throws FileNotFoundException, IOException {
        File inFile = new File(filename);
        try (BufferedReader in = new BufferedReader(new FileReader(inFile))) {
            Double x = Double.parseDouble(in.readLine());
            Double y = Double.parseDouble(in.readLine());
            Double radius = Double.parseDouble(in.readLine());

            return new Circle(x, y, radius);
        }
    }
}
