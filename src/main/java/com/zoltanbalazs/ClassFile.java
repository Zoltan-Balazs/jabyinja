package com.zoltanbalazs;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
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

    private static List<Pair<Class<?>, Object>> stack = new ArrayList<>();
    private static Object[] local = new Object[4];

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
            throws IOException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException,
            NoSuchMethodException, SecurityException, InstantiationException, IllegalArgumentException,
            InvocationTargetException {

        // TODO: Change from this to for loop
        try (InputStream codeStream = new ByteArrayInputStream(code);
                DataInputStream codeData = new DataInputStream(codeStream)) {

            while (codeData.available() != 0) {
                byte opCode = ClassFile_Helper.readByte(codeData);

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
                        Instructions.BIPUSH(stack, (int) ClassFile_Helper.readByte(codeData));
                    }
                    case SIPUSH -> {
                        Instructions.BIPUSH(stack, (int) ClassFile_Helper.readShort(codeData));
                    }
                    case LDC -> {
                        byte index = ClassFile_Helper.readByte(codeData);
                        Instructions.LDC(stack, CONSTANT_POOL, index);
                    }
                    case LDC_W -> {
                        short index = ClassFile_Helper.readShort(codeData);
                        Instructions.LDC(stack, CONSTANT_POOL, index);
                    }
                    case LDC2_W -> {
                        short index = ClassFile_Helper.readShort(codeData);
                        Instructions.LDC(stack, CONSTANT_POOL, index);
                    }
                    case ILOAD -> {
                        Instructions.ILOAD(stack, (int) local[ClassFile_Helper.readByte(codeData)]);
                    }
                    case LLOAD -> {
                        Instructions.LLOAD(stack, (long) local[ClassFile_Helper.readByte(codeData)]);
                    }
                    case FLOAD -> {
                        Instructions.FLOAD(stack, (float) local[ClassFile_Helper.readByte(codeData)]);
                    }
                    case DLOAD -> {
                        Instructions.DLOAD(stack, (double) local[ClassFile_Helper.readByte(codeData)]);
                    }
                    case ALOAD -> {
                        Instructions.ALOAD(stack, local[ClassFile_Helper.readByte(codeData)]);
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
                        byte index = ClassFile_Helper.readByte(codeData);
                        Instructions.ISTORE(stack, local, index);
                    }
                    case LSTORE -> {
                        byte index = ClassFile_Helper.readByte(codeData);
                        Instructions.LSTORE(stack, local, index);
                    }
                    case FSTORE -> {
                        byte index = ClassFile_Helper.readByte(codeData);
                        Instructions.FSTORE(stack, local, index);
                    }
                    case DSTORE -> {
                        byte index = ClassFile_Helper.readByte(codeData);
                        Instructions.DSTORE(stack, local, index);
                    }
                    case ASTORE -> {
                        byte index = ClassFile_Helper.readByte(codeData);
                        Instructions.ASTORE(stack, local, index);
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
                        // TODO
                    }
                    case LASTORE -> {
                        // TODO
                    }
                    case FASTORE -> {
                        // TODO
                    }
                    case DASTORE -> {
                        // TODO
                    }
                    case AASTORE -> {
                        // TODO
                    }
                    case BASTORE -> {
                        // TODO
                    }
                    case CASTORE -> {
                        // TODO
                    }
                    case SASTORE -> {
                        // TODO
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
                        byte index = ClassFile_Helper.readByte(codeData);
                        byte constVal = ClassFile_Helper.readByte(codeData); // Potential bug, constVal is a signed byte

                        local[index] = (int) local[index] + constVal;
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
                        short offset = ClassFile_Helper.readShort(codeData);
                        System.out.println(offset);
                    }
                    case IFNE -> {
                        short offset = ClassFile_Helper.readShort(codeData);
                        System.out.println(offset);
                    }
                    case IFLT -> {
                        short offset = ClassFile_Helper.readShort(codeData);
                        System.out.println(offset);
                    }
                    case IFGE -> {
                        short offset = ClassFile_Helper.readShort(codeData);
                        System.out.println(offset);
                    }
                    case IFGT -> {
                        short offset = ClassFile_Helper.readShort(codeData);
                        System.out.println(offset);
                    }
                    case IFLE -> {
                        short offset = ClassFile_Helper.readShort(codeData);
                        System.out.println(offset);
                    }
                    case IF_ICMPEQ -> {
                        short offset = ClassFile_Helper.readShort(codeData);
                        System.out.println(offset);
                    }
                    case IF_ICMPNE -> {
                        byte branchbyte1 = ClassFile_Helper.readByte(codeData);
                        byte branchbyte2 = ClassFile_Helper.readByte(codeData);

                        int value1 = 0;// (int) args.get(args.size() - 2);
                        int value2 = 0;// (int) args.get(args.size() - 1);

                        if (value1 == value2) {
                            int branch = branchbyte1 << 8 | branchbyte2;
                            System.out.println(branch);
                        }
                    }
                    case IF_ICMPLT -> {
                        short offset = ClassFile_Helper.readShort(codeData);
                        System.out.println(offset);
                    }
                    case IF_ICMPGE -> {
                        short offset = ClassFile_Helper.readShort(codeData);
                        System.out.println(offset);
                    }
                    case IF_ICMPGT -> {
                        short offset = ClassFile_Helper.readShort(codeData);
                        System.out.println(offset);
                    }
                    case IF_ICMPLE -> {
                        short offset = ClassFile_Helper.readShort(codeData);
                        System.out.println(offset);
                    }
                    case IF_ACMPEQ -> {
                        short offset = ClassFile_Helper.readShort(codeData);
                        System.out.println(offset);
                    }
                    case IF_ACMPNE -> {
                        short offset = ClassFile_Helper.readShort(codeData);
                        System.out.println(offset);
                    }
                    case GOTO -> {
                        short index = ClassFile_Helper.readShort(codeData);
                        System.out.println(index);
                    }
                    case JSR -> {
                        // WILL NOT IMPLEMENT
                        throw new UnsupportedOperationException(
                                "JSR is deprecated since Java 7, this program only supports Java 7+ class files");
                    }
                    case RET -> {
                        // WILL NOT IMPLEMENT
                        throw new UnsupportedOperationException(
                                "RET is (effectively) deprecated since Java 7, this program only supports Java 7+ class files");
                    }
                    case TABLESWITCH -> {
                        // TODO ?
                    }
                    case LOOKUPSWITCH -> {
                        // TODO ?
                    }
                    case IRETURN -> {
                        // TODO
                    }
                    case LRETURN -> {
                        // TODO
                    }
                    case FRETURN -> {
                        // TODO
                    }
                    case DRETURN -> {
                        // TODO
                    }
                    case ARETURN -> {
                        // TODO
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

                        stack.add(new Pair<Class<?>, Object>(systemClass, outField.get(null)));
                    }
                    case PUTSTATIC -> {
                        short index = ClassFile_Helper.readShort(codeData);
                        CP_Info fieldRef = CONSTANT_POOL.get(index - 1);
                        String className = getNameOfClass(fieldRef.getClassIndex());
                        String memberName = getNameOfMember(fieldRef.getNameAndTypeIndex());

                        System.out.println(className);
                        System.out.println(memberName);
                    }
                    case GETFIELD -> {
                        // TODO
                    }
                    case PUTFIELD -> {
                        // TODO
                    }
                    case INVOKEVIRTUAL -> {
                        short index = ClassFile_Helper.readShort(codeData);
                        CP_Info methodRef = CONSTANT_POOL.get(index - 1);
                        String className = getNameOfClass(methodRef.getClassIndex());
                        String memberName = getNameOfMember(methodRef.getNameAndTypeIndex());

                        try {
                            Pair<Class<?>, Object> objectref = stack.remove(0);
                            List<Pair<Class<?>, Object>> args = stack;
                            List<Object> arg = new ArrayList<Object>();
                            List<Class<?>> type = new ArrayList<Class<?>>();
                            for (var a : args) {
                                Class<?> a_type = a.first;
                                Object curr_arg = a.second;
                                if (a_type == int.class) {
                                    arg.add((int) curr_arg);
                                } else if (a_type == float.class) {
                                    arg.add((float) curr_arg);
                                } else if (a_type == double.class) {
                                    arg.add((double) curr_arg);
                                } else if (a_type == long.class) {
                                    arg.add((long) curr_arg);
                                } else {
                                    arg.add(a_type.cast(curr_arg));
                                }
                                type.add(a.first);
                            }

                            Class<?>[] types = new Class<?>[type.size()];
                            for (int i = 0; i < type.size(); ++i) {
                                types[i] = (Class<?>) type.get(i);
                            }

                            Class<?> classClass = Class.forName(className.replace("/", "."));
                            Method method = classClass.getDeclaredMethod(memberName, types);

                            Object[] arguments = new Object[arg.size()];
                            for (int i = 0; i < arg.size(); ++i) {
                                arguments[i] = (Object) arg.get(i);
                            }

                            Object result = method.invoke(objectref.second, arguments);

                            stack.removeAll(stack);

                            if (result != null) {
                                stack.add(new Pair<Class<?>, Object>(result.getClass(), result));
                            }
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
                    // TODO: Recursive call.. push back to stack
                    case INVOKESTATIC -> {
                        short index = ClassFile_Helper.readShort(codeData);
                        CP_Info methodRef = CONSTANT_POOL.get(index - 1);
                        String className = getNameOfClass(methodRef.getClassIndex());
                        String memberName = getNameOfMember(methodRef.getNameAndTypeIndex());

                        try {
                            Pair<Class<?>, Object> objectref = stack.remove(0);
                            List<Pair<Class<?>, Object>> args = stack;
                            List<Object> arg = new ArrayList<Object>();
                            List<Class<?>> type = new ArrayList<Class<?>>();
                            for (var a : args) {
                                Class<?> a_type = a.first;
                                Object curr_arg = a.second;
                                if (a_type == int.class) {
                                    arg.add((int) curr_arg);
                                } else if (a_type == float.class) {
                                    arg.add((float) curr_arg);
                                } else if (a_type == double.class) {
                                    arg.add((double) curr_arg);
                                } else if (a_type == long.class) {
                                    arg.add((long) curr_arg);
                                } else {
                                    arg.add(a_type.cast(curr_arg));
                                }
                                type.add(a.first);
                            }

                            Class<?>[] types = new Class<?>[type.size()];
                            for (int i = 0; i < type.size(); ++i) {
                                types[i] = (Class<?>) type.get(i);
                            }

                            Class<?> classClass = Class.forName(className.replace("/", "."));
                            Method method = classClass.getDeclaredMethod(memberName, types);

                            Object[] arguments = new Object[arg.size()];
                            for (int i = 0; i < arg.size(); ++i) {
                                arguments[i] = (Object) arg.get(i);
                            }

                            Object result = method.invoke(objectref.second, arguments);

                            stack.removeAll(stack);

                            if (result != null) {
                                stack.add(new Pair<Class<?>, Object>(result.getClass(), result));
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
                    case INVOKEINTERFACE -> {
                        // TODO
                    }
                    case INVOKEDYNAMIC -> {
                        // TODO
                    }
                    case NEW -> {
                        short index = ClassFile_Helper.readShort(codeData);
                        CP_Info classRef = (CONSTANT_Class_Info) CONSTANT_POOL.get(index - 1);
                        String memberName = getNameOfMember(index);

                        Class<?> classClass = Class.forName(memberName.replace("/", "."));
                        // TODO: Fix, for general usage..
                        Constructor<?> conEmpty = classClass.getConstructor(InputStream.class);
                        stack.add(new Pair<Class<?>, Object>(classClass, conEmpty.newInstance(System.in)));
                    }
                    case NEWARRAY -> {
                        // TODO
                    }
                    case ANEWARRAY -> {
                        // TODO
                    }
                    case ARRAYLENGTH -> {
                        // TODO
                    }
                    case ATHROW -> {
                        // TODO
                    }
                    case CHECKCAST -> {
                        // TODO
                    }
                    case INSTANCEOF -> {
                        // TODO
                    }
                    case MONITORENTER -> {
                        // TODO
                    }
                    case MONITOREXIT -> {
                        // TODO
                    }
                    case WIDE -> {
                        // TODO
                    }
                    case MULTIANEWARRAY -> {
                        // TODO
                    }
                    case IFNULL -> {
                        // TODO
                    }
                    case IFNONNULL -> {
                        // TODO
                    }
                    case GOTO_W -> {
                        // TODO
                    }
                    case JSR_W -> {
                        // WILL NOT IMPLEMENT
                        throw new UnsupportedOperationException(
                                "JSR_W is deprecated since Java 7, this program only supports Java 7+ class files");
                    }
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