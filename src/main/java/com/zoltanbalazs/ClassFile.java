package com.zoltanbalazs;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.List;

class InvalidClassFileException extends RuntimeException {
}

class ClassFile_Helper {
    public static byte readByte(DataInputStream classFileData) throws IOException {
        return (byte) classFileData.readUnsignedByte();
    }

    public static short readShort(DataInputStream classFileData) throws IOException {
        return (short) classFileData.readUnsignedShort();
    }

    public static int readInt(DataInputStream classFileData) throws IOException {
        return classFileData.readInt();
    }
}

// https://docs.oracle.com/javase/specs/jvms/se19/html/jvms-4.html
// https://en.wikipedia.org/wiki/Java_class_file
class ClassFile {
    private static final byte[] MAGIC_NUMBER = HexFormat.of().parseHex("CAFEBABE");

    private static boolean VALID_CLASS_FILE = false;
    private static short MINOR_VERSION;
    private static short MAJOR_VERSION;
    private static short CONSTANT_POOL_COUNT;
    private static List<CP_Info> CONSTANT_POOL;
    private static List<Class_Access_Flags> ACCESS_FLAGS;
    private static short THIS_CLASS;
    private static short SUPER_CLASS;
    private static short INTERFACES_COUNT;
    private static List<Interface> INTERFACES;
    private static short FIELDS_COUNT;
    private static List<Field_Info> FIELDS;
    private static short METHODS_COUNT;
    private static List<Method_Info> METHODS;
    private static short ATTRIBUTES_COUNT;
    private static List<Attribute_Info> ATTRIBUTES;

    public ClassFile(String fileName) {
        readClassFile(fileName);
    }

    public void readClassFile(String fileName) {
        try (InputStream classFile = new FileInputStream(fileName);
                DataInputStream classFileData = new DataInputStream(classFile)) {
            if (!Arrays.equals(classFile.readNBytes(4), MAGIC_NUMBER)) {
                throw new InvalidClassFileException();
            }
            VALID_CLASS_FILE = true;

            MINOR_VERSION = ClassFile_Helper.readShort(classFileData);
            MAJOR_VERSION = ClassFile_Helper.readShort(classFileData);
            CONSTANT_POOL_COUNT = ClassFile_Helper.readShort(classFileData);
            CONSTANT_POOL = Constant_Pool_Helper.readConstantPool(classFileData, CONSTANT_POOL_COUNT);
            ACCESS_FLAGS = Class_Access_Flags.parseFlags(ClassFile_Helper.readShort(classFileData));
            THIS_CLASS = ClassFile_Helper.readShort(classFileData);
            SUPER_CLASS = ClassFile_Helper.readShort(classFileData);
            INTERFACES_COUNT = ClassFile_Helper.readShort(classFileData);
            INTERFACES = Interface_Helper.readInterfaces(classFileData, INTERFACES_COUNT);
            FIELDS_COUNT = ClassFile_Helper.readShort(classFileData);
            FIELDS = Field_Helper.readFields(classFileData, FIELDS_COUNT);
            METHODS_COUNT = ClassFile_Helper.readShort(classFileData);
            METHODS = Method_Helper.readMethods(classFileData, METHODS_COUNT);
            ATTRIBUTES_COUNT = ClassFile_Helper.readShort(classFileData);
            ATTRIBUTES = Attribute_Helper.readAttributes(classFileData, ATTRIBUTES_COUNT);
        } catch (FileNotFoundException e) {
            System.err.println("The file '" + fileName + "' cannot be found!");
        } catch (InvalidClassFileException e) {
            System.err.println("The file '" + fileName + "' is not a valid Java Class file!");
        } catch (InvalidConstantPoolTagException e) {
            System.err.println("The file '" + fileName + "' contains invalid Constant Pool tag!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Method_Info findMethodsByName(String methodName) {
        for (Method_Info METHOD : METHODS) {
            CP_Info currentItem = CONSTANT_POOL.get(METHOD.name_index - 1);

            // TODO: Instead of doing it this way, convert the user input into bytes
            // FIXME: That doesn't work yet... String.getBytes() doesn't return the correct
            // byte array, even when forcing the encoding
            if (new String(currentItem.getBytes(), StandardCharsets.UTF_8).equals(methodName)) {
                return METHOD;
            }
        }

        return null;
    }

    public List<Attribute_Info> findAttributesByName(List<Attribute_Info> attributes, String attributeName) {
        List<Attribute_Info> attr = new ArrayList<Attribute_Info>();

        for (Attribute_Info ATTRIBUTE : attributes) {
            CP_Info currentItem = CONSTANT_POOL.get(ATTRIBUTE.attribute_name_index - 1);

            // TODO: Instead of doing it this way, convert the user input into bytes
            // FIXME: That doesn't work yet... String.getBytes() doesn't return the correct
            // byte array, even when forcing the encoding
            if (new String(currentItem.getBytes(), StandardCharsets.UTF_8).equals(attributeName)) {
                attr.add(ATTRIBUTE);
            }
        }

        return attr;
    }

    public String getNameOfClass(short class_index) {
        return new String(CONSTANT_POOL.get(CONSTANT_POOL.get(class_index - 1).getNameIndex() - 1).getBytes(),
                StandardCharsets.UTF_8);
    }

    public String getNameOfMember(short name_and_type_index) {
        return new String(CONSTANT_POOL.get(CONSTANT_POOL.get(name_and_type_index - 1).getNameIndex() - 1).getBytes(),
                StandardCharsets.UTF_8);
    }

    public void executeCode(byte[] code) throws IOException {
        List<HashMap<String, Object>> stack = new ArrayList<>();

        try (InputStream codeStream = new ByteArrayInputStream(code);
                DataInputStream codeData = new DataInputStream(codeStream)) {

            while (codeData.available() != 0) {
                byte opCode = ClassFile_Helper.readByte(codeData);

                switch (Opcode.opcodeRepresentation(opCode)) {
                    case LDC -> {
                        byte index = ClassFile_Helper.readByte(codeData);
                        stack.add(new HashMap<>() {
                            {
                                put("type", "Constant");
                                put("const", CONSTANT_POOL.get(index - 1));
                            }
                        });
                    }
                    case ICONST_0, ICONST_1, ICONST_2, ICONST_3, ICONST_4, ICONST_5 -> {
                        // TODO
                    }
                    case ALOAD_0, ALOAD_1, ALOAD_2, ALOAD_3 -> {
                        // TODO
                    }
                    case AALOAD -> {
                        // TODO
                    }
                    case ASTORE_0, ASTORE_1, ASTORE_2, ASTORE_3 -> {
                        // TODO
                    }
                    case DUP -> {
                        // TODO
                    }
                    case IFNE -> {
                        short offset = ClassFile_Helper.readShort(codeData);
                        System.out.println(offset);
                    }
                    case GOTO -> {
                        short index = ClassFile_Helper.readShort(codeData);
                        System.out.println(index);
                    }
                    case RETURN -> {
                        return;
                    }
                    case GETSTATIC -> {
                        short index = ClassFile_Helper.readShort(codeData);
                        CP_Info fieldRef = CONSTANT_POOL.get(index - 1);
                        String className = getNameOfClass(fieldRef.getClassIndex());
                        String memberName = getNameOfMember(fieldRef.getNameAndTypeIndex());

                        if (className.equals("java/lang/System") && memberName.equals("out")) {
                            stack.add(new HashMap<>() {
                                {
                                    put("type", "PrintStream");
                                }
                            });
                        } else {
                            throw new UnsupportedOperationException(
                                    "Not supported field: " + className + "/" + memberName + " in getstatic");
                        }
                    }
                    case PUTSTATIC -> {
                        short index = ClassFile_Helper.readShort(codeData);
                        CP_Info fieldRef = CONSTANT_POOL.get(index - 1);
                        String className = getNameOfClass(fieldRef.getClassIndex());
                        String memberName = getNameOfMember(fieldRef.getNameAndTypeIndex());

                        System.out.println(className);
                        System.out.println(memberName);
                    }
                    case INVOKVEVIRTUAL -> {
                        short index = ClassFile_Helper.readShort(codeData);
                        CP_Info methodRef = CONSTANT_POOL.get(index - 1);
                        String className = getNameOfClass(methodRef.getClassIndex());
                        String memberName = getNameOfMember(methodRef.getNameAndTypeIndex());

                        if (className.equals("java/io/PrintStream") && memberName.equals("println")) {
                            int n = stack.size();
                            if (n < 2) {
                                throw new UnsupportedOperationException(
                                        className + "/" + memberName + "expects 2 arguments, but there is only " + n);
                            }
                            HashMap<String, Object> obj = stack.get(n - 2);
                            if (!obj.get("type").equals("PrintStream")) {
                                System.out.println("Unsupported stream type " + obj.get("type"));
                            }
                            HashMap<String, Object> arg = stack.get(n - 1);
                            if (arg.get("type").equals("Constant")) {
                                if (arg.get("const") instanceof CONSTANT_String_Info) {
                                    System.out.println(new String(CONSTANT_POOL
                                            .get(((CONSTANT_String_Info) arg.get("const")).getStringIndex() - 1)
                                            .getBytes(), StandardCharsets.UTF_8));
                                } else {
                                    throw new UnsupportedOperationException(
                                            "println for " + arg.get("const").getClass().getSimpleName()
                                                    + " is not implemented");
                                }
                            } else {
                                throw new UnsupportedOperationException(
                                        "Support for " + arg.get("type") + " is not implemented");
                            }
                        } else {
                            throw new UnsupportedOperationException(
                                    "Not supported method: " + className + "/" + memberName + " in getstatic");
                        }
                    }
                    case INVOKESPECIAL -> {
                        short index = ClassFile_Helper.readShort(codeData);
                        CP_Info methodRef = CONSTANT_POOL.get(index - 1);
                        String className = getNameOfClass(methodRef.getClassIndex());
                        String memberName = getNameOfMember(methodRef.getNameAndTypeIndex());

                        System.out.println(className);
                        System.out.println(memberName);
                    }
                    case INVOKESTATIC -> {
                        short index = ClassFile_Helper.readShort(codeData);
                        CP_Info methodRef = CONSTANT_POOL.get(index - 1);
                        String className = getNameOfClass(methodRef.getClassIndex());
                        String memberName = getNameOfMember(methodRef.getNameAndTypeIndex());

                        System.out.println(className);
                        System.out.println(memberName);
                    }
                    case NEW -> {
                        short index = ClassFile_Helper.readShort(codeData);
                        System.out.println(index);
                    }
                    case ARRAYLENGTH -> {
                        // TODO
                    }
                    default -> throw new UnsupportedOperationException(
                            "Not supported opcode: " + Opcode.opcodeRepresentation(opCode) + " ("
                                    + String.format("0x%02X", opCode)
                                    + ")");
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("Valid class file: " + VALID_CLASS_FILE + "\n");
        str.append("Minor version: " + MINOR_VERSION + "\n");
        str.append("Major version: " + MAJOR_VERSION + "\n");
        str.append("Constant pool count: " + CONSTANT_POOL_COUNT + "\n");
        str.append("Constant pool:\n");
        for (CP_Info CONSTANT : CONSTANT_POOL) {
            str.append(CONSTANT + "\n");
        }
        if (CONSTANT_POOL_COUNT == 0) {
            str.append("\n");
        }
        str.append("Access flags: " + ACCESS_FLAGS + "\n");
        str.append("This class: " + THIS_CLASS + "\n");
        str.append("Super class: " + SUPER_CLASS + "\n");
        str.append("Interfaces count: " + INTERFACES_COUNT);
        for (Interface INTERFACE : INTERFACES) {
            str.append(INTERFACE + "\n");
        }
        if (INTERFACES_COUNT == 0) {
            str.append("\n");
        }
        str.append("Fields count: " + FIELDS_COUNT + "\n");
        for (Field_Info FIELD : FIELDS) {
            str.append(FIELD + "\n");
        }
        if (FIELDS_COUNT == 0) {
            str.append("\n");
        }
        str.append("Methods count: " + METHODS_COUNT + "\n");
        for (Method_Info METHOD : METHODS) {
            str.append(METHOD + "\n");
        }
        if (METHODS_COUNT == 0) {
            str.append("\n");
        }
        str.append("Attributes count: " + ATTRIBUTES_COUNT + "\n");
        for (Attribute_Info ATTRIBUTE : ATTRIBUTES) {
            str.append(ATTRIBUTE + "\n");
        }

        return str.toString();
    }
}