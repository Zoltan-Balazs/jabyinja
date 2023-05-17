package com.zoltanbalazs.PTI._06._04;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CircleMain {
    public static void main(String[] args) {
        try {
            String basePath = new File(".").getAbsolutePath().replace(".", "")
                    + "src/test/java/com/zoltanbalazs/PTI/_06/_04/";
            Circle c1 = new Circle(2.3, 5.6, 20);
            c1.saveToFile(basePath + "circle.txt");

            Circle c2 = Circle.readFromFile(basePath + "in.txt");
            System.out.println(c2);

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
