package com.zoltanbalazs;

public enum Opcode {
    // TODO: More bytecodes
    NOP(0x00),
    BIPUSH(0x10),
    LDC(0x12),
    RETURN(0xB1),
    GETSTATIC(0xB2),
    INVOKVEVIRTUAL(0xB6);

    public final byte op_code;

    private Opcode(int value) {
        this.op_code = (byte) value;
    }
}