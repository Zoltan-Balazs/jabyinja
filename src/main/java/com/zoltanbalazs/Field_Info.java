package com.zoltanbalazs;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Field_Helper {
	/***
	 * Reads the fields from a class file
	 * 
	 * @param in    The class file as an input stream
	 * @param count The number of fields
	 * @return The fields
	 * @throws IOException If an I/O error occurs
	 */
	public static List<Field_Info> readFields(DataInputStream in, int count) throws IOException {
		List<Field_Info> fields = new ArrayList<Field_Info>(count);

		for (int i = 0; i < count; ++i) {
			Field_Info tmp = new Field_Info();
			tmp.access_flags = Field_Access_Flags.parseFlags(ClassFile_Helper.readShort(in));
			tmp.name_index = ClassFile_Helper.readShort(in);
			tmp.description_index = ClassFile_Helper.readShort(in);
			tmp.attributes_count = ClassFile_Helper.readShort(in);
			tmp.attributes = Attribute_Helper.readAttributes(in, tmp.attributes_count);
			fields.add(tmp);
		}

		return fields;
	}
}

public class Field_Info {
	public List<Field_Access_Flags> access_flags;
	public short name_index;
	public short description_index;
	public short attributes_count;
	public List<Attribute_Info> attributes;

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();

		str.append("Field: " + access_flags + " - " + name_index + " - " + description_index + " - " + attributes_count
				+ "\n");

		for (Attribute_Info attribute : attributes) {
			str.append("\t" + attribute + "\n");
		}
		str.deleteCharAt(str.length() - 1);

		return str.toString();
	}
}