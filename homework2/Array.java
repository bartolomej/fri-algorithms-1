package homework2;

import java.util.Arrays;
import java.util.Objects;

public class Array<T> {

    public static final int DEFAULT_CAPACITY = 64;
    private Object[] array;
    private int length;

    public Array() {
        this(DEFAULT_CAPACITY);
    }

    public Array(int maxSize) {
        this.array = new Object[maxSize];
        this.length = 0;
    }

    public Array(T[] array) {
        this.array = array;
        this.length = array.length;
    }

    @SuppressWarnings("unchecked")
    public T get(int i) {
        return (T) this.array[i];
    }

    public void set(int i, T x) {
        this.array[i] = x;
    }

    public void add(T x) {
        this.insert(this.length, x);
    }

    public void insert(int i, T x) {
        while (i > this.array.length - 1) {
            this.resize();
        }
        int l = i + 1;
        if (l > this.length) {
            this.length = l;
        }
        this.set(i, x);
    }

    public void delete(int i) {
        this.array[i] = null;
    }

    public Array<T> subarray(int start, int end) {
        int length = end - start + 1;
        Array<T> a = new Array<>(length);
        System.arraycopy(this.array, start, a.array, 0, length);
        a.length = length;
        return a;
    }

    public void addAll(Array<T> a) {
        int remainingCapacity = this.array.length - this.length;
        if (remainingCapacity < a.length) {
            this.resize();
        }
        System.arraycopy(a.array, 0, this.array, this.length, a.length);
        this.length += a.length;
    }

    public int size() {
        return this.length;
    }

    private void resize() {
        Object[] newArray = new Object[this.array.length * 2];
        System.arraycopy(this.array, 0, newArray, 0, this.array.length);
        this.array = newArray;
    }

    @Override
    protected Array<T> clone() {
        return this.subarray(0, this.size() - 1);
    }

    @Override
    public String toString() {
        // clip internal array, so that null elements are ignored
        Array<T> clipped = this.clone();
        return Arrays.toString(clipped.array);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Array)) {
            return false;
        }
        Array<?> array1 = (Array<?>) o;
        return this.size() == array1.size() && Arrays.equals(this.clone().array, array1.clone().array);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(length);
        result = 31 * result + Arrays.hashCode(array);
        return result;
    }
}