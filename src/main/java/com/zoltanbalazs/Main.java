package com.zoltanbalazs;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static ClassFile CLASS_FILE;

    public static void main(String[] args) {
        Boolean DEBUG_ENV = Boolean.valueOf(System.getenv("JABYINJA_DEBUG"));

        if (args.length == 0) {
            CLASS_FILE = new ClassFile("Main.class");
        } else {
            CLASS_FILE = new ClassFile(args[0]);
        }

        CLASS_FILE.IS_DEBUG = DEBUG_ENV;

        Method_Info method = new Method_Info();
        List<Attribute_Info> attributes = new ArrayList<>();

        try {
            method = CLASS_FILE.findMethodsByName("main");
            attributes = CLASS_FILE.findAttributesByName(method.attributes, "Code");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (CLASS_FILE.IS_DEBUG) {
            System.out.println(CLASS_FILE + "\n");
            System.out.println(method + "\n");
            System.out.println(attributes + "\n");
        }

        for (Attribute_Info attribute : attributes) {
            try {
                Code_Attribute codeAttribute = Code_Attribute_Helper.readCodeAttributes(attribute);

                if (CLASS_FILE.IS_DEBUG) {
                    System.out.println(codeAttribute + "\n");
                }

                CLASS_FILE.executeCode(codeAttribute.code);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}