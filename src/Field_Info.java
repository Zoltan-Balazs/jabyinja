import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

class Field_Helper {
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

		str.append("Field:\n");
		str.append(" - access_flags = " + access_flags + "\n");
		str.append(" - name_index = " + name_index + "\n");
		str.append(" - description_index = " + description_index + "\n");
		str.append(" - attributes_count = " + attributes_count + "\n");

		for (Attribute_Info ATTRIBUTE : attributes) {
			str.append(ATTRIBUTE + "\n");
		}
		if (attributes_count == 0) {
			str.append("\n");
		}

		return str.toString();
	}
}