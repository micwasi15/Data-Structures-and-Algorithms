package com.company;

import java.util.Iterator;

public class Merger {
    public static OneWayLinkedList<Integer> merge(
            OneWayLinkedList<Integer> list1,
            OneWayLinkedList<Integer> list2) {
        OneWayLinkedList<Integer> mergedList = new OneWayLinkedList<>();
        Iterator<Integer> i1 = list1.iterator();
        Iterator<Integer> i2 = list2.iterator();
        int value1;
        int value2;

        if (i1.hasNext() && i2.hasNext()) {
            value1 = i1.next();
            value2 = i2.next();
            while (i1.hasNext() && i2.hasNext()) {
                if (value1 > value2) {
                    mergedList.add(value2);
                    value2 = i2.next();
                } else {
                    mergedList.add(value1);
                    value1 = i1.next();
                }
            }
            if (value1 > value2) {
                mergedList.add(value2);
                mergedList.add(value1);
            } else {
                mergedList.add(value1);
                mergedList.add(value2);
            }
        }

        while (i1.hasNext())
            mergedList.add(i1.next());
        while (i2.hasNext())
            mergedList.add(i2.next());

        return mergedList;
    }
}
