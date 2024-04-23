package com.company;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class OneWayLinkedList<T> implements IList<T> {
    private Element head;
    private Element tail;
    private int size = 0;

    @Override
    public void add(T value) {
        size++;
        if (head == null) {
            head = new Element(value);
            tail = head;
        } else {
            Element newElem = new Element(value);
            tail.setNext(newElem);
            tail = newElem;
        }
    }

    @Override
    public void addAt(int index, T value) throws NoSuchElementException {
        if (index < 0) throw new NoSuchElementException();
        if (index == size)
            add(value);
        else {
            Element newElement = new Element(value);
            if (index == 0) {
                newElement.setNext(head);
                head = newElement;
            } else {
                Element actElement = getElement(index - 1);
                newElement.setNext(actElement.getNext());
                actElement.setNext(newElement);
            }
            size++;
        }
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean contains(T value) {
        return indexOf(value) >= 0;
    }

    @Override
    public T get(int index) throws NoSuchElementException {
        return getElement(index).getValue();
    }

    public Element getElement(int index) throws NoSuchElementException {
        if (index < 0) throw new NoSuchElementException();
        Element actElement = head;
        while (index > 0 && actElement != null) {
            index--;
            actElement = actElement.getNext();
        }
        if (actElement == null) throw new NoSuchElementException();
        return actElement;
    }

    @Override
    public void set(int index, T value) throws NoSuchElementException {
        getElement(index).setValue(value);
    }

    @Override
    public int indexOf(T value) {
        int pos = 0;
        Element actElement = head;
        while (actElement != null) {
            if (actElement.getValue() == value)
                return pos;
            pos++;
            actElement = actElement.getNext();
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public T removeAt(int index) throws NoSuchElementException {
        if (index < 0 || head == null) throw new NoSuchElementException();
        Element ret;
        if (index == 0) {
            ret = head;
            head = head.getNext();
            if (ret == tail)
                tail = null;
            return ret.getValue();
        }
        Element actElement = getElement(index - 1);
        if (actElement.getNext() == null)
            throw new IndexOutOfBoundsException();
        ret = actElement.getNext();
        actElement.setNext(actElement.getNext().getNext());
        if (ret == tail)
            tail = actElement;
        return ret.getValue();
    }

    @Override
    public boolean remove(T value) {
        if (head == null)
            return false;
        if (head.getValue().equals(value)) {
            if (head == tail)
                tail = null;
            head = head.getNext();
            return true;
        }
        Element actElement = head;
        while (actElement.getNext() != null && !actElement.getNext().getValue().equals(value))
            actElement = actElement.getNext();
        if (actElement.getNext() == null)
            return false;
        if (actElement.getNext() == tail)
            tail = actElement;
        actElement.setNext(actElement.getNext().getNext());
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new OneWayLinkedListIterator();
    }

    private class OneWayLinkedListIterator implements Iterator<T> {
        private Element actElement;

        private OneWayLinkedListIterator() {
            actElement = head;
        }

        @Override
        public boolean hasNext() {
            return actElement != null;
        }

        @Override
        public T next() throws NoSuchElementException {
            if (actElement == null) throw new NoSuchElementException();
            T value = actElement.getValue();
            actElement = actElement.getNext();
            return value;
        }
    }

    private class Element {
        private T value;
        private Element nextElement;

        public Element(T value) {
            this.value = value;
            nextElement = null;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Element getNext() {
            return nextElement;
        }

        public void setNext(Element nextElement) {
            this.nextElement = nextElement;
        }
    }
}
