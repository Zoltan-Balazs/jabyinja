package com.zoltanbalazs.PTI._02._04;

public class Complex {
    double re, im;

    double abs() {
        return Math.sqrt(this.re * this.re + this.im * this.im);
    }

    void add(Complex c) {
        this.re += c.re;
        this.im += c.im;
    }

    void sub(Complex c) {
        this.re -= c.re;
        this.im -= c.im;
    }

    void mul(Complex c) {
        double reCopy = this.re;
        double imCopy = this.im;
        this.re = reCopy * c.re - imCopy * c.im;
        this.im = reCopy * c.im + imCopy * c.re;
    }

    void div(Complex c) {
        Complex cConj = c;
        cConj.conjugate();

    }

    void conjugate() {
        this.im = -1 * this.im;
    }

    void reciprocate() {

    }
}