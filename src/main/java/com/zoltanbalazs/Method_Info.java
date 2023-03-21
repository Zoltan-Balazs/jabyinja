package com.zoltanbalazs;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Method_Helper {
	public static List<Method_Info> readMethods(DataInputStream in, int count) throws IOException {
		List<Method_Info> methods = new ArrayList<Method_Info>(count);

		for (int i = 0; i < count; ++i) {
			Method_Info tmp = new Method_Info();
			tmp.access_flags = Method_Access_Flags.parseFlags(ClassFile_Helper.readShort(in));
			tmp.name_index = ClassFile_Helper.readShort(in);
			tmp.description_index = ClassFile_Helper.readShort(in);
			tmp.attributes_count = ClassFile_Helper.readShort(in);
			tmp.attributes = Attribute_Helper.readAttributes(in, tmp.attributes_count);
			methods.add(tmp);
		}

		return methods;
	}
}

class Method_Info {
	public List<Method_Access_Flags> access_flags;
	public short name_index;
	public short description_index;
	public short attributes_count;
	public List<Attribute_Info> attributes;

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();

		str.append("Method: " + access_flags + " - " + name_index + " - " + description_index + " - " + attributes_count + "\n");

		for (Attribute_Info ATTRIBUTE : attributes) {
			str.append("\t" + ATTRIBUTE + "\n");
		}
		str.deleteCharAt(str.length() - 1);

		return str.toString();
	}
}