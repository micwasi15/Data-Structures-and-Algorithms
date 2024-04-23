package com.company;

import com.company.exceptions.*;

public class TwoWayLinkedListQueue<T> implements IQueue<T> {
    private TwoWayLinkedList<T> list;
    private int capacity;
    private int size;

    public TwoWayLinkedListQueue(int capacity) {
        list = new TwoWayLinkedList<>();
        this.capacity = capacity;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    @Override
    public boolean isFull() {
        return size == capacity;
    }

    @Override
    public void enqueue(T value) throws FullQueueException {
        if (isFull())
            throw new FullQueueException();
        list.add(value);
        size++;
    }

    @Override
    public T first() throws EmptyQueueException {
        if (isEmpty())
            throw new EmptyQueueException();
        return list.get(0);
    }

    @Override
    public T dequeue() throws EmptyQueueException {
        if (isEmpty())
            throw new EmptyQueueException();
        T value = list.removeAt(0);
        size--;
        return value;
    }

    @Override
    public int size() {
        return size;
    }
}
