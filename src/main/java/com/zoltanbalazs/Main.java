package com.zoltanbalazs;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static ClassFile CLASS_FILE;

    public static void main(String[] args) {
        Boolean DEBUG = Boolean.valueOf(System.getenv("JABYINJA_DEBUG"));

        if (args.length == 0) {
            CLASS_FILE = new ClassFile("Main.class");
        } else {
            CLASS_FILE = new ClassFile(args[0]);
        }

        Method_Info method = new Method_Info();
        List<Attribute_Info> attributes = new ArrayList<>();

        try {
            method = CLASS_FILE.findMethodsByName("main");
            attributes = CLASS_FILE.findAttributesByName(method.attributes, "Code");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (DEBUG) {
            System.out.println(CLASS_FILE);
            System.out.println(method);
            System.out.println(attributes);
        }

        for (Attribute_Info attribute : attributes) {
            try {
                Code_Attribute codeAttribute = Code_Attribute_Helper.readCodeAttributes(attribute);
                System.out.println(codeAttribute);

                CLASS_FILE.executeCode(codeAttribute.code);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}