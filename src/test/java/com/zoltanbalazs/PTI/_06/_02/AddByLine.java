package com.zoltanbalazs.PTI._06._02;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AddByLine {
    public static void main(String[] args) {
        String basePath = new File(".").getAbsolutePath().replace(".", "")
                + "src/test/java/com/zoltanbalazs/PTI/_06/_02/";
        processFile(basePath + "in.txt", basePath + "out.txt");
    }

    public static void processFile(String inFilename, String outFilename) {
        File inFile = new File(inFilename);
        File outFile = new File(outFilename);

        try (BufferedReader in = new BufferedReader(new FileReader(inFile));
                BufferedWriter out = new BufferedWriter(new FileWriter(outFile))) {

            String line;

            while ((line = in.readLine()) != null) {
                String[] nums = line.split(",");

                int sum = 0;
                for (String num : nums) {
                    sum += Integer.parseInt(num);
                }

                System.out.println(sum);
                out.write(sum + "\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
