package com.company;

import java.util.Iterator;

public class Distincter {
    public static TwoWayLinkedList<Integer> distinct(TwoWayLinkedList<Integer> list) {
        Iterator<Integer> i = list.iterator();
        TwoWayLinkedList<Integer> newList = new TwoWayLinkedList<>();
        int previousValue;
        int value;
        if (i.hasNext()) {
            previousValue = i.next();
            newList.add(previousValue);
            while (i.hasNext()) {
                value = i.next();
                if (value != previousValue) {
                    newList.add(value);
                    previousValue = value;
                }
            }
        }
        return newList;
        // TODO: Zwróć nową listę zawierającą unikalne wartości w liście źródłowej.
        // Możesz założyć, że lista na wejściu jest posortowana.
        // Przykład: [1, 1, 2, 3, 3] -> [1, 2, 3]
    }
}
