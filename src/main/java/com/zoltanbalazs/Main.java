package com.zoltanbalazs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static ClassFile CLASS_FILE;

    public static void main(String[] args) {
        if (args.length == 0) {
            CLASS_FILE = new ClassFile("Main.class", null);
        } else {
            CLASS_FILE = new ClassFile(args[0], Arrays.copyOfRange(args, 1, args.length));
        }

        Method_Info method = new Method_Info();
        List<Attribute_Info> attributes = new ArrayList<>();

        try {
            method = CLASS_FILE.findMethodsByName("main");
            attributes = CLASS_FILE.findAttributesByName(method.attributes, "Code");
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Attribute_Info attribute : attributes) {
            try {
                Code_Attribute codeAttribute = Code_Attribute_Helper.readCodeAttributes(attribute);

                CLASS_FILE.executeCode(codeAttribute.code);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}