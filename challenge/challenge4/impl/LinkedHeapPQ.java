package challenge4.impl;

import challenge4.interfaces.CollectionException;
import challenge4.interfaces.PriorityQueue;

class HeapNode<T> {
    T item;
    HeapNode<T> left;
    HeapNode<T> right;
}

// explicit max pseudo-heap with Node objects (represent nodes of a binary tree)
public class LinkedHeapPQ<T extends Comparable<T>> implements PriorityQueue<T> {

    private HeapNode<T> root;

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public T front() throws CollectionException {
        return null;
    }

    @Override
    public void enqueue(T x) {

    }

    @Override
    public T dequeue() throws CollectionException {
        return null;
    }

    private void siftUp() {
    }
}