package com.zoltanbalazs;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Interface {
	public short interface_index;

	@Override
	public String toString() {
		return "Interface: " + interface_index;
	}
}

class Interface_Helper {
	/***
	 * Reads the interfaces from a class file
	 * 
	 * @param in    The class file as an input stream
	 * @param count The number of interfaces
	 * @return The interfaces
	 * @throws IOException If an I/O error occurs
	 */
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