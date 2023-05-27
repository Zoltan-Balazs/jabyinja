package com.zoltanbalazs;

import java.util.ArrayList;
import java.util.List;

public interface Access_Flags {
    /***
     * Parses the access flags
     * 
     * @param value The value of the access flags
     * @return The access flags
     */
    public static List<Access_Flags> parseFlags(short value) {
        return new ArrayList<Access_Flags>();
    }
}

enum Class_Access_Flags implements Access_Flags {
    ACC_PUBLIC(0x0001),
    ACC_FINAL(0x0010),
    ACC_SUPER(0x0020),
    ACC_INTERFACE(0x0200),
    ACC_ABSTRACT(0x0400),
    ACC_SYNTHETIC(0x1000),
    ACC_ANNOTATION(0x2000),
    ACC_ENUM(0x4000);

    public final short value;

    private Class_Access_Flags(int value) {
        this.value = (short) value;
    }

    /***
     * Parses the class access flags
     * 
     * @param value The value of the class access flags
     * @return The class access flags
     */
    public static List<Class_Access_Flags> parseFlags(short value) {
        List<Class_Access_Flags> accessFlags = new ArrayList<Class_Access_Flags>();

        for (Class_Access_Flags access_flag : Class_Access_Flags.values()) {
            if ((value & access_flag.value) != 0) {
                accessFlags.add(access_flag);
            }
        }

        return accessFlags;
    }
}

enum Field_Access_Flags implements Access_Flags {
    ACC_PUBLIC(0x0001),
    ACC_PRIVATE(0x0002),
    ACC_PROTECTED(0x0004),
    ACC_STATIC(0x0008),
    ACC_FINAL(0x0010),
    ACC_VOLATILE(0x0040),
    ACC_TRANSIENT(0x0080),
    ACC_SYNTHETIC(0x1000),
    ACC_ENUM(0x4000);

    public final short value;

    private Field_Access_Flags(int value) {
        this.value = (short) value;
    }

    /***
     * Parses the field access flags
     * 
     * @param value The value of the field access flags
     * @return The field access flags
     */
    public static List<Field_Access_Flags> parseFlags(short value) {
        List<Field_Access_Flags> accessFlags = new ArrayList<Field_Access_Flags>();

        for (Field_Access_Flags access_flag : Field_Access_Flags.values()) {
            if ((value & access_flag.value) != 0) {
                accessFlags.add(access_flag);
            }
        }

        return accessFlags;
    }
}

enum Method_Access_Flags implements Access_Flags {
    ACC_PUBLIC(0x0001),
    ACC_PRIVATE(0x0002),
    ACC_PROTECTED(0x0004),
    ACC_STATIC(0x0008),
    ACC_FINAL(0x0010),
    ACC_SYNCHRONIZED(0x0020),
    ACC_BRIDGE(0x0040),
    ACC_VARARGS(0x0080),
    ACC_NATIVE(0x0100),
    ACC_ABSTRACT(0x0400),
    ACC_STRICT(0x0800),
    ACC_SYNTHETIC(0x1000);

    public final short value;

    private Method_Access_Flags(int value) {
        this.value = (short) value;
    }

    /***
     * Parses the method access flags
     * 
     * @param value The value of the method access flags
     * @return The method access flags
     */
    public static List<Method_Access_Flags> parseFlags(short value) {
        List<Method_Access_Flags> accessFlags = new ArrayList<Method_Access_Flags>();

        for (Method_Access_Flags access_flag : Method_Access_Flags.values()) {
            if ((value & access_flag.value) != 0) {
                accessFlags.add(access_flag);
            }
        }

        return accessFlags;
    }
}