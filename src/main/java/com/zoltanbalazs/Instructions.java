package com.zoltanbalazs;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class Instructions {
    public static void ICONST(List<Object> args, List<Class<?>> types, int value) {
        types.add(int.class);
        args.add(value);
    }

    public static void LCONST(List<Object> args, List<Class<?>> types, long value) {
        types.add(long.class);
        args.add(value);
    }

    public static void FCONST(List<Object> args, List<Class<?>> types, float value) {
        types.add(float.class);
        args.add(value);
    }

    public static void DCONST(List<Object> args, List<Class<?>> types, double value) {
        types.add(double.class);
        args.add(value);
    }

    public static void BIPUSH(List<Object> args, List<Class<?>> types, int value) {
        types.add(int.class);
        args.add(value);
    }

    public static void SIPUSH(List<Object> args, List<Class<?>> types, int value) {
        types.add(int.class);
        args.add(value);
    }

    public static void LDC(List<Object> args, List<Class<?>> types, Class<?> type, Object value) {
        types.add(type);
        args.add(value);
    }

    public static void ILOAD(List<Object> args, List<Class<?>> types, int value) {
        types.add(int.class);
        args.add(value);
    }

    public static void LLOAD(List<Object> args, List<Class<?>> types, long value) {
        types.add(long.class);
        args.add(value);
    }

    public static void FLOAD(List<Object> args, List<Class<?>> types, float value) {
        types.add(float.class);
        args.add(value);
    }

    public static void DLOAD(List<Object> args, List<Class<?>> types, double value) {
        types.add(double.class);
        args.add(value);
    }

    public static void ALOAD(List<Object> args, List<Class<?>> types, Object value) {
        types.add(value.getClass());
        args.add(value);
    }

    public static Pair<Integer, Integer> IARIT(List<Object> args, List<Class<?>> types) {
        int value2 = (int) args.remove(args.size() - 1);
        int value1 = (int) args.remove(args.size() - 1);

        types.remove(types.size() - 1);
        types.remove(types.size() - 1);

        return new Pair<Integer, Integer>(value1, value2);
    }

    public static Pair<Long, Long> LARIT(List<Object> args, List<Class<?>> types) {
        long value2 = (long) args.remove(args.size() - 1);
        long value1 = (long) args.remove(args.size() - 1);

        types.remove(types.size() - 1);
        types.remove(types.size() - 1);

        return new Pair<Long, Long>(value1, value2);
    }

    public static Pair<Float, Float> FARIT(List<Object> args, List<Class<?>> types) {
        float value2 = (float) args.remove(args.size() - 1);
        float value1 = (float) args.remove(args.size() - 1);

        types.remove(types.size() - 1);
        types.remove(types.size() - 1);

        return new Pair<Float, Float>(value1, value2);
    }

    public static Pair<Double, Double> DARIT(List<Object> args, List<Class<?>> types) {
        double value2 = (double) args.remove(args.size() - 1);
        double value1 = (double) args.remove(args.size() - 1);

        types.remove(types.size() - 1);
        types.remove(types.size() - 1);

        return new Pair<Double, Double>(value1, value2);
    }
}

class Instructions_Helper {
    public static Object tagSwitchValue(List<CP_Info> CONSTANT_POOL, ConstantPoolTag tag, int index) {
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

    public static Class<?> tagSwitchType(List<CP_Info> CONSTANT_POOL, ConstantPoolTag tag) {
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
}