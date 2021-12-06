package challenge4.impl;

import challenge4.interfaces.Collection;
import challenge4.interfaces.CollectionException;
import challenge4.interfaces.PriorityQueue;

// items are stored in unordered array
public class ArrayPQ<T extends Comparable<T>> implements PriorityQueue<T> {
    private final Sequence<T> items = new Sequence<>();

    @Override
    public boolean isEmpty() {
        return items.size() == 0;
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public T front() throws CollectionException {
        int index = findNextIndex();
        return items.get(index);
    }

    @Override
    public void enqueue(T x) {
        items.add(x);
    }

    @Override
    public T dequeue() throws CollectionException {
        int index = findNextIndex();
        T e = items.get(index);
        items.delete(index);
        return e;
    }

    private int findNextIndex() throws CollectionException {
        checkEmpty();
        int index = 0;
        for (int i = 0; i < items.size(); i++) {
            T a = items.get(i);
            T b = items.get(index);
            if (a != null && b != null && a.compareTo(b) > 0) {
                index = i; // e has a higher priority than max
            }
        }
        return index;
    }

    private void checkEmpty() throws CollectionException {
        if (isEmpty()) {
            throw new CollectionException(Collection.ERR_MSG_EMPTY);
        }
    }
}