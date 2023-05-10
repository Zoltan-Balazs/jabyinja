package com.zoltanbalazs.PTI._10._03;

import java.util.HashMap;

public class Bag<T> {
    private HashMap<T, Integer> data;

    public Bag() {
        this.data = new HashMap<T, Integer>();
    }

    public void add(T element) {
        if (this.data.containsKey(element)) {
            this.data.put(element, this.data.get(element) + 1);
        } else {
            this.data.put(element, 1);
        }
    }

    public HashMap<T, Integer> getData() {
        return new HashMap<>(this.data);
    }

    public int countOf(T element) {
        if (this.data.containsKey(element)) {
            return this.data.get(element);
        }
        return 0;
    }

    public void remove(T element) throws NotInBagException {
        if (this.data.containsKey(element)) {
            if (this.data.get(element) == 1) {
                this.data.remove(element);
            } else {
                this.data.put(element, this.data.get(element) - 1);
            }
        } else {
            throw new NotInBagException("Element not in bag!");
        }
    }
}

class NotInBagException extends Exception {
    public NotInBagException(String msg) {
        super(msg);
    }
}