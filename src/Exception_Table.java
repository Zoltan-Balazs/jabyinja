import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Exception_Table_Helper {
    public static List<Exception_Table> readExceptionTable(DataInputStream in, int count) throws IOException {
        List<Exception_Table> exceptionTable = new ArrayList<Exception_Table>(count);

        for (int i = 0; i < count; ++i) {
            Exception_Table tmp = new Exception_Table();
            tmp.start_pc = ClassFile_Helper.readShort(in);
            tmp.end_pc = ClassFile_Helper.readShort(in);
            tmp.handler_pc = ClassFile_Helper.readShort(in);
            tmp.catch_type = ClassFile_Helper.readShort(in);
            exceptionTable.add(tmp);
        }

        return exceptionTable;
    }
}

class Exception_Table {
    public short start_pc;
    public short end_pc;
    public short handler_pc;
    public short catch_type;

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("Exception_table:\n");
        str.append(" - start_pc = " + start_pc + "\n");
        str.append(" - end_pc = " + end_pc + "\n");
        str.append(" - handler_pc = " + handler_pc + "\n");
        str.append(" - catch_type = " + catch_type);

        return str.toString();
    }
}