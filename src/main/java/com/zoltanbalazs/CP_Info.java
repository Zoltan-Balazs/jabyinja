package com.zoltanbalazs;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

class InvalidConstantPoolTagException extends RuntimeException {
	public InvalidConstantPoolTagException(String message) {
		super(message);
	}
}

class Constant_Pool_Helper {
	public static List<CP_Info> readConstantPool(DataInputStream in, int count)
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
				case ERROR -> {
					throw new InvalidConstantPoolTagException(Byte.toString(tagValue));
				}
			}

			constant_pool.get(i).tag = tag;

			if (tag == ConstantPoolTag.CONSTANT_Long || tag == ConstantPoolTag.CONSTANT_Double) {
				i++;
				constant_pool.add(new CP_Info());
				constant_pool.get(i).tag = ConstantPoolTag.POOR_CHOICE;
			}
		}

		return constant_pool;
	}
}

public class CP_Info {
	public ConstantPoolTag tag;

	public byte[] getBytes() {
		return new byte[] {};
	}

	public short getNameIndex() {
		return -1;
	}

	public short getNameAndTypeIndex() {
		return -1;
	}

	public short getClassIndex() {
		return -1;
	}

	public short getStringIndex() {
		return -1;
	}

	@Override
	public String toString() {
		return "CONSTANT_Pool_Info: " + tag;
	}
}

class CONSTANT_Class_Info extends CP_Info {
	public short name_index;

	@Override
	public short getNameIndex() {
		return name_index;
	}

	@Override
	public String toString() {
		return "CONSTANT_Class_Info: " + name_index;
	}
}

class CONSTANT_Fieldref_Info extends CP_Info {
	public short class_index;
	public short name_and_type_index;

	@Override
	public short getClassIndex() {
		return class_index;
	}

	@Override
	public short getNameAndTypeIndex() {
		return name_and_type_index;
	}

	@Override
	public String toString() {
		return "CONSTANT_Fieldref_Info: " + class_index + " - " + name_and_type_index;
	}
}

class CONSTANT_Methodref_Info extends CP_Info {
	public short class_index;
	public short name_and_type_index;

	@Override
	public short getClassIndex() {
		return class_index;
	}

	@Override
	public short getNameAndTypeIndex() {
		return name_and_type_index;
	}

	@Override
	public String toString() {
		return "CONSTANT_Methodref_Info: " + class_index + " - " + name_and_type_index;
	}
}

class CONSTANT_InterfaceMethodref_Info extends CP_Info {
	public short class_index;
	public short name_and_type_index;

	@Override
	public short getClassIndex() {
		return class_index;
	}

	@Override
	public short getNameAndTypeIndex() {
		return name_and_type_index;
	}

	@Override
	public String toString() {
		return "CONSTANT_InterfaceMethodref_Info: " + class_index + " - " + name_and_type_index;
	}
}

class CONSTANT_String_Info extends CP_Info {
	public short string_index;

	@Override
	public short getStringIndex() {
		return string_index;
	}

	@Override
	public String toString() {
		return "CONSTANT_String_Info: " + string_index;
	}
}

class CONSTANT_Integer_Info extends CP_Info {
	public int bytes;

	@Override
	public String toString() {
		return "CONSTANT_Integer_Info: " + bytes;
	}
}

class CONSTANT_Float_Info extends CP_Info {
	public int bytes;

	@Override
	public String toString() {
		return "CONSTANT_Float_Info: " + bytes;
	}
}

class CONSTANT_Long_Info extends CP_Info {
	public int high_bytes;
	public int low_bytes;

	@Override
	public String toString() {
		return "CONSTANT_Long_Info: " + high_bytes + " - " + low_bytes;
	}
}

class CONSTANT_Double_Info extends CP_Info {
	public int high_bytes;
	public int low_bytes;

	@Override
	public String toString() {
		return "CONSTANT_Double_Info: " + high_bytes + " - " + low_bytes;
	}
}

class CONSTANT_NameAndType_Info extends CP_Info {
	public short name_index;
	public short descriptor_index;

	@Override
	public short getNameIndex() {
		return name_index;
	}

	@Override
	public String toString() {
		return "CONSTANT_NameAndType_Info: " + name_index + " - " + descriptor_index;
	}
}

class CONSTANT_Utf8_Info extends CP_Info {
	public short length;
	public byte[] bytes;

	public byte[] getBytes() {
		return bytes;
	}

	@Override
	public String toString() {
		return "CONSTANT_Utf8_Info: " + length + " - " + bytes + " - " + new String(bytes, StandardCharsets.UTF_8);
	}
}

class CONSTANT_MethodHandle_Info extends CP_Info {
	public byte reference_kind;
	public short reference_index;

	@Override
	public String toString() {
		return "CONSTANT_MethodHandle_Info: " + reference_kind + " - " + reference_index;
	}
}

class CONSTANT_MethodType_Info extends CP_Info {
	public short descriptor_index;

	@Override
	public String toString() {
		return "CONSTANT_MethodType_Info: " + descriptor_index;
	}
}

class CONSTANT_InvokeDynamic_Info extends CP_Info {
	public short bootstrap_method_attr_index;
	public short name_and_type_index;

	@Override
	public short getNameAndTypeIndex() {
		return name_and_type_index;
	}

	@Override
	public String toString() {
		return "CONSTANT_InvokeDynamic_Info: " + bootstrap_method_attr_index + " - " + name_and_type_index;
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
	ERROR(-1),
	POOR_CHOICE(-2);

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