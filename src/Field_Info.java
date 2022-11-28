import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

class Field_Helper {
	public static List<Field_Info> readFields(InputStream in, int count) throws IOException {
		List<Field_Info> fields = new ArrayList<Field_Info>(count);

		for (int i = 0; i < count; ++i) {
			Field_Info tmp = new Field_Info();
			tmp.access_flags = ClassFile_Helper.readShort(in);
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
	public short access_flags;
	public short name_index;
	public short description_index;
	public short attributes_count;
	public List<Attribute_Info> attributes;

	@Override
	public String toString() {
		return "Field:\n" +
				" - access_flags = " + access_flags + "\n" +
				" - name_index = " + name_index + "\n" +
				" - description_index = " + description_index + "\n" +
				" - attributes_count = " + attributes_count;
	}
}