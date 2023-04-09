package com.zoltanbalazs;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
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
    private static List<Access_Flags> ACCESS_FLAGS;
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
            ACCESS_FLAGS = Access_Flags.parseFlags(ClassFile_Helper.readShort(classFileData));
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
            System.err
                    .println("The file '" + fileName + "' contains invalid Constant Pool tag! Tag: " + e.getMessage());
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

    public void executeCode(byte[] code)
            throws IOException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        // TODO: Change from stack - args & types to just a single stack
        List<Object> stack = new ArrayList<>();
        List<Object> args = new ArrayList<>();
        List<Class<?>> types = new ArrayList<>();
        Object[] local = new Object[4];

        // TODO: Change from this to for loop
        try (InputStream codeStream = new ByteArrayInputStream(code);
                DataInputStream codeData = new DataInputStream(codeStream)) {

            while (codeData.available() != 0) {
                byte opCode = ClassFile_Helper.readByte(codeData);

                switch (Opcode.opcodeRepresentation(opCode)) {
                    case ICONST_M1, ICONST_0, ICONST_1, ICONST_2, ICONST_3, ICONST_4, ICONST_5 -> {
                        // ICONST_M1 is 0x02 .. ICONST_5 is 0x08
                        Instructions.ICONST(args, types, opCode - 0x03);
                    }
                    case LCONST_0, LCONST_1 -> {
                        // LCONST_0 is 0x09, LCONST_1 is 0x0A
                        Instructions.LCONST(args, types, opCode - 0x09);
                    }
                    case FCONST_0, FCONST_1, FCONST_2 -> {
                        // FCONST_0 is 0x0B .. FCONST_2 is 0x0D
                        Instructions.FCONST(args, types, (float) (opCode - 0x0B));
                    }
                    case DCONST_0, DCONST_1 -> {
                        // DCONST_0 is 0x0E, DCONST_1 is 0x0F
                        Instructions.DCONST(args, types, (double) (opCode - 0x0E));
                    }
                    case BIPUSH -> {
                        Instructions.BIPUSH(args, types, (int) ClassFile_Helper.readByte(codeData));
                    }
                    case SIPUSH -> {
                        Instructions.BIPUSH(args, types, (int) ClassFile_Helper.readShort(codeData));
                    }
                    case LDC -> {
                        byte index = ClassFile_Helper.readByte(codeData);
                        ConstantPoolTag tag = CONSTANT_POOL.get(index - 1).tag;

                        Class<?> type = Instructions_Helper.tagSwitchType(CONSTANT_POOL, tag);
                        Object value = Instructions_Helper.tagSwitchValue(CONSTANT_POOL, tag, index);

                        Instructions.LDC(args, types, type, value);
                    }
                    case LDC_W -> {
                        short index = ClassFile_Helper.readShort(codeData);
                        ConstantPoolTag tag = CONSTANT_POOL.get(index - 1).tag;

                        Class<?> type = Instructions_Helper.tagSwitchType(CONSTANT_POOL, tag);
                        Object value = Instructions_Helper.tagSwitchValue(CONSTANT_POOL, tag, index);

                        Instructions.LDC(args, types, type, value);
                    }
                    case LDC2_W -> {
                        short index = ClassFile_Helper.readShort(codeData);
                        ConstantPoolTag tag = CONSTANT_POOL.get(index - 1).tag;

                        Class<?> type = Instructions_Helper.tagSwitchType(CONSTANT_POOL, tag);
                        Object value = Instructions_Helper.tagSwitchValue(CONSTANT_POOL, tag, index);

                        Instructions.LDC(args, types, type, value);
                    }
                    case ILOAD -> {
                        Instructions.ILOAD(args, types, (int) local[ClassFile_Helper.readByte(codeData)]);
                    }
                    case LLOAD -> {
                        Instructions.LLOAD(args, types, (long) local[ClassFile_Helper.readByte(codeData)]);
                    }
                    case FLOAD -> {
                        Instructions.FLOAD(args, types, (float) local[ClassFile_Helper.readByte(codeData)]);
                    }
                    case DLOAD -> {
                        Instructions.DLOAD(args, types, (double) local[ClassFile_Helper.readByte(codeData)]);
                    }
                    case ALOAD -> {
                        Instructions.ALOAD(args, types, local[ClassFile_Helper.readByte(codeData)]);
                    }
                    case ILOAD_0, ILOAD_1, ILOAD_2, ILOAD_3 -> {
                        // ILOAD_0 is 0x1A .. ILOAD_3 is 0x1D
                        Instructions.ILOAD(args, types, (int) local[opCode - 0x1A]);
                    }
                    case LLOAD_0, LLOAD_1, LLOAD_2, LLOAD_3 -> {
                        // LLOAD_0 is 0x1E .. ILOAD_3 is 0x21
                        Instructions.LLOAD(args, types, (long) local[opCode - 0x1E]);
                    }
                    case FLOAD_0, FLOAD_1, FLOAD_2, FLOAD_3 -> {
                        // FLOAD_0 is 0x22 .. FLOAD_3 is 0x25
                        Instructions.FLOAD(args, types, (float) local[opCode - 0x22]);
                    }
                    case DLOAD_0, DLOAD_1, DLOAD_2, DLOAD_3 -> {
                        // DLOAD_0 is 0x26 .. DLOAD_3 is 0x29
                        Instructions.DLOAD(args, types, (double) local[opCode - 0x26]);
                    }
                    case ALOAD_0, ALOAD_1, ALOAD_2, ALOAD_3 -> {
                        // ALOAD_0 is 0x2A .. ALOAD_3 is 0x2D
                        Instructions.ALOAD(args, types, local[opCode - 0x2A]);
                    }
                    case IALOAD -> {
                        // TODO
                    }
                    case LALOAD -> {
                        // TODO
                    }
                    case FALOAD -> {
                        // TODO
                    }
                    case DALOAD -> {
                        // TODO
                    }
                    case AALOAD -> {
                        // TODO
                    }
                    case BALOAD -> {
                        // TODO
                    }
                    case CALOAD -> {
                        // TODO
                    }
                    case SALOAD -> {
                        // TODO
                    }
                    case ISTORE -> {
                        // TODO
                    }
                    case LSTORE -> {
                        // TODO
                    }
                    case FSTORE -> {
                        // TODO
                    }
                    case DSTORE -> {
                        // TODO
                    }
                    case ASTORE -> {
                        // TODO
                    }
                    case ISTORE_0, ISTORE_1, ISTORE_2, ISTORE_3 -> {
                        Object value = args.remove(args.size() - 1);
                        local[opCode - 0x3B] = (int) value; // ISTORE_0 is 0x3B .. ISTORE_3 is 0x3E
                    }
                    case ASTORE_0, ASTORE_1, ASTORE_2, ASTORE_3 -> {
                        // TODO
                    }
                    case DUP -> {
                        // TODO
                    }
                    case IADD -> {
                        Pair<Integer, Integer> values = Instructions.IARIT(args, types);

                        types.add(int.class);
                        args.add(values.first + values.second);
                    }
                    case LADD -> {
                        Pair<Long, Long> values = Instructions.LARIT(args, types);

                        types.add(long.class);
                        args.add(values.first + values.second);
                    }
                    case FADD -> {
                        Pair<Float, Float> values = Instructions.FARIT(args, types);

                        types.add(float.class);
                        args.add(values.first + values.second);
                    }
                    case DADD -> {
                        Pair<Double, Double> values = Instructions.DARIT(args, types);

                        types.add(Double.class);
                        args.add(values.first + values.second);
                    }
                    case ISUB -> {
                        Pair<Integer, Integer> values = Instructions.IARIT(args, types);

                        types.add(int.class);
                        args.add(values.first - values.second);
                    }
                    case LSUB -> {
                        Pair<Long, Long> values = Instructions.LARIT(args, types);

                        types.add(long.class);
                        args.add(values.first - values.second);
                    }
                    case FSUB -> {
                        Pair<Float, Float> values = Instructions.FARIT(args, types);

                        types.add(float.class);
                        args.add(values.first - values.second);
                    }
                    case DSUB -> {
                        Pair<Double, Double> values = Instructions.DARIT(args, types);

                        types.add(Double.class);
                        args.add(values.first - values.second);
                    }
                    case IMUL -> {
                        Pair<Integer, Integer> values = Instructions.IARIT(args, types);

                        types.add(int.class);
                        args.add(values.first * values.second);
                    }
                    case LMUL -> {
                        Pair<Long, Long> values = Instructions.LARIT(args, types);

                        types.add(long.class);
                        args.add(values.first * values.second);
                    }
                    case FMUL -> {
                        Pair<Float, Float> values = Instructions.FARIT(args, types);

                        types.add(float.class);
                        args.add(values.first * values.second);
                    }
                    case DMUL -> {
                        Pair<Double, Double> values = Instructions.DARIT(args, types);

                        types.add(Double.class);
                        args.add(values.first * values.second);
                    }
                    case IDIV -> {
                        Pair<Integer, Integer> values = Instructions.IARIT(args, types);

                        types.add(int.class);
                        args.add(values.first / values.second);
                    }
                    case LDIV -> {
                        Pair<Long, Long> values = Instructions.LARIT(args, types);

                        types.add(long.class);
                        args.add(values.first / values.second);
                    }
                    case FDIV -> {
                        Pair<Float, Float> values = Instructions.FARIT(args, types);

                        types.add(float.class);
                        args.add(values.first / values.second);
                    }
                    case DDIV -> {
                        Pair<Double, Double> values = Instructions.DARIT(args, types);

                        types.add(Double.class);
                        args.add(values.first / values.second);
                    }
                    case IREM -> {
                        Pair<Integer, Integer> values = Instructions.IARIT(args, types);

                        types.add(int.class);
                        args.add(values.first % values.second);
                    }
                    case LREM -> {
                        Pair<Long, Long> values = Instructions.LARIT(args, types);

                        types.add(long.class);
                        args.add(values.first % values.second);
                    }
                    case FREM -> {
                        Pair<Float, Float> values = Instructions.FARIT(args, types);

                        types.add(float.class);
                        args.add(values.first % values.second);
                    }
                    case DREM -> {
                        Pair<Double, Double> values = Instructions.DARIT(args, types);

                        types.add(Double.class);
                        args.add(values.first % values.second);
                    }
                    case INEG -> {
                        int value = (int) args.remove(args.size() - 1);
                        args.add(value * -1);
                    }
                    case LNEG -> {
                        long value = (long) args.remove(args.size() - 1);
                        args.add(value * -1);
                    }
                    case FNEG -> {
                        float value = (float) args.remove(args.size() - 1);
                        args.add(value * -1);
                    }
                    case DNEG -> {
                        double value = (double) args.remove(args.size() - 1);
                        args.add(value * -1);
                    }
                    case ISHL -> {
                        Pair<Integer, Integer> values = Instructions.IARIT(args, types);

                        types.add(int.class);
                        args.add(values.first << values.second);
                    }
                    case LSHL -> {
                        Pair<Long, Long> values = Instructions.LARIT(args, types);

                        types.add(long.class);
                        args.add(values.first << values.second);
                    }
                    case ISHR -> {
                        Pair<Integer, Integer> values = Instructions.IARIT(args, types);

                        types.add(int.class);
                        args.add(values.first >> values.second);
                    }
                    case LSHR -> {
                        Pair<Long, Long> values = Instructions.LARIT(args, types);

                        types.add(long.class);
                        args.add(values.first >> values.second);
                    }
                    case IUSHR -> {
                        Pair<Integer, Integer> values = Instructions.IARIT(args, types);

                        types.add(int.class);
                        args.add(values.first >>> values.second);
                    }
                    case LUSHR -> {
                        Pair<Long, Long> values = Instructions.LARIT(args, types);

                        types.add(long.class);
                        args.add(values.first >>> values.second);
                    }
                    case IAND -> {
                        Pair<Integer, Integer> values = Instructions.IARIT(args, types);

                        types.add(int.class);
                        args.add(values.first & values.second);
                    }
                    case LAND -> {
                        Pair<Long, Long> values = Instructions.LARIT(args, types);

                        types.add(long.class);
                        args.add(values.first & values.second);
                    }
                    case IOR -> {
                        Pair<Integer, Integer> values = Instructions.IARIT(args, types);

                        types.add(int.class);
                        args.add(values.first | values.second);
                    }
                    case LOR -> {
                        Pair<Long, Long> values = Instructions.LARIT(args, types);

                        types.add(long.class);
                        args.add(values.first | values.second);
                    }
                    case IXOR -> {
                        Pair<Integer, Integer> values = Instructions.IARIT(args, types);

                        types.add(int.class);
                        args.add(values.first ^ values.second);
                    }
                    case LXOR -> {
                        Pair<Long, Long> values = Instructions.LARIT(args, types);

                        types.add(long.class);
                        args.add(values.first ^ values.second);
                    }
                    case IINC -> {
                        byte index = ClassFile_Helper.readByte(codeData);
                        byte constVal = ClassFile_Helper.readByte(codeData); // Potential bug, constVal is a signed byte

                        local[index] = (int) local[index] + constVal;
                    }
                    case I2L -> {
                        Instructions.ICONV(args, types, long.class);
                    }
                    case I2F -> {
                        Instructions.ICONV(args, types, float.class);
                    }
                    case I2D -> {
                        Instructions.ICONV(args, types, double.class);
                    }
                    case L2I -> {
                        Instructions.LCONV(args, types, int.class);
                    }
                    case L2F -> {
                        Instructions.LCONV(args, types, float.class);
                    }
                    case L2D -> {
                        Instructions.LCONV(args, types, double.class);
                    }
                    case F2I -> {
                        Instructions.FCONV(args, types, int.class);
                    }
                    case F2L -> {
                        Instructions.FCONV(args, types, long.class);
                    }
                    case F2D -> {
                        Instructions.FCONV(args, types, double.class);
                    }
                    case D2I -> {
                        Instructions.DCONV(args, types, int.class);
                    }
                    case D2L -> {
                        Instructions.DCONV(args, types, long.class);
                    }
                    case D2F -> {
                        Instructions.DCONV(args, types, float.class);
                    }
                    case I2B -> {
                        Instructions.ICONV(args, types, Byte.class);
                    }
                    case I2C -> {
                        Instructions.ICONV(args, types, Character.class);
                    }
                    case I2S -> {
                        Instructions.ICONV(args, types, Short.class);
                    }
                    case LCMP -> {
                        Instructions.LCMP(args, types);
                    }
                    case FCMPL -> {
                        Instructions.FCMP(args, types, true);
                    }
                    case FCMPG -> {
                        Instructions.FCMP(args, types, false);
                    }
                    case DCMPL -> {
                        Instructions.DCMP(args, types, true);
                    }
                    case DCMPG -> {
                        Instructions.DCMP(args, types, false);
                    }
                    case IFNE -> {
                        short offset = ClassFile_Helper.readShort(codeData);
                        System.out.println(offset);
                    }
                    case IF_ICMPNE -> {
                        byte branchbyte1 = ClassFile_Helper.readByte(codeData);
                        byte branchbyte2 = ClassFile_Helper.readByte(codeData);

                        int value1 = (int) args.get(args.size() - 2);
                        int value2 = (int) args.get(args.size() - 1);

                        if (value1 == value2) {
                            int branch = branchbyte1 << 8 | branchbyte2;
                            System.out.println(branch);
                        }
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

                        Class<?> systemClass = Class.forName(className.replace("/", "."));
                        Field outField = systemClass.getField(memberName);

                        stack.add(outField.get(null));
                    }
                    case PUTSTATIC -> {
                        short index = ClassFile_Helper.readShort(codeData);
                        CP_Info fieldRef = CONSTANT_POOL.get(index - 1);
                        String className = getNameOfClass(fieldRef.getClassIndex());
                        String memberName = getNameOfMember(fieldRef.getNameAndTypeIndex());

                        System.out.println(className);
                        System.out.println(memberName);
                    }
                    case INVOKEVIRTUAL -> {
                        short index = ClassFile_Helper.readShort(codeData);
                        CP_Info methodRef = CONSTANT_POOL.get(index - 1);
                        String className = getNameOfClass(methodRef.getClassIndex());
                        String memberName = getNameOfMember(methodRef.getNameAndTypeIndex());

                        try {
                            Object arg = args.remove(args.size() - 1);
                            // TODO: Use type from look-up table, there are only a handful of primitives..
                            Class<?> type = types.remove(types.size() - 1);

                            Class<?> classClass = Class.forName(className.replace("/", "."));
                            Method method = classClass.getDeclaredMethod(memberName, type);

                            Object result;

                            if (type == int.class) {
                                result = method.invoke(stack.remove(stack.size() - 1), (int) arg);
                            } else if (type == float.class) {
                                result = method.invoke(stack.remove(stack.size() - 1), (float) arg);
                            } else if (type == double.class) {
                                result = method.invoke(stack.remove(stack.size() - 1), (double) arg);
                            } else if (type == long.class) {
                                result = method.invoke(stack.remove(stack.size() - 1), (long) arg);
                            } else {
                                result = method.invoke(stack.remove(stack.size() - 1), type.cast(arg));
                            }

                            stack.add(result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    case INVOKESPECIAL -> {
                        short index = ClassFile_Helper.readShort(codeData);
                        CP_Info methodRef = CONSTANT_POOL.get(index - 1);
                        String className = getNameOfClass(methodRef.getClassIndex());
                        String memberName = getNameOfMember(methodRef.getNameAndTypeIndex());

                        try {
                            Class<?> c = Class.forName(className.replace("/", "."));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        System.out.println(className);
                        System.out.println(memberName);
                    }
                    case INVOKESTATIC -> {
                        short index = ClassFile_Helper.readShort(codeData);
                        CP_Info methodRef = CONSTANT_POOL.get(index - 1);
                        String className = getNameOfClass(methodRef.getClassIndex());
                        String memberName = getNameOfMember(methodRef.getNameAndTypeIndex());

                        try {
                            Object arg = args.remove(args.size() - 1);
                            // TODO: Use type from look-up table, there are only a handful of primitives..
                            Class<?> type = types.remove(types.size() - 1);

                            // File f = new File ("Test/Main.class");
                            // URL[] cp = { f.toURI().toURL() };
                            // URLClassLoader urlcl = new URLClassLoader(cp);
                            // Class<?> dd = urlcl.loadClass("main.Main");

                            Class<?> classClass = Class.forName(className.replace("/", "."));
                            Method method = classClass.getDeclaredMethod(memberName, type);

                            if (type == int.class) {
                                method.invoke(stack.remove(stack.size() - 1), (int) arg);
                            } else if (type == float.class) {
                                method.invoke(stack.remove(stack.size() - 1), (float) arg);
                            } else if (type == double.class) {
                                method.invoke(stack.remove(stack.size() - 1), (double) arg);
                            } else if (type == long.class) {
                                method.invoke(stack.remove(stack.size() - 1), (long) arg);
                            } else {
                                method.invoke(stack.remove(stack.size() - 1), type.cast(arg));
                            }
                        } catch (ClassNotFoundException e) {
                            Method_Info method = findMethodsByName(memberName);
                            List<Attribute_Info> attributes = findAttributesByName(method.attributes, "Code");

                            for (Attribute_Info attribute : attributes) {
                                try {
                                    Code_Attribute codeAttribute = Code_Attribute_Helper.readCodeAttributes(attribute);

                                    executeCode(codeAttribute.code);
                                } catch (Exception ee) {
                                    ee.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
        if (CONSTANT_POOL_COUNT != 0) {
            str.append("Constant pool:\n");
            for (int i = 0; i < CONSTANT_POOL_COUNT - 1; ++i) {
                str.append("\t #" + i + " " + CONSTANT_POOL.get(i) + "\n");
            }
        }
        str.append("Access flags: " + ACCESS_FLAGS + "\n");
        str.append("This class: " + THIS_CLASS + "\n");
        str.append("Super class: " + SUPER_CLASS + "\n");
        str.append("Interface count: " + INTERFACES_COUNT + "\n");
        for (int i = 0; i < INTERFACES_COUNT; ++i) {
            str.append("\t #" + i + " " + INTERFACES.get(i) + "\n");
        }
        str.append("Field count: " + FIELDS_COUNT + "\n");
        for (int i = 0; i < FIELDS_COUNT; ++i) {
            str.append("\t #" + i + " " + FIELDS.get(i) + "\n");
        }
        str.append("Methods count: " + METHODS_COUNT + "\n");
        for (int i = 0; i < METHODS_COUNT; ++i) {
            str.append("\t #" + i + " " + METHODS.get(i) + "\n");
        }
        str.append("Attribute count: " + ATTRIBUTES_COUNT + "\n");
        for (int i = 0; i < ATTRIBUTES_COUNT; ++i) {
            str.append("\t #" + i + " " + ATTRIBUTES.get(i) + "\n");
        }
        if (ATTRIBUTES_COUNT == 0) {
            str.append("\n");
        }
        str.deleteCharAt(str.length() - 1);

        return str.toString();
    }
}