package com.zoltanbalazs;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class Instructions {
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

    public static void LDC(List<Pair<Class<?>, Object>> stack, Class<?> type, Object value) {
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

    public static void ICONV(List<Pair<Class<?>, Object>> stack, Class<?> result) {
        Pair<Class<?>, Object> value = stack.remove(stack.size() - 1);

        if (result == long.class) {
            stack.add(new Pair<Class<?>, Object>(long.class, (long) value.second));
        } else if (result == float.class) {
            stack.add(new Pair<Class<?>, Object>(float.class, (float) value.second));
        } else if (result == double.class) {
            stack.add(new Pair<Class<?>, Object>(double.class, (double) value.second));
        } else if (result == byte.class) {
            stack.add(new Pair<Class<?>, Object>(byte.class, (byte) value.second));
        } else if (result == char.class) {
            stack.add(new Pair<Class<?>, Object>(char.class, (char) value.second));
        } else if (result == short.class) {
            stack.add(new Pair<Class<?>, Object>(short.class, (short) value.second));
        } else {
            throw new UnsupportedOperationException("Integer to " + result + " conversion is not supported!");
        }
    }

    public static void LCONV(List<Pair<Class<?>, Object>> stack, Class<?> result) {
        Pair<Class<?>, Object> value = stack.remove(stack.size() - 1);

        if (result == int.class) {
            stack.add(new Pair<Class<?>, Object>(int.class, (int) value.second));
        } else if (result == float.class) {
            stack.add(new Pair<Class<?>, Object>(float.class, (float) value.second));
        } else if (result == double.class) {
            stack.add(new Pair<Class<?>, Object>(double.class, (double) value.second));
        } else {
            throw new UnsupportedOperationException("Long to " + result + " conversion is not supported!");
        }
    }

    public static void FCONV(List<Pair<Class<?>, Object>> stack, Class<?> result) {
        Pair<Class<?>, Object> value = stack.remove(stack.size() - 1);

        if (result == int.class) {
            stack.add(new Pair<Class<?>, Object>(int.class, (int) value.second));
        } else if (result == long.class) {
            stack.add(new Pair<Class<?>, Object>(long.class, (long) value.second));
        } else if (result == double.class) {
            stack.add(new Pair<Class<?>, Object>(double.class, (double) value.second));
        } else {
            throw new UnsupportedOperationException("Float to " + result + " conversion is not supported!");
        }
    }

    public static void DCONV(List<Pair<Class<?>, Object>> stack, Class<?> result) {
        Pair<Class<?>, Object> value = stack.remove(stack.size() - 1);

        if (result == int.class) {
            stack.add(new Pair<Class<?>, Object>(int.class, (int) value.second));
        } else if (result == long.class) {
            stack.add(new Pair<Class<?>, Object>(long.class, (long) value.second));
        } else if (result == float.class) {
            stack.add(new Pair<Class<?>, Object>(float.class, (float) value.second));
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
        } else {
            stack.add(new Pair<Class<?>, Object>(int.class, 1));
        }
    }

    public static void FCMP(List<Pair<Class<?>, Object>> stack, boolean lessThan) {
        Pair<Class<?>, Object> value2 = stack.remove(stack.size() - 1);
        Pair<Class<?>, Object> value1 = stack.remove(stack.size() - 1);

        float f_value1 = (float) value1.second;
        float f_value2 = (float) value2.second;

        if (Float.isNaN(f_value1) || Float.isNaN(f_value2)) {
            if (lessThan) {
                stack.add(new Pair<Class<?>, Object>(int.class, -1));
            } else {
                stack.add(new Pair<Class<?>, Object>(int.class, 1));
            }
        } else if (f_value1 == f_value2) {
            stack.add(new Pair<Class<?>, Object>(int.class, 0));
        } else if (f_value1 < f_value2) {
            stack.add(new Pair<Class<?>, Object>(int.class, -1));
        } else {
            stack.add(new Pair<Class<?>, Object>(int.class, 1));
        }
    }

    public static void DCMP(List<Pair<Class<?>, Object>> stack, boolean lessThan) {
        Pair<Class<?>, Object> value2 = stack.remove(stack.size() - 1);
        Pair<Class<?>, Object> value1 = stack.remove(stack.size() - 1);

        double d_value1 = (double) value1.second;
        double d_value2 = (double) value2.second;

        if (Double.isNaN(d_value1) || Double.isNaN(d_value2)) {
            if (lessThan) {
                stack.add(new Pair<Class<?>, Object>(int.class, -1));
            } else {
                stack.add(new Pair<Class<?>, Object>(int.class, 1));
            }
        } else if (d_value1 == d_value2) {
            stack.add(new Pair<Class<?>, Object>(int.class, 0));
        } else if (d_value1 < d_value2) {
            stack.add(new Pair<Class<?>, Object>(int.class, -1));
        } else {
            stack.add(new Pair<Class<?>, Object>(int.class, 1));
        }
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