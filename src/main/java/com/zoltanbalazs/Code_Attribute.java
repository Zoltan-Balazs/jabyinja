package com.zoltanbalazs;

import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

class Code_Attribute_Helper {
    public static Code_Attribute readCodeAttributes(Attribute_Info attribute) throws IOException {
        Code_Attribute codeAttribute = new Code_Attribute();

        try (InputStream infoStream = new ByteArrayInputStream(attribute.info);
                DataInputStream infoData = new DataInputStream(infoStream)) {

            codeAttribute.attribute_name_index = attribute.attribute_name_index;
            codeAttribute.attribute_length = attribute.attribute_length;
            codeAttribute.max_stack = ClassFile_Helper.readShort(infoData);
            codeAttribute.max_locals = ClassFile_Helper.readShort(infoData);
            codeAttribute.code_length = ClassFile_Helper.readInt(infoData);
            codeAttribute.code = new byte[codeAttribute.code_length];
            codeAttribute.code = infoData.readNBytes(codeAttribute.code_length);
            codeAttribute.exception_table_length = ClassFile_Helper.readShort(infoData);
            codeAttribute.exception_table = Exception_Table_Helper.readExceptionTable(infoData,
                    codeAttribute.exception_table_length);
            codeAttribute.attributes_count = ClassFile_Helper.readShort(infoData);
            codeAttribute.attributes = Attribute_Helper.readAttributes(infoData, codeAttribute.attributes_count);
        }

        return codeAttribute;
    }
}

class Code_Attribute {
    public short attribute_name_index;
    public int attribute_length;
    public short max_stack;
    public short max_locals;
    public int code_length;
    public byte[] code;
    public short exception_table_length;
    public List<Exception_Table> exception_table;
    public short attributes_count;
    public List<Attribute_Info> attributes;

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("Code:\n");
        str.append(" - attribute_name_index = " + attribute_name_index + "\n");
        str.append(" - attribute_length = " + attribute_length + "\n");
        str.append(" - max_stack = " + max_stack + "\n");
        str.append(" - max_locals = " + max_locals + "\n");
        str.append(" - code_length = " + code_length + "\n");
        str.append(" - code = " + code + "\n");
        str.append(" - exception_table_length = " + exception_table_length + "\n");

        for (Exception_Table EXCEPTION : exception_table) {
            str.append(EXCEPTION + "\n");
        }
        if (exception_table_length == 0) {
            str.append("\n");
        }

        str.append(" - attributes_count = " + attributes_count + "\n");

        for (Attribute_Info ATTRIBUTE : attributes) {
            str.append(ATTRIBUTE + "\n");
        }
        if (attributes_count == 0) {
            str.append("\n");
        }

        return str.toString();
    }
}