package com.zoltanbalazs.Own;

public class Athrow {
    public static void main(String[] args) throws Exception {
        try {
            throw new Exception("This is a test exception message");
        } catch (Exception e) {
            System.out.println("An exception was throw " + e.getMessage());
        }
    }
}
