package com.zoltanbalazs.PTI._06._03;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IsPartOf {
    // "target/test-classes/com/zoltanbalazs/PTI/_06/_03/in.txt"
    public static void main(String[] args) {
        try {
            if (args.length != 1) {
                throw new IllegalArgumentException();
            }

            InputStreamReader isReader = new InputStreamReader(System.in);
            BufferedReader bufReader = new BufferedReader(isReader);

            String pattern = bufReader.readLine();

            // System.out.print("Please enter the pattern to match with: ");
            // String pattern = System.console().readLine();

            int times = processFile(args[0], pattern);

            System.out.println("The given pattern: " + pattern + " was found " + times + " times.");
        } catch (IllegalArgumentException e) {
            System.out.println("Please enter exactly one filename!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static int processFile(String filename, String match) {
        File file = new File(filename);
        int i = 0;

        try (BufferedReader buf = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = buf.readLine()) != null) {
                if (line.contains(match)) {
                    ++i;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return i;
    }
}
