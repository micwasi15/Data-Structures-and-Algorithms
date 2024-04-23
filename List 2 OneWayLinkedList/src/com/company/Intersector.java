package com.company;

import java.util.Iterator;

public class Intersector {
    public static OneWayLinkedList<Integer> intersect(
            OneWayLinkedList<Integer> list1,
            OneWayLinkedList<Integer> list2) {
        OneWayLinkedList<Integer> intersectedList = new OneWayLinkedList<>();
        Iterator<Integer> i1 = list1.iterator();
        Iterator<Integer> i2 = list2.iterator();
        int value1;
        int value2;

        if (i1.hasNext() && i2.hasNext()) {
            value1 = i1.next();
            value2 = i2.next();
            while (i1.hasNext() && i2.hasNext()) {
                if (value1 == value2) {
                    intersectedList.add(value1);
                    value1 = i1.next();
                    value2 = i2.next();
                } else if (value1 > value2) {
                    value2 = i2.next();
                } else {
                    value1 = i1.next();
                }
            }
            if (value1 == value2)
                intersectedList.add(value1);
            else if (value1 < value2)
                while (i1.hasNext() && value1 < value2) {
                    value1 = i1.next();
                    if (value1 == value2)
                        intersectedList.add(value1);
                }
            else
                while (i2.hasNext() && value1 > value2) {
                    value2 = i2.next();
                    if (value1 == value2)
                        intersectedList.add(value1);
                }
        }
        return intersectedList;
    }
}
