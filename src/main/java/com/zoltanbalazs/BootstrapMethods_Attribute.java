package com.zoltanbalazs;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

class BootstrapMethods_Attribute_Helper {
    public static BootstrapMethods_Attribute readBootStrapMethodAttributes(Attribute_Info attribute)
            throws IOException {
        BootstrapMethods_Attribute bootstrapMethodsAttribute = new BootstrapMethods_Attribute();

        try (InputStream infoStream = new ByteArrayInputStream(attribute.info);
                DataInputStream infoData = new DataInputStream(infoStream)) {

            bootstrapMethodsAttribute.attribute_name_index = attribute.attribute_name_index;
            bootstrapMethodsAttribute.attribute_length = attribute.attribute_length;
            bootstrapMethodsAttribute.num_bootstrap_methods = ClassFile_Helper.readShort(infoData);
            bootstrapMethodsAttribute.bootstrap_methods = BoostrapMethod_Helper.readBootStrapMethods(infoData,
                    bootstrapMethodsAttribute.num_bootstrap_methods);
        }

        return bootstrapMethodsAttribute;
    }
}

class BoostrapMethod_Helper {
    public static List<BoostrapMethod> readBootStrapMethods(DataInputStream in, int count) throws IOException {
        List<BoostrapMethod> bootstrapMethods = new ArrayList<BoostrapMethod>(count);

        for (int i = 0; i < count; ++i) {
            BoostrapMethod tmp = new BoostrapMethod();
            tmp.bootstrap_method_ref = ClassFile_Helper.readShort(in);
            tmp.num_bootstrap_arguments = ClassFile_Helper.readShort(in);
            tmp.bootstrap_arguments = new short[tmp.num_bootstrap_arguments];
            for (int j = 0; j < tmp.num_bootstrap_arguments; ++j) {
                tmp.bootstrap_arguments[j] = ClassFile_Helper.readShort(in);
            }
            bootstrapMethods.add(tmp);
        }

        return bootstrapMethods;
    }
}

class BoostrapMethod {
    public short bootstrap_method_ref;
    public short num_bootstrap_arguments;
    public short[] bootstrap_arguments;

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("Bootstrap Method:\n");
        str.append("\bootstrap_method_ref = " + bootstrap_method_ref + "\n");
        str.append("\num_bootstrap_arguments = " + num_bootstrap_arguments + "\n");

        if (num_bootstrap_arguments != 0) {
            str.append("\tbootstrap_methods: \n");
            for (short args : bootstrap_arguments) {
                str.append("\t\t" + args + "\n");
            }
        }
        if (num_bootstrap_arguments == 0) {
            str.append("\n");
        }
        str.deleteCharAt(str.length() - 1);

        return str.toString();
    }
}

class BootstrapMethods_Attribute {
    public short attribute_name_index;
    public int attribute_length;
    public short num_bootstrap_methods;
    public List<BoostrapMethod> bootstrap_methods;

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("Code:\n");
        str.append("\tattribute_name_index = " + attribute_name_index + "\n");
        str.append("\tattribute_length = " + attribute_length + "\n");
        str.append("\num_bootstrap_methods = " + num_bootstrap_methods + "\n");

        if (num_bootstrap_methods != 0) {
            str.append("\tbootstrap_methods: \n");
            for (BoostrapMethod METHOD : bootstrap_methods) {
                str.append("\t\t" + METHOD + "\n");
            }
        }
        if (num_bootstrap_methods == 0) {
            str.append("\n");
        }
        str.deleteCharAt(str.length() - 1);

        return str.toString();
    }
}
