package com.zoltanbalazs.Own;

public class SwitchAthrow {
    public static void main(String[] args) {
        try {
            throw new Exception("This is a test");
        } catch (ArithmeticException ae) {
            System.out.println("I got an arithmetic exception " + ae.getMessage());
        } catch (Exception e) {
            System.out.println("Wasn't an arithmetic exception :(");
            System.out.println(e.getMessage());
        }
    }
}
