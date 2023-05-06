package com.zoltanbalazs.PTI._05._02;

public class IntegerMatrix {
    private int rowNum;
    private int colNum;
    private int[][] data;

    public IntegerMatrix(int rowNum, int colNum, int[] linearData) {
        if (rowNum * colNum != linearData.length) {
            throw new IllegalArgumentException("rowNum * colNum != linearData length");
        }

        this.rowNum = rowNum;
        this.colNum = colNum;
        data = new int[rowNum][colNum];
        for (int i = 0; i < rowNum; ++i) {
            for (int j = 0; j < colNum; ++j) {
                data[i][j] = linearData[colNum * i + j];
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder bld = new StringBuilder();

        for (int i = 0; i < rowNum; ++i) {
            for (int j = 0; j < colNum; ++j) {
                bld.append(data[i][j] + ",");
            }
            bld.deleteCharAt(bld.length() - 1);
            bld.append(";");
        }
        bld.deleteCharAt(bld.length() - 1);

        return bld.toString();
    }
}