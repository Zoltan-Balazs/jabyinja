package com.zoltanbalazs.PTI._02._05;

public class Line {
    double a, b, c;

    boolean contains(Point p) {
        return this.a * p.x + this.b * p.y == this.c;
    }

    boolean isParallelWith(Line l) {
        double m1 = -1 * (this.a / this.b);
        double m2 = -1 * (l.a / l.b);
        return m1 == m2;
    }

    boolean isOrthogonalTo(Line l) {
        double m1 = -1 * (this.a / this.b);
        double m2 = -1 * (l.a / l.b);
        return m1 == (-1 / m2);
    }
}
