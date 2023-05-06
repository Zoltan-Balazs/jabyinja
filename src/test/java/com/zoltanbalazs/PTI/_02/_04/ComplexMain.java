package com.zoltanbalazs.PTI._02._04;

public class ComplexMain {
    public static void main(String[] args) {
        Complex alpha = new Complex();
        Complex beta = new Complex();
        Complex gamma = new Complex();

        alpha.re = 3;
        alpha.im = 2;

        beta.re = 1;
        beta.im = 2;

        gamma.re = 1;
        gamma.im = 1;

        alpha.add(beta);
        System.out.println("alpha = " + alpha.re + " + " + alpha.im + "i");

        alpha.sub(beta);
        System.out.println("alpha = " + alpha.re + " + " + alpha.im + "i");

        alpha.mul(gamma);
        System.out.println("alpha = " + alpha.re + " + " + alpha.im + "i");

        System.out.println(gamma.abs());
    }
}
