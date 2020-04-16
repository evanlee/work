package com.example.demo.demo;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;


public class Compare {
    public static void main(String[] args) {
        TreeMap<String, Integer> map = new TreeMap<String, Integer>(new xbComparator());
        map.put("key_1", 1);
        map.put("key_2", 2);
        map.put("key_3", 3);
        Set<String> keys = map.keySet();
        Iterator<String> iter = keys.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            System.out.println(" " + key + ":" + map.get(key));
        }
    }
}

class xbComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        String i1 = (String) o1;
        String i2 = (String) o2;
        return -i1.compareTo(i2);
    }
}