import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

class InvalidConstantPoolTagException extends RuntimeException { }

class Constant_Pool_Helper {
	public static List<CP_Info> readConstantPool(InputStream in, int count)
			throws IOException, InvalidConstantPoolTagException {
		List<CP_Info> constant_pool = new ArrayList<CP_Info>(count - 1);

		for (int i = 0; i < count - 1; ++i) {
			byte tagValue = ClassFile_Helper.readByte(in);
			ConstantPoolTag tag = ConstantPoolTag.getValue(tagValue);

			switch (tag) {
				case CONSTANT_Class -> {
					CONSTANT_Class_Info tmp = new CONSTANT_Class_Info();
					tmp.name_index = ClassFile_Helper.readShort(in);
					constant_pool.add(tmp);
				}
					
				case CONSTANT_Fieldref -> {
					CONSTANT_Fieldref_Info tmp = new CONSTANT_Fieldref_Info();
					tmp.class_index = ClassFile_Helper.readShort(in);
					tmp.name_and_type_index = ClassFile_Helper.readShort(in);
					constant_pool.add(tmp);
				}
				case CONSTANT_Methodref -> {
					CONSTANT_Methodref_Info tmp = new CONSTANT_Methodref_Info();
					tmp.class_index = ClassFile_Helper.readShort(in);
					tmp.name_and_type_index = ClassFile_Helper.readShort(in);
					constant_pool.add(tmp);
					break;
				}
				case CONSTANT_InterfaceMethodref -> {
					CONSTANT_InterfaceMethodref_Info tmp = new CONSTANT_InterfaceMethodref_Info();
					tmp.class_index = ClassFile_Helper.readShort(in);
					tmp.name_and_type_index = ClassFile_Helper.readShort(in);
					constant_pool.add(tmp);
				}

				case CONSTANT_String -> {
					CONSTANT_String_Info tmp = new CONSTANT_String_Info();
					tmp.string_index = ClassFile_Helper.readShort(in);
					constant_pool.add(tmp);
				}

				case CONSTANT_Integer -> {
					CONSTANT_Integer_Info tmp = new CONSTANT_Integer_Info();
					tmp.bytes = ClassFile_Helper.readInt(in);
					constant_pool.add(tmp);
				}
				case CONSTANT_Float -> {
					CONSTANT_Float_Info tmp = new CONSTANT_Float_Info();
					tmp.bytes = ClassFile_Helper.readInt(in);
					constant_pool.add(tmp);
				}

				case CONSTANT_Long -> {
					CONSTANT_Long_Info tmp = new CONSTANT_Long_Info();
					tmp.high_bytes = ClassFile_Helper.readInt(in);
					tmp.low_bytes = ClassFile_Helper.readInt(in);
					constant_pool.add(tmp);
				}
				case CONSTANT_Double -> {
					CONSTANT_Double_Info tmp = new CONSTANT_Double_Info();
					tmp.high_bytes = ClassFile_Helper.readInt(in);
					tmp.low_bytes = ClassFile_Helper.readInt(in);
					constant_pool.add(tmp);
				}

				case CONSTANT_NameAndType -> {
					CONSTANT_NameAndType_Info tmp = new CONSTANT_NameAndType_Info();
					tmp.name_index = ClassFile_Helper.readShort(in);
					tmp.descriptor_index = ClassFile_Helper.readShort(in);
					constant_pool.add(tmp);
				}

				case CONSTANT_Utf8 -> {
					CONSTANT_Utf8_Info tmp = new CONSTANT_Utf8_Info();
					tmp.length = ClassFile_Helper.readShort(in);
					tmp.bytes = new byte[tmp.length];
					tmp.bytes = in.readNBytes(tmp.length);
					constant_pool.add(tmp);
				}

				case CONSTANT_MethodHandle -> {
					CONSTANT_MethodHandle_Info tmp = new CONSTANT_MethodHandle_Info();
					tmp.reference_kind = ClassFile_Helper.readByte(in);
					tmp.reference_index = ClassFile_Helper.readShort(in);
					constant_pool.add(tmp);
				}

				case CONSTANT_MethodType -> {
					CONSTANT_MethodType_Info tmp = new CONSTANT_MethodType_Info();
					tmp.descriptor_index = ClassFile_Helper.readShort(in);
					constant_pool.add(tmp);
				}

				case CONSTANT_InvokeDynamic -> {
					CONSTANT_InvokeDynamic_Info tmp = new CONSTANT_InvokeDynamic_Info();
					tmp.bootstrap_method_attr_index = ClassFile_Helper.readShort(in);
					tmp.name_and_type_index = ClassFile_Helper.readShort(in);
					constant_pool.add(tmp);
				}

				case ERROR -> throw new InvalidConstantPoolTagException();
			}

			constant_pool.get(i).tag = tag;
		}

		return constant_pool;
	}
}

public class CP_Info {
	public ConstantPoolTag tag;

	@Override
	public String toString() {
		return "CONSTANT_Pool_Info:\n" +
				" - tag = " + tag;
	}
}

class CONSTANT_Class_Info extends CP_Info {
	public short name_index;

	@Override
	public String toString() {
		return "CONSTANT_Class_Info:\n" +
				" - name_index = " + name_index + "\n" +
				" - tag = " + tag;
	}
}

class CONSTANT_Fieldref_Info extends CP_Info {
	public short class_index;
	public short name_and_type_index;

	@Override
	public String toString() {
		return "CONSTANT_Fieldref_Info:\n" +
				" - class_index = " + class_index + "\n" +
				" - name_and_type_index = " + name_and_type_index + "\n" +
				" - tag = " + tag;
	}
}

class CONSTANT_Methodref_Info extends CP_Info {
	public short class_index;
	public short name_and_type_index;

	@Override
	public String toString() {
		return "CONSTANT_Methodref_Info:\n" +
				" - class_index = " + class_index + "\n" +
				" - name_and_type_index = " + name_and_type_index + "\n" +
				" - tag = " + tag;
	}
}

class CONSTANT_InterfaceMethodref_Info extends CP_Info {
	public short class_index;
	public short name_and_type_index;

	@Override
	public String toString() {
		return "CONSTANT_InterfaceMethodref_Info:\n" +
				" - class_index = " + class_index + "\n" +
				" - name_and_type_index = " + name_and_type_index + "\n" +
				" - tag = " + tag;
	}
}

class CONSTANT_String_Info extends CP_Info {
	public short string_index;

	@Override
	public String toString() {
		return "CONSTANT_String_Info:\n" +
				" - string_index = " + string_index + "\n" +
				" - tag = " + tag;
	}
}

class CONSTANT_Integer_Info extends CP_Info {
	public int bytes;

	@Override
	public String toString() {
		return "CONSTANT_Integer_Info:\n" +
				" - bytes = " + bytes + "\n" +
				" - tag = " + tag;
	}
}

class CONSTANT_Float_Info extends CP_Info {
	public int bytes;

	@Override
	public String toString() {
		return "CONSTANT_Float_Info:\n" +
				" - bytes = " + bytes + "\n" +
				" - tag = " + tag;
	}
}

class CONSTANT_Long_Info extends CP_Info {
	public int high_bytes;
	public int low_bytes;

	@Override
	public String toString() {
		return "CONSTANT_Long_Info:\n" +
				" - high_bytes = " + high_bytes + "\n" +
				" - low_bytes = " + low_bytes + "\n" +
				" - tag = " + tag;
	}
}

class CONSTANT_Double_Info extends CP_Info {
	public int high_bytes;
	public int low_bytes;

	@Override
	public String toString() {
		return "CONSTANT_Double_Info:\n" +
				" - high_bytes = " + high_bytes + "\n" +
				" - low_bytes = " + low_bytes + "\n" +
				" - tag = " + tag;
	}
}

class CONSTANT_NameAndType_Info extends CP_Info {
	public short name_index;
	public short descriptor_index;

	@Override
	public String toString() {
		return "CONSTANT_NameAndType_Info:\n" +
				" - name_index = " + name_index + "\n" +
				" - descriptor_index = " + descriptor_index + "\n" +
				" - tag = " + tag;
	}
}

class CONSTANT_Utf8_Info extends CP_Info {
	public short length;
	public byte[] bytes;

	@Override
	public String toString() {
		return "CONSTANT_Utf8_Info:\n" +
				" - length = " + length + "\n" +
				" - bytes = " + bytes + "\n" +
				" - tag = " + tag;
	}
}

class CONSTANT_MethodHandle_Info extends CP_Info {
	public byte reference_kind;
	public short reference_index;

	@Override
	public String toString() {
		return "CONSTANT_MethodHandle_Info:\n" +
				" - reference_kind = " + reference_kind + "\n" +
				" - reference_index = " + reference_index + "\n" +
				" - tag = " + tag;
	}
}

class CONSTANT_MethodType_Info extends CP_Info {
	public short descriptor_index;

	@Override
	public String toString() {
		return "CONSTANT_MethodType_Info:\n" +
				" - descriptor_index = " + descriptor_index + "\n" +
				" - tag = " + tag;
	}
}

class CONSTANT_InvokeDynamic_Info extends CP_Info {
	public short bootstrap_method_attr_index;
	public short name_and_type_index;

	@Override
	public String toString() {
		return "CONSTANT_InvokeDynamic_Info:\n" +
				" - bootstrap_method_attr_index = " + bootstrap_method_attr_index + "\n" +
				" - name_and_type_index = " + name_and_type_index + "\n" +
				" - tag = " + tag;
	}
}

enum ConstantPoolTag {
	CONSTANT_Class(7),
	CONSTANT_Fieldref(9),
	CONSTANT_Methodref(10),
	CONSTANT_InterfaceMethodref(11),
	CONSTANT_String(8),
	CONSTANT_Integer(3),
	CONSTANT_Float(4),
	CONSTANT_Long(5),
	CONSTANT_Double(6),
	CONSTANT_NameAndType(12),
	CONSTANT_Utf8(1),
	CONSTANT_MethodHandle(15),
	CONSTANT_MethodType(16),
	CONSTANT_InvokeDynamic(18),
	ERROR(-1);

	private final int tagValue;
    private ConstantPoolTag(int tagValue) {
        this.tagValue = tagValue;
    }

	public boolean compare(int tagValue) {
		return this.tagValue == tagValue;
	}

    public int getTagValue() {
        return tagValue;
    }

	public static ConstantPoolTag getValue(int tag) {
		ConstantPoolTag[] tags = ConstantPoolTag.values();
		for (int i = 0; i < tags.length; ++i) {
			if (tags[i].compare(tag)) {
				return tags[i];
			}
		}
		return ConstantPoolTag.ERROR;
	}
}