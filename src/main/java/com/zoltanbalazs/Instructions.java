package com.zoltanbalazs;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
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

    public static void LDC(List<Pair<Class<?>, Object>> stack, List<CP_Info> constant_pool, short index) {
        ConstantPoolTag tag = constant_pool.get((index & 0xFF) - 1).tag;

        Class<?> type = Instructions_Helper.TagSwitchType(constant_pool, tag);
        Object value = Instructions_Helper.TagSwitchValue(constant_pool, tag, (index & 0xFF));

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
        stack.add(new Pair<Class<?>, Object>(value.getClass(), value));
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

    public static void IINC(Object[] local, int index, int constVal) {
        local[index] = (int) local[index] + constVal;
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

        if (!cf.MUST_INITIALIZE) {
            stack.clear();
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
        if (!cf.MUST_INITIALIZE) {
            stack.clear();
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
        if (!cf.MUST_INITIALIZE) {
            stack.clear();
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

    public static void GETSTATIC(List<Pair<Class<?>, Object>> stack, List<CP_Info> constant_pool, short index,
            String file_name, ClassFile cf)
            throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, Throwable {
        CP_Info reference_to_field = constant_pool.get(index - 1);
        String name_of_class = cf.getNameOfClass(reference_to_field.getClassIndex());
        String name_of_field = cf.getNameOfMember(reference_to_field.getNameAndTypeIndex());

        RETURN(cf);

        Class<?> reference_to_class = null;
        if (ClassFile.isClassBuiltIn(name_of_class)) {
            reference_to_class = Class.forName(name_of_class.replace("/", "."));
        } else {
            reference_to_class = Instructions_Helper.LOAD_CLASS_FROM_OTHER_FILE(file_name, name_of_class).second;
        }

        for (int i = 0; i < 65535 && cf.local[i] != null; ++i) {
            if (cf.local[i].getClass().getName().equals(reference_to_class.getName())) {
                reference_to_class = cf.local[i].getClass();
            }
        }

        Field outField = reference_to_class.getDeclaredField(name_of_field);
        outField.setAccessible(true);
        Object field = outField.get(null);
        stack.add(new Pair<Class<?>, Object>(field.getClass(), field));
    }

    public static void PUTSTATIC(List<Pair<Class<?>, Object>> stack, List<CP_Info> constant_pool, short index,
            String file_name, ClassFile cf)
            throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        CP_Info reference_to_field = constant_pool.get(index - 1);
        String name_of_class = cf.getNameOfClass(reference_to_field.getClassIndex());
        String name_of_field = cf.getNameOfMember(reference_to_field.getNameAndTypeIndex());

        Class<?> reference_to_class = null;
        if (ClassFile.isClassBuiltIn(name_of_class)) {
            reference_to_class = Class.forName(name_of_class.replace("/", "."));
        } else {
            reference_to_class = Instructions_Helper.LOAD_CLASS_FROM_OTHER_FILE(file_name, name_of_class).second;
        }

        int stack_size = stack.size();
        Pair<Class<?>, Object> value = stack.get(stack_size - 1);

        Field inField = reference_to_class.getDeclaredField(name_of_field);

        if (cf.MUST_INITIALIZE) {
            cf.STATICS_TO_INITIALIZE.add(new Pair<Field, Pair<Class<?>, Object>>(inField,
                    new Pair<Class<?>, Object>(reference_to_class, value.second)));
            return;
        }

        inField.setAccessible(true);
        inField.set(reference_to_class, value.second);

        stack.subList(stack_size - 1, stack_size).clear();
    }

    public static void GETFIELD(List<Pair<Class<?>, Object>> stack, List<CP_Info> constant_pool, short index,
            ClassFile cf)
            throws NoSuchFieldException, IllegalAccessException, Throwable {
        CP_Info reference_to_field = constant_pool.get(index - 1);
        String name_of_field = cf.getNameOfMember(reference_to_field.getNameAndTypeIndex());

        RETURN(cf);

        int stack_size = stack.size();
        Pair<Class<?>, Object> objectref = stack.get(stack_size - 1);

        Field f = null;
        try {
            f = objectref.second.getClass().getDeclaredField(name_of_field);
        } catch (NoSuchFieldException nfe) {
            f = objectref.second.getClass().getSuperclass().getDeclaredField(name_of_field);
        }

        stack.subList(stack_size - 1, stack_size).clear();

        f.setAccessible(true);
        Object field = f.get(objectref.second);
        stack.add(new Pair<Class<?>, Object>(field.getClass(), field));
    }

    public static void PUTFIELD(List<Pair<Class<?>, Object>> stack, List<CP_Info> constant_pool, short index,
            ClassFile cf)
            throws NoSuchFieldException, IllegalAccessException {
        CP_Info reference_to_field = constant_pool.get(index - 1);
        String name_of_field = cf.getNameOfMember(reference_to_field.getNameAndTypeIndex());

        int stack_size = stack.size();
        Pair<Class<?>, Object> objectref = stack.get(stack_size - 2);
        Pair<Class<?>, Object> value = stack.get(stack_size - 1);

        if (cf.MUST_INITIALIZE) {
            cf.FIELDS_TO_INITIALIZE.add(new Pair<String, Pair<Object, Object>>(name_of_field,
                    new Pair<Object, Object>(objectref.second, value.second)));
            return;
        }

        Field f = null;
        try {
            f = objectref.second.getClass().getDeclaredField(name_of_field);
        } catch (NoSuchFieldException nfe) {
            f = objectref.second.getClass().getSuperclass().getDeclaredField(name_of_field);
        }

        f.setAccessible(true);
        f.set(objectref.second, value.second);

        stack.subList(stack_size - 2, stack_size).clear();
    }

    public static void INVOKEVIRTUAL(List<Pair<Class<?>, Object>> stack, List<CP_Info> constant_pool, short index,
            Object[] local,
            String file_name, ClassFile cf, boolean isINVOKESPECIAL)
            throws ClassNotFoundException, MalformedURLException, IOException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {
        CP_Info reference_to_method = constant_pool.get(index - 1);
        String name_of_class = cf.getNameOfClass(reference_to_method.getClassIndex());
        String name_and_type_of_member = cf.getNameOfMember(reference_to_method.getNameAndTypeIndex());
        String description_of_method = cf.getDescriptionOfMethod(reference_to_method.getNameAndTypeIndex());

        List<Class<?>> method_arguments = cf.getArguments(description_of_method);
        int number_of_method_arguments = method_arguments.size();

        int stack_size = stack.size();
        Pair<Class<?>, Object> objectref = stack.get(stack_size - number_of_method_arguments - 1);

        List<Pair<Class<?>, Object>> arguments_on_stack = stack.subList(stack_size - number_of_method_arguments,
                stack_size);

        if (objectref.second.getClass().getName() != name_of_class && !isINVOKESPECIAL
                && !ClassFile.isClassBuiltIn(name_of_class)) {
            name_of_class = objectref.second.getClass().getName().replace(".", "/");
        }

        List<Object> arguments_of_function = new ArrayList<Object>();
        List<Class<?>> type_of_arguments = new ArrayList<Class<?>>();
        Instructions_Helper.SETARGUMENTS_AND_TYPES(arguments_on_stack, arguments_of_function, type_of_arguments);
        Class<?>[] types_of_function_paramaters = new Class<?>[type_of_arguments.size()];
        for (int j = 0; j < type_of_arguments.size(); ++j) {
            types_of_function_paramaters[j] = Object.class;
        }

        String new_filename = "";
        Class<?> reference_to_class = null;
        if (ClassFile.isClassBuiltIn(name_of_class)) {
            reference_to_class = Class.forName(name_of_class.replace("/", "."));
        } else {
            Pair<String, Class<?>> returned = Instructions_Helper.LOAD_CLASS_FROM_OTHER_FILE(file_name, name_of_class);
            reference_to_class = returned.second;
            new_filename = returned.first;
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

        try {
            if (ClassFile.isClassBuiltIn(name_of_class)) {
                Method method = Instructions_Helper.GET_CORRECT_METHOD(reference_to_class, name_and_type_of_member,
                        types_of_function_paramaters,
                        method_arguments, arguments_on_stack);
                types_of_function_paramaters = Instructions_Helper.GET_CORRECT_METHOD_TYPES(reference_to_class,
                        name_and_type_of_member,
                        types_of_function_paramaters, method_arguments, arguments_on_stack);

                Object[] arguments_as_objects = new Object[arguments_of_function.size()];
                for (int j = 0; j < arguments_of_function.size(); ++j) {
                    arguments_as_objects[j] = (Object) arguments_of_function.get(j);
                }

                method.setAccessible(true);
                result = method.invoke(objectref.second, arguments_as_objects);
                if (result != null) {
                    returnType = result.getClass();
                }
            } else {
                ClassFile CLASS_FILE = new ClassFile(new_filename, null);

                Method_Info fn_method = new Method_Info();
                List<Attribute_Info> attributes = new ArrayList<>();

                try {
                    fn_method = CLASS_FILE.findMethodsByName(name_and_type_of_member, description_of_method);

                    if (fn_method == null) {
                        name_of_class = cf.getNameOfClass(reference_to_method.getClassIndex());

                        if (ClassFile.isClassBuiltIn(name_of_class)) {
                            Method method = Instructions_Helper.GET_CORRECT_METHOD(reference_to_class,
                                    name_and_type_of_member,
                                    types_of_function_paramaters,
                                    method_arguments, arguments_on_stack);
                            types_of_function_paramaters = Instructions_Helper.GET_CORRECT_METHOD_TYPES(
                                    reference_to_class,
                                    name_and_type_of_member,
                                    types_of_function_paramaters, method_arguments, arguments_on_stack);

                            Object[] arguments_as_objects = new Object[arguments_of_function.size()];
                            for (int j = 0; j < arguments_of_function.size(); ++j) {
                                arguments_as_objects[j] = (Object) arguments_of_function.get(j);
                            }

                            method.setAccessible(true);
                            result = method.invoke(objectref.second, arguments_as_objects);
                            if (result != null) {
                                returnType = result.getClass();
                            }
                        } else {
                            Pair<String, Class<?>> returned = Instructions_Helper.LOAD_CLASS_FROM_OTHER_FILE(file_name,
                                    name_of_class);
                            reference_to_class = returned.second;
                            new_filename = returned.first;

                            CLASS_FILE = new ClassFile(new_filename, null);
                            fn_method = CLASS_FILE.findMethodsByName(name_and_type_of_member, description_of_method);

                            if (fn_method == null) {
                                name_of_class = objectref.second.getClass().getSuperclass().getName().replace(".", "/");

                                if (ClassFile.isClassBuiltIn(name_of_class)) {
                                    Method method = Instructions_Helper.GET_CORRECT_METHOD(reference_to_class,
                                            name_and_type_of_member,
                                            types_of_function_paramaters,
                                            method_arguments, arguments_on_stack);
                                    types_of_function_paramaters = Instructions_Helper.GET_CORRECT_METHOD_TYPES(
                                            reference_to_class,
                                            name_and_type_of_member,
                                            types_of_function_paramaters, method_arguments, arguments_on_stack);

                                    Object[] arguments_as_objects = new Object[arguments_of_function.size()];
                                    for (int j = 0; j < arguments_of_function.size(); ++j) {
                                        arguments_as_objects[j] = (Object) arguments_of_function.get(j);
                                    }
                                    method.setAccessible(true);
                                    result = method.invoke(objectref.second, arguments_as_objects);
                                    if (result != null) {
                                        returnType = result.getClass();
                                    }
                                } else {
                                    returned = Instructions_Helper.LOAD_CLASS_FROM_OTHER_FILE(file_name,
                                            name_of_class);
                                    reference_to_class = returned.second;
                                    new_filename = returned.first;

                                    CLASS_FILE = new ClassFile(new_filename, null);
                                    fn_method = CLASS_FILE.findMethodsByName(name_and_type_of_member,
                                            description_of_method);
                                }
                            }
                        }
                    }
                    if (result == null) {
                        attributes = CLASS_FILE.findAttributesByName(fn_method.attributes, "Code");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                CLASS_FILE.local[0] = objectref.second;
                int localIdx = 1;
                for (int i = stack_size - number_of_method_arguments; i < stack_size; ++i) {
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

                        Pair<Class<?>, Object> returnResult = CLASS_FILE.executeCode(codeAttribute);
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

                Method method = Instructions_Helper.GET_CORRECT_METHOD(reference_to_class, name_and_type_of_member,
                        types_of_function_paramaters,
                        method_arguments, arguments_on_stack);
                types_of_function_paramaters = Instructions_Helper.GET_CORRECT_METHOD_TYPES(reference_to_class,
                        name_and_type_of_member,
                        types_of_function_paramaters, method_arguments, arguments_on_stack);

                Object[] arguments_as_objects = new Object[arguments_of_function.size()];
                for (int j = 0; j < arguments_of_function.size(); ++j) {
                    arguments_as_objects[j] = (Object) arguments_of_function.get(j);
                }

                method = obj.getClass().getDeclaredMethod(name_and_type_of_member,
                        types_of_function_paramaters);
                if (ClassFile.isClassBuiltIn(name_of_class)) {
                    method.setAccessible(true);
                    result = method.invoke(objectref.second, arguments_as_objects);
                    if (result != null) {
                        returnType = result.getClass();
                    }
                } else {
                    ClassFile CLASS_FILE = new ClassFile(new_filename, null);

                    Method_Info fn_method = new Method_Info();
                    List<Attribute_Info> attributes = new ArrayList<>();

                    try {
                        fn_method = CLASS_FILE.findMethodsByName(name_and_type_of_member, description_of_method);
                        attributes = CLASS_FILE.findAttributesByName(fn_method.attributes, "Code");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    CLASS_FILE.local[0] = objectref.second;

                    for (Attribute_Info attribute : attributes) {
                        try {
                            Code_Attribute codeAttribute = Code_Attribute_Helper.readCodeAttributes(attribute);

                            Pair<Class<?>, Object> returnResult = CLASS_FILE.executeCode(codeAttribute);
                            result = returnResult.second;
                            returnType = returnResult.first;
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                }

                local[objectRefIdx] = obj;
            } catch (Exception e) {
                Method method = Instructions_Helper.GET_CORRECT_METHOD(reference_to_class, name_and_type_of_member,
                        types_of_function_paramaters,
                        method_arguments, arguments_on_stack);
                types_of_function_paramaters = Instructions_Helper.GET_CORRECT_METHOD_TYPES(reference_to_class,
                        name_and_type_of_member,
                        types_of_function_paramaters, method_arguments, arguments_on_stack);

                Object[] arguments_as_objects = new Object[arguments_of_function.size()];
                for (int j = 0; j < arguments_of_function.size(); ++j) {
                    arguments_as_objects[j] = (Object) arguments_of_function.get(j);
                }

                if (obj == null) {
                    obj = objectref.first;

                    method = Instructions_Helper.GET_CORRECT_METHOD(objectref.first.getClass(),
                            name_and_type_of_member,
                            types_of_function_paramaters,
                            method_arguments, arguments_on_stack);
                } else {
                    method = Instructions_Helper.GET_CORRECT_METHOD(reference_to_class,
                            name_and_type_of_member,
                            types_of_function_paramaters,
                            method_arguments, arguments_on_stack);
                }

                if (ClassFile.isClassBuiltIn(name_of_class)) {
                    method.setAccessible(true);
                    result = method.invoke(objectref.second, arguments_as_objects);
                    if (result != null) {
                        returnType = result.getClass();
                    }
                } else {
                    ClassFile CLASS_FILE = new ClassFile(new_filename, null);

                    Method_Info fn_method = new Method_Info();
                    List<Attribute_Info> attributes = new ArrayList<>();

                    try {
                        fn_method = CLASS_FILE.findMethodsByName(name_and_type_of_member, description_of_method);
                        attributes = CLASS_FILE.findAttributesByName(fn_method.attributes, "Code");
                    } catch (Exception ee) {
                        e.printStackTrace();
                    }

                    CLASS_FILE.local[0] = objectref.second;

                    for (Attribute_Info attribute : attributes) {
                        try {
                            Code_Attribute codeAttribute = Code_Attribute_Helper.readCodeAttributes(attribute);

                            Pair<Class<?>, Object> returnResult = CLASS_FILE.executeCode(codeAttribute);
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

        stack.subList(stack_size - number_of_method_arguments - 1, stack_size).clear();

        if (result != null) {
            if (returnType == int.class && description_of_method
                    .substring(description_of_method.indexOf(")", 0) + 1, description_of_method.length()).equals("Z")) {
                stack.add(new Pair<Class<?>, Object>(boolean.class, ((Number) result).intValue() == 1 ? true : false));
            } else {
                stack.add(new Pair<Class<?>, Object>(returnType, result));
            }
        } else if (obj != null) {
            local[objectRefIdx] = obj;
        } else {
            stack.add(new Pair<Class<?>, Object>(String.class, "null"));
        }
    }

    public static void INVOKESPECIAL(List<Pair<Class<?>, Object>> stack, List<CP_Info> constant_pool, short index,
            Object[] local, String file_name, ClassFile cf)
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException,
            ClassNotFoundException, MalformedURLException, IOException {
        CP_Info reference_to_method = constant_pool.get(index - 1);
        String name_of_class = cf.getNameOfClass(reference_to_method.getClassIndex());
        String name_and_type_of_member = cf.getNameOfMember(reference_to_method.getNameAndTypeIndex());

        Class<?> reference_to_class = null;
        if (ClassFile.isClassBuiltIn(name_of_class)) {
            reference_to_class = Class.forName(name_of_class.replace("/", "."));
        } else {
            reference_to_class = Instructions_Helper.LOAD_CLASS_FROM_OTHER_FILE(file_name, name_of_class).second;
        }

        if (name_and_type_of_member.equals("<init>")) {
            String description_of_method = cf.getDescriptionOfMethod(reference_to_method.getNameAndTypeIndex());
            List<Class<?>> method_arguments = cf.getArguments(description_of_method);
            int number_of_method_arguments = method_arguments.size();

            int stack_size = stack.size();
            List<Pair<Class<?>, Object>> arguments_on_stack = stack.subList(stack_size - number_of_method_arguments,
                    stack_size);

            List<Object> arguments_of_function = new ArrayList<Object>();
            List<Class<?>> type_of_arguments = new ArrayList<Class<?>>();
            Instructions_Helper.SETARGUMENTS_AND_TYPES(arguments_on_stack, arguments_of_function, type_of_arguments);
            Class<?>[] types_of_function_paramaters = new Class<?>[type_of_arguments.size()];
            for (int j = 0; j < type_of_arguments.size(); ++j) {
                types_of_function_paramaters[j] = (Class<?>) type_of_arguments.get(j);
            }

            Object[] arguments_as_objects = new Object[arguments_of_function.size()];
            for (int j = 0; j < arguments_of_function.size(); ++j) {
                arguments_as_objects[j] = (Object) arguments_of_function.get(j);
            }

            Constructor<?> initConstructor = null;
            try {
                initConstructor = reference_to_class.getDeclaredConstructor(types_of_function_paramaters);
            } catch (Exception e) {
                for (Constructor<?> ctor : reference_to_class.getDeclaredConstructors()) {
                    boolean isCorrectConstructors = true;
                    boolean hadToassign = false;

                    for (int j = 0; j < type_of_arguments.size(); ++j) {
                        types_of_function_paramaters[j] = (Class<?>) type_of_arguments.get(j);
                    }

                    if (ctor.getParameterTypes().length == number_of_method_arguments) {
                        for (int i = 0; i < ctor.getParameterTypes().length; ++i) {
                            Class<?> type = ctor.getParameterTypes()[i];
                            if (!type.getName().equals(types_of_function_paramaters[i].getName())
                                    && !type.isAssignableFrom(types_of_function_paramaters[i])) {
                                types_of_function_paramaters[i] = Object.class;
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

            initConstructor.setAccessible(true);
            stack.subList(stack_size - number_of_method_arguments - 2, stack_size).clear();

            stack.add(
                    new Pair<Class<?>, Object>(reference_to_class,
                            initConstructor.newInstance(arguments_as_objects)));
        } else {
            INVOKEVIRTUAL(stack, constant_pool, index, local, file_name, cf, true);
        }
    }

    public static void INVOKESTATIC(List<Pair<Class<?>, Object>> stack, List<CP_Info> constant_pool, short index,
            Object[] local, String file_name, ClassFile cf, List<Exception_Table> exceptions, CodeIndex codeIndex)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        CP_Info reference_to_method = constant_pool.get(index - 1);
        String name_of_class = cf.getNameOfClass(reference_to_method.getClassIndex());
        String name_and_type_of_member = cf.getNameOfMember(reference_to_method.getNameAndTypeIndex());
        String description_of_method = cf.getDescriptionOfMethod(reference_to_method.getNameAndTypeIndex());

        List<Class<?>> method_arguments = cf.getArguments(description_of_method);
        int number_of_method_arguments = method_arguments.size();

        int stack_size = stack.size();
        List<Pair<Class<?>, Object>> arguments_on_stack = new ArrayList<>();
        if (0 <= stack_size - number_of_method_arguments) {
            arguments_on_stack = stack.subList(stack_size - number_of_method_arguments, stack_size);
        }

        List<Object> arguments_of_function = new ArrayList<Object>();
        List<Class<?>> type_of_arguments = new ArrayList<Class<?>>();
        Instructions_Helper.SETARGUMENTS_AND_TYPES(arguments_on_stack, arguments_of_function, type_of_arguments);
        Class<?>[] types_of_function_paramaters = new Class<?>[type_of_arguments.size()];
        for (int j = 0; j < type_of_arguments.size(); ++j) {
            types_of_function_paramaters[j] = (Class<?>) type_of_arguments.get(j);
        }

        String new_filename = "";
        Class<?> reference_to_class = null;
        if (ClassFile.isClassBuiltIn(name_of_class)) {
            reference_to_class = Class.forName(name_of_class.replace("/", "."));
        } else {
            Pair<String, Class<?>> returned = Instructions_Helper.LOAD_CLASS_FROM_OTHER_FILE(file_name, name_of_class);
            reference_to_class = returned.second;
            new_filename = returned.first;
        }

        Object result = null;
        Class<?> returnType = void.class;
        if (ClassFile.isClassBuiltIn(name_of_class)) {
            Method method = Instructions_Helper.GET_CORRECT_METHOD(reference_to_class, name_and_type_of_member,
                    types_of_function_paramaters,
                    method_arguments, arguments_on_stack);

            Object[] arguments_as_objects = new Object[arguments_of_function.size()];
            for (int j = 0; j < arguments_of_function.size(); ++j) {
                arguments_as_objects[j] = (Object) arguments_of_function.get(j);
            }

            method.setAccessible(true);
            try {
                result = method.invoke(name_of_class.replace("/", "."), arguments_as_objects);
                if (result != null) {
                    returnType = result.getClass();
                }
            } catch (Throwable t) {
                if (name_and_type_of_member.equals("arraycopy")) {
                    int objectIdx = -1;
                    for (int i = 0; i < 65535 && objectIdx == -1; ++i) {
                        if (local[i] == stack.get(stack_size - number_of_method_arguments + 2).second) {
                            objectIdx = i;
                        }
                    }
                    stack.set(stack_size - number_of_method_arguments + 2,
                            stack.get(stack_size - number_of_method_arguments));
                    if (objectIdx != -1) {
                        local[objectIdx] = stack.get(stack_size - number_of_method_arguments).second;
                    }
                } else {
                    Throwable problem = t.getCause();
                    if (0 < stack_size) {
                        stack.subList(0, stack_size - 1).clear();
                    }
                    boolean exception_thrown = false;
                    for (Exception_Table exception : exceptions) {
                        String exception_name = new String(constant_pool
                                .get(constant_pool.get(exception.catch_type - 1).getNameIndex() - 1)
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
            ClassFile CLASS_FILE = new ClassFile(new_filename, null);

            Method_Info fn_method = new Method_Info();
            List<Attribute_Info> attributes = new ArrayList<>();

            try {
                fn_method = CLASS_FILE.findMethodsByName(name_and_type_of_member, description_of_method);
                attributes = CLASS_FILE.findAttributesByName(fn_method.attributes, "Code");
            } catch (Exception e) {
                e.printStackTrace();
            }

            int localIdx = 0;
            for (int i = stack_size - number_of_method_arguments; i < stack_size; ++i) {
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

                    Pair<Class<?>, Object> returnResult = CLASS_FILE.executeCode(codeAttribute);
                    result = returnResult.second;
                    returnType = returnResult.first;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }

        if (0 <= stack_size - number_of_method_arguments) {
            stack.subList(stack_size - number_of_method_arguments, stack_size).clear();
        }

        if (result != null) {
            stack.add(new Pair<Class<?>, Object>(returnType, result));
        }

    }

    public static void INVOKEINTERFACE(List<Pair<Class<?>, Object>> stack, List<CP_Info> constant_pool, short index,
            byte count,
            Object[] local,
            String file_name, ClassFile cf)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // TODO
        CP_Info reference_to_interface = (CONSTANT_InterfaceMethodref_Info) constant_pool.get(index - 1);
        String name_of_class = cf.getNameOfClass(reference_to_interface.getClassIndex());
        String name_and_type_of_member = cf.getNameOfMember(reference_to_interface.getNameAndTypeIndex());
        String description_of_method = cf.getDescriptionOfMethod(reference_to_interface.getNameAndTypeIndex());

        List<Class<?>> method_arguments = cf.getArguments(description_of_method);

        int stack_size = stack.size();
        Pair<Class<?>, Object> objectref = stack.get(stack_size - count);

        List<Pair<Class<?>, Object>> arguments_on_stack = stack.subList(stack_size - count + 1,
                stack_size);

        if (objectref.second.getClass().getName() != name_of_class && !ClassFile.isClassBuiltIn(name_of_class)) {
            name_of_class = objectref.second.getClass().getName().replace(".", "/");
        }

        List<Object> arguments_of_function = new ArrayList<Object>();
        List<Class<?>> type_of_arguments = new ArrayList<Class<?>>();
        Instructions_Helper.SETARGUMENTS_AND_TYPES(arguments_on_stack, arguments_of_function, type_of_arguments);
        Class<?>[] types_of_function_paramaters = new Class<?>[type_of_arguments.size()];
        for (int j = 0; j < type_of_arguments.size(); ++j) {
            types_of_function_paramaters[j] = (Class<?>) type_of_arguments.get(j);
        }

        String new_filename = "";
        Class<?> reference_to_class = null;
        if (ClassFile.isClassBuiltIn(name_of_class)) {
            reference_to_class = Class.forName(name_of_class.replace("/", "."));
        } else {
            Pair<String, Class<?>> returned = Instructions_Helper.LOAD_CLASS_FROM_OTHER_FILE(file_name, name_of_class);
            reference_to_class = returned.second;
            new_filename = returned.first;
        }

        Method method = Instructions_Helper.GET_CORRECT_METHOD(reference_to_class, name_and_type_of_member,
                types_of_function_paramaters,
                method_arguments, arguments_on_stack);
        types_of_function_paramaters = Instructions_Helper.GET_CORRECT_METHOD_TYPES(reference_to_class,
                name_and_type_of_member,
                types_of_function_paramaters, method_arguments, arguments_on_stack);

        Object[] arguments_as_objects = new Object[arguments_of_function.size()];
        for (int j = 0; j < arguments_of_function.size(); ++j) {
            arguments_as_objects[j] = /* (Object) */ arguments_of_function.get(j);
        }

        Object result = null;
        Class<?> returnType = void.class;
        if (ClassFile.isClassBuiltIn(name_of_class)) {
            method.setAccessible(true);
            result = method.invoke(objectref.second, arguments_as_objects);
            if (result != null) {
                returnType = result.getClass();
            }
        } else {
            ClassFile CLASS_FILE = new ClassFile(new_filename, null);

            Method_Info fn_method = new Method_Info();
            List<Attribute_Info> attributes = new ArrayList<>();

            try {
                fn_method = CLASS_FILE.findMethodsByName(name_and_type_of_member, description_of_method);
                attributes = CLASS_FILE.findAttributesByName(fn_method.attributes, "Code");
            } catch (Exception e) {
                e.printStackTrace();
            }

            CLASS_FILE.local[0] = objectref.second;
            int localIdx = 1;
            for (int i = stack_size - method_arguments.size(); i < stack_size; ++i) {
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

                    Pair<Class<?>, Object> returnResult = CLASS_FILE.executeCode(codeAttribute);
                    result = returnResult.second;
                    returnType = returnResult.first;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }

        stack.subList(stack_size - count, stack_size).clear();

        if (result != null) {
            stack.add(new Pair<Class<?>, Object>(returnType, result));
        } else {
            stack.add(objectref);
        }
    }

    public static void INVOKEDYNAMIC(List<Pair<Class<?>, Object>> stack, List<CP_Info> constant_pool, short index,
            Object[] local, List<BootstrapMethods_Attribute> bootstrap_methods, List<CallSite> call_sites,
            String file_name, ClassFile cf) throws ClassNotFoundException, Throwable {
        CP_Info reference_to_method = constant_pool.get(index - 1);
        String description_of_method = cf.getDescriptionOfMethod(reference_to_method.getNameAndTypeIndex());
        List<Class<?>> method_arguments = cf.getArguments(description_of_method);
        int number_of_method_arguments = method_arguments.size();

        short bootstrap_method_attr_index = reference_to_method.getBootStrapMethodAttributeIndex();
        BootstrapMethods_Attribute bootstrap_method = bootstrap_methods.get(bootstrap_method_attr_index);
        // int bootstrap_method_ref_index = bootstrap_method.bootstrap_methods
        // .get(bootstrap_method_attr_index).bootstrap_method_ref;

        BoostrapMethod stuff = bootstrap_method.bootstrap_methods
                .get(bootstrap_method_attr_index);

        for (int i = 0; i < stuff.num_bootstrap_arguments; ++i) {
            short currIndex = stuff.bootstrap_arguments[i];
            CP_Info stuff1 = constant_pool.get(currIndex - 1);
            if (stuff1 instanceof CONSTANT_MethodHandle_Info) {
                short reference = stuff1.getReferenceIndex();
                CP_Info stuff2 = constant_pool.get(reference - 1);
                String name_of_class = new String(
                        constant_pool.get(constant_pool.get(stuff2.getClassIndex() - 1).getNameIndex() - 1).getBytes(),
                        StandardCharsets.UTF_8).replace("/", ".");
                String name_of_function = new String(
                        constant_pool.get(constant_pool.get(stuff2.getNameAndTypeIndex() - 1).getNameIndex() - 1)
                                .getBytes(),
                        StandardCharsets.UTF_8);

                Method method = null;
                if (ClassFile.isClassBuiltIn(name_of_class)) {
                    Class<?> c = Class.forName(name_of_class);
                    method = c.getDeclaredMethod(name_of_function);
                } else {
                    Pair<String, Class<?>> returned = Instructions_Helper.LOAD_CLASS_FROM_OTHER_FILE(file_name,
                            name_of_class);
                    Class<?> reference_to_class = returned.second;
                    String new_filename = returned.first;
                    Class<?> resolved_class = null;

                    File f = new File(new_filename);
                    int length = 0;
                    if (reference_to_class.getName().contains("/")) {
                        length = reference_to_class.getName().split("/").length;
                    } else {
                        length = reference_to_class.getName().split("\\.").length;
                    }
                    for (int j = 0; j < length + 1 && resolved_class == null; ++j) {
                        URL[] cp = { f.toURI().toURL() };
                        URLClassLoader urlcl = new URLClassLoader(cp);
                        try {
                            resolved_class = urlcl.loadClass(reference_to_class.getName());
                        } catch (Exception eee) {

                        }
                        urlcl.close();

                        f = new File(f.getParent());
                    }

                    for (Method m : resolved_class.getDeclaredMethods()) {
                        if (m.getName().equals(name_of_function)) {
                            method = m;
                            break;
                        }
                    }
                }

                stack.add(new Pair<Class<?>, Object>(Method.class, method));
            }
        }
    }

    public static void NEW(List<Pair<Class<?>, Object>> stack, List<CP_Info> constant_pool, short index, Object[] local,
            String file_name, ClassFile cf)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, MalformedURLException,
            IOException {
        String name_of_class = cf.getNameOfMember(index);
        Class<?> reference_to_class = null;

        if (ClassFile.isClassBuiltIn(name_of_class)) {
            reference_to_class = Class.forName(name_of_class.replace("/", "."));
        } else {
            reference_to_class = Instructions_Helper.LOAD_CLASS_FROM_OTHER_FILE(file_name,
                    name_of_class).second;
        }

        stack.add(new Pair<Class<?>, Object>(reference_to_class, reference_to_class));
    }

    public static void NEWARRAY(List<Pair<Class<?>, Object>> stack, byte atype) {
        Class<?> arrayType = Instructions_Helper.GetArrayType(atype);
        int count = ((Number) stack.remove(stack.size() - 1).second).intValue();

        stack.add(new Pair<Class<?>, Object>(arrayType, Array.newInstance(arrayType, count)));
    }

    public static void ANEWARRAY(List<Pair<Class<?>, Object>> stack, List<CP_Info> constant_pool, short index,
            String file_name, ClassFile cf)
            throws ClassNotFoundException {
        ConstantPoolTag tag = constant_pool.get((index & 0xFF) - 1).tag;

        Class<?> arrayType = null;
        if (tag == ConstantPoolTag.CONSTANT_Utf8 || tag == ConstantPoolTag.CONSTANT_Fieldref
                || tag == ConstantPoolTag.CONSTANT_Class) {
            String name_of_class = cf.getNameOfClass(index);
            if (ClassFile.isClassBuiltIn(name_of_class)) {
                arrayType = Class.forName(name_of_class.replace("/", "."));
            } else {
                arrayType = Instructions_Helper.LOAD_CLASS_FROM_OTHER_FILE(file_name, name_of_class).second;
            }
        } else {
            arrayType = Instructions_Helper.TagSwitchType(constant_pool, tag);
        }
        int count = ((Number) stack.remove(stack.size() - 1).second).intValue();

        stack.add(new Pair<Class<?>, Object>(arrayType, Array.newInstance(arrayType, count)));
    }

    public static void ARRAYLENGTH(List<Pair<Class<?>, Object>> stack) {
        Pair<Class<?>, Object> arrayref = stack.remove(stack.size() - 1);
        stack.add(new Pair<Class<?>, Object>(int.class, Array.getLength(arrayref.second)));
    }

    public static void ATHROW(List<Pair<Class<?>, Object>> stack, List<CP_Info> constant_pool,
            List<Exception_Table> exceptions, CodeIndex codeIndex)
            throws Throwable {
        int stack_size = stack.size() - 1;
        Object objectref = stack.get(stack_size).second;

        if (objectref instanceof Throwable) {
            if (0 < stack_size) {
                stack.subList(0, stack_size - 1).clear();
            }
            boolean exception_thrown = false;
            for (Exception_Table exception : exceptions) {
                String exception_name = new String(constant_pool
                        .get(constant_pool.get(exception.catch_type - 1).getNameIndex() - 1)
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
        String name_of_class = cf.getNameOfMember(index);

        Class<?> reference_to_class = null;
        if (ClassFile.isClassBuiltIn(name_of_class)) {
            reference_to_class = Class.forName(name_of_class.replace("/", "."));
        } else {
            reference_to_class = Instructions_Helper.LOAD_CLASS_FROM_OTHER_FILE(file_name, name_of_class).second;
        }

        Pair<Class<?>, Object> objectRef = stack.get(stack.size() - 1);
        if (objectRef == null) {
            return;
        }

        try {
            reference_to_class.isAssignableFrom(objectRef.second.getClass());
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
            String name_of_class = cf.getNameOfMember(index);
            Class<?> reference_to_class = null;
            if (ClassFile.isClassBuiltIn(name_of_class)) {
                reference_to_class = Class.forName(name_of_class.replace("/", "."));
            } else {
                reference_to_class = Instructions_Helper.LOAD_CLASS_FROM_OTHER_FILE(file_name, name_of_class).second;
            }

            Class<?> type = objectRef.first;
            if (reference_to_class.isAssignableFrom(type)) {
                stack.add(new Pair<Class<?>, Object>(int.class, 1));
            } else {
                stack.add(new Pair<Class<?>, Object>(int.class, 0));
            }
        }
    }

    public static void WIDE(byte[] code, CodeIndex codeIndex, List<Pair<Class<?>, Object>> stack, Object[] local,
            Opcode instruction) {
        short index = -1;
        short constVal = -1;

        switch (instruction) {
            case ILOAD, LLOAD, FLOAD, DLOAD, ALOAD, LSTORE, ISTORE, FSTORE, DSTORE, ASTORE -> {
                index = ClassFile_Helper.readShort(code, codeIndex);
            }
            case IINC -> {
                index = ClassFile_Helper.readShort(code, codeIndex);
                constVal = ClassFile_Helper.readShort(code, codeIndex);
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
                IINC(local, index & 0xFFFF, constVal);
            }
            default -> {
                throw new UnsupportedOperationException(
                        "WIDE for " + instruction + " is not supported!");
            }
        }
    }

    public static void MULTIANEWARRAY(List<Pair<Class<?>, Object>> stack, List<CP_Info> constant_pool, short index,
            byte dimensions, String file_name, ClassFile cf)
            throws ClassNotFoundException {
        ConstantPoolTag tag = constant_pool.get((index & 0xFF) - 1).tag;

        Class<?> arrayType = null;
        Class<?> hackyType = null;
        if (tag == ConstantPoolTag.CONSTANT_Utf8 || tag == ConstantPoolTag.CONSTANT_Fieldref
                || tag == ConstantPoolTag.CONSTANT_Class) {
            String name_of_class = cf.getNameOfClass(index);
            if (ClassFile.isClassBuiltIn(name_of_class)) {
                arrayType = Class.forName(name_of_class.replace("/", "."));
                hackyType = Class.forName(name_of_class.replace("/", ".").replaceAll("\\[+", "["));
            } else {
                arrayType = Instructions_Helper.LOAD_CLASS_FROM_OTHER_FILE(file_name, name_of_class).second;
                hackyType = arrayType;
            }
        } else {
            arrayType = Instructions_Helper.TagSwitchType(constant_pool, tag);
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
                if (value == null && value != "null") {
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
            String class_name = cf.CLASS_NAME.replace("/", ".");

            Pair<String, Class<?>> returned = Instructions_Helper.LOAD_CLASS_FROM_OTHER_FILE(cf.FILE_NAME,
                    cf.CLASS_NAME);
            Class<?> reference_to_class = returned.second;
            String new_filename = returned.first;
            Class<?> resolved_class = null;

            File f = new File(new_filename);
            int length = 0;
            if (reference_to_class.getName().contains("/")) {
                length = reference_to_class.getName().split("/").length;
            } else {
                length = reference_to_class.getName().split("\\.").length;
            }
            for (int i = 0; i < length + 1 && resolved_class == null; ++i) {
                URL[] cp = { f.toURI().toURL() };
                URLClassLoader urlcl = new URLClassLoader(cp);
                try {
                    resolved_class = urlcl.loadClass(reference_to_class.getName());
                } catch (Exception eee) {

                }
                urlcl.close();

                f = new File(f.getParent());
            }

            int numberOfVariables = 0;
            for (int i = 0; i < cf.stack.size(); i++) {
                Object currentObject = cf.stack.get(i).second;
                if (!(currentObject instanceof Class<?>
                        && class_name.equals(((Class<?>) currentObject).getName()))) {
                    numberOfVariables++;
                }
            }

            Object[] arguments_as_objects = new Object[numberOfVariables];
            int idx = 0;
            for (int i = 0; i < cf.stack.size(); i++) {
                Object currentObject = cf.stack.get(i).second;
                if (!(currentObject instanceof Class<?>
                        && class_name.equals(((Class<?>) currentObject).getName()))) {
                    arguments_as_objects[idx++] = currentObject;
                }
            }

            Constructor<?> initConstructor = null;
            for (Constructor<?> ctor : resolved_class.getDeclaredConstructors()) {
                if (ctor.getParameterCount() == numberOfVariables) {
                    initConstructor = ctor;
                }
            }

            if (initConstructor == null) {
                for (int j = 0; j < resolved_class.getDeclaredConstructors().length; j++) {
                    Constructor<?> ctor = resolved_class.getDeclaredConstructors()[j];
                    Class<?>[] argumentTypes = ctor.getParameterTypes();
                    arguments_as_objects = new Object[ctor.getParameterCount()];
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
                            arguments_as_objects[currentArguments] = currentObject;
                            currentArguments++;
                        }
                    }
                    if (currentArguments == ctor.getParameterCount()
                            || j == resolved_class.getDeclaredConstructors().length - 1) {
                        initConstructor = ctor;
                    }
                }
            }

            try {
                initConstructor.setAccessible(true);
                initConstructor.newInstance(arguments_as_objects);
                cf.stack.clear();
                cf.stack.add(
                        new Pair<Class<?>, Object>(resolved_class, initConstructor.newInstance(arguments_as_objects)));
            } catch (Throwable e) {
                for (int j = 0; j < resolved_class.getDeclaredConstructors().length; j++) {
                    Constructor<?> ctor = resolved_class.getDeclaredConstructors()[j];
                    Class<?>[] argumentTypes = ctor.getParameterTypes();
                    arguments_as_objects = new Object[ctor.getParameterCount()];
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
                            arguments_as_objects[currentArguments] = currentObject;
                            currentArguments++;
                        } else if (!(currentObject instanceof Class<?>)) {
                            if (arguments_as_objects[0] == null || numberOfVariables < 2) {
                                return;
                            }
                            arguments_as_objects[currentArguments] = new int[((Number) arguments_as_objects[0])
                                    .intValue() * ((Number) arguments_as_objects[1]).intValue()];
                            currentArguments++;
                        }
                    }
                    if (currentArguments == ctor.getParameterCount()
                            || j == resolved_class.getDeclaredConstructors().length - 1) {
                        initConstructor = ctor;

                        argumentTypes = initConstructor.getParameterTypes();
                        boolean allValid = true;
                        for (int i = 0; i < argumentTypes.length; ++i) {
                            if (!arguments_as_objects[i].getClass().getName().equals(argumentTypes[i].getName())) {
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
                            new Pair<Class<?>, Object>(resolved_class,
                                    initConstructor.newInstance(arguments_as_objects)));
                } catch (Throwable ee) {
                    return;
                }
            }

            cf.local[0] = cf.stack.get(0).second;

            for (var INITIALIZE : cf.STATICS_TO_INITIALIZE) {
                String name_of_field = INITIALIZE.first.getName();
                reference_to_class = cf.stack.get(0).second.getClass();
                Field inField = reference_to_class.getDeclaredField(name_of_field);
                inField.setAccessible(true);
                inField.set(reference_to_class, INITIALIZE.second.second);
            }

            cf.MUST_INITIALIZE = false;
        }
    }
}

class Instructions_Helper {
    public static Object TagSwitchValue(List<CP_Info> CONSTANT_POOL, ConstantPoolTag tag, int index) {
        switch (tag) {
            case CONSTANT_String -> {
                return new String(CONSTANT_POOL.get((CONSTANT_POOL.get(index - 1)).getStringIndex() - 1).getBytes(),
                        StandardCharsets.UTF_8);
            }
            case CONSTANT_Float -> {
                return CONSTANT_POOL.get(index - 1).getFloatValue();
            }
            case CONSTANT_Integer -> {
                return CONSTANT_POOL.get(index - 1).getIntValue();
            }
            case CONSTANT_Long -> {
                return CONSTANT_POOL.get(index - 1).getLongValue();
            }
            case CONSTANT_Double -> {
                return CONSTANT_POOL.get(index - 1).getDoubleValue();
            }
            default -> {
                throw new UnsupportedOperationException(tag + " is not implemented yet!");
            }
        }
    }

    public static Class<?> TagSwitchType(List<CP_Info> CONSTANT_POOL, ConstantPoolTag tag) {
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

    public static Class<?> GetArrayType(byte atype) {
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
        Pair<Class<?>, Object> arrayRef = stack.remove(stack.size() - 1);

        return Array.get(arrayRef.second, index);
    }

    public static void ARRAYSTORE(List<Pair<Class<?>, Object>> stack) {
        Pair<Class<?>, Object> value = stack.remove(stack.size() - 1);
        int index = ((Number) stack.remove(stack.size() - 1).second).intValue();
        Pair<Class<?>, Object> arrayRef = stack.remove(stack.size() - 1);

        Array.set(arrayRef.second, index, value.second);
    }

    public static void SETARGUMENTS_AND_TYPES(List<Pair<Class<?>, Object>> arguments_on_stack,
            List<Object> arguments_of_function, List<Class<?>> type_of_arguments) {
        for (var arguments : arguments_on_stack) {
            Class<?> argument_type = arguments.first;
            Object current_argument = arguments.second;

            if (current_argument.getClass().isArray() && current_argument.getClass() != argument_type) {
                arguments_of_function.add(current_argument.getClass().cast(current_argument));
                // arguments_of_function.add(argument_type.cast(current_argument));
                type_of_arguments.add(current_argument.getClass());
                continue;
            } else if (argument_type == int.class) {
                arguments_of_function.add((int) current_argument);
            } else if (argument_type == float.class) {
                arguments_of_function.add((float) current_argument);
            } else if (argument_type == double.class) {
                arguments_of_function.add((double) current_argument);
            } else if (argument_type == long.class) {
                arguments_of_function.add((long) current_argument);
            } else {
                if (!argument_type.equals(current_argument) && argument_type != boolean.class) {
                    arguments_of_function.add(argument_type.cast(current_argument));
                } else {
                    arguments_of_function.add(current_argument);
                }
            }
            type_of_arguments.add(arguments.first);
        }
    }

    public static Pair<String, Class<?>> LOAD_CLASS_FROM_OTHER_FILE(String file_name, String class_name) {
        Pair<String, Class<?>> returned = null;
        try {
            File f = new File(file_name);
            int length = 0;
            if (class_name.contains("/")) {
                length = class_name.split("/").length;
            } else {
                length = class_name.split("\\.").length;
            }
            for (int i = 0; i < length + 1; ++i) {
                URL[] cp = { f.toURI().toURL() };
                URLClassLoader urlcl = new URLClassLoader(cp);
                try {
                    returned = new Pair<String, Class<?>>(f.getPath() + "/" + class_name + ".class",
                            urlcl.loadClass(class_name.replace("/", ".")));
                } catch (Exception e) {

                }
                urlcl.close();

                f = new File(f.getParent());
            }
        } catch (Exception e) {
            try {
                File f = new File(file_name.split(class_name.split("/")[0])[0] + class_name + ".class");
                URL[] cp = { f.toURI().toURL() };
                URLClassLoader urlcl = new URLClassLoader(cp);
                try {
                    returned = new Pair<String, Class<?>>(f.getPath() + "/" + class_name + ".class",
                            urlcl.loadClass(class_name.replace("/", ".")));
                } catch (Exception ee) {

                }
                urlcl.close();
            } catch (Exception ee) {
                return null;
            }
        }
        return returned;
    }

    public static Method GET_CORRECT_METHOD(Class<?> reference_to_class, String name_and_type_of_member,
            Class<?>[] types_of_function_paramaters, List<Class<?>> method_arguments,
            List<Pair<Class<?>, Object>> arguments_on_stack) throws NoSuchMethodException {
        Method returnedMethod = null;
        Class<?> original_reference = reference_to_class;

        if (ClassFile.doesMethodExists(reference_to_class, name_and_type_of_member, types_of_function_paramaters)) {
            while (reference_to_class != null && returnedMethod == null) {
                try {
                    returnedMethod = reference_to_class.getDeclaredMethod(name_and_type_of_member,
                            types_of_function_paramaters);
                } catch (Throwable t) {

                }
                reference_to_class = reference_to_class.getSuperclass();
            }

            if (returnedMethod == null) {
                for (Method method : original_reference.getDeclaredMethods()) {
                    boolean isCorrectMethod = true;

                    if (method.getName().equals(name_and_type_of_member)
                            && method.getParameterTypes().length == types_of_function_paramaters.length) {
                        for (int i = 0; i < method.getParameterTypes().length; ++i) {
                            Class<?> type = method.getParameterTypes()[i];
                            if (!type.getName().equals(types_of_function_paramaters[i].getName())) {
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
            types_of_function_paramaters = new Class<?>[method_arguments.size()];
            for (int j = 0; j < method_arguments.size(); ++j) {
                types_of_function_paramaters[j] = (Class<?>) method_arguments.get(j);
            }
            if (ClassFile.doesMethodExists(reference_to_class, name_and_type_of_member, types_of_function_paramaters)) {
                try {
                    returnedMethod = reference_to_class.getDeclaredMethod(name_and_type_of_member,
                            types_of_function_paramaters);
                } catch (Exception e) {
                    for (Method method : reference_to_class.getDeclaredMethods()) {
                        boolean isCorrectMethod = true;

                        if (method.getName().equals(name_and_type_of_member)
                                && method.getParameterTypes().length == types_of_function_paramaters.length) {
                            for (int i = 0; i < method.getParameterTypes().length; ++i) {
                                Class<?> type = method.getParameterTypes()[i];
                                if (!type.getName().equals(types_of_function_paramaters[i].getName())) {
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
                types_of_function_paramaters = new Class<?>[method_arguments.size()];
                for (int j = 0; j < method_arguments.size(); ++j) {
                    types_of_function_paramaters[j] = arguments_on_stack.get(j).first;
                }
                if (ClassFile.doesMethodExists(reference_to_class, name_and_type_of_member,
                        types_of_function_paramaters)) {
                    returnedMethod = reference_to_class.getDeclaredMethod(name_and_type_of_member,
                            types_of_function_paramaters);
                } else if (Enum.class.isAssignableFrom(reference_to_class)) {
                    returnedMethod = Enum.class.getDeclaredMethod(name_and_type_of_member,
                            types_of_function_paramaters);
                }
            }
        }

        return returnedMethod;
    }

    public static Class<?>[] GET_CORRECT_METHOD_TYPES(Class<?> reference_to_class, String name_and_type_of_member,
            Class<?>[] types_of_function_paramaters, List<Class<?>> method_arguments,
            List<Pair<Class<?>, Object>> arguments_on_stack) throws NoSuchMethodException {
        if (ClassFile.doesMethodExists(reference_to_class, name_and_type_of_member, types_of_function_paramaters)) {
            try {
                reference_to_class.getDeclaredMethod(name_and_type_of_member, types_of_function_paramaters);
            } catch (Exception e) {
                types_of_function_paramaters = new Class<?>[method_arguments.size()];
                for (int j = 0; j < method_arguments.size(); ++j) {
                    types_of_function_paramaters[j] = arguments_on_stack.get(j).first;
                }
            }
            return types_of_function_paramaters;
        } else {
            types_of_function_paramaters = new Class<?>[method_arguments.size()];
            for (int j = 0; j < method_arguments.size(); ++j) {
                types_of_function_paramaters[j] = (Class<?>) method_arguments.get(j);
            }
            if (ClassFile.doesMethodExists(reference_to_class, name_and_type_of_member, types_of_function_paramaters)) {
                try {
                    reference_to_class.getDeclaredMethod(name_and_type_of_member,
                            types_of_function_paramaters);

                    return types_of_function_paramaters;
                } catch (Exception e) {
                    for (Method method : reference_to_class.getDeclaredMethods()) {
                        boolean isCorrectMethod = true;

                        if (method.getName().equals(name_and_type_of_member)) {
                            for (int i = 0; i < method.getParameterTypes().length; ++i) {
                                Class<?> type = method.getParameterTypes()[i];
                                if (!type.getName().equals(types_of_function_paramaters[i].getName())) {
                                    isCorrectMethod = false;
                                }
                            }
                        } else {
                            isCorrectMethod = false;
                        }

                        if (isCorrectMethod) {
                            return types_of_function_paramaters;
                        }
                    }
                }
            } else {
                types_of_function_paramaters = new Class<?>[method_arguments.size()];
                for (int j = 0; j < method_arguments.size(); ++j) {
                    types_of_function_paramaters[j] = (Class<?>) method_arguments.get(j);
                }
                if (ClassFile.doesMethodExists(reference_to_class, name_and_type_of_member,
                        types_of_function_paramaters)) {
                    reference_to_class.getDeclaredMethod(name_and_type_of_member,
                            types_of_function_paramaters);
                    return types_of_function_paramaters;
                } else if (Enum.class.isAssignableFrom(reference_to_class)) {
                    Enum.class.getDeclaredMethod(name_and_type_of_member, types_of_function_paramaters);
                    return types_of_function_paramaters;
                }
            }
        }

        return null;
    }
}