package challenge4.impl;

import challenge4.interfaces.Collection;
import challenge4.interfaces.CollectionException;
import challenge4.interfaces.PriorityQueue;

// implicit max heap with array
public class ArrayHeapPQ<T extends Comparable<T>> implements PriorityQueue<T> {

    private final Sequence<T> items = new Sequence<>();
    private int last = 0;

    @Override
    public boolean isEmpty() {
        return last == 0;
    }

    @Override
    public int size() {
        return last;
    }

    @Override
    public T front() throws CollectionException {
        return items.get(0);
    }

    @Override
    public void enqueue(T x) {
        items.insert(last, x);
        last++;
        siftUp(items.size() - 1);
    }

    @Override
    public T dequeue() throws CollectionException {
        checkEmpty();
        T x = items.get(0);
        last--;
        swap(0, last);
        siftDown(0);
        return x;
    }

    /**
     * DVIGOVANJE ELEMENTA:
     * – skoraj kopica, v kateri le en element
     * – otrok kvari urejenost (glede na starša)
     * – zamenjano ga z njegovim staršem
     * – ponavljamo dokler gre
     */
    private void siftUp(int c) {
        while (c > 0) {
            int p = (c - 1) / 2;
            if (items.get(p).compareTo(items.get(c)) >= 0) {
                break;
            }
            swap(p, c);
            c = p;
        }
    }

    /**
     * UGREZANJE ELEMENTA:
     * - starš na indeksu i kvari urejenost (glede na otroke)
     * – obe poddrevesi na 2i+1 in 2i+2 sta že kopici
     * – zamenjamo ga z večjim (max-kopica) otrokom
     */
    private void siftDown(int p) {
        int c = 2 * p + 1;
        while (c <= last) {
            // primerjavo desni (c+1) in levi (c) element
            if (c + 1 <= last && items.get(c + 1).compareTo(items.get(c)) > 0) {
                c += 1;
            }
            if (items.get(p).compareTo(items.get(c)) >= 0) {
                break;
            }
            swap(p, c);
            p = c;
            c = 2 * p + 1;
        }
    }

    private void swap(int a, int b) {
        T tempB = items.get(b);
        items.insert(b, items.get(a));
        items.insert(a, tempB);
    }

    private void checkEmpty() throws CollectionException {
        if (this.isEmpty()) {
            throw new CollectionException(Collection.ERR_MSG_EMPTY);
        }
    }
}