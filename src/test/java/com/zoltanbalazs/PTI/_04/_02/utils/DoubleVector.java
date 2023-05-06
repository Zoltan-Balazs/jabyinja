package com.zoltanbalazs.PTI._04._02.utils;

public class DoubleVector {
    private double[] nums;

    public DoubleVector(double[] arr) {
        nums = arr.clone();
    }

    public DoubleVector add(double[] other) {
        DoubleVector res = new DoubleVector(nums);
        if (nums.length != other.length) {
            throw new IllegalArgumentException("Both vectors must be the same size!");
        }

        for (int i = 0; i < nums.length; ++i) {
            res.nums[i] += other[i];
        }

        return res;
    }

    public DoubleVector subtract(double[] other) {
        DoubleVector res = new DoubleVector(nums);
        if (nums.length != other.length) {
            throw new IllegalArgumentException("Both vectors must be the same size!");
        }

        for (int i = 0; i < nums.length; ++i) {
            res.nums[i] -= other[i];
        }

        return res;
    }

    public double vectorMultiply(double[] other) {
        DoubleVector res = new DoubleVector(nums);
        if (nums.length != other.length) {
            throw new IllegalArgumentException("Both vectors must be the same size!");
        }

        double sum = 0;
        for (int i = 0; i < nums.length; ++i) {
            sum += res.nums[i] * other[i];
        }

        return sum;
    }

    public DoubleVector scalarMultiply(double n) {
        DoubleVector res = new DoubleVector(nums);
        for (int i = 0; i < nums.length; ++i) {
            res.nums[i] *= n;
        }

        return res;
    }

    @Override
    public String toString() {
        StringBuilder bld = new StringBuilder().append("<");
        for (int i = 0; i < nums.length - 1; ++i) {
            bld.append(nums[i] + ", ");
        }
        bld.append(nums[nums.length - 1] + ">");

        return bld.toString();
    }
}