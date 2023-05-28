package com.zoltanbalazs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static ClassFile CLASS_FILE;

    public static void main(String[] args) {
        if (args.length == 0) {
            CLASS_FILE = new ClassFile("Main.class");
        } else {
            CLASS_FILE = new ClassFile(args[0]);
        }

        Method_Info methodInfo = new Method_Info();
        List<Attribute_Info> methodAttributes = new ArrayList<>();

        try {
            methodInfo = CLASS_FILE.findMethod("main", "([Ljava/lang/String;)V");
            CLASS_FILE.IN_MAIN_METHOD = true;
            methodAttributes = CLASS_FILE.findAttributesByName(methodInfo.attributes, "Code");
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Attribute_Info attribute : methodAttributes) {
            try {
                Code_Attribute codeAttribute = Code_Attribute_Helper.readCodeAttributes(attribute);

                CLASS_FILE.executeCode(codeAttribute,
                        args.length > 1 ? Arrays.copyOfRange(args, 1, args.length) : null);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}