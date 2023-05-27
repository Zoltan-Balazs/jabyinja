package com.zoltanbalazs;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Exception_Table_Helper {
    /***
     * Reads the exception table from a class file
     * 
     * @param in    The class file as an input stream
     * @param count The number of exceptions
     * @return The exception table
     * @throws IOException If an I/O error occurs
     */
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
        return "Exception_table: " + start_pc + " - " + end_pc + " - " + handler_pc + " - " + catch_type;
    }
}