package com.zoltanbalazs;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Interface {
	public short interface_index;

	@Override
	public String toString() {
		return "Interface:\n" +
				" - interface_index = " + interface_index;
	}
}

class Interface_Helper {
	public static List<Interface> readInterfaces(DataInputStream in, int count) throws IOException {
		List<Interface> interfaces = new ArrayList<Interface>(count);

		for (int i = 0; i < count; ++i) {
			Interface tmp = new Interface();
			tmp.interface_index = ClassFile_Helper.readShort(in);
			interfaces.add(tmp);
		}

		return interfaces;
	}
}