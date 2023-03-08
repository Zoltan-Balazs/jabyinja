import java.util.ArrayList;
import java.util.List;

public interface Access_Flags {
    public static List<Access_Flags> parseFlags(short value) {
        return new ArrayList<Access_Flags>();
    }
}

enum Class_Access_Flags implements Access_Flags {
    ACC_PUBLIC((short) 0x0001),
    ACC_FINAL((short) 0x0010),
    ACC_SUPER((short) 0x0020),
    ACC_INTERFACE((short) 0x0200),
    ACC_ABSTRACT((short) 0x0400),
    ACC_SYNTHETIC((short) 0x1000),
    ACC_ANNOTATION((short) 0x2000),
    ACC_ENUM((short) 0x4000);

    public final short value;

    private Class_Access_Flags(short value) {
        this.value = value;
    }

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
    ACC_PUBLIC((short) 0x0001),
    ACC_PRIVATE((short) 0x0002),
    ACC_PROTECTED((short) 0x0004),
    ACC_STATIC((short) 0x0008),
    ACC_FINAL((short) 0x0010),
    ACC_VOLATILE((short) 0x0040),
    ACC_TRANSIENT((short) 0x0080),
    ACC_SYNTHETIC((short) 0x1000),
    ACC_ENUM((short) 0x4000);

    public final short value;

    private Field_Access_Flags(short value) {
        this.value = value;
    }

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
    ACC_PUBLIC((short) 0x0001),
    ACC_PRIVATE((short) 0x0002),
    ACC_PROTECTED((short) 0x0004),
    ACC_STATIC((short) 0x0008),
    ACC_FINAL((short) 0x0010),
    ACC_SYNCHRONIZED((short) 0x0020),
    ACC_BRIDGE((short) 0x0040),
    ACC_VARARGS((short) 0x0080),
    ACC_NATIVE((short) 0x0100),
    ACC_ABSTRACT((short) 0x0400),
    ACC_STRICT((short) 0x0800),
    ACC_SYNTHETIC((short) 0x1000);

    public final short value;

    private Method_Access_Flags(short value) {
        this.value = value;
    }

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