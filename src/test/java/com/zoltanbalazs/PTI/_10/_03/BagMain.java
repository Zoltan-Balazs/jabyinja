package com.zoltanbalazs.PTI._10._03;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map.Entry;

public class BagMain {
    public static void main(String[] args) {
        Bag<String> input = new Bag<>();

        String basePath = new File(".").getAbsolutePath().replace(".", "")
                + "target/test-classes/com/zoltanbalazs/PTI/_10/_03/";
        File inFile = new File(basePath + "input.txt");
        try (BufferedReader buf = new BufferedReader(new FileReader(inFile))) {
            String line;

            while ((line = buf.readLine()) != null) {
                input.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        for (Entry<String, Integer> e : input.getData().entrySet()) {
            System.out.println(e.getKey() + " - " + e.getValue());
        }
    }
}
