package com.zoltanbalazs.Own;

public class Nested {
    static class Nest {
        public int x;
        public String y;
        public Another a;

        public Nest(int x, String y, double z) {
            this.x = x;
            this.y = y;
            this.a = new Another(z);
        }

        @Override
        public String toString() {
            return String.valueOf(x) + "\n" + y + "\n" + a.toString();
        }
    }

    static class Another {
        public double z;

        public Another(double z) {
            this.z = z;
        }

        @Override
        public String toString() {
            return String.valueOf(z);
        }
    }

    public static void main(String[] args) {
        Nest n = new Nest(2023, "ELTE", 5.0);
        System.out.println(n);
    }
}
