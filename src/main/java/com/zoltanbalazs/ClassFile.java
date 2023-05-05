package com.zoltanbalazs;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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

    public static short readShort(byte[] data, CodeIndex start) {
        byte[] dataRead = { data[start.Next()], data[start.Next()] };

        return (short) (((dataRead[0] & 0xFF) << 8) | (dataRead[1] & 0xFF));
    }

    public static int readInt(DataInputStream classFileData) throws IOException {
        return classFileData.readInt();
    }

    public static int readInt(byte[] data, CodeIndex start) {
        byte[] dataRead = { data[start.Next()], data[start.Next()], data[start.Next()], data[start.Next()] };
        return (int) (((dataRead[0] & 0xFF) << 24) | ((dataRead[1] & 0xFF) << 16) | ((dataRead[2] & 0xFF) << 8)
                | (dataRead[3] & 0xFF));
    }
}

class CodeIndex {
    private int index = 0;

    public void Inc(int value) {
        index += value;
    }

    public int Next() {
        return index++;
    }

    public void Set(int value) {
        index = value;
    }

    public int Get() {
        return index;
    }
}

// https://docs.oracle.com/javase/specs/jvms/se19/html/jvms-4.html
// https://en.wikipedia.org/wiki/Java_class_file
class ClassFile {
    private static final byte[] MAGIC_NUMBER = HexFormat.of().parseHex("CAFEBABE");

    private static String FILE_NAME = "";
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

    private static List<Pair<Class<?>, Object>> stack = new ArrayList<>();
    private static Object[] local = new Object[65535];

    public ClassFile(String fileName, String[] mainArgs) {
        FILE_NAME = fileName;
        readClassFile(fileName);
        local[0] = mainArgs;
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

    public static String getNameOfClass(short class_index) {
        return new String(CONSTANT_POOL.get(CONSTANT_POOL.get(class_index - 1).getNameIndex() - 1).getBytes(),
                StandardCharsets.UTF_8);
    }

    public static String getNameOfMember(short name_and_type_index) {
        return new String(CONSTANT_POOL.get(CONSTANT_POOL.get(name_and_type_index - 1).getNameIndex() - 1).getBytes(),
                StandardCharsets.UTF_8);
    }

    public static String getDescriptionOfMethod(short name_and_type_index) {
        return new String(
                CONSTANT_POOL.get(CONSTANT_POOL.get(name_and_type_index - 1).getDescriptorIndex() - 1).getBytes(),
                StandardCharsets.UTF_8);
    }

    public static List<Class<?>> getArguments(String method_descriptions) throws ClassNotFoundException {
        return stringToTypes(
                method_descriptions.substring(method_descriptions.indexOf("(") + 1,
                        method_descriptions.indexOf(")")));
    }

    public static boolean isClassBuiltIn(String className) {
        try {
            Class.forName(className.replace("/", "."));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean doesMethodExists(Class<?> reference_to_class, String name_and_type_of_member,
            Class<?>[] types_of_function_paramaters) {
        try {
            reference_to_class.getDeclaredMethod(name_and_type_of_member, types_of_function_paramaters);
            return true;
        } catch (Throwable e) {
            for (Method method : reference_to_class.getDeclaredMethods()) {
                if (method.getName().equals(name_and_type_of_member)) {
                    for (int i = 0; i < method.getParameterTypes().length; ++i) {
                        Class<?> type = method.getParameterTypes()[i];
                        if (!type.getName().equals(types_of_function_paramaters[i].getName())) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
    }

    public static int getNumberOfArguments(short name_and_type_index) throws ClassNotFoundException {
        String descriptor = getDescriptionOfMethod(name_and_type_index);
        return getArguments(descriptor).size();
    }

    public Pair<Class<?>, Object> executeCode(byte[] code)
            throws IOException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException,
            NoSuchMethodException, SecurityException, InstantiationException, IllegalArgumentException,
            InvocationTargetException {

        CodeIndex codeIndex = new CodeIndex();
        while (codeIndex.Get() < code.length) {
            byte opCode = code[codeIndex.Next()];

            switch (Opcode.opcodeRepresentation(opCode)) {
                case NOP -> {
                }
                case ACONST_NULL -> {
                    Instructions.ACONST(stack);
                }
                case ICONST_M1, ICONST_0, ICONST_1, ICONST_2, ICONST_3, ICONST_4, ICONST_5 -> {
                    // ICONST_M1 is 0x02 .. ICONST_5 is 0x08
                    Instructions.ICONST(stack, opCode - 0x03);
                }
                case LCONST_0, LCONST_1 -> {
                    // LCONST_0 is 0x09, LCONST_1 is 0x0A
                    Instructions.LCONST(stack, opCode - 0x09);
                }
                case FCONST_0, FCONST_1, FCONST_2 -> {
                    // FCONST_0 is 0x0B .. FCONST_2 is 0x0D
                    Instructions.FCONST(stack, (float) (opCode - 0x0B));
                }
                case DCONST_0, DCONST_1 -> {
                    // DCONST_0 is 0x0E, DCONST_1 is 0x0F
                    Instructions.DCONST(stack, (double) (opCode - 0x0E));
                }
                case BIPUSH -> {
                    Instructions.BIPUSH(stack, (int) code[codeIndex.Next()]);
                }
                case SIPUSH -> {
                    Instructions.BIPUSH(stack, (int) ClassFile_Helper.readShort(code, codeIndex));
                }
                case LDC -> {
                    byte index = code[codeIndex.Next()];
                    Instructions.LDC(stack, CONSTANT_POOL, index);
                }
                case LDC_W -> {
                    short index = ClassFile_Helper.readShort(code, codeIndex);
                    Instructions.LDC(stack, CONSTANT_POOL, index);
                }
                case LDC2_W -> {
                    short index = ClassFile_Helper.readShort(code, codeIndex);
                    Instructions.LDC(stack, CONSTANT_POOL, index);
                }
                case ILOAD -> {
                    Instructions.ILOAD(stack, (int) local[code[codeIndex.Next()] & 0xFF]);
                }
                case LLOAD -> {
                    Instructions.LLOAD(stack, (long) local[code[codeIndex.Next()] & 0xFF]);
                }
                case FLOAD -> {
                    Instructions.FLOAD(stack, (float) local[code[codeIndex.Next()] & 0xFF]);
                }
                case DLOAD -> {
                    Instructions.DLOAD(stack, (double) local[code[codeIndex.Next()] & 0xFF]);
                }
                case ALOAD -> {
                    Instructions.ALOAD(stack, local[code[codeIndex.Next()] & 0xFF]);
                }
                case ILOAD_0, ILOAD_1, ILOAD_2, ILOAD_3 -> {
                    // ILOAD_0 is 0x1A .. ILOAD_3 is 0x1D
                    Instructions.ILOAD(stack, (int) local[opCode - 0x1A]);
                }
                case LLOAD_0, LLOAD_1, LLOAD_2, LLOAD_3 -> {
                    // LLOAD_0 is 0x1E .. ILOAD_3 is 0x21
                    Instructions.LLOAD(stack, (long) local[opCode - 0x1E]);
                }
                case FLOAD_0, FLOAD_1, FLOAD_2, FLOAD_3 -> {
                    // FLOAD_0 is 0x22 .. FLOAD_3 is 0x25
                    Instructions.FLOAD(stack, (float) local[opCode - 0x22]);
                }
                case DLOAD_0, DLOAD_1, DLOAD_2, DLOAD_3 -> {
                    // DLOAD_0 is 0x26 .. DLOAD_3 is 0x29
                    Instructions.DLOAD(stack, (double) local[opCode - 0x26]);
                }
                case ALOAD_0, ALOAD_1, ALOAD_2, ALOAD_3 -> {
                    // ALOAD_0 is 0x2A .. ALOAD_3 is 0x2D
                    Instructions.ALOAD(stack, local[opCode - 0x2A]);
                }
                case IALOAD -> {
                    Instructions.IALOAD(stack);
                }
                case LALOAD -> {
                    Instructions.LALOAD(stack);
                }
                case FALOAD -> {
                    Instructions.FALOAD(stack);
                }
                case DALOAD -> {
                    Instructions.DALOAD(stack);
                }
                case AALOAD -> {
                    Instructions.AALOAD(stack);
                }
                case BALOAD -> {
                    // Potential bug.. boolean is treated as byte!
                    Instructions.BALOAD(stack);
                }
                case CALOAD -> {
                    Instructions.CALOAD(stack);
                }
                case SALOAD -> {
                    Instructions.SALOAD(stack);
                }
                case ISTORE -> {
                    byte index = code[codeIndex.Next()];
                    Instructions.ISTORE(stack, local, index & 0xFF);
                }
                case LSTORE -> {
                    byte index = code[codeIndex.Next()];
                    Instructions.LSTORE(stack, local, index & 0xFF);
                }
                case FSTORE -> {
                    byte index = code[codeIndex.Next()];
                    Instructions.FSTORE(stack, local, index & 0xFF);
                }
                case DSTORE -> {
                    byte index = code[codeIndex.Next()];
                    Instructions.DSTORE(stack, local, index & 0xFF);
                }
                case ASTORE -> {
                    byte index = code[codeIndex.Next()];
                    Instructions.ASTORE(stack, local, index & 0xFF);
                }
                case ISTORE_0, ISTORE_1, ISTORE_2, ISTORE_3 -> {
                    // ISTORE_0 is 0x3B .. ISTORE_3 is 0x3E
                    Instructions.ISTORE(stack, local, opCode - 0x3B);
                }
                case LSTORE_0, LSTORE_1, LSTORE_2, LSTORE_3 -> {
                    // LSTORE_0 is 0x3F .. LSTORE_3 is 0x42
                    Instructions.LSTORE(stack, local, opCode - 0x3F);
                }
                case FSTORE_0, FSTORE_1, FSTORE_2, FSTORE_3 -> {
                    // FSTORE_0 is 0x43 .. FSTORE_3 is 0x46
                    Instructions.FSTORE(stack, local, opCode - 0x43);
                }
                case DSTORE_0, DSTORE_1, DSTORE_2, DSTORE_3 -> {
                    // DSTORE_0 is 0x47 .. LSTORE_3 is 0x4A
                    Instructions.DSTORE(stack, local, opCode - 0x47);
                }
                case ASTORE_0, ASTORE_1, ASTORE_2, ASTORE_3 -> {
                    // ASTORE_0 is 0x4B .. ASTORE_3 is 0x4E
                    Instructions.ASTORE(stack, local, opCode - 0x4B);
                }
                case IASTORE -> {
                    Instructions.IASTORE(stack);
                }
                case LASTORE -> {
                    Instructions.LASTORE(stack);
                }
                case FASTORE -> {
                    Instructions.FASTORE(stack);
                }
                case DASTORE -> {
                    Instructions.DASTORE(stack);
                }
                case AASTORE -> {
                    Instructions.AASTORE(stack);
                }
                case BASTORE -> {
                    Instructions.BASTORE(stack);
                }
                case CASTORE -> {
                    Instructions.CASTORE(stack);
                }
                case SASTORE -> {
                    Instructions.SASTORE(stack);
                }
                case POP -> {
                    Instructions.POP(stack, Opcode.POP);
                }
                case POP2 -> {
                    Instructions.POP(stack, Opcode.POP2);
                }
                case DUP -> {
                    Instructions.DUP(stack, Opcode.DUP);
                }
                case DUP_X1 -> {
                    Instructions.DUP(stack, Opcode.DUP_X1);
                }
                case DUP_X2 -> {
                    Instructions.DUP(stack, Opcode.DUP_X2);
                }
                case DUP2 -> {
                    Instructions.DUP2(stack, Opcode.DUP2);
                }
                case DUP2_X1 -> {
                    Instructions.DUP2(stack, Opcode.DUP2_X1);
                }
                case DUP2_X2 -> {
                    Instructions.DUP2(stack, Opcode.DUP2_X2);
                }
                case IADD -> {
                    Pair<Integer, Integer> values = Instructions.IARIT(stack);
                    stack.add(new Pair<Class<?>, Object>(int.class, values.first + values.second));
                }
                case LADD -> {
                    Pair<Long, Long> values = Instructions.LARIT(stack);
                    stack.add(new Pair<Class<?>, Object>(long.class, values.first + values.second));
                }
                case FADD -> {
                    Pair<Float, Float> values = Instructions.FARIT(stack);
                    stack.add(new Pair<Class<?>, Object>(float.class, values.first + values.second));
                }
                case DADD -> {
                    Pair<Double, Double> values = Instructions.DARIT(stack);
                    stack.add(new Pair<Class<?>, Object>(double.class, values.first + values.second));
                }
                case ISUB -> {
                    Pair<Integer, Integer> values = Instructions.IARIT(stack);
                    stack.add(new Pair<Class<?>, Object>(int.class, values.first - values.second));
                }
                case LSUB -> {
                    Pair<Long, Long> values = Instructions.LARIT(stack);
                    stack.add(new Pair<Class<?>, Object>(long.class, values.first - values.second));
                }
                case FSUB -> {
                    Pair<Float, Float> values = Instructions.FARIT(stack);
                    stack.add(new Pair<Class<?>, Object>(float.class, values.first - values.second));
                }
                case DSUB -> {
                    Pair<Double, Double> values = Instructions.DARIT(stack);
                    stack.add(new Pair<Class<?>, Object>(double.class, values.first - values.second));
                }
                case IMUL -> {
                    Pair<Integer, Integer> values = Instructions.IARIT(stack);
                    stack.add(new Pair<Class<?>, Object>(int.class, values.first * values.second));
                }
                case LMUL -> {
                    Pair<Long, Long> values = Instructions.LARIT(stack);
                    stack.add(new Pair<Class<?>, Object>(long.class, values.first * values.second));
                }
                case FMUL -> {
                    Pair<Float, Float> values = Instructions.FARIT(stack);
                    stack.add(new Pair<Class<?>, Object>(float.class, values.first * values.second));
                }
                case DMUL -> {
                    Pair<Double, Double> values = Instructions.DARIT(stack);
                    stack.add(new Pair<Class<?>, Object>(double.class, values.first * values.second));
                }
                case IDIV -> {
                    Pair<Integer, Integer> values = Instructions.IARIT(stack);
                    stack.add(new Pair<Class<?>, Object>(int.class, values.first / values.second));
                }
                case LDIV -> {
                    Pair<Long, Long> values = Instructions.LARIT(stack);
                    stack.add(new Pair<Class<?>, Object>(long.class, values.first / values.second));
                }
                case FDIV -> {
                    Pair<Float, Float> values = Instructions.FARIT(stack);
                    stack.add(new Pair<Class<?>, Object>(float.class, values.first / values.second));
                }
                case DDIV -> {
                    Pair<Double, Double> values = Instructions.DARIT(stack);
                    stack.add(new Pair<Class<?>, Object>(double.class, values.first / values.second));
                }
                case IREM -> {
                    Pair<Integer, Integer> values = Instructions.IARIT(stack);
                    stack.add(new Pair<Class<?>, Object>(int.class, values.first % values.second));
                }
                case LREM -> {
                    Pair<Long, Long> values = Instructions.LARIT(stack);
                    stack.add(new Pair<Class<?>, Object>(long.class, values.first % values.second));
                }
                case FREM -> {
                    Pair<Float, Float> values = Instructions.FARIT(stack);
                    stack.add(new Pair<Class<?>, Object>(float.class, values.first % values.second));
                }
                case DREM -> {
                    Pair<Double, Double> values = Instructions.DARIT(stack);
                    stack.add(new Pair<Class<?>, Object>(double.class, values.first % values.second));
                }
                case INEG -> {
                    int idx = stack.size() - 1;
                    stack.set(idx, new Pair<Class<?>, Object>(int.class, (int) stack.get(idx).second * -1));
                }
                case LNEG -> {
                    int idx = stack.size() - 1;
                    stack.set(idx, new Pair<Class<?>, Object>(long.class, (long) stack.get(idx).second * -1));
                }
                case FNEG -> {
                    int idx = stack.size() - 1;
                    stack.set(idx, new Pair<Class<?>, Object>(float.class, (float) stack.get(idx).second * -1));
                }
                case DNEG -> {
                    int idx = stack.size() - 1;
                    stack.set(idx, new Pair<Class<?>, Object>(double.class, (double) stack.get(idx).second * -1));
                }
                case ISHL -> {
                    Pair<Integer, Integer> values = Instructions.IARIT(stack);
                    stack.add(new Pair<Class<?>, Object>(int.class, values.first << values.second));
                }
                case LSHL -> {
                    Pair<Long, Long> values = Instructions.LARIT(stack);
                    stack.add(new Pair<Class<?>, Object>(long.class, values.first << values.second));
                }
                case ISHR -> {
                    Pair<Integer, Integer> values = Instructions.IARIT(stack);
                    stack.add(new Pair<Class<?>, Object>(int.class, values.first >> values.second));
                }
                case LSHR -> {
                    Pair<Long, Long> values = Instructions.LARIT(stack);
                    stack.add(new Pair<Class<?>, Object>(long.class, values.first >> values.second));
                }
                case IUSHR -> {
                    Pair<Integer, Integer> values = Instructions.IARIT(stack);
                    stack.add(new Pair<Class<?>, Object>(int.class, values.first >>> values.second));
                }
                case LUSHR -> {
                    Pair<Long, Long> values = Instructions.LARIT(stack);
                    stack.add(new Pair<Class<?>, Object>(long.class, values.first >>> values.second));
                }
                case IAND -> {
                    Pair<Integer, Integer> values = Instructions.IARIT(stack);
                    stack.add(new Pair<Class<?>, Object>(int.class, values.first & values.second));
                }
                case LAND -> {
                    Pair<Long, Long> values = Instructions.LARIT(stack);
                    stack.add(new Pair<Class<?>, Object>(long.class, values.first & values.second));
                }
                case IOR -> {
                    Pair<Integer, Integer> values = Instructions.IARIT(stack);
                    stack.add(new Pair<Class<?>, Object>(int.class, values.first | values.second));
                }
                case LOR -> {
                    Pair<Long, Long> values = Instructions.LARIT(stack);
                    stack.add(new Pair<Class<?>, Object>(long.class, values.first | values.second));
                }
                case IXOR -> {
                    Pair<Integer, Integer> values = Instructions.IARIT(stack);
                    stack.add(new Pair<Class<?>, Object>(int.class, values.first ^ values.second));
                }
                case LXOR -> {
                    Pair<Long, Long> values = Instructions.LARIT(stack);
                    stack.add(new Pair<Class<?>, Object>(long.class, values.first ^ values.second));
                }
                case IINC -> {
                    byte index = code[codeIndex.Next()];
                    byte constVal = code[codeIndex.Next()]; // Potential bug, constVal is a signed byte

                    Instructions.IINC(local, index & 0xFF, constVal);
                }
                case I2L -> {
                    Instructions.ICONV(stack, long.class);
                }
                case I2F -> {
                    Instructions.ICONV(stack, float.class);
                }
                case I2D -> {
                    Instructions.ICONV(stack, double.class);
                }
                case L2I -> {
                    Instructions.LCONV(stack, int.class);
                }
                case L2F -> {
                    Instructions.LCONV(stack, float.class);
                }
                case L2D -> {
                    Instructions.LCONV(stack, double.class);
                }
                case F2I -> {
                    Instructions.FCONV(stack, int.class);
                }
                case F2L -> {
                    Instructions.FCONV(stack, long.class);
                }
                case F2D -> {
                    Instructions.FCONV(stack, double.class);
                }
                case D2I -> {
                    Instructions.DCONV(stack, int.class);
                }
                case D2L -> {
                    Instructions.DCONV(stack, long.class);
                }
                case D2F -> {
                    Instructions.DCONV(stack, float.class);
                }
                case I2B -> {
                    Instructions.ICONV(stack, byte.class);
                }
                case I2C -> {
                    Instructions.ICONV(stack, char.class);
                }
                case I2S -> {
                    Instructions.ICONV(stack, short.class);
                }
                case LCMP -> {
                    Instructions.LCMP(stack);
                }
                case FCMPL -> {
                    Instructions.FCMP(stack, Opcode.FCMPL);
                }
                case FCMPG -> {
                    Instructions.FCMP(stack, Opcode.FCMPG);
                }
                case DCMPL -> {
                    Instructions.DCMP(stack, Opcode.DCMPL);
                }
                case DCMPG -> {
                    Instructions.DCMP(stack, Opcode.DCMPG);
                }
                case SWAP -> {
                    Instructions.SWAP(stack);
                }
                case IFEQ -> {
                    short offset = ClassFile_Helper.readShort(code, codeIndex);
                    Instructions.IF1I(codeIndex, offset, stack, Opcode.IFEQ);
                }
                case IFNE -> {
                    short offset = ClassFile_Helper.readShort(code, codeIndex);
                    Instructions.IF1I(codeIndex, offset, stack, Opcode.IFNE);
                }
                case IFLT -> {
                    short offset = ClassFile_Helper.readShort(code, codeIndex);
                    Instructions.IF1I(codeIndex, offset, stack, Opcode.IFLT);
                }
                case IFGE -> {
                    short offset = ClassFile_Helper.readShort(code, codeIndex);
                    Instructions.IF1I(codeIndex, offset, stack, Opcode.IFGE);
                }
                case IFGT -> {
                    short offset = ClassFile_Helper.readShort(code, codeIndex);
                    Instructions.IF1I(codeIndex, offset, stack, Opcode.IFGT);
                }
                case IFLE -> {
                    short offset = ClassFile_Helper.readShort(code, codeIndex);
                    Instructions.IF1I(codeIndex, offset, stack, Opcode.IFLE);
                }
                case IF_ICMPEQ -> {
                    short offset = ClassFile_Helper.readShort(code, codeIndex);
                    Instructions.IF2I(codeIndex, offset, stack, Opcode.IF_ICMPEQ);
                }
                case IF_ICMPNE -> {
                    short offset = ClassFile_Helper.readShort(code, codeIndex);
                    Instructions.IF2I(codeIndex, offset, stack, Opcode.IF_ICMPNE);
                }
                case IF_ICMPLT -> {
                    short offset = ClassFile_Helper.readShort(code, codeIndex);
                    Instructions.IF2I(codeIndex, offset, stack, Opcode.IF_ICMPLT);
                }
                case IF_ICMPGE -> {
                    short offset = ClassFile_Helper.readShort(code, codeIndex);
                    Instructions.IF2I(codeIndex, offset, stack, Opcode.IF_ICMPGE);
                }
                case IF_ICMPGT -> {
                    short offset = ClassFile_Helper.readShort(code, codeIndex);
                    Instructions.IF2I(codeIndex, offset, stack, Opcode.IF_ICMPGT);
                }
                case IF_ICMPLE -> {
                    short offset = ClassFile_Helper.readShort(code, codeIndex);
                    Instructions.IF2I(codeIndex, offset, stack, Opcode.IF_ICMPLE);
                }
                case IF_ACMPEQ -> {
                    short offset = ClassFile_Helper.readShort(code, codeIndex);
                    Instructions.IF2A(codeIndex, offset, stack, Opcode.IF_ACMPEQ);
                }
                case IF_ACMPNE -> {
                    short offset = ClassFile_Helper.readShort(code, codeIndex);
                    Instructions.IF2A(codeIndex, offset, stack, Opcode.IF_ACMPNE);
                }
                case GOTO -> {
                    short offset = ClassFile_Helper.readShort(code, codeIndex);
                    Instructions.GOTO(codeIndex, offset);
                }
                case JSR -> {
                    Instructions.JSR();
                }
                case RET -> {
                    Instructions.RET();
                }
                case TABLESWITCH -> {
                    // If switch cases are close enough
                    Instructions.TABLESWITCH(code, codeIndex, stack);
                }
                case LOOKUPSWITCH -> {
                    // If there are big gaps in the switch cases
                    Instructions.LOOKUPSWITCH(code, codeIndex, stack);
                }
                case IRETURN, LRETURN, FRETURN, DRETURN, ARETURN -> {
                    int stackSize = stack.size();
                    return stack.get(stackSize - 1);
                }
                case RETURN -> {
                    return new Pair<Class<?>, Object>(void.class, null);
                }
                case GETSTATIC -> {
                    // TODO
                    short index = ClassFile_Helper.readShort(code, codeIndex);
                    CP_Info fieldRef = CONSTANT_POOL.get(index - 1);
                    String className = getNameOfClass(fieldRef.getClassIndex());
                    String memberName = getNameOfMember(fieldRef.getNameAndTypeIndex());

                    Class<?> systemClass = Class.forName(className.replace("/", "."));
                    Field outField = systemClass.getField(memberName);

                    stack.add(new Pair<Class<?>, Object>(systemClass, outField.get(null)));
                }
                case PUTSTATIC -> {
                    // TODO
                    short index = ClassFile_Helper.readShort(code, codeIndex);
                    CP_Info fieldRef = CONSTANT_POOL.get(index - 1);
                    String className = getNameOfClass(fieldRef.getClassIndex());
                    String memberName = getNameOfMember(fieldRef.getNameAndTypeIndex());

                    System.out.println(className);
                    System.out.println(memberName);
                }
                case GETFIELD -> {
                    short index = ClassFile_Helper.readShort(code, codeIndex);
                    Instructions.GETFIELD(stack, CONSTANT_POOL, index);
                }
                case PUTFIELD -> {
                    short index = ClassFile_Helper.readShort(code, codeIndex);
                    Instructions.PUTFIELD(stack, CONSTANT_POOL, index);
                }
                case INVOKEVIRTUAL -> {
                    short index = ClassFile_Helper.readShort(code, codeIndex);
                    Instructions.INVOKEVIRTUAL(stack, CONSTANT_POOL, index, local, FILE_NAME);
                }
                case INVOKESPECIAL -> {
                    short index = ClassFile_Helper.readShort(code, codeIndex);
                    Instructions.INVOKESPECIAL(stack, CONSTANT_POOL, index, local, FILE_NAME);
                }
                case INVOKESTATIC -> {
                    short index = ClassFile_Helper.readShort(code, codeIndex);
                    Instructions.INVOKESTATIC(stack, CONSTANT_POOL, index, local, FILE_NAME);
                }
                case INVOKEINTERFACE -> {
                    short index = ClassFile_Helper.readShort(code, codeIndex);
                    byte count = code[codeIndex.Next()]; // TODO: Unsigned byte...
                    codeIndex.Next();

                    Instructions.INVOKEINTERFACE(stack, CONSTANT_POOL, index, count, local, FILE_NAME);
                }
                case INVOKEDYNAMIC -> {
                    short index = ClassFile_Helper.readShort(code, codeIndex);

                    // Discard..
                    codeIndex.Next();
                    codeIndex.Next();

                    Instructions.INVOKEDYNAMIC(stack, CONSTANT_POOL, index, local, FILE_NAME);
                }
                case NEW -> {
                    short index = ClassFile_Helper.readShort(code, codeIndex);
                    Instructions.NEW(stack, CONSTANT_POOL, index, local, FILE_NAME);
                }
                case NEWARRAY -> {
                    byte atype = code[codeIndex.Next()];
                    Instructions.NEWARRAY(stack, atype);
                }
                case ANEWARRAY -> {
                    short index = ClassFile_Helper.readShort(code, codeIndex);
                    Instructions.ANEWARRAY(stack, CONSTANT_POOL, index);
                }
                case ARRAYLENGTH -> {
                    Instructions.ARRAYLENGTH(stack);
                }
                case ATHROW -> {
                    // TODO
                }
                case CHECKCAST -> {
                    short index = ClassFile_Helper.readShort(code, codeIndex);
                    Instructions.CHECKCAST(stack, index, FILE_NAME);
                }
                case INSTANCEOF -> {
                    short index = ClassFile_Helper.readShort(code, codeIndex);
                    Instructions.INSTANCEOF(stack, index, FILE_NAME);
                }
                case MONITORENTER -> {
                    // TODO
                }
                case MONITOREXIT -> {
                    // TODO
                }
                case WIDE -> {
                    Opcode newOpcode = Opcode.opcodeRepresentation(code[codeIndex.Next()]);
                    Instructions.WIDE(code, codeIndex, stack, local, newOpcode);
                }
                case MULTIANEWARRAY -> {
                    short index = ClassFile_Helper.readShort(code, codeIndex);
                    byte dimensions = code[codeIndex.Next()];
                    Instructions.MULTIANEWARRAY(stack, CONSTANT_POOL, index, dimensions);
                }
                case IFNULL -> {
                    short offset = ClassFile_Helper.readShort(code, codeIndex);
                    Instructions.IF1A(codeIndex, offset, stack, Opcode.IFNULL);
                }
                case IFNONNULL -> {
                    short offset = ClassFile_Helper.readShort(code, codeIndex);
                    Instructions.IF1A(codeIndex, offset, stack, Opcode.IFNONNULL);
                }
                case GOTO_W -> {
                    int offset = ClassFile_Helper.readInt(code, codeIndex);
                    Instructions.GOTO_W(codeIndex, offset);
                }
                case JSR_W -> {
                    Instructions.JSR_W();
                }
            }
        }
        return new Pair<Class<?>, Object>(void.class, null); // Should throw an error...
    }

    public static Pair<Integer, String> decodeClassName(String argument) {
        String type = argument.substring(0 + 1);
        int endIdx = type.indexOf(";");
        type = type.substring(0, endIdx);
        return new Pair<Integer, String>(1 + endIdx + 1, type.replace("/", "."));
    }

    public static Pair<Integer, Class<?>> stringToType(String argument) throws ClassNotFoundException {
        switch (argument.charAt(0)) {
            case 'B' -> {
                return new Pair<Integer, Class<?>>(1, byte.class);
            }
            case 'C' -> {
                return new Pair<Integer, Class<?>>(1, char.class);
            }
            case 'D' -> {
                return new Pair<Integer, Class<?>>(1, double.class);
            }
            case 'F' -> {
                return new Pair<Integer, Class<?>>(1, float.class);
            }
            case 'I' -> {
                return new Pair<Integer, Class<?>>(1, int.class);
            }
            case 'J' -> {
                return new Pair<Integer, Class<?>>(1, int.class);
            }
            case 'L' -> {
                Pair<Integer, String> className = decodeClassName(argument);
                if (ClassFile.isClassBuiltIn(className.second)) {
                    return new Pair<Integer, Class<?>>(className.first, Class.forName(className.second));
                } else {
                    return new Pair<Integer, Class<?>>(className.first,
                            Instructions_Helper.LOAD_CLASS_FROM_OTHER_FILE(FILE_NAME, className.second));
                }

            }
            case 'S' -> {
                return new Pair<Integer, Class<?>>(1, short.class);
            }
            case 'Z' -> {
                return new Pair<Integer, Class<?>>(1, boolean.class);
            }
            case '[' -> {
                StringBuilder type = new StringBuilder();
                int idx = 0;
                while (argument.charAt(idx) == '[') {
                    type.append("[");
                    idx++;
                }
                if (argument.charAt(idx) == 'L') {
                    Pair<Integer, String> className = decodeClassName(argument.substring(idx, argument.length()));
                    type.append("L");
                    type.append(className.second);
                    type.append(";");
                    return new Pair<Integer, Class<?>>(idx + className.first, Class.forName(type.toString()));
                } else {
                    type.append(argument.charAt(idx));
                    return new Pair<Integer, Class<?>>(idx + 1, Class.forName(type.toString()));
                }
            }
            case 'V' -> {
                return new Pair<Integer, Class<?>>(1, void.class);
            }
            default -> {
                throw new IllegalStateException("Unknown type identifier: " + argument);
            }
        }
    }

    public static List<Class<?>> stringToTypes(String arguments) throws ClassNotFoundException {
        List<Class<?>> argTypes = new ArrayList<Class<?>>();

        int idx = 0;
        while (idx != arguments.length()) {
            Pair<Integer, Class<?>> currentType = stringToType(arguments.substring(idx, arguments.length()));
            argTypes.add(currentType.second);
            idx += currentType.first;
        }

        return argTypes;
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