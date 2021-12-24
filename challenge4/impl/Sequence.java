package challenge4.impl;

import challenge4.interfaces.SequenceAdt;

public class Sequence<T> implements SequenceAdt<T> {

    public static final int DEFAULT_CAPACITY = 64;
    private int length;
    private Object[] array;

    public Sequence() {
        this(DEFAULT_CAPACITY);
    }

    public Sequence(int maxSize) {
        this.length = 0;
        this.array = new Object[maxSize];
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int i) {
        return (T) this.array[i];
    }

    private void set(int i, T x) {
        this.array[i] = x;
    }

    @Override
    public void insert(int i, T x) {
        while (i > this.array.length - 1) {
            this.resize();
        }
        // extend array length if new index larger than current length
        if (i >= this.length - 1) {
            this.length = i + 1;
        }
        this.set(i, x);
    }

    @Override
    public void add(T x) {
        // insert into the next available space
        this.insert(this.length, x);
    }

    @Override
    public void delete(int i) {
        // shorten array length when removing last element
        if (i == this.length - 1) {
            this.length--;
        }
        this.array[i] = null;
    }

    public int size() {
        return this.length;
    }

    private void resize() {
        Object[] newArray = new Object[this.array.length * 2];
        System.arraycopy(this.array, 0, newArray, 0, this.array.length);
        this.array = newArray;
    }
}