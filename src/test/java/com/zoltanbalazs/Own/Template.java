package com.zoltanbalazs.Own;

class TemplateTest<T> {
    T obj;

    TemplateTest(T obj) {
        this.obj = obj;
    }

    TemplateTest(Double obj, T obj2) {
        this.obj = obj2;
        System.out.println(obj);
    }

    public T getObject() {
        return this.obj;
    }
}

public class Template {
    public static void main(String[] args) {
        TemplateTest<Integer> iObj = new TemplateTest<Integer>(5.0, 2023);
        System.out.println(iObj.getObject());

        TemplateTest<String> sObj = new TemplateTest<String>("ELTE");
        System.out.println(sObj.getObject());
    }
}