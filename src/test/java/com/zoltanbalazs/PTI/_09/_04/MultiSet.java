package com.zoltanbalazs.PTI._09._04;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MultiSet {
    private Map<String, Integer> data;

    public MultiSet() {
        this.data = new HashMap<>();
    }

    public MultiSet(Map<String, Integer> in) {
        in.forEach((k, v) -> this.data.put(k, v));
    }

    public Map<String, Integer> getData() {
        return new HashMap<>(this.data);
    }

    public void put(String key) {
        if (this.data.containsKey(key)) {
            this.data.put(key, this.data.get(key) + 1);
        } else {
            this.data.put(key, 1);
        }
    }

    /*
     * Csak kényelmi indokból vezettem ezt be, amúgy az intersectben
     * egy for ciklust kéne elvégezni annyiszor amennyi darabunk van
     * az adott elemből.
     */
    public void put(String key, int count) {
        this.data.put(key, count);
    }

    public MultiSet intersect(MultiSet other) {
        MultiSet res = new MultiSet();

        if (other.data.size() > this.data.size()) {
            for (Entry<String, Integer> e : other.data.entrySet()) {
                String key = e.getKey();
                if (this.data.containsKey(key)) {
                    res.put(key, Math.min(other.data.get(key), this.data.get(key)));
                }
            }
        } else {
            for (Entry<String, Integer> e : this.data.entrySet()) {
                String key = e.getKey();
                if (other.data.containsKey(key)) {
                    res.put(key, Math.min(other.data.get(key), this.data.get(key)));
                }
            }
        }

        return res;
    }
}
