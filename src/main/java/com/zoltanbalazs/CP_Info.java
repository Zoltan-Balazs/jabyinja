package com.zoltanbalazs;

import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

class InvalidConstantPoolTagException extends RuntimeException {
	/***
	 * Constructs a new exception with the specified detail message.
	 * 
	 * @param message The message
	 */
	public InvalidConstantPoolTagException(String message) {
		super(message);
	}
}

class Constant_Pool_Helper {
	/***
	 * Converts a byte array to a long value
	 * 
	 * @param bytes The byte array
	 * @return The long value
	 */
	public static long convertToLong(byte[] bytes) {
		long value = 0L;

		for (byte b : bytes) {
			value = (value << 8) + (b & 255);
		}

		return value;
	}

	/***
	 * Reads the constant pool from a class file
	 * 
	 * @param in    The class file as an input stream
	 * @param count The number of entries in the constant pool
	 * @return The constant pool
	 * @throws IOException                     If an I/O error occurs
	 * @throws InvalidConstantPoolTagException If the constant pool tag is invalid
	 */
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
					byte[] high_bytes = new byte[4];
					byte[] low_bytes = new byte[4];
					for (int idx = 0; idx < 4; idx++) {
						high_bytes[idx] = ClassFile_Helper.readByte(in);
					}
					for (int idx = 0; idx < 4; idx++) {
						low_bytes[idx] = ClassFile_Helper.readByte(in);
					}
					tmp.high_bytes = convertToLong(high_bytes);
					tmp.low_bytes = convertToLong(low_bytes);
					constant_pool.add(tmp);
				}
				case CONSTANT_Double -> {
					CONSTANT_Double_Info tmp = new CONSTANT_Double_Info();
					byte[] high_bytes = new byte[4];
					byte[] low_bytes = new byte[4];
					for (int idx = 0; idx < 4; idx++) {
						high_bytes[idx] = ClassFile_Helper.readByte(in);
					}
					for (int idx = 0; idx < 4; idx++) {
						low_bytes[idx] = ClassFile_Helper.readByte(in);
					}
					tmp.high_bytes = convertToLong(high_bytes);
					tmp.low_bytes = convertToLong(low_bytes);
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
				case POOR_CHOICE -> {
					// Ignored..
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

	public byte getReferenceKind() {
		return -1;
	}

	public short getReferenceIndex() {
		return -1;
	}

	public short getNameIndex() {
		return -1;
	}

	public short getBootStrapMethodAttributeIndex() {
		return -1;
	}

	public short getNameAndTypeIndex() {
		return -1;
	}

	public short getDescriptorIndex() {
		return -1;
	}

	public short getClassIndex() {
		return -1;
	}

	public short getStringIndex() {
		return -1;
	}

	public int getIntValue() {
		return 0;
	}

	public long getLongValue() {
		return 0L;
	}

	public float getFloatValue() {
		return 0.0f;
	}

	public double getDoubleValue() {
		return 0.0d;
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
	public int getIntValue() {
		return bytes;
	}

	@Override
	public String toString() {
		return "CONSTANT_Integer_Info: " + getIntValue();
	}
}

class CONSTANT_Float_Info extends CP_Info {
	public int bytes;

	@Override
	public float getFloatValue() {
		int bits = bytes;

		if (bits == 0x7F800000) {
			return Float.POSITIVE_INFINITY;
		}
		if (bits == 0xFF800000) {
			return Float.NEGATIVE_INFINITY;
		}
		if ((0x7F800001 <= bits && bits <= 0x7FFFFFFF) || (0xFF800001 <= bits && bits <= 0xFFFFFFFF)) {
			return Float.NaN;
		}

		BigDecimal base = new BigDecimal(2);
		int tmp_s = ((bits >> 31) == 0) ? 1 : -1;
		BigDecimal s = new BigDecimal(tmp_s);
		int e = (bits >> 23) & 0xFF;
		int tmp_m = (e == 0) ? (bits & 0x7FFFFF) << 1 : (bits & 0x7FFFFF) | 0x800000;
		BigDecimal m = new BigDecimal(tmp_m);

		return s.multiply(m).multiply(base.pow(e - 150, MathContext.DECIMAL128)).floatValue();
	}

	@Override
	public String toString() {
		return "CONSTANT_Float_Info: " + getFloatValue();
	}
}

class CONSTANT_Long_Info extends CP_Info {
	public long high_bytes;
	public long low_bytes;

	@Override
	public long getLongValue() {
		return ((long) high_bytes << 32) + low_bytes;
	}

	@Override
	public String toString() {
		return "CONSTANT_Long_Info: " + getLongValue();
	}
}

class CONSTANT_Double_Info extends CP_Info {
	public long high_bytes;
	public long low_bytes;

	@Override
	public double getDoubleValue() {
		long bits = ((long) high_bytes << 32) + low_bytes;

		if (bits == 0x7FF0000000000000L) {
			return Double.POSITIVE_INFINITY;
		}

		if (bits == 0xFFF0000000000000L) {
			return Double.NEGATIVE_INFINITY;
		}

		if ((0x7FF0000000000001L <= bits && bits <= 0x7FFFFFFFFFFFFFFFL)
				|| (0xFFF0000000000001L <= bits && bits <= 0xFFFFFFFFFFFFFFFFL)) {
			return Double.NaN;
		}

		BigDecimal base = new BigDecimal(2);
		int tmp_s = ((bits >> 63) == 0) ? 1 : -1;
		BigDecimal s = new BigDecimal(tmp_s);
		int e = (int) ((bits >> 52) & 0x7FFL);
		long tmp_m = (e == 0) ? (bits & 0xFFFFFFFFFFFFFL) << 1 : (bits & 0xFFFFFFFFFFFFFL) | 0x10000000000000L;
		BigDecimal m = new BigDecimal(tmp_m);

		return s.multiply(m).multiply(base.pow(e - 1075, MathContext.DECIMAL128)).doubleValue();
	}

	@Override
	public String toString() {
		return "CONSTANT_Double_Info: " + getDoubleValue();
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
	public short getDescriptorIndex() {
		return descriptor_index;
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
	public byte getReferenceKind() {
		return reference_kind;
	}

	@Override
	public short getReferenceIndex() {
		return reference_index;
	}

	@Override
	public String toString() {
		return "CONSTANT_MethodHandle_Info: " + reference_kind + " - " + reference_index;
	}
}

class CONSTANT_MethodType_Info extends CP_Info {
	public short descriptor_index;

	@Override
	public short getDescriptorIndex() {
		return descriptor_index;
	}

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
	public short getBootStrapMethodAttributeIndex() {
		return bootstrap_method_attr_index;
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

	/***
	 * Get the enum tag value from the tag (int)
	 * 
	 * @param tag The tag (int)
	 * @return The tag value (enum)
	 */
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