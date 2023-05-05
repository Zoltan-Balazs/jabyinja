package com.zoltanbalazs;

import java.io.File;
import java.io.IOException;
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
        local[index] = ((Number) stack.remove(stack.size() - 1).second).intValue();
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

    public static void IF1I(CodeIndex codeIndex, short offset, List<Pair<Class<?>, Object>> stack, Opcode type) {
        int value = ((Number) stack.remove(stack.size() - 1).second).intValue();

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

    public static void IF2I(CodeIndex codeIndex, short offset, List<Pair<Class<?>, Object>> stack, Opcode type) {
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

    public static void IF2A(CodeIndex codeIndex, short offset, List<Pair<Class<?>, Object>> stack, Opcode type) {
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

    public static void INVOKEVIRTUAL(List<Pair<Class<?>, Object>> stack, List<CP_Info> constant_pool, short index,
            Object[] local,
            String file_name)
            throws ClassNotFoundException, MalformedURLException, IOException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {
        CP_Info reference_to_method = constant_pool.get(index - 1);
        String name_of_class = ClassFile.getNameOfClass(reference_to_method.getClassIndex());
        String name_and_type_of_member = ClassFile.getNameOfMember(reference_to_method.getNameAndTypeIndex());
        String description_of_method = ClassFile.getDescriptionOfMethod(reference_to_method.getNameAndTypeIndex());

        List<Class<?>> method_arguments = ClassFile.getArguments(description_of_method);
        int number_of_method_arguments = method_arguments.size();

        int stack_size = stack.size();
        List<Object> arguments_of_function = new ArrayList<Object>();
        List<Class<?>> type_of_arguments = new ArrayList<Class<?>>();
        List<Pair<Class<?>, Object>> arguments_on_stack = stack.subList(stack_size - number_of_method_arguments,
                stack_size);

        Instructions_Helper.SETARGUMENTS_AND_TYPES(arguments_on_stack, arguments_of_function, type_of_arguments);
        Class<?>[] types_of_function_paramaters = new Class<?>[type_of_arguments.size()];
        for (int j = 0; j < type_of_arguments.size(); ++j) {
            types_of_function_paramaters[j] = Object.class;
        }

        Class<?> reference_to_class = null;
        if (ClassFile.isClassBuiltIn(name_of_class)) {
            reference_to_class = Class.forName(name_of_class.replace("/", "."));
        } else {
            reference_to_class = Instructions_Helper.LOAD_CLASS_FROM_OTHER_FILE(file_name, name_of_class);
        }

        Method method = Instructions_Helper.GET_CORRECT_METHOD(reference_to_class, name_and_type_of_member,
                types_of_function_paramaters,
                method_arguments);
        types_of_function_paramaters = Instructions_Helper.GET_CORRECT_METHOD_TYPES(reference_to_class,
                name_and_type_of_member,
                types_of_function_paramaters, method_arguments);

        Object[] arguments_as_objects = new Object[arguments_of_function.size()];
        for (int j = 0; j < arguments_of_function.size(); ++j) {
            arguments_as_objects[j] = (Object) arguments_of_function.get(j);
        }

        Pair<Class<?>, Object> objectref = stack.get(stack_size - number_of_method_arguments - 1);
        Object result = null;
        try {
            result = method.invoke(objectref.second, arguments_as_objects);
        } catch (IllegalArgumentException ie) {
            try {
                Object obj = null;

                for (Constructor<?> ctor : objectref.first.getConstructors()) {

                    Field[] fields = objectref.second.getClass().getFields();

                    int nonStaticParams = 0;
                    for (int i = 0; i < fields.length; ++i) {
                        if (!java.lang.reflect.Modifier.isStatic(fields[i].getModifiers())) {
                            nonStaticParams++;
                        }
                    }

                    Object[] values = new Object[nonStaticParams];
                    int ctr = 0;
                    for (int i = 0; i < fields.length; ++i) {
                        if (!java.lang.reflect.Modifier.isStatic(fields[i].getModifiers())) {
                            values[ctr++] = fields[i].get(objectref.second);
                        }
                    }

                    try {
                        obj = ctor.newInstance(values);
                    } catch (Exception e) {

                    }

                    if (obj != null) {
                        break;
                    }
                }

                method = obj.getClass().getDeclaredMethod(name_and_type_of_member, types_of_function_paramaters);
                result = method.invoke(obj, arguments_as_objects);

                for (int i = 0; i < 65536; ++i) {
                    if (local[i] == objectref.second) {
                        local[i] = obj;
                    }
                }
            } catch (Exception e) {
                arguments_as_objects = new Object[method_arguments.size()];
                for (int j = 0; j < method_arguments.size(); ++j) {
                    if (arguments_of_function.size() <= j) {
                        arguments_as_objects[j] = null;
                    } else {
                        arguments_as_objects[j] = (Object) arguments_of_function.get(j);
                    }
                }
            }
        }

        stack.subList(stack_size - number_of_method_arguments - 1, stack_size).clear();

        if (result != null) {
            stack.add(new Pair<Class<?>, Object>(result.getClass(), result));
        }
    }


    public static void NEW(List<Pair<Class<?>, Object>> stack, List<CP_Info> constant_pool, short index, Object[] local,
            String file_name) throws ClassNotFoundException {
        String name_of_class = ClassFile.getNameOfMember(index);
        Class<?> reference_to_class = null;

        if (ClassFile.isClassBuiltIn(name_of_class)) {
            reference_to_class = Class.forName(name_of_class.replace("/", "."));
        } else {
            reference_to_class = Instructions_Helper.LOAD_CLASS_FROM_OTHER_FILE(file_name, name_of_class);
        }

        stack.add(new Pair<Class<?>, Object>(reference_to_class, reference_to_class));
    }

    public static void NEWARRAY(List<Pair<Class<?>, Object>> stack, byte atype) {
        Class<?> arrayType = Instructions_Helper.GetArrayType(atype);
        int count = ((Number) stack.remove(stack.size() - 1).second).intValue();

        stack.add(new Pair<Class<?>, Object>(arrayType, Array.newInstance(arrayType, count)));
    }

    public static void ANEWARRAY(List<Pair<Class<?>, Object>> stack, List<CP_Info> constant_pool, short index)
            throws ClassNotFoundException {
        ConstantPoolTag tag = constant_pool.get((index & 0xFF) + 1).tag;

        Class<?> arrayType = null;
        if (tag == ConstantPoolTag.CONSTANT_Utf8 || tag == ConstantPoolTag.CONSTANT_Fieldref) {
            arrayType = Class.forName(ClassFile.getNameOfClass(index).replace("/", "."));
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
            byte dimensions)
            throws ClassNotFoundException {
        ConstantPoolTag tag = constant_pool.get((index & 0xFF) + 1).tag;

        Class<?> arrayType = null;
        Class<?> hackyType = null;
        if (tag == ConstantPoolTag.CONSTANT_Utf8 || tag == ConstantPoolTag.CONSTANT_Fieldref) {
            arrayType = Class.forName(ClassFile.getNameOfClass(index).replace("/", "."));
            hackyType = Class.forName(ClassFile.getNameOfClass(index).replace("/", ".").replaceAll("\\[+", "["));
        } else {
            arrayType = Instructions_Helper.TagSwitchType(constant_pool, tag);
        }

        int[] size = new int[dimensions];
        for (int i = 0; i < dimensions; ++i) {
            int count = ((Number) stack.remove(0).second).intValue();
            size[i] = count;
        }

        stack.add(new Pair<Class<?>, Object>(arrayType, Array.newInstance(hackyType, size)));
    }

    public static void IF1A(CodeIndex codeIndex, short offset, List<Pair<Class<?>, Object>> stack, Opcode type) {
        Object value = stack.remove(stack.size() - 1).second;

        switch (type) {
            case IFNULL -> {
                if (value == null) {
                    codeIndex.Inc(offset - 2 - 1);
                }
            }
            case IFNONNULL -> {
                if (value != null) {
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
            if (argument_type == int.class) {
                arguments_of_function.add((int) current_argument);
            } else if (argument_type == float.class) {
                arguments_of_function.add((float) current_argument);
            } else if (argument_type == double.class) {
                arguments_of_function.add((double) current_argument);
            } else if (argument_type == long.class) {
                arguments_of_function.add((long) current_argument);
            } else {
                arguments_of_function.add(argument_type.cast(current_argument));
            }
            type_of_arguments.add(arguments.first);
        }
    }

    public static Class<?> LOAD_CLASS_FROM_OTHER_FILE(String file_name, String class_name) {
        Class<?> returned_class = null;
        try {
            File f = new File(file_name);
            int length = class_name.split("/").length;
            for (int i = 0; i < length; ++i) {
                f = new File(f.getParent());
            }

            URL[] cp = { f.toURI().toURL() };
            URLClassLoader urlcl = new URLClassLoader(cp);
            returned_class = urlcl.loadClass(class_name.replace("/", "."));
            urlcl.close();
        } catch (Exception e) {
            return null;
        }
        return returned_class;
    }

    public static Method GET_CORRECT_METHOD(Class<?> reference_to_class, String name_and_type_of_member,
            Class<?>[] types_of_function_paramaters, List<Class<?>> method_arguments) throws NoSuchMethodException {
        Method returnedMethod = null;

        if (ClassFile.doesMethodExists(reference_to_class, name_and_type_of_member, types_of_function_paramaters)) {
            returnedMethod = reference_to_class.getDeclaredMethod(name_and_type_of_member,
                    types_of_function_paramaters);
        } else {
            types_of_function_paramaters = new Class<?>[method_arguments.size()];
            for (int j = 0; j < method_arguments.size(); ++j) {
                types_of_function_paramaters[j] = (Class<?>) method_arguments.get(j);
            }
            if (ClassFile.doesMethodExists(reference_to_class, name_and_type_of_member, types_of_function_paramaters)) {
                returnedMethod = reference_to_class.getDeclaredMethod(name_and_type_of_member,
                        types_of_function_paramaters);
            } else {
                types_of_function_paramaters = new Class<?>[method_arguments.size()];
                for (int j = 0; j < method_arguments.size(); ++j) {
                    types_of_function_paramaters[j] = (Class<?>) method_arguments.get(j);
                }
                if (ClassFile.doesMethodExists(reference_to_class, name_and_type_of_member,
                        types_of_function_paramaters)) {
                    returnedMethod = reference_to_class.getDeclaredMethod(name_and_type_of_member,
                            types_of_function_paramaters);
                }
            }
        }

        return returnedMethod;
    }

    public static Class<?>[] GET_CORRECT_METHOD_TYPES(Class<?> reference_to_class, String name_and_type_of_member,
            Class<?>[] types_of_function_paramaters, List<Class<?>> method_arguments) throws NoSuchMethodException {
        if (ClassFile.doesMethodExists(reference_to_class, name_and_type_of_member, types_of_function_paramaters)) {
            reference_to_class.getDeclaredMethod(name_and_type_of_member, types_of_function_paramaters);
            return types_of_function_paramaters;
        } else {
            types_of_function_paramaters = new Class<?>[method_arguments.size()];
            for (int j = 0; j < method_arguments.size(); ++j) {
                types_of_function_paramaters[j] = (Class<?>) method_arguments.get(j);
            }
            if (ClassFile.doesMethodExists(reference_to_class, name_and_type_of_member, types_of_function_paramaters)) {
                reference_to_class.getDeclaredMethod(name_and_type_of_member,
                        types_of_function_paramaters);
                return types_of_function_paramaters;
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
                }
            }
        }

        return null;
    }
}