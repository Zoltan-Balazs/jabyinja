import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

class Attribute_Helper {
	public static List<Attribute_Info> readAttributes(DataInputStream in, int count) throws IOException {
		List<Attribute_Info> attributes = new ArrayList<Attribute_Info>(count);

		for (int i = 0; i < count; ++i) {
			Attribute_Info tmp = new Attribute_Info();
			tmp.attribute_name_index = ClassFile_Helper.readShort(in);
			tmp.attribute_length = ClassFile_Helper.readInt(in);
			tmp.info = new byte[tmp.attribute_length];
			tmp.info = in.readNBytes(tmp.attribute_length);
			attributes.add(tmp);
		}

		return attributes;
	}
}

class Attribute_Info {
	public short attribute_name_index;
	public int attribute_length;
	public byte[] info;

	@Override
	public String toString() {
		return "Attribute:\n" +
				" - attribute_name_index = " + attribute_name_index + "\n" +
				" - attribute_length = " + attribute_length + "\n" +
				" - info = " + info;
	}
}