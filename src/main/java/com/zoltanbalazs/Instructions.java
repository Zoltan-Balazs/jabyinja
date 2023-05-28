package com.zoltanbalazs;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.CallSite;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Instructions {
    public static void ACONST(List<Pair<Class<?>, Object>> stack) {
        stack.add(new Pair<Class<?>, Object>(void.class, null));
    }

    public static void ICONST(List<Pair<Class<?>, Object>> stack, int value) {
        stack.add(new Pair<Class<?>, Object>(int.class, value));
    }

    public static void LCONST(List<Pair<Class<?>, Object>> stack, long value) {
        stack.add(new Pair<Class<?>, Object>(long.class, value));
    }

    public static void FCONST(List<Pair<Class<?>, Object>> stack, float value) {
        stack.add(new Pair<Class<?>, Object>(float.class, value));
    }

    public static void DCONST(List<Pair<Class<?>, Object>> stack, double value) {
        stack.add(new Pair<Class<?>, Object>(double.class, value));
    }

    public static void BIPUSH(List<Pair<Class<?>, Object>> stack, int value) {
        stack.add(new Pair<Class<?>, Object>(int.class, value));
    }

    public static void SIPUSH(List<Pair<Class<?>, Object>> stack, int value) {
        stack.add(new Pair<Class<?>, Object>(int.class, value));
    }

    public static void LDC(List<Pair<Class<?>, Object>> stack, List<CP_Info> constantPool, short index) {
        ConstantPoolTag tag = constantPool.get((index & 0xFF) - 1).tag;

        Class<?> type = Instructions_Helper.tagSwitchType(constantPool, tag);
        Object value = Instructions_Helper.tagSwitchValue(constantPool, tag, (index & 0xFF));

        stack.add(new Pair<Class<?>, Object>(type, value));
    }

    public static void ILOAD(List<Pair<Class<?>, Object>> stack, int value) {
        stack.add(new Pair<Class<?>, Object>(int.class, value));
    }

    public static void LLOAD(List<Pair<Class<?>, Object>> stack, long value) {
        stack.add(new Pair<Class<?>, Object>(long.class, value));
    }

    public static void FLOAD(List<Pair<Class<?>, Object>> stack, float value) {
        stack.add(new Pair<Class<?>, Object>(float.class, value));
    }

    public static void DLOAD(List<Pair<Class<?>, Object>> stack, double value) {
        stack.add(new Pair<Class<?>, Object>(double.class, value));
    }

    public static void ALOAD(List<Pair<Class<?>, Object>> stack, Object value) {
        if (value == null) {
            stack.add(new Pair<Class<?>, Object>(void.class, new Object[] {}));
        } else {
            stack.add(new Pair<Class<?>, Object>(value.getClass(), value));
        }
    }

    public static void IALOAD(List<Pair<Class<?>, Object>> stack) {
        Object value = Instructions_Helper.ARRAYLOAD(stack);
        stack.add(new Pair<Class<?>, Object>(int.class, value));
    }

    public static void LALOAD(List<Pair<Class<?>, Object>> stack) {
        Object value = Instructions_Helper.ARRAYLOAD(stack);
        stack.add(new Pair<Class<?>, Object>(long.class, value));
    }

    public static void FALOAD(List<Pair<Class<?>, Object>> stack) {
        Object value = Instructions_Helper.ARRAYLOAD(stack);
        stack.add(new Pair<Class<?>, Object>(float.class, value));
    }

    public static void DALOAD(List<Pair<Class<?>, Object>> stack) {
        Object value = Instructions_Helper.ARRAYLOAD(stack);
        stack.add(new Pair<Class<?>, Object>(double.class, value));
    }

    public static void AALOAD(List<Pair<Class<?>, Object>> stack) {
        Object value = Instructions_Helper.ARRAYLOAD(stack);
        stack.add(new Pair<Class<?>, Object>(value.getClass(), value));
    }

    public static void BALOAD(List<Pair<Class<?>, Object>> stack) {
        Object value = Instructions_Helper.ARRAYLOAD(stack);
        stack.add(new Pair<Class<?>, Object>(byte.class, value));
    }

    public static void CALOAD(List<Pair<Class<?>, Object>> stack) {
        Object value = Instructions_Helper.ARRAYLOAD(stack);
        stack.add(new Pair<Class<?>, Object>(char.class, value));
    }

    public static void SALOAD(List<Pair<Class<?>, Object>> stack) {
        Object value = Instructions_Helper.ARRAYLOAD(stack);
        stack.add(new Pair<Class<?>, Object>(short.class, value));
    }

    public static void ISTORE(List<Pair<Class<?>, Object>> stack, Object[] local, int index) {
        if (stack.get(stack.size() - 1).first == Character.class) {
            local[index] = (int) ((Character) stack.remove(stack.size() - 1).second);
        } else {
            local[index] = ((Number) stack.remove(stack.size() - 1).second).intValue();
        }
    }

    public static void LSTORE(List<Pair<Class<?>, Object>> stack, Object[] local, int index) {
        local[index] = ((Number) stack.remove(stack.size() - 1).second).longValue();
        local[index + 1] = local[index];
    }

    public static void FSTORE(List<Pair<Class<?>, Object>> stack, Object[] local, int index) {
        local[index] = ((Number) stack.remove(stack.size() - 1).second).floatValue();
    }

    public static void DSTORE(List<Pair<Class<?>, Object>> stack, Object[] local, int index) {
        local[index] = ((Number) stack.remove(stack.size() - 1).second).doubleValue();
        local[index + 1] = local[index];
    }

    public static void ASTORE(List<Pair<Class<?>, Object>> stack, Object[] local, int index) {
        local[index] = stack.remove(stack.size() - 1).second;
    }

    public static void IASTORE(List<Pair<Class<?>, Object>> stack) {
        Instructions_Helper.ARRAYSTORE(stack);
    }

    public static void LASTORE(List<Pair<Class<?>, Object>> stack) {
        Instructions_Helper.ARRAYSTORE(stack);
    }

    public static void FASTORE(List<Pair<Class<?>, Object>> stack) {
        Instructions_Helper.ARRAYSTORE(stack);
    }

    public static void DASTORE(List<Pair<Class<?>, Object>> stack) {
        Instructions_Helper.ARRAYSTORE(stack);
    }

    public static void AASTORE(List<Pair<Class<?>, Object>> stack) {
        Instructions_Helper.ARRAYSTORE(stack);
    }

    public static void BASTORE(List<Pair<Class<?>, Object>> stack) {
        Instructions_Helper.ARRAYSTORE(stack);
    }

    public static void CASTORE(List<Pair<Class<?>, Object>> stack) {
        Instructions_Helper.ARRAYSTORE(stack);
    }

    public static void SASTORE(List<Pair<Class<?>, Object>> stack) {
        Instructions_Helper.ARRAYSTORE(stack);
    }

    public static void POP(List<Pair<Class<?>, Object>> stack, Opcode type) {
        int idx = stack.size() - 1;
        if (type == Opcode.POP) {
            stack.remove(idx);
        } else if (type == Opcode.POP2) {
            if (stack.get(idx).first == long.class || stack.get(idx).first == double.class) {
                stack.remove(idx);
            } else {
                stack.remove(idx);
                stack.remove(stack.size() - 1);
            }
        } else {
            throw new UnsupportedOperationException("POP for " + type + " is not implemented!");
        }
    }

    public static void DUP(List<Pair<Class<?>, Object>> stack, Opcode type) {
        int stackSize = stack.size();
        if (type == Opcode.DUP) {
            stack.add((stackSize - 1) > 0 ? (stackSize - 1) : 0, stack.get(stackSize - 1));
        } else if (type == Opcode.DUP_X1) {
            stack.add((stackSize - 2) > 0 ? (stackSize - 2) : 0, stack.get(stackSize - 1));
        } else if (type == Opcode.DUP_X2) {
            stack.add((stackSize - 3) > 0 ? (stackSize - 3) : 0, stack.get(stackSize - 1));
        } else {
            throw new UnsupportedOperationException("DUP for " + type + " is not implemented!");
        }
    }

    public static void DUP2(List<Pair<Class<?>, Object>> stack, Opcode type) {
        int stackSize = stack.size();
        if (type == Opcode.DUP2) {
            if (stack.get(stackSize - 1).first == long.class || stack.get(stackSize - 1).first == double.class) {
                stack.add((stackSize - 1) > 0 ? (stackSize - 1) : 0, stack.get(stackSize - 1));
            } else {
                stack.add((stackSize - 2) > 0 ? (stackSize - 2) : 0, stack.get(stackSize - 1));
                stack.add((stackSize - 2) > 0 ? (stackSize - 2) : 0, stack.get(stackSize - 1));
            }
        } else if (type == Opcode.DUP_X1) {
            if (stack.get(stackSize - 2).first == long.class || stack.get(stackSize - 2).first == double.class) {
                stack.add((stackSize - 2) > 0 ? (stackSize - 2) : 0, stack.get(stackSize - 1));
            } else {
                stack.add((stackSize - 3) > 0 ? (stackSize - 3) : 0, stack.get(stackSize - 3));
                stack.add((stackSize - 3) > 0 ? (stackSize - 3) : 0, stack.get(stackSize - 3));
            }
        } else if (type == Opcode.DUP_X2) {
            if (stack.get(stackSize - 3).first == long.class || stack.get(stackSize - 3).first == double.class) {
                stack.add((stackSize - 3) > 0 ? (stackSize - 3) : 0, stack.get(stackSize - 1));
            } else {
                stack.add((stackSize - 4) > 0 ? (stackSize - 4) : 0, stack.get(stackSize - 4));
                stack.add((stackSize - 4) > 0 ? (stackSize - 4) : 0, stack.get(stackSize - 4));
            }
        } else {
            throw new UnsupportedOperationException("DUP2 for " + type + " is not implemented!");
        }
    }

    public static void SWAP(List<Pair<Class<?>, Object>> stack) {
        Pair<Class<?>, Object> value1 = stack.get(stack.size() - 1);
        Pair<Class<?>, Object> value2 = stack.get(stack.size() - 2);

        if (value1.first == long.class || value1.first == double.class || value2.first == long.class
                || value2.first == double.class) {
            throw new UnsupportedOperationException("SWAP for long/double is not supported!");
        }

        stack.set(stack.size() - 1, value2);
        stack.set(stack.size() - 2, value1);
    }

    public static Pair<Integer, Integer> IARIT(List<Pair<Class<?>, Object>> stack) {
        Pair<Class<?>, Object> value2 = stack.remove(stack.size() - 1);
        Pair<Class<?>, Object> value1 = stack.remove(stack.size() - 1);

        return new Pair<Integer, Integer>((int) value1.second, (int) value2.second);
    }

    public static Pair<Long, Long> LARIT(List<Pair<Class<?>, Object>> stack) {
        Pair<Class<?>, Object> value2 = stack.remove(stack.size() - 1);
        Pair<Class<?>, Object> value1 = stack.remove(stack.size() - 1);

        return new Pair<Long, Long>((long) value1.second, (long) value2.second);
    }

    public static Pair<Float, Float> FARIT(List<Pair<Class<?>, Object>> stack) {
        Pair<Class<?>, Object> value2 = stack.remove(stack.size() - 1);
        Pair<Class<?>, Object> value1 = stack.remove(stack.size() - 1);

        return new Pair<Float, Float>((float) value1.second, (float) value2.second);
    }

    public static Pair<Double, Double> DARIT(List<Pair<Class<?>, Object>> stack) {
        Pair<Class<?>, Object> value2 = stack.remove(stack.size() - 1);
        Pair<Class<?>, Object> value1 = stack.remove(stack.size() - 1);

        return new Pair<Double, Double>((double) value1.second, (double) value2.second);
    }

    public static void IINC(Object[] local, int index, int constantValue) {
        local[index] = (int) local[index] + constantValue;
    }

    public static void ICONV(List<Pair<Class<?>, Object>> stack, Class<?> result) {
        Pair<Class<?>, Object> value = stack.remove(stack.size() - 1);

        if (result == long.class) {
            stack.add(new Pair<Class<?>, Object>(long.class, (long) (int) value.second));
        } else if (result == float.class) {
            stack.add(new Pair<Class<?>, Object>(float.class, (float) (int) value.second));
        } else if (result == double.class) {
            stack.add(new Pair<Class<?>, Object>(double.class, (double) (int) value.second));
        } else if (result == byte.class) {
            stack.add(new Pair<Class<?>, Object>(byte.class, (byte) (int) value.second));
        } else if (result == char.class) {
            stack.add(new Pair<Class<?>, Object>(char.class, (char) (int) value.second));
        } else if (result == short.class) {
            stack.add(new Pair<Class<?>, Object>(short.class, (short) (int) value.second));
        } else {
            throw new UnsupportedOperationException("Integer to " + result + " conversion is not supported!");
        }
    }

    public static void LCONV(List<Pair<Class<?>, Object>> stack, Class<?> result) {
        Pair<Class<?>, Object> value = stack.remove(stack.size() - 1);

        if (result == int.class) {
            stack.add(new Pair<Class<?>, Object>(int.class, (int) (long) value.second));
        } else if (result == float.class) {
            stack.add(new Pair<Class<?>, Object>(float.class, (float) (long) value.second));
        } else if (result == double.class) {
            stack.add(new Pair<Class<?>, Object>(double.class, (double) (long) value.second));
        } else {
            throw new UnsupportedOperationException("Long to " + result + " conversion is not supported!");
        }
    }

    public static void FCONV(List<Pair<Class<?>, Object>> stack, Class<?> result) {
        Pair<Class<?>, Object> value = stack.remove(stack.size() - 1);

        if (result == int.class) {
            stack.add(new Pair<Class<?>, Object>(int.class, (int) (float) value.second));
        } else if (result == long.class) {
            stack.add(new Pair<Class<?>, Object>(long.class, (long) (float) value.second));
        } else if (result == double.class) {
            stack.add(new Pair<Class<?>, Object>(double.class, (double) (float) value.second));
        } else {
            throw new UnsupportedOperationException("Float to " + result + " conversion is not supported!");
        }
    }

    public static void DCONV(List<Pair<Class<?>, Object>> stack, Class<?> result) {
        Pair<Class<?>, Object> value = stack.remove(stack.size() - 1);

        if (result == int.class) {
            stack.add(new Pair<Class<?>, Object>(int.class, (int) (double) value.second));
        } else if (result == long.class) {
            stack.add(new Pair<Class<?>, Object>(long.class, (long) (double) value.second));
        } else if (result == float.class) {
            stack.add(new Pair<Class<?>, Object>(float.class, (float) (double) value.second));
        } else {
            throw new UnsupportedOperationException("Double to " + result + " conversion is not supported!");
        }
    }

    public static void LCMP(List<Pair<Class<?>, Object>> stack) {
        Pair<Class<?>, Object> value2 = stack.remove(stack.size() - 1);
        Pair<Class<?>, Object> value1 = stack.remove(stack.size() - 1);

        long l_value1 = (long) value1.second;
        long l_value2 = (long) value2.second;

        if (l_value1 == l_value2) {
            stack.add(new Pair<Class<?>, Object>(int.class, 0));
        } else if (l_value1 < l_value2) {
            stack.add(new Pair<Class<?>, Object>(int.class, -1));
        } else if (l_value1 > l_value2) {
            stack.add(new Pair<Class<?>, Object>(int.class, 1));
        } else {
            throw new UnsupportedOperationException(
                    "Long comparison failed, first value: " + l_value1 + ", second value: " + l_value2);
        }
    }

    public static void FCMP(List<Pair<Class<?>, Object>> stack, Opcode type) {
        Pair<Class<?>, Object> value2 = stack.remove(stack.size() - 1);
        Pair<Class<?>, Object> value1 = stack.remove(stack.size() - 1);

        float f_value1 = (float) value1.second;
        float f_value2 = (float) value2.second;

        if (Float.isNaN(f_value1) || Float.isNaN(f_value2)) {
            if (type == Opcode.FCMPL) {
                stack.add(new Pair<Class<?>, Object>(int.class, -1));
            } else if (type == Opcode.FCMPG) {
                stack.add(new Pair<Class<?>, Object>(int.class, 1));
            } else {
                throw new UnsupportedOperationException(
                        "Float comparison for " + type + " is not supported!");
            }
        } else if (f_value1 == f_value2) {
            stack.add(new Pair<Class<?>, Object>(int.class, 0));
        } else if (f_value1 < f_value2) {
            stack.add(new Pair<Class<?>, Object>(int.class, -1));
        } else if (f_value1 > f_value2) {
            stack.add(new Pair<Class<?>, Object>(int.class, 1));
        } else {
            throw new UnsupportedOperationException(
                    "Float comparison failed, first value: " + f_value1 + ", second value: " + f_value2);
        }
    }

    public static void DCMP(List<Pair<Class<?>, Object>> stack, Opcode type) {
        Pair<Class<?>, Object> value2 = stack.remove(stack.size() - 1);
        Pair<Class<?>, Object> value1 = stack.remove(stack.size() - 1);

        double d_value1 = (double) value1.second;
        double d_value2 = (double) value2.second;

        if (Double.isNaN(d_value1) || Double.isNaN(d_value2)) {
            if (type == Opcode.DCMPL) {
                stack.add(new Pair<Class<?>, Object>(int.class, -1));
            } else if (type == Opcode.DCMPG) {
                stack.add(new Pair<Class<?>, Object>(int.class, 1));
            } else {
                throw new UnsupportedOperationException(
                        "Double comparison for " + type + " is not supported!");
            }
        } else if (d_value1 == d_value2) {
            stack.add(new Pair<Class<?>, Object>(int.class, 0));
        } else if (d_value1 < d_value2) {
            stack.add(new Pair<Class<?>, Object>(int.class, -1));
        } else if (d_value1 > d_value2) {
            stack.add(new Pair<Class<?>, Object>(int.class, 1));
        } else {
            throw new UnsupportedOperationException(
                    "Double comparison failed, first value: " + d_value1 + ", second value: " + d_value2);
        }
    }

    public static void IF1I(CodeIndex codeIndex, short offset, List<Pair<Class<?>, Object>> stack, Opcode type,
            ClassFile cf) {
        int value;
        if (stack.get(stack.size() - 1).first == Boolean.class || stack.get(stack.size() - 1).first == boolean.class) {
            value = (boolean) stack.remove(stack.size() - 1).second ? 1 : 0;
        } else {
            value = ((Number) stack.remove(stack.size() - 1).second).intValue();
        }

        switch (type) {
            case IFEQ -> {
                if (value == 0) {
                    codeIndex.Inc(offset - 2 - 1);
                }
            }
            case IFNE -> {
                if (value != 0) {
                    codeIndex.Inc(offset - 2 - 1);
                }
            }
            case IFLT -> {
                if (value < 0) {
                    codeIndex.Inc(offset - 2 - 1);
                }
            }
            case IFGE -> {
                if (value >= 0) {
                    codeIndex.Inc(offset - 2 - 1);
                }
            }
            case IFGT -> {
                if (value > 0) {
                    codeIndex.Inc(offset - 2 - 1);
                }
            }
            case IFLE -> {
                if (value <= 0) {
                    codeIndex.Inc(offset - 2 - 1);
                }
            }
            default ->
                throw new IllegalArgumentException("One value Integer IF for opcode " + type + " is not supported!");
        }
    }

    public static void IF2I(CodeIndex codeIndex, short offset, List<Pair<Class<?>, Object>> stack, Opcode type,
            ClassFile cf) {
        int value2 = ((Number) stack.remove(stack.size() - 1).second).intValue();
        int value1 = ((Number) stack.remove(stack.size() - 1).second).intValue();

        switch (type) {
            case IF_ICMPEQ -> {
                if (value1 == value2) {
                    codeIndex.Inc(offset - 2 - 1);
                }
            }
            case IF_ICMPNE -> {
                if (value1 != value2) {
                    codeIndex.Inc(offset - 2 - 1);
                }
            }
            case IF_ICMPLT -> {
                if (value1 < value2) {
                    codeIndex.Inc(offset - 2 - 1);
                }
            }
            case IF_ICMPGE -> {
                if (value1 >= value2) {
                    codeIndex.Inc(offset - 2 - 1);
                }
            }
            case IF_ICMPGT -> {
                if (value1 > value2) {
                    codeIndex.Inc(offset - 2 - 1);
                }
            }
            case IF_ICMPLE -> {
                if (value1 <= value2) {
                    codeIndex.Inc(offset - 2 - 1);
                }
            }
            default ->
                throw new IllegalArgumentException("Two value Integer IF for opcode " + type + " is not supported!");
        }
    }

    public static void IF2A(CodeIndex codeIndex, short offset, List<Pair<Class<?>, Object>> stack, Opcode type,
            ClassFile cf) {
        Object value2 = stack.remove(stack.size() - 1).second;
        Object value1 = stack.remove(stack.size() - 1).second;

        switch (type) {
            case IF_ACMPEQ -> {
                if (value1.equals(value2)) {
                    codeIndex.Inc(offset - 2 - 1);
                }
            }
            case IF_ACMPNE -> {
                if (!value1.equals(value2)) {
                    codeIndex.Inc(offset - 2 - 1);
                }
            }
            default ->
                throw new IllegalArgumentException("Two value Reference IF for opcode " + type + " is not supported!");
        }
    }

    public static void GOTO(CodeIndex codeIndex, short offset) {
        codeIndex.Inc(offset - 2 - 1);
    }

    public static void JSR() {
        throw new UnsupportedOperationException(
                "JSR is deprecated since Java 7, this program only supports Java 7+ class files");
    }

    public static void RET() {
        throw new UnsupportedOperationException(
                "RET is (effectively) deprecated since Java 7, this program only supports Java 7+ class files");
    }

    public static void TABLESWITCH(byte[] code, CodeIndex codeIndex, List<Pair<Class<?>, Object>> stack) {
        int startingPos = codeIndex.Get() - 1;
        while (codeIndex.Get() % 4 != 0) {
            codeIndex.Inc(1);
        }

        int defaultOffset = ClassFile_Helper.readInt(code, codeIndex);
        int lowValue = ClassFile_Helper.readInt(code, codeIndex);
        int highValue = ClassFile_Helper.readInt(code, codeIndex);
        int comparedValue = ((Number) stack.remove(stack.size() - 1).second).intValue();
        if (comparedValue < lowValue || highValue < comparedValue) {
            codeIndex.Set(startingPos + defaultOffset);
        } else {
            for (int i = lowValue; i < comparedValue; ++i) {
                ClassFile_Helper.readInt(code, codeIndex); // SKIP
            }
            int offset = ClassFile_Helper.readInt(code, codeIndex);
            codeIndex.Set(startingPos + offset);
        }
    }

    public static void LOOKUPSWITCH(byte[] code, CodeIndex codeIndex, List<Pair<Class<?>, Object>> stack) {
        int startingPos = codeIndex.Get() - 1;
        while (codeIndex.Get() % 4 != 0) {
            codeIndex.Inc(1);
        }

        int defaultOffset = ClassFile_Helper.readInt(code, codeIndex);
        int numberOfPairs = ClassFile_Helper.readInt(code, codeIndex);
        int comparedValue = ((Number) stack.remove(stack.size() - 1).second).intValue();
        boolean valueFound = false;
        for (int i = 0; i < numberOfPairs && !valueFound; ++i) {
            Pair<Integer, Integer> key = new Pair<Integer, Integer>(
                    ClassFile_Helper.readInt(code, codeIndex), ClassFile_Helper.readInt(code, codeIndex));
            if (key.first == comparedValue) {
                codeIndex.Set(startingPos + key.second);
                valueFound = true;
            }
        }
        if (!valueFound) {
            codeIndex.Set(startingPos + defaultOffset);
        }
    }

    public static void GETSTATIC(List<Pair<Class<?>, Object>> stack, List<CP_Info> constantPool, short index,
            String file_name, ClassFile cf)
            throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, Throwable {
        CP_Info reference_to_field = constantPool.get(index - 1);
        String className = cf.getNameOfClass(reference_to_field.getClassIndex());
        String name_of_field = cf.getNameOfMember(reference_to_field.getNameAndTypeIndex());

        RETURN(cf);

        Class<?> classReference = null;
        if (ClassFile.isClassBuiltIn(className)) {
            classReference = Class.forName(className.replace("/", "."));
        } else {
            classReference = Instructions_Helper.loadClassFromOtherFile(file_name, className).second;
        }

        for (int i = 0; i < 65535 && cf.local[i] != null; ++i) {
            if (cf.local[i].getClass().getName().equals(classReference.getName())) {
                classReference = cf.local[i].getClass();
            }
        }

        Field outField = classReference.getDeclaredField(name_of_field);
        outField.setAccessible(true);
        Object field = outField.get(null);
        stack.add(new Pair<Class<?>, Object>(field.getClass(), field));
    }

    public static void PUTSTATIC(List<Pair<Class<?>, Object>> stack, List<CP_Info> constantPool, short index,
            String file_name, ClassFile cf)
            throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        CP_Info reference_to_field = constantPool.get(index - 1);
        String className = cf.getNameOfClass(reference_to_field.getClassIndex());
        String name_of_field = cf.getNameOfMember(reference_to_field.getNameAndTypeIndex());

        Class<?> classReference = null;
        if (ClassFile.isClassBuiltIn(className)) {
            classReference = Class.forName(className.replace("/", "."));
        } else {
            classReference = Instructions_Helper.loadClassFromOtherFile(file_name, className).second;
        }

        int stackSize = stack.size();
        Pair<Class<?>, Object> value = stack.get(stackSize - 1);

        Field inField = classReference.getDeclaredField(name_of_field);

        if (cf.MUST_INITIALIZE) {
            cf.STATICS_TO_INITIALIZE.add(new Pair<Field, Pair<Class<?>, Object>>(inField,
                    new Pair<Class<?>, Object>(classReference, value.second)));
            return;
        }

        inField.setAccessible(true);
        inField.set(classReference, value.second);

        stack.subList(stackSize - 1, stackSize).clear();
    }

    public static void GETFIELD(List<Pair<Class<?>, Object>> stack, List<CP_Info> constantPool, short index,
            ClassFile cf)
            throws NoSuchFieldException, IllegalAccessException, Throwable {
        CP_Info reference_to_field = constantPool.get(index - 1);
        String name_of_field = cf.getNameOfMember(reference_to_field.getNameAndTypeIndex());

        RETURN(cf);

        int stackSize = stack.size();
        Pair<Class<?>, Object> objectref = stack.get(stackSize - 1);

        Field f = null;
        try {
            f = objectref.second.getClass().getDeclaredField(name_of_field);
        } catch (NoSuchFieldException nfe) {
            f = objectref.second.getClass().getSuperclass().getDeclaredField(name_of_field);
        }

        stack.subList(stackSize - 1, stackSize).clear();

        f.setAccessible(true);
        Object field = f.get(objectref.second);
        if (field == null) {
            stack.add(new Pair<Class<?>, Object>(void.class, field));
        } else {
            stack.add(new Pair<Class<?>, Object>(field.getClass(), field));
        }
    }

    public static void PUTFIELD(List<Pair<Class<?>, Object>> stack, List<CP_Info> constantPool, short index,
            ClassFile cf)
            throws NoSuchFieldException, IllegalAccessException {
        CP_Info reference_to_field = constantPool.get(index - 1);
        String name_of_field = cf.getNameOfMember(reference_to_field.getNameAndTypeIndex());

        int stackSize = stack.size();
        Pair<Class<?>, Object> objectref = stack.get(stackSize - 2);
        Pair<Class<?>, Object> value = stack.get(stackSize - 1);

        if (cf.MUST_INITIALIZE) {
            cf.FIELDS_TO_INITIALIZE.add(new Pair<String, Pair<Object, Object>>(name_of_field,
                    new Pair<Object, Object>(objectref.second, value.second)));
            return;
        } else if (cf.IN_INIT_METHOD) {
            return;
        }

        Field f = null;
        try {
            f = objectref.second.getClass().getDeclaredField(name_of_field);
        } catch (NoSuchFieldException nfe) {
            f = objectref.second.getClass().getSuperclass().getDeclaredField(name_of_field);
        }

        f.setAccessible(true);
        try {
            f.set(objectref.second, value.second);
        } catch (Exception e) {
            if (!f.get(objectref.second).getClass().getName()
                    .equals(value.second.getClass().getName())) {
                return;
            }
        }

        stack.subList(stackSize - 2, stackSize).clear();
    }

    public static void INVOKEVIRTUAL(List<Pair<Class<?>, Object>> stack, List<CP_Info> constantPool, short index,
            Object[] local,
            String file_name, ClassFile cf, boolean isINVOKESPECIAL)
            throws ClassNotFoundException, MalformedURLException, IOException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {
        CP_Info methodReference = constantPool.get(index - 1);
        String className = cf.getNameOfClass(methodReference.getClassIndex());
        String memberNameAndType = cf.getNameOfMember(methodReference.getNameAndTypeIndex());
        String methodDescription = cf.getDescriptionOfMethod(methodReference.getNameAndTypeIndex());

        List<Class<?>> methodArguments = cf.getArguments(methodDescription);
        int numberOfMethodArguments = methodArguments.size();

        if (cf.IN_INIT_METHOD && className.equals("java/io/PrintStream")
                && memberNameAndType.equals("println")) {
            return;
        }

        int stackSize = stack.size();
        Pair<Class<?>, Object> objectref = stack.get(stackSize - numberOfMethodArguments - 1);

        List<Pair<Class<?>, Object>> argumentsOnStack = stack.subList(stackSize - numberOfMethodArguments,
                stackSize);

        if (objectref.second.getClass().getName() != className && !isINVOKESPECIAL
                && !ClassFile.isClassBuiltIn(className)) {
            className = objectref.second.getClass().getName().replace(".", "/");
        }

        List<Object> functionArguments = new ArrayList<Object>();
        List<Class<?>> typeOfArguments = new ArrayList<Class<?>>();
        Instructions_Helper.setArgumentsAndTypes(argumentsOnStack, functionArguments, typeOfArguments);
        int tmpIdx = 0;
        Class<?>[] typesOfFunctionParameters = new Class<?>[typeOfArguments.size()];
        for (int j = 0; j < typeOfArguments.size(); ++j) {
            typesOfFunctionParameters[j] = typeOfArguments.get(tmpIdx++);
        }

        String newFileName = "";
        Class<?> classReference = null;
        if (ClassFile.isClassBuiltIn(className)) {
            classReference = Class.forName(className.replace("/", "."));
        } else {
            Pair<String, Class<?>> returned = Instructions_Helper.loadClassFromOtherFile(file_name, className);

            if (returned == null) {
                className = className.replace("[", "");
                className = className.substring(1, className.length() - 1);
                returned = Instructions_Helper.loadClassFromOtherFile(file_name, className);
            }
            classReference = returned.second;
            newFileName = returned.first;
        }

        Object result = null;
        Class<?> returnType = void.class;
        Object obj = null;

        int objectRefIdx = -1;
        for (int i = 0; i < 65535 && objectRefIdx == -1; ++i) {
            if (local[i] == objectref.second) {
                objectRefIdx = i;
            }
        }

        if (memberNameAndType.equals("clone")) {
            return;
        }

        try {
            if (ClassFile.isClassBuiltIn(className)) {
                List<Class<?>> inputTypes = cf.stringToTypes(methodDescription
                        .substring(methodDescription.indexOf("(") + 1, methodDescription.indexOf(")")));

                for (int i = 0; i < inputTypes.size(); ++i) {
                    if (inputTypes.get(i) == char.class && functionArguments.get(i) instanceof Number) {
                        argumentsOnStack.set(i, new Pair<Class<?>, Object>(char.class,
                                (char) (((Number) (functionArguments.get(i))).intValue())));
                        typesOfFunctionParameters[i] = char.class;
                    }
                }

                Method method = Instructions_Helper.getCorrectMethod(classReference, memberNameAndType,
                        typesOfFunctionParameters,
                        methodArguments, argumentsOnStack);
                typesOfFunctionParameters = Instructions_Helper.getCorrectMethodTypes(classReference,
                        memberNameAndType,
                        typesOfFunctionParameters, methodArguments, argumentsOnStack);

                Object[] argumentsAsObjects = new Object[functionArguments.size()];
                for (int j = 0; j < functionArguments.size(); ++j) {
                    if (inputTypes.get(j) == char.class && functionArguments.get(j) instanceof Number) {
                        functionArguments.set(j, (char) (((Number) (functionArguments.get(j))).intValue()));
                    }
                    argumentsAsObjects[j] = (Object) functionArguments.get(j);
                }

                method.setAccessible(true);
                result = method.invoke(objectref.second, argumentsAsObjects);
                if (result != null) {
                    returnType = result.getClass();
                }
            } else {
                ClassFile CLASS_FILE = new ClassFile(newFileName);

                Method_Info functionMethod = new Method_Info();
                List<Attribute_Info> attributes = new ArrayList<>();

                try {
                    functionMethod = CLASS_FILE.findMethod(memberNameAndType, methodDescription);

                    if (functionMethod == null) {
                        className = cf.getNameOfClass(methodReference.getClassIndex());

                        if (ClassFile.isClassBuiltIn(className)) {
                            Method method = Instructions_Helper.getCorrectMethod(classReference,
                                    memberNameAndType,
                                    typesOfFunctionParameters,
                                    methodArguments, argumentsOnStack);
                            typesOfFunctionParameters = Instructions_Helper.getCorrectMethodTypes(
                                    classReference,
                                    memberNameAndType,
                                    typesOfFunctionParameters, methodArguments, argumentsOnStack);

                            Object[] argumentsAsObjects = new Object[functionArguments.size()];
                            for (int j = 0; j < functionArguments.size(); ++j) {
                                argumentsAsObjects[j] = (Object) functionArguments.get(j);
                            }

                            method.setAccessible(true);
                            result = method.invoke(objectref.second, argumentsAsObjects);
                            if (result != null) {
                                returnType = result.getClass();
                            }
                        } else {
                            Pair<String, Class<?>> returned = Instructions_Helper.loadClassFromOtherFile(file_name,
                                    className);

                            if (returned == null) {
                                className = className.replace("[", "");
                                className = className.substring(1, className.length() - 1);
                                returned = Instructions_Helper.loadClassFromOtherFile(file_name, className);
                            }
                            classReference = returned.second;
                            newFileName = returned.first;

                            CLASS_FILE = new ClassFile(newFileName);
                            functionMethod = CLASS_FILE.findMethod(memberNameAndType, methodDescription);

                            if (functionMethod == null) {
                                className = objectref.second.getClass().getSuperclass().getName().replace(".", "/");

                                if (ClassFile.isClassBuiltIn(className)) {
                                    Method method = Instructions_Helper.getCorrectMethod(classReference,
                                            memberNameAndType,
                                            typesOfFunctionParameters,
                                            methodArguments, argumentsOnStack);
                                    typesOfFunctionParameters = Instructions_Helper.getCorrectMethodTypes(
                                            classReference,
                                            memberNameAndType,
                                            typesOfFunctionParameters, methodArguments, argumentsOnStack);

                                    Object[] argumentsAsObjects = new Object[functionArguments.size()];
                                    for (int j = 0; j < functionArguments.size(); ++j) {
                                        argumentsAsObjects[j] = (Object) functionArguments.get(j);
                                    }
                                    method.setAccessible(true);
                                    result = method.invoke(objectref.second, argumentsAsObjects);
                                    if (result != null) {
                                        returnType = result.getClass();
                                    }
                                } else {
                                    returned = Instructions_Helper.loadClassFromOtherFile(file_name,
                                            className);
                                    classReference = returned.second;
                                    newFileName = returned.first;

                                    CLASS_FILE = new ClassFile(newFileName);
                                    functionMethod = CLASS_FILE.findMethod(memberNameAndType,
                                            methodDescription);
                                }
                            }
                        }
                    }
                    if (result == null) {
                        attributes = CLASS_FILE.findAttributesByName(functionMethod.attributes, "Code");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                CLASS_FILE.local[0] = objectref.second;
                int localIdx = 1;
                for (int i = stackSize - numberOfMethodArguments; i < stackSize; ++i) {
                    CLASS_FILE.local[localIdx++] = stack.get(i).second;
                    if (stack.get(i).first == long.class || stack.get(i).first == Long.class
                            || stack.get(i).first == double.class
                            || stack.get(i).first == Double.class) {
                        CLASS_FILE.local[localIdx++] = stack.get(i).second;
                    }
                }

                for (Attribute_Info attribute : attributes) {
                    try {
                        Code_Attribute codeAttribute = Code_Attribute_Helper.readCodeAttributes(attribute);

                        Pair<Class<?>, Object> returnResult = CLASS_FILE.executeCode(codeAttribute, null);
                        result = returnResult.second;
                        returnType = returnResult.first;
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IllegalArgumentException ie) {
            try {
                for (Constructor<?> ctor : objectref.first.getDeclaredConstructors()) {

                    Field[] fields = objectref.second.getClass().getDeclaredFields();

                    int nonStaticParams = 0;
                    for (int i = 0; i < fields.length; ++i) {
                        if (!java.lang.reflect.Modifier.isStatic(fields[i].getModifiers())) {
                            nonStaticParams++;
                        }
                    }

                    Object[] values = new Object[nonStaticParams];
                    int ctr = 0;
                    for (int i = 0; i < fields.length; ++i) {
                        try {
                            if (!java.lang.reflect.Modifier.isStatic(fields[i].getModifiers())) {
                                fields[i].setAccessible(true);
                                values[ctr++] = fields[i].get(objectref.second);
                            }
                        } catch (Exception e) {

                        }
                    }

                    try {
                        obj = ctor.newInstance(values);
                    } catch (Exception e) {
                        int params = ctor.getParameterCount();
                        values = new Object[params];
                        ctr = 0;
                        for (int i = 0; i < fields.length; ++i) {
                            try {
                                if (!java.lang.reflect.Modifier.isStatic(fields[i].getModifiers())) {
                                    fields[i].setAccessible(true);
                                    values[ctr++] = fields[i].get(objectref.second);
                                }
                            } catch (Exception ee) {

                            }
                        }
                        try {
                            obj = ctor.newInstance(values);
                        } catch (Exception ee) {

                        }
                    }

                    if (obj != null) {
                        break;
                    }
                }

                if (obj == null && objectRefIdx != -1) {
                    obj = local[objectRefIdx];
                } else if (obj == null) {
                    obj = objectref.first;
                }

                Method method = Instructions_Helper.getCorrectMethod(classReference, memberNameAndType,
                        typesOfFunctionParameters,
                        methodArguments, argumentsOnStack);
                typesOfFunctionParameters = Instructions_Helper.getCorrectMethodTypes(classReference,
                        memberNameAndType,
                        typesOfFunctionParameters, methodArguments, argumentsOnStack);

                Object[] argumentsAsObjects = new Object[functionArguments.size()];
                for (int j = 0; j < functionArguments.size(); ++j) {
                    argumentsAsObjects[j] = (Object) functionArguments.get(j);
                }

                method = obj.getClass().getDeclaredMethod(memberNameAndType,
                        typesOfFunctionParameters);
                if (ClassFile.isClassBuiltIn(className)) {
                    method.setAccessible(true);
                    result = method.invoke(objectref.second, argumentsAsObjects);
                    if (result != null) {
                        returnType = result.getClass();
                    }
                } else {
                    ClassFile CLASS_FILE = new ClassFile(newFileName);

                    Method_Info functionMethod = new Method_Info();
                    List<Attribute_Info> attributes = new ArrayList<>();

                    try {
                        functionMethod = CLASS_FILE.findMethod(memberNameAndType, methodDescription);
                        attributes = CLASS_FILE.findAttributesByName(functionMethod.attributes, "Code");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    CLASS_FILE.local[0] = objectref.second;

                    for (Attribute_Info attribute : attributes) {
                        try {
                            Code_Attribute codeAttribute = Code_Attribute_Helper.readCodeAttributes(attribute);

                            Pair<Class<?>, Object> returnResult = CLASS_FILE.executeCode(codeAttribute, null);
                            result = returnResult.second;
                            returnType = returnResult.first;
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                }

                local[objectRefIdx] = obj;
            } catch (Exception e) {
                Method method = Instructions_Helper.getCorrectMethod(classReference, memberNameAndType,
                        typesOfFunctionParameters,
                        methodArguments, argumentsOnStack);
                typesOfFunctionParameters = Instructions_Helper.getCorrectMethodTypes(classReference,
                        memberNameAndType,
                        typesOfFunctionParameters, methodArguments, argumentsOnStack);

                Object[] argumentsAsObjects = new Object[functionArguments.size()];
                for (int j = 0; j < functionArguments.size(); ++j) {
                    argumentsAsObjects[j] = (Object) functionArguments.get(j);
                }

                if (obj == null) {
                    obj = objectref.first;

                    method = Instructions_Helper.getCorrectMethod(objectref.first.getClass(),
                            memberNameAndType,
                            typesOfFunctionParameters,
                            methodArguments, argumentsOnStack);
                } else {
                    method = Instructions_Helper.getCorrectMethod(classReference,
                            memberNameAndType,
                            typesOfFunctionParameters,
                            methodArguments, argumentsOnStack);
                }

                if (ClassFile.isClassBuiltIn(className)) {
                    method.setAccessible(true);
                    result = method.invoke(objectref.second, argumentsAsObjects);
                    if (result != null) {
                        returnType = result.getClass();
                    }
                } else {
                    ClassFile CLASS_FILE = new ClassFile(newFileName);

                    Method_Info functionMethod = new Method_Info();
                    List<Attribute_Info> attributes = new ArrayList<>();

                    try {
                        functionMethod = CLASS_FILE.findMethod(memberNameAndType, methodDescription);
                        attributes = CLASS_FILE.findAttributesByName(functionMethod.attributes, "Code");
                    } catch (Exception ee) {
                        e.printStackTrace();
                    }

                    CLASS_FILE.local[0] = objectref.second;

                    for (Attribute_Info attribute : attributes) {
                        try {
                            Code_Attribute codeAttribute = Code_Attribute_Helper.readCodeAttributes(attribute);

                            Pair<Class<?>, Object> returnResult = CLASS_FILE.executeCode(codeAttribute, null);
                            result = returnResult.second;
                            returnType = returnResult.first;
                        } catch (Throwable ee) {
                            e.printStackTrace();
                        }
                    }
                }

                local[objectRefIdx] = obj;
            }
        }

        stack.subList(stackSize - numberOfMethodArguments - 1, stackSize).clear();

        if (result != null) {
            if (returnType == int.class && methodDescription
                    .substring(methodDescription.indexOf(")", 0) + 1, methodDescription.length()).equals("Z")) {
                stack.add(new Pair<Class<?>, Object>(boolean.class, ((Number) result).intValue() == 1 ? true : false));
            } else {
                stack.add(new Pair<Class<?>, Object>(returnType, result));
            }
        } else if (obj != null) {
            local[objectRefIdx] = obj;
        } else {
            Class<?> return_type = cf.stringToType(methodDescription
                    .substring(methodDescription.indexOf(")") + 1, methodDescription.length())).second;

            if (return_type == String.class) {
                stack.add(new Pair<Class<?>, Object>(String.class, "null"));
            } else if (return_type == Object.class) {
                stack.add(new Pair<Class<?>, Object>(Object.class, null));
            }
        }
    }

    public static void INVOKESPECIAL(List<Pair<Class<?>, Object>> stack, List<CP_Info> constantPool, short index,
            Object[] local, String file_name, ClassFile cf)
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException,
            ClassNotFoundException, MalformedURLException, IOException {
        CP_Info methodReference = constantPool.get(index - 1);
        String className = cf.getNameOfClass(methodReference.getClassIndex());
        String memberNameAndType = cf.getNameOfMember(methodReference.getNameAndTypeIndex());
        String methodDescription = cf.getDescriptionOfMethod(methodReference.getNameAndTypeIndex());

        List<Class<?>> methodArguments = cf.getArguments(methodDescription);
        int numberOfMethodArguments = methodArguments.size();

        int stackSize = stack.size();
        Pair<Class<?>, Object> objectref = stack.get(stackSize - numberOfMethodArguments - 1);

        List<Pair<Class<?>, Object>> argumentsOnStack = stack.subList(stackSize - numberOfMethodArguments,
                stackSize);

        List<Object> functionArguments = new ArrayList<Object>();
        List<Class<?>> typeOfArguments = new ArrayList<Class<?>>();
        Instructions_Helper.setArgumentsAndTypes(argumentsOnStack, functionArguments, typeOfArguments);
        Class<?>[] typesOfFunctionParameters = new Class<?>[typeOfArguments.size()];
        for (int j = 0; j < typeOfArguments.size(); ++j) {
            typesOfFunctionParameters[j] = (Class<?>) typeOfArguments.get(j);
        }

        if (className.equals("java/lang/Object")) {
            stack.clear();
            cf.MUST_INITIALIZE = true;
            return;
        }

        String newFileName = "";
        Class<?> classReference = null;
        if (ClassFile.isClassBuiltIn(className)) {
            classReference = Class.forName(className.replace("/", "."));
        } else {
            Pair<String, Class<?>> returned = Instructions_Helper.loadClassFromOtherFile(file_name, className);
            classReference = returned.second;
            newFileName = returned.first;
        }

        if (ClassFile.isClassBuiltIn(className) && memberNameAndType.equals("<init>")) {
            Object[] argumentsAsObjects = new Object[functionArguments.size()];
            for (int j = 0; j < functionArguments.size(); ++j) {
                argumentsAsObjects[j] = (Object) functionArguments.get(j);
            }

            Constructor<?> initConstructor = null;
            try {
                initConstructor = classReference.getDeclaredConstructor(typesOfFunctionParameters);
            } catch (Throwable e) {
                try {
                    for (Constructor<?> ctor : classReference.getDeclaredConstructors()) {
                        boolean isCorrectConstructors = true;
                        boolean hadToassign = false;

                        for (int j = 0; j < typeOfArguments.size(); ++j) {
                            typesOfFunctionParameters[j] = (Class<?>) typeOfArguments.get(j);
                        }

                        if (ctor.getParameterTypes().length == numberOfMethodArguments) {
                            for (int i = 0; i < ctor.getParameterTypes().length; ++i) {
                                Class<?> type = ctor.getParameterTypes()[i];
                                if (!type.getName().equals(typesOfFunctionParameters[i].getName())
                                        && !type.isAssignableFrom(typesOfFunctionParameters[i])) {
                                    typesOfFunctionParameters[i] = Object.class;
                                    hadToassign = true;
                                }
                            }
                        } else {
                            isCorrectConstructors = false;
                        }

                        if (isCorrectConstructors && !(hadToassign && ctor != null)) {
                            initConstructor = ctor;
                        }
                    }
                } catch (Throwable ee) {
                    Pair<String, Class<?>> returned = Instructions_Helper.loadClassFromOtherFile(file_name,
                            ee.getMessage());
                    classReference = returned.second;
                    newFileName = returned.first;
                    String newClassName = classReference.getName();
                    Class<?> resolvedClass = Instructions_Helper.getCorrectClassLoader(newFileName, className)
                            .loadClass(newClassName);

                    ClassLoader correct_loader = resolvedClass.getClassLoader();

                    Thread.currentThread().setContextClassLoader(correct_loader);
                    Thread.currentThread().getContextClassLoader().loadClass(resolvedClass.getName())
                            .getDeclaredConstructors();
                    for (Constructor<?> ctor : classReference.getDeclaredConstructors()) {
                        boolean isCorrectConstructors = true;
                        boolean hadToassign = false;

                        for (int j = 0; j < typeOfArguments.size(); ++j) {
                            typesOfFunctionParameters[j] = (Class<?>) typeOfArguments.get(j);
                        }

                        if (ctor.getParameterTypes().length == numberOfMethodArguments) {
                            for (int i = 0; i < ctor.getParameterTypes().length; ++i) {
                                Class<?> type = ctor.getParameterTypes()[i];
                                if (!type.getName().equals(typesOfFunctionParameters[i].getName())
                                        && !type.isAssignableFrom(typesOfFunctionParameters[i])) {
                                    typesOfFunctionParameters[i] = Object.class;
                                    hadToassign = true;
                                }
                            }
                        } else {
                            isCorrectConstructors = false;
                        }

                        if (isCorrectConstructors && !(hadToassign && ctor != null)) {
                            initConstructor = ctor;
                        }
                    }
                }
            }

            if (initConstructor != null) {
                initConstructor.setAccessible(true);
                stack.subList(stackSize - numberOfMethodArguments - 2, stackSize).clear();

                stack.add(
                        new Pair<Class<?>, Object>(classReference,
                                initConstructor.newInstance(argumentsAsObjects)));
            }
        } else if (memberNameAndType.equals("<init>")) {
            ClassFile CLASS_FILE = new ClassFile(newFileName);
            CLASS_FILE.MUST_INITIALIZE = true;

            Pair<String, Class<?>> returned = Instructions_Helper.loadClassFromOtherFile(file_name,
                    className);
            classReference = returned.second;
            newFileName = returned.first;
            String newClassName = classReference.getName();
            Class<?> resolvedClass = Instructions_Helper.getCorrectClassLoader(newFileName, newClassName)
                    .loadClass(newClassName);

            Method_Info functionMethod = CLASS_FILE.findMethod(memberNameAndType,
                    methodDescription);
            List<Attribute_Info> attributes = CLASS_FILE.findAttributesByName(functionMethod.attributes, "Code");

            CLASS_FILE.local[0] = objectref.second;
            int localIdx = 1;
            int methodArgumentsIdx = 0;
            for (int i = stackSize - numberOfMethodArguments; i < stackSize; ++i) {
                CLASS_FILE.local[localIdx++] = stack.get(i).second;
                if (stack.get(i).first == long.class || stack.get(i).first == double.class
                        || methodArguments.get(methodArgumentsIdx) == long.class
                        || methodArguments.get(methodArgumentsIdx) == double.class) {
                    CLASS_FILE.local[localIdx++] = stack.get(i).second;
                    methodArgumentsIdx++;
                }
            }

            for (Attribute_Info attribute : attributes) {
                try {
                    Code_Attribute codeAttribute = Code_Attribute_Helper.readCodeAttributes(attribute);
                    CLASS_FILE.INIT_ARGS = functionArguments;
                    CLASS_FILE.INIT_ARG_TYPES = methodArguments;
                    CLASS_FILE.IN_INIT_METHOD = true;

                    CLASS_FILE.executeCode(codeAttribute, null);

                    objectref = new Pair<Class<?>, Object>(CLASS_FILE.local[0].getClass(), CLASS_FILE.local[0]);

                    if (file_name.contains(className) || cf.IN_MAIN_METHOD) {
                        objectref = new Pair<Class<?>, Object>(CLASS_FILE.local[0].getClass(),
                                CLASS_FILE.local[0]);
                    } else if (CLASS_FILE.MUST_INITIALIZE) {
                        returned = Instructions_Helper.loadClassFromOtherFile(cf.FILE_NAME,
                                cf.CLASS_NAME);
                        classReference = returned.second;
                        newFileName = returned.first;
                        newClassName = classReference.getName();
                        resolvedClass = Instructions_Helper.getCorrectClassLoader(newFileName, newClassName)
                                .loadClass(newClassName);

                        try {
                            Object[] argumets_as_objects = new Object[numberOfMethodArguments];
                            int idx = 0;
                            for (int i = stackSize - numberOfMethodArguments; i < stackSize; ++i) {
                                argumets_as_objects[idx++] = stack.get(i).second;
                            }

                            Constructor<?> initConstructor = null;
                            for (Constructor<?> ctor : resolvedClass.getDeclaredConstructors()) {
                                if (ctor.getParameterCount() == numberOfMethodArguments) {
                                    typesOfFunctionParameters = ctor.getParameterTypes();
                                    boolean isCorrectConstructors = true;
                                    boolean hadToassign = false;
                                    if (typesOfFunctionParameters.length == numberOfMethodArguments) {
                                        for (int i = 0; i < typesOfFunctionParameters.length; ++i) {
                                            if (typesOfFunctionParameters[i] != argumets_as_objects[i]
                                                    .getClass()
                                                    && !typesOfFunctionParameters[i]
                                                            .isAssignableFrom(argumets_as_objects[i]
                                                                    .getClass())) {
                                                typesOfFunctionParameters[i] = Object.class;
                                                hadToassign = true;
                                            }
                                        }
                                    } else {
                                        isCorrectConstructors = false;
                                    }

                                    if (isCorrectConstructors && !(hadToassign && initConstructor != null)) {
                                        initConstructor = ctor;
                                    }
                                }
                            }
                            objectref = new Pair<Class<?>, Object>(classReference,
                                    initConstructor.newInstance(argumets_as_objects));
                        } catch (Throwable ee) {
                            returned = Instructions_Helper.loadClassFromOtherFile(cf.FILE_NAME,
                                    ee.getMessage());
                            if (returned == null) {
                                return;
                            }
                            classReference = returned.second;
                            newFileName = returned.first;
                            newClassName = classReference.getName();
                            Thread.currentThread().setContextClassLoader(
                                    Instructions_Helper.getCorrectClassLoader(newFileName, newClassName));
                        }

                    }

                    boolean assigned = false;
                    for (int i = 0; i < 65535 && !assigned; ++i) {
                        if (local[i] == null || local[i].getClass().getName().equals("Object")
                                || (local[i].getClass().getName().equals("[Ljava.lang.String;")
                                        && ((String[]) local[i]).length == 0)) {
                            local[i] = objectref.second;
                            assigned = true;
                        }
                    }

                    if (0 < stackSize - numberOfMethodArguments - 2) {
                        stack.subList(stackSize - numberOfMethodArguments - 2,
                                stackSize).clear();
                    }

                    stack.add(objectref);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }

        } else {
            INVOKEVIRTUAL(stack, constantPool, index, local, file_name, cf, true);
        }
    }

    public static void INVOKESTATIC(List<Pair<Class<?>, Object>> stack, List<CP_Info> constantPool, short index,
            Object[] local, String file_name, ClassFile cf, List<Exception_Table> exceptions, CodeIndex codeIndex)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        CP_Info methodReference = constantPool.get(index - 1);
        String className = cf.getNameOfClass(methodReference.getClassIndex());
        String memberNameAndType = cf.getNameOfMember(methodReference.getNameAndTypeIndex());
        String methodDescription = cf.getDescriptionOfMethod(methodReference.getNameAndTypeIndex());

        List<Class<?>> methodArguments = cf.getArguments(methodDescription);
        int numberOfMethodArguments = methodArguments.size();

        int stackSize = stack.size();
        List<Pair<Class<?>, Object>> argumentsOnStack = new ArrayList<>();
        if (0 <= stackSize - numberOfMethodArguments) {
            argumentsOnStack = stack.subList(stackSize - numberOfMethodArguments, stackSize);
        }

        List<Object> functionArguments = new ArrayList<Object>();
        List<Class<?>> typeOfArguments = new ArrayList<Class<?>>();
        Instructions_Helper.setArgumentsAndTypes(argumentsOnStack, functionArguments, typeOfArguments);
        Class<?>[] typesOfFunctionParameters = new Class<?>[typeOfArguments.size()];
        for (int j = 0; j < typeOfArguments.size(); ++j) {
            typesOfFunctionParameters[j] = (Class<?>) typeOfArguments.get(j);
        }

        String newFileName = "";
        Class<?> classReference = null;
        if (ClassFile.isClassBuiltIn(className)) {
            classReference = Class.forName(className.replace("/", "."));
        } else {
            Pair<String, Class<?>> returned = Instructions_Helper.loadClassFromOtherFile(file_name, className);
            classReference = returned.second;
            newFileName = returned.first;
        }

        Object result = null;
        Class<?> returnType = void.class;
        if (ClassFile.isClassBuiltIn(className)) {
            Method method = Instructions_Helper.getCorrectMethod(classReference, memberNameAndType,
                    typesOfFunctionParameters,
                    methodArguments, argumentsOnStack);

            Object[] argumentsAsObjects = new Object[functionArguments.size()];
            for (int j = 0; j < functionArguments.size(); ++j) {
                argumentsAsObjects[j] = (Object) functionArguments.get(j);
            }

            method.setAccessible(true);
            try {
                result = method.invoke(className.replace("/", "."), argumentsAsObjects);
                if (result != null) {
                    returnType = result.getClass();
                }
            } catch (Throwable t) {
                if (memberNameAndType.equals("arraycopy")) {
                    int objectIdx = -1;
                    for (int i = 0; i < 65535 && objectIdx == -1; ++i) {
                        if (local[i] == stack.get(stackSize - numberOfMethodArguments + 2).second) {
                            objectIdx = i;
                        }
                    }
                    stack.set(stackSize - numberOfMethodArguments + 2,
                            stack.get(stackSize - numberOfMethodArguments));
                    if (objectIdx != -1) {
                        local[objectIdx] = stack.get(stackSize - numberOfMethodArguments).second;
                    }
                } else {
                    Throwable problem = t.getCause();
                    if (0 < stackSize) {
                        stack.subList(0, stackSize - 1).clear();
                    }
                    boolean exception_thrown = false;
                    for (Exception_Table exception : exceptions) {
                        String exception_name = new String(constantPool
                                .get(constantPool.get(exception.catch_type - 1).getNameIndex() - 1)
                                .getBytes(), StandardCharsets.UTF_8);
                        if (problem.getClass().getName().equals(exception_name.replace("/", "."))
                                && exception.start_pc <= codeIndex.Get()
                                && codeIndex.Get() <= exception.end_pc) {
                            codeIndex.Set(exception.handler_pc);
                            exception_thrown = true;
                        }
                    }
                    if (!exception_thrown) {
                        problem.printStackTrace();
                    }
                    stack.add(new Pair<Class<?>, Object>(problem.getClass(), problem));
                }
            }
        } else {
            ClassFile CLASS_FILE = new ClassFile(newFileName);

            Method_Info functionMethod = new Method_Info();
            List<Attribute_Info> attributes = new ArrayList<>();

            try {
                functionMethod = CLASS_FILE.findMethod(memberNameAndType, methodDescription);
                attributes = CLASS_FILE.findAttributesByName(functionMethod.attributes, "Code");
            } catch (Exception e) {
                e.printStackTrace();
            }

            int localIdx = 0;
            for (int i = stackSize - numberOfMethodArguments; i < stackSize; ++i) {
                CLASS_FILE.local[localIdx++] = stack.get(i).second;
                if (stack.get(i).first == long.class || stack.get(i).first == Long.class
                        || stack.get(i).first == double.class
                        || stack.get(i).first == Double.class) {
                    CLASS_FILE.local[localIdx++] = stack.get(i).second;
                }
            }

            for (Attribute_Info attribute : attributes) {
                try {
                    Code_Attribute codeAttribute = Code_Attribute_Helper.readCodeAttributes(attribute);

                    Pair<Class<?>, Object> returnResult = CLASS_FILE.executeCode(codeAttribute, null);
                    result = returnResult.second;
                    returnType = cf.stringToType(
                            methodDescription.substring(methodDescription.indexOf(")") + 1)).second;

                    if (returnType == boolean.class && result instanceof Integer) {
                        result = ((Integer) result) == 1;
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }

        if (0 <= stackSize - numberOfMethodArguments) {
            stack.subList(stackSize - numberOfMethodArguments, stackSize).clear();
        }

        if (result != null) {
            stack.add(new Pair<Class<?>, Object>(returnType, result));
        }

    }

    public static void INVOKEINTERFACE(List<Pair<Class<?>, Object>> stack, List<CP_Info> constantPool, short index,
            byte count,
            Object[] local,
            String file_name, ClassFile cf)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        CP_Info reference_to_interface = (CONSTANT_InterfaceMethodref_Info) constantPool.get(index - 1);
        String className = cf.getNameOfClass(reference_to_interface.getClassIndex());
        String memberNameAndType = cf.getNameOfMember(reference_to_interface.getNameAndTypeIndex());
        String methodDescription = cf.getDescriptionOfMethod(reference_to_interface.getNameAndTypeIndex());

        List<Class<?>> methodArguments = cf.getArguments(methodDescription);

        int stackSize = stack.size();
        Pair<Class<?>, Object> objectref = stack.get(stackSize - count);

        List<Pair<Class<?>, Object>> argumentsOnStack = stack.subList(stackSize - count + 1,
                stackSize);

        if (objectref.second.getClass().getName() != className && !ClassFile.isClassBuiltIn(className)) {
            className = objectref.second.getClass().getName().replace(".", "/");
        }

        List<Object> functionArguments = new ArrayList<Object>();
        List<Class<?>> typeOfArguments = new ArrayList<Class<?>>();
        Instructions_Helper.setArgumentsAndTypes(argumentsOnStack, functionArguments, typeOfArguments);
        Class<?>[] typesOfFunctionParameters = new Class<?>[typeOfArguments.size()];
        for (int j = 0; j < typeOfArguments.size(); ++j) {
            typesOfFunctionParameters[j] = (Class<?>) typeOfArguments.get(j);
        }

        String newFileName = "";
        Class<?> classReference = null;
        if (ClassFile.isClassBuiltIn(className)) {
            classReference = Class.forName(className.replace("/", "."));
        } else {
            Pair<String, Class<?>> returned = Instructions_Helper.loadClassFromOtherFile(file_name, className);
            classReference = returned.second;
            newFileName = returned.first;
        }

        Method method = Instructions_Helper.getCorrectMethod(classReference, memberNameAndType,
                typesOfFunctionParameters,
                methodArguments, argumentsOnStack);
        typesOfFunctionParameters = Instructions_Helper.getCorrectMethodTypes(classReference,
                memberNameAndType,
                typesOfFunctionParameters, methodArguments, argumentsOnStack);

        Object[] argumentsAsObjects = new Object[functionArguments.size()];
        for (int j = 0; j < functionArguments.size(); ++j) {
            argumentsAsObjects[j] = /* (Object) */ functionArguments.get(j);
        }

        Object result = null;
        Class<?> returnType = void.class;
        if (ClassFile.isClassBuiltIn(className)) {
            method.setAccessible(true);
            result = method.invoke(objectref.second, argumentsAsObjects);
            if (result != null) {
                returnType = result.getClass();
            }
        } else {
            ClassFile CLASS_FILE = new ClassFile(newFileName);

            Method_Info functionMethod = new Method_Info();
            List<Attribute_Info> attributes = new ArrayList<>();

            try {
                functionMethod = CLASS_FILE.findMethod(memberNameAndType, methodDescription);
                attributes = CLASS_FILE.findAttributesByName(functionMethod.attributes, "Code");
            } catch (Exception e) {
                e.printStackTrace();
            }

            CLASS_FILE.local[0] = objectref.second;
            int localIdx = 1;
            for (int i = stackSize - methodArguments.size(); i < stackSize; ++i) {
                CLASS_FILE.local[localIdx++] = stack.get(i).second;
                if (stack.get(i).first == long.class || stack.get(i).first == Long.class
                        || stack.get(i).first == double.class
                        || stack.get(i).first == Double.class) {
                    CLASS_FILE.local[localIdx++] = stack.get(i).second;
                }
            }

            for (Attribute_Info attribute : attributes) {
                try {
                    Code_Attribute codeAttribute = Code_Attribute_Helper.readCodeAttributes(attribute);

                    Pair<Class<?>, Object> returnResult = CLASS_FILE.executeCode(codeAttribute, null);
                    result = returnResult.second;
                    returnType = returnResult.first;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }

        stack.subList(stackSize - count, stackSize).clear();

        if (result != null) {
            stack.add(new Pair<Class<?>, Object>(returnType, result));
        } else {
            stack.add(objectref);
        }
    }

    public static void INVOKEDYNAMIC(List<Pair<Class<?>, Object>> stack, List<CP_Info> constantPool, short index,
            Object[] local, List<BootstrapMethods_Attribute> bootstrap_methods, List<CallSite> call_sites,
            String file_name, ClassFile cf) {
        throw new UnsupportedOperationException("Invokedynamic isn't supported yet");
    }

    public static void NEW(List<Pair<Class<?>, Object>> stack, List<CP_Info> constantPool, short index, Object[] local,
            String file_name, ClassFile cf)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, MalformedURLException,
            IOException {
        String className = cf.getNameOfMember(index);
        Class<?> classReference = null;

        if (ClassFile.isClassBuiltIn(className)) {
            classReference = Class.forName(className.replace("/", "."));
        } else {
            classReference = Instructions_Helper.loadClassFromOtherFile(file_name,
                    className).second;
        }

        stack.add(new Pair<Class<?>, Object>(classReference, classReference));
    }

    public static void NEWARRAY(List<Pair<Class<?>, Object>> stack, byte atype) {
        Class<?> arrayType = Instructions_Helper.getArrayType(atype);
        int count = ((Number) stack.remove(stack.size() - 1).second).intValue();

        stack.add(new Pair<Class<?>, Object>(arrayType, Array.newInstance(arrayType, count)));
    }

    public static void ANEWARRAY(List<Pair<Class<?>, Object>> stack, List<CP_Info> constantPool, short index,
            String file_name, ClassFile cf)
            throws ClassNotFoundException {
        ConstantPoolTag tag = constantPool.get((index & 0xFF) - 1).tag;

        Class<?> arrayType = null;
        if (tag == ConstantPoolTag.CONSTANT_Utf8 || tag == ConstantPoolTag.CONSTANT_Fieldref
                || tag == ConstantPoolTag.CONSTANT_Class) {
            String className = cf.getNameOfClass(index);
            if (ClassFile.isClassBuiltIn(className)) {
                arrayType = Class.forName(className.replace("/", "."));
            } else {
                arrayType = Instructions_Helper.loadClassFromOtherFile(file_name, className).second;
            }
        } else {
            arrayType = Instructions_Helper.tagSwitchType(constantPool, tag);
        }
        int count = ((Number) stack.remove(stack.size() - 1).second).intValue();

        stack.add(new Pair<Class<?>, Object>(arrayType, Array.newInstance(arrayType, count)));
    }

    public static void ARRAYLENGTH(List<Pair<Class<?>, Object>> stack) {
        Pair<Class<?>, Object> arrayReference = stack.remove(stack.size() - 1);
        stack.add(new Pair<Class<?>, Object>(int.class, Array.getLength(arrayReference.second)));
    }

    public static void ATHROW(List<Pair<Class<?>, Object>> stack, List<CP_Info> constantPool,
            List<Exception_Table> exceptions, CodeIndex codeIndex)
            throws Throwable {
        int stackSize = stack.size() - 1;
        Object objectref = stack.get(stackSize).second;

        if (objectref instanceof Throwable) {
            if (0 < stackSize) {
                stack.subList(0, stackSize - 1).clear();
            }
            boolean exception_thrown = false;
            for (Exception_Table exception : exceptions) {
                String exception_name = new String(constantPool
                        .get(constantPool.get(exception.catch_type - 1).getNameIndex() - 1)
                        .getBytes(), StandardCharsets.UTF_8);
                if (objectref.getClass().getName().equals(exception_name.replace("/", "."))
                        && exception.start_pc <= codeIndex.Get()
                        && codeIndex.Get() <= exception.end_pc) {
                    codeIndex.Set(exception.handler_pc);
                    exception_thrown = true;
                }
            }
            if (!exception_thrown) {
                throw (Throwable) objectref;
            }
        }
    }

    // https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html#jvms-6.5.checkcast
    public static void CHECKCAST(List<Pair<Class<?>, Object>> stack, short index, String file_name, ClassFile cf)
            throws ClassNotFoundException {
        String className = cf.getNameOfMember(index);

        Class<?> classReference = null;
        if (ClassFile.isClassBuiltIn(className)) {
            classReference = Class.forName(className.replace("/", "."));
        } else {
            try {
                classReference = Instructions_Helper.loadClassFromOtherFile(file_name, className).second;
            } catch (Exception e) {

            }
        }

        Pair<Class<?>, Object> objectRef = stack.get(stack.size() - 1);
        if (objectRef == null) {
            return;
        }

        try {
            if (classReference == null) {
                if (!objectRef.second.getClass().getName().equals(className.replace("/", "."))) {
                    throw new ClassCastException();
                }
            } else {
                if (!(classReference.isAssignableFrom(objectRef.second.getClass())
                        || classReference.getName().equals(objectRef.second.getClass().getName())
                        || classReference.getName()
                                .equals(objectRef.second.getClass().getSuperclass().getName()))) {
                    throw new ClassCastException();
                }
            }
        } catch (Exception e) {
            throw new ClassCastException(e.getMessage());
        }
    }

    // https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html#jvms-6.5.instanceof
    public static void INSTANCEOF(List<Pair<Class<?>, Object>> stack, short index, String file_name, ClassFile cf)
            throws ClassNotFoundException {
        Pair<Class<?>, Object> objectRef = stack.remove(stack.size() - 1);

        if (objectRef == null) {
            stack.add(new Pair<Class<?>, Object>(int.class, 0));
        } else {
            String className = cf.getNameOfMember(index);
            Class<?> classReference = null;
            if (ClassFile.isClassBuiltIn(className)) {
                classReference = Class.forName(className.replace("/", "."));
            } else {
                classReference = Instructions_Helper.loadClassFromOtherFile(file_name, className).second;
            }

            Class<?> type = objectRef.first;
            if (classReference.isAssignableFrom(type) || classReference.getName().equals(type.getName())) {
                stack.add(new Pair<Class<?>, Object>(int.class, 1));
            } else {
                stack.add(new Pair<Class<?>, Object>(int.class, 0));
            }
        }
    }

    public static void WIDE(byte[] code, CodeIndex codeIndex, List<Pair<Class<?>, Object>> stack, Object[] local,
            Opcode instruction) {
        short index = -1;
        short constantValue = -1;

        switch (instruction) {
            case ILOAD, LLOAD, FLOAD, DLOAD, ALOAD, LSTORE, ISTORE, FSTORE, DSTORE, ASTORE -> {
                index = ClassFile_Helper.readShort(code, codeIndex);
            }
            case IINC -> {
                index = ClassFile_Helper.readShort(code, codeIndex);
                constantValue = ClassFile_Helper.readShort(code, codeIndex);
            }
            case RET -> {
                throw new UnsupportedOperationException(
                        "RET is (effectively) deprecated since Java 7, this program only supports Java 7+ class files");
            }
            default -> {
                throw new UnsupportedOperationException(
                        "WIDE for " + instruction + " is not supported!");
            }
        }

        switch (instruction) {
            case ILOAD -> {
                ILOAD(stack, (int) local[index & 0xFFFF]);
            }
            case LLOAD -> {
                LLOAD(stack, (long) local[index & 0xFFFF]);
            }
            case FLOAD -> {
                FLOAD(stack, (float) local[index & 0xFFFF]);
            }
            case DLOAD -> {
                DLOAD(stack, (double) local[index & 0xFFFF]);
            }
            case ALOAD -> {
                ALOAD(stack, local[index & 0xFFFF]);
            }
            case ISTORE -> {
                ISTORE(stack, local, index & 0xFFFF);
            }
            case LSTORE -> {
                LSTORE(stack, local, index & 0xFFFF);
            }
            case FSTORE -> {
                FSTORE(stack, local, index & 0xFFFF);
            }
            case DSTORE -> {
                DSTORE(stack, local, index & 0xFFFF);
            }
            case ASTORE -> {
                ASTORE(stack, local, index & 0xFFFF);
            }
            case IINC -> {
                IINC(local, index & 0xFFFF, constantValue);
            }
            default -> {
                throw new UnsupportedOperationException(
                        "WIDE for " + instruction + " is not supported!");
            }
        }
    }

    public static void MULTIANEWARRAY(List<Pair<Class<?>, Object>> stack, List<CP_Info> constantPool, short index,
            byte dimensions, String file_name, ClassFile cf)
            throws ClassNotFoundException {
        ConstantPoolTag tag = constantPool.get((index & 0xFF) - 1).tag;

        Class<?> arrayType = null;
        Class<?> hackyType = null;
        if (tag == ConstantPoolTag.CONSTANT_Utf8 || tag == ConstantPoolTag.CONSTANT_Fieldref
                || tag == ConstantPoolTag.CONSTANT_Class) {
            String className = cf.getNameOfClass(index);
            if (ClassFile.isClassBuiltIn(className)) {
                arrayType = Class.forName(className.replace("/", "."));
                hackyType = Class.forName(className.replace("/", ".").replaceAll("\\[+", "["));
            } else {
                arrayType = Instructions_Helper.loadClassFromOtherFile(file_name, className).second;
                hackyType = arrayType;
            }
        } else {
            arrayType = Instructions_Helper.tagSwitchType(constantPool, tag);
        }

        int[] size = new int[dimensions];
        int sizeIdx = 0;
        for (int i = stack.size() - dimensions; i < stack.size();) {
            int count = ((Number) stack.remove(i).second).intValue();
            size[sizeIdx++] = count;
        }

        stack.add(new Pair<Class<?>, Object>(arrayType, Array.newInstance(hackyType, size)));
    }

    public static void IF1A(CodeIndex codeIndex, short offset, List<Pair<Class<?>, Object>> stack, Opcode type) {
        Object value = stack.remove(stack.size() - 1).second;

        switch (type) {
            case IFNULL -> {
                if (value == null || value == "null") {
                    codeIndex.Inc(offset - 2 - 1);
                }
            }
            case IFNONNULL -> {
                if (value != null && value != "null") {
                    codeIndex.Inc(offset - 2 - 1);
                }
            }
            default ->
                throw new IllegalArgumentException("One value Reference IF for opcode " + type + " is not supported!");
        }
    }

    public static void GOTO_W(CodeIndex codeIndex, int offset) {
        codeIndex.Inc(offset - 4 - 1);
    }

    public static void JSR_W() {
        throw new UnsupportedOperationException(
                "JSR_W is deprecated since Java 7, this program only supports Java 7+ class files");
    }

    public static void RETURN(ClassFile cf) throws Throwable {
        if (cf.MUST_INITIALIZE) {
            String className = cf.CLASS_NAME.replace("/", ".");

            Pair<String, Class<?>> returned = Instructions_Helper.loadClassFromOtherFile(cf.FILE_NAME,
                    cf.CLASS_NAME);
            Class<?> classReference = returned.second;
            String newFileName = returned.first;
            Class<?> resolvedClass = null;

            File f = new File(newFileName);
            int length = 0;
            if (classReference.getName().contains("/")) {
                length = classReference.getName().split("/").length;
            } else {
                length = classReference.getName().split("\\.").length;
            }
            for (int i = 0; i < length + 1 && resolvedClass == null; ++i) {
                URL[] cp = { f.toURI().toURL() };
                URLClassLoader urlcl = new URLClassLoader(cp);
                try {
                    resolvedClass = urlcl.loadClass(classReference.getName());
                } catch (Exception eee) {

                }
                urlcl.close();

                f = new File(f.getParent());
            }

            int numberOfArguments = cf.INIT_ARG_TYPES.size();
            Object[] argumentsAsObjects = new Object[numberOfArguments];
            int idx = 0;
            for (int i = 0; i < cf.INIT_ARGS.size() && idx < numberOfArguments; i++) {
                argumentsAsObjects[idx++] = cf.INIT_ARGS.get(i);
            }

            Constructor<?> initConstructor = null;
            try {
                for (Constructor<?> ctor : resolvedClass.getDeclaredConstructors()) {
                    boolean valid_ctor = ctor.getParameterCount() == numberOfArguments;
                    idx = 0;
                    if (valid_ctor) {
                        for (Class<?> current_type : ctor.getParameterTypes()) {
                            if (!(current_type.isAssignableFrom(cf.INIT_ARG_TYPES.get(idx))
                                    || current_type.getName().equals(cf.INIT_ARG_TYPES.get(idx).getName()))) {
                                valid_ctor = false;
                            }
                            idx++;
                        }
                    }
                    if (valid_ctor) {
                        initConstructor = ctor;
                    }
                }

                if (initConstructor == null) {
                    for (int j = 0; j < resolvedClass.getDeclaredConstructors().length; j++) {
                        Constructor<?> ctor = resolvedClass.getDeclaredConstructors()[j];
                        Class<?>[] argumentTypes = ctor.getParameterTypes();
                        argumentsAsObjects = new Object[ctor.getParameterCount()];
                        int currentArguments = 0;
                        for (int i = 0; i < cf.stack.size() && currentArguments < ctor.getParameterCount(); i++) {
                            Object currentObject = cf.stack.get(i).second;
                            Class<?> currentArgumentType = argumentTypes[currentArguments];
                            if (currentObject instanceof Object
                                    && (currentObject.getClass().isAssignableFrom(currentArgumentType)
                                            || (currentArgumentType == int.class
                                                    && currentObject.getClass() == Integer.class)
                                            || (currentArgumentType == double.class
                                                    && currentObject.getClass() == Double.class))) {
                                argumentsAsObjects[currentArguments] = currentObject;
                                currentArguments++;
                            }
                        }
                        if (currentArguments == ctor.getParameterCount()
                                || j == resolvedClass.getDeclaredConstructors().length - 1) {
                            initConstructor = ctor;
                        }
                    }
                }

                if (numberOfArguments == 1 && className.equals("com.zoltanbalazs.Own.Child")) {
                    Class<?> type = initConstructor.getParameterTypes()[0];
                    Constructor<?> ctorrr = type.getDeclaredConstructors()[0];
                    ctorrr.setAccessible(true);
                    Object arg = ctorrr.newInstance(new Object[] {});
                    argumentsAsObjects[0] = arg;
                }

                initConstructor.setAccessible(true);
                Pair<Class<?>, Object> test = new Pair<Class<?>, Object>(resolvedClass,
                        initConstructor.newInstance(argumentsAsObjects));
                cf.stack.clear();
                cf.stack.add(test);
            } catch (Throwable e) {
                try {
                    className = e.getCause().toString().split(" ")[1].replace("/", ".");

                    URL[] correct = new URL[2];
                    correct[0] = f.toURI().toURL();

                    f = new File(newFileName);
                    length = 0;
                    if (className.contains("/")) {
                        length = className.split("/").length;
                    } else {
                        length = className.split("\\.").length;
                    }
                    for (int i = 0; i < length + 1; ++i) {
                        URL[] cp = { f.toURI().toURL() };
                        URLClassLoader urlcl = new URLClassLoader(cp);
                        try {
                            urlcl.loadClass(className);
                            correct[1] = cp[0];
                        } catch (Exception eee) {

                        }
                        urlcl.close();

                        f = new File(f.getParent());
                    }

                    Class<?>[] argumentTypes = new Class<?>[numberOfArguments];
                    for (int i = 0; i < numberOfArguments; ++i) {
                        argumentTypes[i] = cf.INIT_ARG_TYPES.get(i);
                    }

                    URLClassLoader urlcl = new URLClassLoader(correct);
                    Thread.currentThread().setContextClassLoader(urlcl);
                    for (Constructor<?> ctor : Thread.currentThread().getContextClassLoader()
                            .loadClass(resolvedClass.getName())
                            .getDeclaredConstructors()) {
                        if (ctor.getParameterCount() == numberOfArguments) {
                            initConstructor = ctor;
                        }
                    }

                    for (int i = 0; i < numberOfArguments; ++i) {
                        if (argumentsAsObjects[i].getClass().getName().equals(className)) {
                            for (Object enums : initConstructor.getParameterTypes()[i].getEnumConstants()) {
                                if (((Enum<?>) argumentsAsObjects[i]).name().equals(((Enum<?>) enums).name())) {
                                    argumentsAsObjects[i] = enums;
                                }
                            }

                        }
                    }

                    initConstructor.setAccessible(true);
                    Pair<Class<?>, Object> test = new Pair<Class<?>, Object>(resolvedClass,
                            initConstructor.newInstance(argumentsAsObjects));
                    cf.stack.clear();
                    cf.stack.add(test);

                    urlcl.close();
                } catch (Throwable ee) {
                    for (int j = 0; j < resolvedClass.getDeclaredConstructors().length; j++) {
                        Constructor<?> ctor = resolvedClass.getDeclaredConstructors()[j];
                        Class<?>[] argumentTypes = ctor.getParameterTypes();
                        argumentsAsObjects = new Object[ctor.getParameterCount()];
                        int currentArguments = 0;
                        for (int i = 0; i < cf.stack.size() && currentArguments < ctor.getParameterCount(); i++) {
                            Object currentObject = cf.stack.get(i).second;
                            Class<?> currentArgumentType = argumentTypes[currentArguments];
                            if (currentObject instanceof Object
                                    && (currentObject.getClass().isAssignableFrom(currentArgumentType)
                                            || (currentArgumentType == int.class
                                                    && currentObject.getClass() == Integer.class)
                                            || (currentArgumentType == double.class
                                                    && currentObject.getClass() == Double.class))) {
                                argumentsAsObjects[currentArguments] = currentObject;
                                currentArguments++;
                            } else if (!(currentObject instanceof Class<?>)) {
                                if (argumentsAsObjects[0] == null || numberOfArguments < 2) {
                                    return;
                                }
                                argumentsAsObjects[currentArguments] = new int[((Number) argumentsAsObjects[0])
                                        .intValue() * ((Number) argumentsAsObjects[1]).intValue()];
                                currentArguments++;
                            }
                        }
                        if (currentArguments == ctor.getParameterCount()
                                || j == resolvedClass.getDeclaredConstructors().length - 1) {
                            initConstructor = ctor;

                            argumentTypes = initConstructor.getParameterTypes();
                            boolean allValid = true;
                            for (int i = 0; i < argumentTypes.length; ++i) {
                                if (!argumentsAsObjects[i].getClass().getName().equals(argumentTypes[i].getName())) {
                                    allValid = false;
                                }
                            }
                            if (allValid) {
                                break;
                            }
                        }
                    }

                    initConstructor.setAccessible(true);
                    cf.stack.clear();
                    try {
                        cf.stack.add(
                                new Pair<Class<?>, Object>(resolvedClass,
                                        initConstructor.newInstance(argumentsAsObjects)));
                    } catch (Throwable eee) {
                        return;
                    }
                }
            }

            cf.local[0] = cf.stack.get(0).second;

            for (var INITIALIZE : cf.STATICS_TO_INITIALIZE) {
                String name_of_field = INITIALIZE.first.getName();
                classReference = cf.stack.get(0).second.getClass();
                Field inField = classReference.getDeclaredField(name_of_field);
                inField.setAccessible(true);
                inField.set(classReference, INITIALIZE.second.second);
            }

            cf.MUST_INITIALIZE = false;
        }
    }
}

class Instructions_Helper {
    /***
     * This method is used to get the value of a constant pool entry
     * 
     * @param constantPool the constant pool of the class file
     * @param tag          the tag of the constant pool entry
     * @param index        the index of the constant pool entry
     * @return the value of the constant pool entry
     */
    public static Object tagSwitchValue(List<CP_Info> constantPool, ConstantPoolTag tag, int index) {
        switch (tag) {
            case CONSTANT_String -> {
                return new String(constantPool.get((constantPool.get(index - 1)).getStringIndex() - 1).getBytes(),
                        StandardCharsets.UTF_8);
            }
            case CONSTANT_Float -> {
                return constantPool.get(index - 1).getFloatValue();
            }
            case CONSTANT_Integer -> {
                return constantPool.get(index - 1).getIntValue();
            }
            case CONSTANT_Long -> {
                return constantPool.get(index - 1).getLongValue();
            }
            case CONSTANT_Double -> {
                return constantPool.get(index - 1).getDoubleValue();
            }
            default -> {
                throw new UnsupportedOperationException(tag + " is not implemented yet!");
            }
        }
    }

    /***
     * Switches the tag of the constant pool to the type of the tag
     * 
     * @param constantPool the constant pool
     * @param tag          the tag of the constant pool
     * @return the type of the tag
     */
    public static Class<?> tagSwitchType(List<CP_Info> constantPool, ConstantPoolTag tag) {
        switch (tag) {
            case CONSTANT_String -> {
                return String.class;
            }
            case CONSTANT_Float -> {
                return float.class;
            }
            case CONSTANT_Integer -> {
                return int.class;
            }
            case CONSTANT_Long -> {
                return long.class;
            }
            case CONSTANT_Double -> {
                return double.class;
            }
            default -> {
                throw new UnsupportedOperationException(tag + " is not implemented yet!");
            }
        }
    }

    /***
     * Switches the type of the array to the type of the array
     * 
     * @param atype the type of the array in bool
     * @return the type of the array
     */
    public static Class<?> getArrayType(byte atype) {
        switch (atype) {
            case 4 -> {
                return Boolean.class;
            }
            case 5 -> {
                return char.class;
            }
            case 6 -> {
                return float.class;
            }
            case 7 -> {
                return double.class;
            }
            case 8 -> {
                return byte.class;
            }
            case 9 -> {
                return short.class;
            }
            case 10 -> {
                return int.class;
            }
            case 11 -> {
                return long.class;
            }
            default -> {
                throw new UnsupportedOperationException("Array type code " + atype + " is not recognized!");
            }
        }
    }

    public static Object ARRAYLOAD(List<Pair<Class<?>, Object>> stack) {
        int index = ((Number) stack.remove(stack.size() - 1).second).intValue();
        Pair<Class<?>, Object> arrayReference = stack.remove(stack.size() - 1);

        return Array.get(arrayReference.second, index);
    }

    public static void ARRAYSTORE(List<Pair<Class<?>, Object>> stack) {
        Pair<Class<?>, Object> value = stack.remove(stack.size() - 1);
        int index = ((Number) stack.remove(stack.size() - 1).second).intValue();
        Pair<Class<?>, Object> arrayReference = stack.remove(stack.size() - 1);

        try {
            Array.set(arrayReference.second, index, value.second);
        } catch (IllegalArgumentException iae) {
            if (arrayReference.second.getClass().getComponentType().getName().equals(value.first.getName())) {
                Object[] arguments = new Object[value.first.getDeclaredFields().length];
                Object[] foundArgs = new Object[value.first.getDeclaredFields().length];
                Object[] actual_arguments = null;
                int idx = 0;

                try {
                    for (Field ff : value.first.getDeclaredFields()) {
                        ff.setAccessible(true);
                        arguments[idx++] = ff.get(value.second);
                    }
                } catch (IllegalAccessException iaeee) {
                    System.out.println(iaeee);
                }

                Constructor<?> initCtor = null;
                for (Constructor<?> ctor : arrayReference.second.getClass().getComponentType()
                        .getDeclaredConstructors()) {
                    boolean canBeValid = true;
                    idx = 0;
                    for (Class<?> clazz : ctor.getParameterTypes()) {
                        boolean found = false;
                        for (int i = idx; i < value.first.getDeclaredFields().length && !found; ++i) {
                            if (clazz.getName().equals(arguments[i].getClass().getName())
                                    || (clazz.getName().equals("double")
                                            && arguments[i].getClass().getName().equals("java.lang.Double"))
                                    || (clazz.getName().equals("int")
                                            && arguments[i].getClass().getName().equals("java.lang.Integer"))) {
                                foundArgs[idx] = arguments[idx];
                                idx++;
                                found = true;
                            }
                        }
                        if (!found) {
                            canBeValid = false;
                        }
                    }
                    if (canBeValid) {
                        initCtor = ctor;
                        actual_arguments = new Object[idx];
                        for (int i = 0; i < idx; ++i) {
                            actual_arguments[i] = foundArgs[i];
                        }
                    }
                }

                try {
                    initCtor.setAccessible(true);
                    Array.set(arrayReference.second, index, initCtor.newInstance(actual_arguments));
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }

    /***
     * Get the correct class loader for the class
     * 
     * @param fileName  The file name of the class
     * @param className The class name
     * @return ClassLoader
     */
    public static ClassLoader getCorrectClassLoader(String fileName, String className) {
        File f = new File(fileName);
        int length = 0;
        if (className.contains("/")) {
            length = className.split("/").length;
        } else {
            length = className.split("\\.").length;
        }
        for (int i = 0; i < length + 1; ++i) {
            try {
                URL[] cp = { f.toURI().toURL() };

                URLClassLoader urlcl = new URLClassLoader(cp);
                try {
                    urlcl.loadClass(className);
                    return urlcl;
                } catch (Exception eee) {

                }
                urlcl.close();

                f = new File(f.getParent());
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return null;
    }

    /***
     * Set arguments and types of arguments
     * 
     * @param argumentsOnStack  List of arguments on stack
     * @param functionArguments List of arguments of function
     * @param typeOfArguments   List of types of arguments
     */
    public static void setArgumentsAndTypes(List<Pair<Class<?>, Object>> argumentsOnStack,
            List<Object> functionArguments, List<Class<?>> typeOfArguments) {
        for (var arguments : argumentsOnStack) {
            Class<?> argument_type = arguments.first;
            Object current_argument = arguments.second;

            if (current_argument != null && current_argument.getClass().isArray()
                    && current_argument.getClass() != argument_type) {
                functionArguments.add(current_argument.getClass().cast(current_argument));
                typeOfArguments.add(current_argument.getClass());
                continue;
            } else if (argument_type == int.class) {
                functionArguments.add((int) current_argument);
            } else if (argument_type == float.class) {
                functionArguments.add((float) current_argument);
            } else if (argument_type == double.class) {
                functionArguments.add((double) current_argument);
            } else if (argument_type == long.class) {
                functionArguments.add((long) current_argument);
            } else {
                if (!argument_type.equals(current_argument) && argument_type != boolean.class) {
                    functionArguments.add(argument_type.cast(current_argument));
                } else {
                    functionArguments.add(current_argument);
                }
            }
            typeOfArguments.add(arguments.first);
        }
    }

    /***
     * Load a class from a file
     * 
     * @param file_name The file name
     * @param className The class name
     * @return A pair of the file name and the class
     */
    public static Pair<String, Class<?>> loadClassFromOtherFile(String file_name, String className) {
        Pair<String, Class<?>> returned = null;
        try {
            File f = new File(file_name);
            int length = 0;
            if (className.contains("/")) {
                length = className.split("/").length;
            } else {
                length = className.split("\\.").length;
            }
            for (int i = 0; i < length + 1; ++i) {
                URL[] cp = { f.toURI().toURL() };
                URLClassLoader urlcl = new URLClassLoader(cp);
                try {
                    returned = new Pair<String, Class<?>>(f.getPath() + "/" + className + ".class",
                            urlcl.loadClass(className.replace("/", ".")));
                } catch (Exception e) {

                }
                urlcl.close();

                f = new File(f.getParent());
            }
        } catch (Exception e) {
            try {
                File f = new File(file_name.split(className.split("/")[0])[0] + className + ".class");
                URL[] cp = { f.toURI().toURL() };
                URLClassLoader urlcl = new URLClassLoader(cp);
                try {
                    returned = new Pair<String, Class<?>>(f.getPath() + "/" + className + ".class",
                            urlcl.loadClass(className.replace("/", ".")));
                } catch (Exception ee) {

                }
                urlcl.close();
            } catch (Exception ee) {
                return null;
            }
        }
        return returned;
    }

    /***
     * This method is used to get the correct method that is going to be called
     * 
     * @param classReference            The class that contains the method
     * @param memberNameAndType         The name and type of the method
     * @param typesOfFunctionParameters The types of the parameters of the method
     * @param methodArguments           The arguments of the method
     * @param argumentsOnStack          The arguments on the stack
     * @return The method that is going to be called
     * @throws NoSuchMethodException If the method does not exist
     */
    public static Method getCorrectMethod(Class<?> classReference, String memberNameAndType,
            Class<?>[] typesOfFunctionParameters, List<Class<?>> methodArguments,
            List<Pair<Class<?>, Object>> argumentsOnStack) throws NoSuchMethodException {
        Method returnedMethod = null;
        Class<?> original_reference = classReference;

        if (ClassFile.doesMethodExists(classReference, memberNameAndType, typesOfFunctionParameters)) {
            while (classReference != null && returnedMethod == null) {
                try {
                    returnedMethod = classReference.getDeclaredMethod(memberNameAndType,
                            typesOfFunctionParameters);
                } catch (Throwable t) {

                }
                classReference = classReference.getSuperclass();
            }

            if (returnedMethod == null) {
                for (Method method : original_reference.getDeclaredMethods()) {
                    boolean isCorrectMethod = true;

                    if (method.getName().equals(memberNameAndType)
                            && method.getParameterTypes().length == typesOfFunctionParameters.length) {
                        for (int i = 0; i < method.getParameterTypes().length; ++i) {
                            Class<?> type = method.getParameterTypes()[i];
                            if (!type.getName().equals(typesOfFunctionParameters[i].getName())) {
                                isCorrectMethod = false;
                            }
                        }
                    } else {
                        isCorrectMethod = false;
                    }

                    if (isCorrectMethod) {
                        return method;
                    }
                }
            }
        } else {
            typesOfFunctionParameters = new Class<?>[methodArguments.size()];
            for (int j = 0; j < methodArguments.size(); ++j) {
                typesOfFunctionParameters[j] = (Class<?>) methodArguments.get(j);
            }
            if (ClassFile.doesMethodExists(classReference, memberNameAndType, typesOfFunctionParameters)) {
                try {
                    returnedMethod = classReference.getDeclaredMethod(memberNameAndType,
                            typesOfFunctionParameters);
                } catch (Exception e) {
                    for (Method method : classReference.getDeclaredMethods()) {
                        boolean isCorrectMethod = true;

                        if (method.getName().equals(memberNameAndType)
                                && method.getParameterTypes().length == typesOfFunctionParameters.length) {
                            for (int i = 0; i < method.getParameterTypes().length; ++i) {
                                Class<?> type = method.getParameterTypes()[i];
                                if (!type.getName().equals(typesOfFunctionParameters[i].getName())) {
                                    isCorrectMethod = false;
                                }
                            }
                        } else {
                            isCorrectMethod = false;
                        }

                        if (isCorrectMethod) {
                            return method;
                        }
                    }
                }
            } else {
                typesOfFunctionParameters = new Class<?>[methodArguments.size()];
                for (int j = 0; j < methodArguments.size(); ++j) {
                    typesOfFunctionParameters[j] = argumentsOnStack.get(j).first;
                }
                if (ClassFile.doesMethodExists(classReference, memberNameAndType,
                        typesOfFunctionParameters)) {
                    returnedMethod = classReference.getDeclaredMethod(memberNameAndType,
                            typesOfFunctionParameters);
                } else if (Enum.class.isAssignableFrom(classReference)) {
                    returnedMethod = Enum.class.getDeclaredMethod(memberNameAndType,
                            typesOfFunctionParameters);
                }
            }
        }

        return returnedMethod;
    }

    /***
     * Returns the correct method types for a method call
     * 
     * @param classReference            The class that contains the method
     * @param memberNameAndType         The name and type of the method
     * @param typesOfFunctionParameters The types of the parameters of the method
     * @param methodArguments           The arguments of the method
     * @param argumentsOnStack          The arguments on the stack
     * @return The correct method types
     * @throws NoSuchMethodException If the method does not exist
     */
    public static Class<?>[] getCorrectMethodTypes(Class<?> classReference, String memberNameAndType,
            Class<?>[] typesOfFunctionParameters, List<Class<?>> methodArguments,
            List<Pair<Class<?>, Object>> argumentsOnStack) throws NoSuchMethodException {
        if (ClassFile.doesMethodExists(classReference, memberNameAndType, typesOfFunctionParameters)) {
            try {
                classReference.getDeclaredMethod(memberNameAndType, typesOfFunctionParameters);
            } catch (Exception e) {
                typesOfFunctionParameters = new Class<?>[methodArguments.size()];
                for (int j = 0; j < methodArguments.size(); ++j) {
                    typesOfFunctionParameters[j] = argumentsOnStack.get(j).first;
                }
            }
            return typesOfFunctionParameters;
        } else {
            typesOfFunctionParameters = new Class<?>[methodArguments.size()];
            for (int j = 0; j < methodArguments.size(); ++j) {
                typesOfFunctionParameters[j] = (Class<?>) methodArguments.get(j);
            }
            if (ClassFile.doesMethodExists(classReference, memberNameAndType, typesOfFunctionParameters)) {
                try {
                    classReference.getDeclaredMethod(memberNameAndType,
                            typesOfFunctionParameters);

                    return typesOfFunctionParameters;
                } catch (Exception e) {
                    for (Method method : classReference.getDeclaredMethods()) {
                        boolean isCorrectMethod = true;

                        if (method.getName().equals(memberNameAndType)) {
                            for (int i = 0; i < method.getParameterTypes().length; ++i) {
                                Class<?> type = method.getParameterTypes()[i];
                                if (!type.getName().equals(typesOfFunctionParameters[i].getName())) {
                                    isCorrectMethod = false;
                                }
                            }
                        } else {
                            isCorrectMethod = false;
                        }

                        if (isCorrectMethod) {
                            return typesOfFunctionParameters;
                        }
                    }
                }
            } else {
                typesOfFunctionParameters = new Class<?>[methodArguments.size()];
                for (int j = 0; j < methodArguments.size(); ++j) {
                    typesOfFunctionParameters[j] = (Class<?>) methodArguments.get(j);
                }
                if (ClassFile.doesMethodExists(classReference, memberNameAndType,
                        typesOfFunctionParameters)) {
                    classReference.getDeclaredMethod(memberNameAndType,
                            typesOfFunctionParameters);
                    return typesOfFunctionParameters;
                } else if (Enum.class.isAssignableFrom(classReference)) {
                    Enum.class.getDeclaredMethod(memberNameAndType, typesOfFunctionParameters);
                    return typesOfFunctionParameters;
                }
            }
        }

        return null;
    }
}